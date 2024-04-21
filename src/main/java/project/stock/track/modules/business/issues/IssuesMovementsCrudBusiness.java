package project.stock.track.modules.business.issues;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.entity.UserEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
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
import project.stock.track.app.beans.pojos.petition.request.GetIssueMovementRequestPojo;
import project.stock.track.app.repository.IssuesMovementsBuyRepositoryImpl;
import project.stock.track.app.repository.IssuesMovementsRepositoryImpl;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusIssue;
import project.stock.track.modules.business.MainBusiness;

@Component
public class IssuesMovementsCrudBusiness extends MainBusiness {
	
	IssuesRepositoryImpl issuesRepository;
	
	IssuesMovementsBuyRepositoryImpl issuesMovementsBuyRepository;
	
	IssuesMovementsRepositoryImpl issuesMovementsRepository;
	
	@Autowired
	public IssuesMovementsCrudBusiness(IssuesRepositoryImpl issuesRepository, IssuesMovementsBuyRepositoryImpl issuesMovementsBuyRepository, IssuesMovementsRepositoryImpl issuesMovementsRepository) {
		this.issuesRepository = issuesRepository;
		this.issuesMovementsBuyRepository = issuesMovementsBuyRepository;
		this.issuesMovementsRepository = issuesMovementsRepository;
	}
	
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
	public void setAddEditIssueMovementBuy(IssuesMovementsBuyEntity issueMovementBuyEntity, Integer idIssueMovement, IssueMovementBuyEntityPojo issueMovementBuyEntityPojo) {
		
		if (issueMovementBuyEntity == null)
			issueMovementBuyEntity = new IssuesMovementsBuyEntity();
		
		IssuesMovementsBuyEntityPk pk = new IssuesMovementsBuyEntityPk();
		pk.setBuyTransactionNumber(issueMovementBuyEntityPojo.getBuyTransactionNumber());
		pk.setIdIssueMovement(idIssueMovement);
		
		issueMovementBuyEntity.setId(pk);
		issueMovementBuyEntity.setBuyPrice(issueMovementBuyEntityPojo.getBuyPrice());
		issueMovementBuyEntity.setBuyDate(issueMovementBuyEntityPojo.getBuyDate() != null ? new Date(issueMovementBuyEntityPojo.getBuyDate()) : null);
		issueMovementBuyEntity.setSellPrice(issueMovementBuyEntityPojo.getSellPrice());
		issueMovementBuyEntity.setSellDate(issueMovementBuyEntityPojo.getSellDate() != null ? new Date(issueMovementBuyEntityPojo.getSellDate()) : null);
		
		genericPersistance.save(issueMovementBuyEntity);
	}
	
	@SuppressWarnings("unchecked")
	public void setAddEditIssueMovement(IssuesMovementsEntity issueMovementEntity, UserEntity userEntity, IssueMovementEntityPojo issueMovementEntityPojo, CrudOptionsEnum crudOptionsEnum) {
		
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
			setAddEditIssueMovementBuy(null, issueMovementEntity.getId(), issueMovementBuyEntityPojo);
	}

	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetIssueMovementDataPojo executeGetIssueMovement(GetIssueMovementRequestPojo requestPojo) {
		
		IssuesMovementsEntity issueMovement = (IssuesMovementsEntity) genericPersistance.findById(IssuesMovementsEntity.class, requestPojo.getIdIssueMovement());
		
		List<IssueMovementBuyEntityPojo> issueMovementBuysPojos = mapEntityToPojoUtil.mapIssueMovementBuyList(issueMovement.getIssuesMovementsBuys());
		
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
			throw new BusinessException(String.format("Issue %s not exist", requestPojo.getIssue()));
		
		IssuesManagerEntityPk pk = new IssuesManagerEntityPk(catalogIssuesEntity.getId(), userEntity.getId());
		
		IssuesManagerEntity issuesManagerEntity = (IssuesManagerEntity) genericPersistance.findById(IssuesManagerEntity.class, pk);
		
		if (issuesManagerEntity == null)
			throw new BusinessException("Issue not exist");
		
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
		
		setAddEditIssueMovement(issuesMovementsEntity, userEntity, issueMovementEntityPojo, crudOptionsEnum);
		genericPersistance.flush();
		
		setManagerIssueIsInvest(issuesManagerEntity, issuesMovementsEntity.getId(), issuesMovementsEntity.getIdStatus());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public void executeInactivateIssueMovement(GetIssueMovementRequestPojo requestPojo) {
		
		IssuesMovementsEntity issueMovementEntity = (IssuesMovementsEntity) genericPersistance.findById(IssuesMovementsEntity.class, requestPojo.getIdIssueMovement());
		issueMovementEntity.setIdStatus(CatalogsEntity.StatusIssueMovement.INACTIVE);
		
		genericPersistance.update(issueMovementEntity);
		genericPersistance.flush();
		
		setManagerIssueIsInvest(issueMovementEntity.getIssuesManagerEntity(), issueMovementEntity.getId(), issueMovementEntity.getIdStatus());
		
	}
}
