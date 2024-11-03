package project.stock.track.modules.business.issues;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.entity.UserEntity;
import lib.base.backend.modules.security.jwt.repository.UserRepositoryImpl;
import lib.base.backend.persistance.GenericPersistence;
import lombok.RequiredArgsConstructor;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntityPk;
import project.stock.track.app.beans.entity.IssuesMovementsEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueMovementPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementBuyEntityPojo;
import project.stock.track.app.beans.pojos.entity.IssueMovementEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueMovementDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddEditIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.DeleteIssueMovementRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueMovementRequestPojo;
import project.stock.track.app.repository.DollarHistoricalPriceRepositoryImpl;
import project.stock.track.app.repository.IssuesMovementsBuyRepositoryImpl;
import project.stock.track.app.repository.IssuesMovementsRepositoryImpl;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusIssue;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.app.vo.catalogs.CatalogsStaticData.StaticData;
import project.stock.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class IssuesMovementsCrudBusiness extends MainBusiness {
	
	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final UserRepositoryImpl userRepository;
	private final IssuesRepositoryImpl issuesRepository;
	private final IssuesMovementsBuyRepositoryImpl issuesMovementsBuyRepository;
	private final IssuesMovementsRepositoryImpl issuesMovementsRepository;
	private final DollarHistoricalPriceRepositoryImpl dollarHistoricalPriceRepository;
	
	@SuppressWarnings("unchecked")
	private void setManagerIssueIsInvest(IssuesManagerEntity issuesManagerEntity, Integer idIssueMovement, Integer idStatus) {
		
		IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = issuesManagerEntity.getIssuesManagerTrackPropertiesEntity();
		
		if(idStatus.equals(CatalogStatusIssue.ACTIVE)) {
			
			if(issuesManagerTrackPropertiesEntity == null)
				issuesManagerTrackPropertiesEntity = new IssuesManagerTrackPropertiesEntity(issuesManagerEntity.getId());
			
			issuesManagerTrackPropertiesEntity.setIsInvest(true);
		}
		else if(idStatus.equals(CatalogStatusIssue.INACTIVE) &&
				!issuesMovementsRepository.existIssueMovementInvested(issuesManagerEntity.getId().getIdUser(), issuesManagerEntity.getId().getIdIssue(), idIssueMovement)){
				issuesManagerTrackPropertiesEntity.setIsInvest(false);
		}
		
		genericPersistance.save(issuesManagerTrackPropertiesEntity);
	}
	
	@SuppressWarnings("unchecked")
	public void setAddEditIssueMovementBuy(IssuesMovementsBuyEntity issueMovementBuyEntity, Integer idIssueMovement, IssueMovementBuyEntityPojo issueMovementBuyEntityPojo, Integer idTypeCurrency) throws BusinessException {
		
		if (issueMovementBuyEntity == null)
			issueMovementBuyEntity = new IssuesMovementsBuyEntity();
		
		DollarHistoricalPriceEntity dollarHistoricalPriceEntityBuy = dollarHistoricalPriceRepository.findByDate(new Date(issueMovementBuyEntityPojo.getBuyDate()));
		
		if(dollarHistoricalPriceEntityBuy == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgDollarHistoricalPriceBuySellNotFound("buy", dateFormatUtil.formatDate(new Date(issueMovementBuyEntityPojo.getBuyDate()), StaticData.DEFAULT_CURRENCY_FORMAT)));
		
		BigDecimal buyPriceUsd = null;
		BigDecimal buyPriceMxn = null;
		BigDecimal sellPriceUsd = null;
		BigDecimal sellPriceMxn = null;
		
		if(idTypeCurrency == CatalogTypeCurrency.USD) {
			buyPriceUsd = issueMovementBuyEntityPojo.getBuyPrice();
			buyPriceMxn = issueMovementBuyEntityPojo.getBuyPrice().multiply(dollarHistoricalPriceEntityBuy.getPrice());
		}
		else if(idTypeCurrency == CatalogTypeCurrency.MXN) {
			buyPriceUsd = issueMovementBuyEntityPojo.getBuyPrice().divide(dollarHistoricalPriceEntityBuy.getPrice(), 5, RoundingMode.HALF_DOWN);
			buyPriceMxn = issueMovementBuyEntityPojo.getBuyPrice();
		}
		
		if(issueMovementBuyEntityPojo.getSellDate() != null) {
			
			DollarHistoricalPriceEntity dollarHistoricalPriceEntitySell = dollarHistoricalPriceRepository.findByDate(new Date(issueMovementBuyEntityPojo.getSellDate()));
			
			if(dollarHistoricalPriceEntitySell == null)
				throw new BusinessException(CatalogsErrorMessage.getErrorMsgDollarHistoricalPriceBuySellNotFound("sell", dateFormatUtil.formatDate(new Date(issueMovementBuyEntityPojo.getSellDate()), StaticData.DEFAULT_CURRENCY_FORMAT)));
			
			if(idTypeCurrency == CatalogTypeCurrency.USD) {
				sellPriceUsd = issueMovementBuyEntityPojo.getSellPrice();
				sellPriceMxn = issueMovementBuyEntityPojo.getSellPrice().multiply(dollarHistoricalPriceEntitySell.getPrice());
			}
			else if(idTypeCurrency == CatalogTypeCurrency.MXN) {
				sellPriceUsd = issueMovementBuyEntityPojo.getSellPrice().divide(dollarHistoricalPriceEntitySell.getPrice(), 5, RoundingMode.HALF_DOWN);
				sellPriceMxn = issueMovementBuyEntityPojo.getSellPrice();
			}
		}
		
		IssuesMovementsBuyEntityPk pk = new IssuesMovementsBuyEntityPk();
		pk.setBuyTransactionNumber(issueMovementBuyEntityPojo.getBuyTransactionNumber());
		pk.setIdIssueMovement(idIssueMovement);
		
		issueMovementBuyEntity.setId(pk);
		issueMovementBuyEntity.setBuyPrice(buyPriceUsd);
		issueMovementBuyEntity.setBuyPriceMxn(buyPriceMxn);
		issueMovementBuyEntity.setBuyDate(issueMovementBuyEntityPojo.getBuyDate() != null ? new Date(issueMovementBuyEntityPojo.getBuyDate()) : null);
		issueMovementBuyEntity.setBuyDate(issueMovementBuyEntityPojo.getBuyDate() != null ? new Date(issueMovementBuyEntityPojo.getBuyDate()) : null);
		issueMovementBuyEntity.setSellPrice(sellPriceUsd);
		issueMovementBuyEntity.setSellPriceMxn(sellPriceMxn);
		issueMovementBuyEntity.setSellDate(issueMovementBuyEntityPojo.getSellDate() != null ? new Date(issueMovementBuyEntityPojo.getSellDate()) : null);
		issueMovementBuyEntity.setTotalShares(issueMovementBuyEntityPojo.getTotalShares());
		
		genericPersistance.save(issueMovementBuyEntity);
	}
	
	@SuppressWarnings("unchecked")
	public void setAddEditIssueMovement(IssuesMovementsEntity issueMovementEntity, UserEntity userEntity, IssueMovementEntityPojo issueMovementEntityPojo, CrudOptionsEnum crudOptionsEnum, Integer idTypeCurrency) throws BusinessException {
		
		issueMovementEntity.setIdIssue(issueMovementEntityPojo.getIdIssue());
		issueMovementEntity.setIdBroker(issueMovementEntityPojo.getIdBroker());
		issueMovementEntity.setPriceMovement(issueMovementEntityPojo.getPriceMovement());
		issueMovementEntity.setIdStatus(issueMovementEntityPojo.getIdStatus());
		
		if (crudOptionsEnum == CrudOptionsEnum.SAVE) {
			issueMovementEntity.setIdUser(userEntity.getId());
			genericPersistance.save(issueMovementEntity);
		}
		else if (crudOptionsEnum == CrudOptionsEnum.UPDATE) {
			genericPersistance.update(issueMovementEntity);
			issuesMovementsBuyRepository.deleteIssueMovementBuys(issueMovementEntity.getId());
		}
		
		for(IssueMovementBuyEntityPojo issueMovementBuyEntityPojo: issueMovementEntityPojo.getIssueMovementBuysList())
			setAddEditIssueMovementBuy(null, issueMovementEntity.getId(), issueMovementBuyEntityPojo, idTypeCurrency);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetIssueMovementDataPojo executeGetIssueMovement(GetIssueMovementRequestPojo requestPojo) {
		
		IssuesMovementsEntity issueMovement = (IssuesMovementsEntity) genericPersistance.findById(IssuesMovementsEntity.class, requestPojo.getIdIssueMovement());
		
		List<IssueMovementBuyEntityPojo> issueMovementBuysPojos = mapEntityToPojoUtil.mapIssueMovementBuyList(issueMovement.getIssuesMovementsBuys(), requestPojo.getIdTypeCurrency());
		
		IssueMovementPojo issueMovementPojo = new IssueMovementPojo();
		issueMovementPojo.setIdIssueMovement(issueMovement.getId());
		issueMovementPojo.setIdIssue(issueMovement.getIdIssue());
		issueMovementPojo.setIssue(issueMovement.getIssuesManagerEntity().getCatalogIssueEntity().getInitials());
		issueMovementPojo.setIdBroker(issueMovement.getIdBroker());
		issueMovementPojo.setIdStatus(issueMovement.getIdStatus());
		issueMovementPojo.setPriceMovement(issueMovement.getPriceMovement());
		issueMovementPojo.setIssueMovementBuysList(issueMovementBuysPojos);
		
		GetIssueMovementDataPojo dataPojo = new GetIssueMovementDataPojo();
		dataPojo.setIssueMovement(issueMovementPojo);
		
		return dataPojo;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public void executeManageAddEditIssueMovement(AddEditIssueMovementRequestPojo requestPojo, CrudOptionsEnum crudOptionsEnum) throws BusinessException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		CatalogIssuesEntity catalogIssuesEntity = issuesRepository.findByInitials(requestPojo.getIssue());
		
		if (catalogIssuesEntity == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueDescriptionNotRegistered(requestPojo.getIssue()));
		
		IssuesManagerEntityPk pk = new IssuesManagerEntityPk(catalogIssuesEntity.getId(), userEntity.getId());
		
		IssuesManagerEntity issuesManagerEntity = (IssuesManagerEntity) genericPersistance.findById(IssuesManagerEntity.class, pk);
		
		if (issuesManagerEntity == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueManagerNotRegistered());
		
		IssuesMovementsEntity issuesMovementsEntity = crudOptionsEnum == CrudOptionsEnum.SAVE
				? new IssuesMovementsEntity()
				: (IssuesMovementsEntity) genericPersistance.findById(IssuesMovementsEntity.class, requestPojo.getIdIssueMovement());
		
		IssueMovementEntityPojo issueMovementEntityPojo = new IssueMovementEntityPojo();
		issueMovementEntityPojo.setIdIssueMovement(requestPojo.getIdIssueMovement());
		issueMovementEntityPojo.setIdIssue(catalogIssuesEntity.getId());
		issueMovementEntityPojo.setIdBroker(requestPojo.getIdBroker());
		issueMovementEntityPojo.setIdStatus(requestPojo.getIdStatus());
		issueMovementEntityPojo.setIssueMovementBuysList(requestPojo.getIssueMovementBuysList());
		issueMovementEntityPojo.setPriceMovement(requestPojo.getPriceMovement());
		
		setAddEditIssueMovement(issuesMovementsEntity, userEntity, issueMovementEntityPojo, crudOptionsEnum, requestPojo.getIdTypeCurrency());
		genericPersistance.flush();
		
		setManagerIssueIsInvest(issuesManagerEntity, issuesMovementsEntity.getId(), issuesMovementsEntity.getIdStatus());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public void executeInactivateIssueMovement(GetIssueMovementRequestPojo requestPojo) {
		
		IssuesMovementsEntity issueMovementEntity = (IssuesMovementsEntity) genericPersistance.findById(IssuesMovementsEntity.class, requestPojo.getIdIssueMovement());
		issueMovementEntity.setIdStatus(CatalogsEntity.CatalogStatusIssueMovement.INACTIVE);
		
		genericPersistance.update(issueMovementEntity);
		genericPersistance.flush();
		
		setManagerIssueIsInvest(issueMovementEntity.getIssuesManagerEntity(), issueMovementEntity.getId(), issueMovementEntity.getIdStatus());
		
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeDeleteIssueMovement(DeleteIssueMovementRequestPojo requestPojo) {
		
		issuesMovementsRepository.deleteIssueMovements(requestPojo.getIdIssueMovement());
		
	}
}
