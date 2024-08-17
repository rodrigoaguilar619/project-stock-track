package project.stock.track.modules.business.issues;

import java.util.ArrayList;
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
import project.stock.track.app.beans.entity.CatalogSectorEntity;
import project.stock.track.app.beans.entity.CatalogStatusIssueEntity;
import project.stock.track.app.beans.entity.CatalogTypeStockEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.pojos.business.issues.IssueManagerResumePojo;
import project.stock.track.app.beans.pojos.business.issues.IssueManagerTrackResumePojo;
import project.stock.track.app.beans.pojos.business.issues.UpdateIssueManagerPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesManagerListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueManagerDataPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueManagerRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesManagerListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueManagerRequestPojo;
import project.stock.track.app.repository.IssuesManagerRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.modules.business.MainBusiness;

@RequiredArgsConstructor
@Component
public class IssuesManagerBusiness extends MainBusiness {

	@SuppressWarnings("rawtypes")
	private final GenericPersistence genericPersistance;
	private final UserRepositoryImpl userRepository;
	private final IssuesManagerRepositoryImpl issuesManagerRepository;
	
	@SuppressWarnings("unchecked")
	public IssuesManagerEntity setAddUpdateIssueManager(IssuesManagerEntity issuesManagerEntity, UpdateIssueManagerPojo updateIssueManagerEntityPojo, CrudOptionsEnum crudOptionsEnum) {
		
		issuesManagerEntity.setIdStatusIssueQuick(updateIssueManagerEntityPojo.getIdStatusIssueQuick());
		issuesManagerEntity.setIdStatusIssueTrading(updateIssueManagerEntityPojo.getIdStatusIssueTrading());
		
		IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = issuesManagerEntity.getIssuesManagerTrackPropertiesEntity();

		if (issuesManagerTrackPropertiesEntity == null)
			issuesManagerTrackPropertiesEntity = new IssuesManagerTrackPropertiesEntity(issuesManagerEntity.getId());
		
		issuesManagerTrackPropertiesEntity.setFairValue(updateIssueManagerEntityPojo.getFairValue());
		issuesManagerTrackPropertiesEntity.setIsInvest(updateIssueManagerEntityPojo.getIsInvest());
		issuesManagerTrackPropertiesEntity.setTrackBuyPrice(updateIssueManagerEntityPojo.getTrackBuyPrice());
		issuesManagerTrackPropertiesEntity.setTrackSellPrice(updateIssueManagerEntityPojo.getTrackSellPrice());
		
		issuesManagerEntity.setIssuesManagerTrackPropertiesEntity(issuesManagerTrackPropertiesEntity);
		
		if (crudOptionsEnum == CrudOptionsEnum.SAVE) {
			genericPersistance.save(issuesManagerEntity);
			genericPersistance.save(issuesManagerTrackPropertiesEntity);
		}
		else if (crudOptionsEnum == CrudOptionsEnum.UPDATE) {
			genericPersistance.update(issuesManagerEntity);
			genericPersistance.save(issuesManagerTrackPropertiesEntity);
		}
		
		return issuesManagerEntity;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetIssuesManagerListDataPojo executeGetIssuesManagerList(GetIssuesManagerListRequestPojo requestPojo) {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		List<IssuesManagerEntity> issuesEntities = issuesManagerRepository.findIssuesManagerList(userEntity.getId(), requestPojo.getFilters());
		List<IssueManagerResumePojo> issueEntityPojos = new ArrayList<>();
		IssueManagerResumePojo issueEntityPojo;
		
		for (IssuesManagerEntity issueManagerEntity: issuesEntities) {
			
			CatalogIssuesEntity catalogIssuesEntity = issueManagerEntity.getCatalogIssueEntity();
			IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = issueManagerEntity.getIssuesManagerTrackPropertiesEntity();
			
			issueEntityPojo = new IssueManagerResumePojo();
			issueEntityPojo.setIdIssue(issueManagerEntity.getId().getIdIssue());
			issueEntityPojo.setInitials(catalogIssuesEntity.getInitials());
			issueEntityPojo.setDescription(catalogIssuesEntity.getDescription());
			issueEntityPojo.setIsSp500(catalogIssuesEntity.getIsSp500());
			issueEntityPojo.setHistoricalStartDate(dataUtil.getValueOrNull(catalogIssuesEntity.getHistoricalStartDate(), Date::getTime));
			issueEntityPojo.setIsInvest(dataUtil.getValueOrNull(issuesManagerTrackPropertiesEntity, IssuesManagerTrackPropertiesEntity::getIsInvest));
			issueEntityPojo.setIdSector(catalogIssuesEntity.getIdSector());
			issueEntityPojo.setIdTypeStock(catalogIssuesEntity.getIdTypeStock());
			issueEntityPojo.setIdStatusIssueQuick(issueManagerEntity.getIdStatusIssueQuick());
			issueEntityPojo.setIdStatusIssueTrading(issueManagerEntity.getIdStatusIssueTrading());
			issueEntityPojo.setIdStatusIssue(catalogIssuesEntity.getIdStatusIssue());
			issueEntityPojo.setDescriptionSector(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogSectorEntity(), CatalogSectorEntity::getDescription));
			issueEntityPojo.setDescriptionStatusIssue(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogStatusIssueEntity(), CatalogStatusIssueEntity::getDescription));
			issueEntityPojo.setDescriptionStatusIssueQuick(dataUtil.getValueOrNull(issueManagerEntity.getCatalogStatusIssueQuickEntity(), CatalogStatusIssueEntity::getDescription));
			issueEntityPojo.setDescriptionStatusIssueTrading(dataUtil.getValueOrNull(issueManagerEntity.getCatalogStatusIssueTradingEntity(), CatalogStatusIssueEntity::getDescription));
			issueEntityPojo.setDescriptionTypeStock(dataUtil.getValueOrNull(catalogIssuesEntity.getCatalogTypeStockEntity(), CatalogTypeStockEntity::getDescription));
			
			issueEntityPojos.add(issueEntityPojo);
		}
		
		GetIssuesManagerListDataPojo dataPojo = new GetIssuesManagerListDataPojo();
		dataPojo.setIssuesManagerList(issueEntityPojos);
		
		return dataPojo;	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public UpdateIssueManagerDataPojo executeUpdateIssueManager(UpdateIssueManagerRequestPojo requestPojo) throws BusinessException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		if (!issuesManagerRepository.existsIssueManager(requestPojo.getIssueManagerData().getIdIssue(), userEntity.getId()))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueNotRegistered());
		
		IssuesManagerEntity issuesManagerEntity = (IssuesManagerEntity) genericPersistance.findById(IssuesManagerEntity.class, new IssuesManagerEntityPk(requestPojo.getIssueManagerData().getIdIssue(), userEntity.getId()));
			
		setAddUpdateIssueManager(issuesManagerEntity, requestPojo.getIssueManagerData(), CrudOptionsEnum.UPDATE);
		
		UpdateIssueManagerDataPojo dataPojo = new UpdateIssueManagerDataPojo();
		dataPojo.setIdIssueManager(issuesManagerEntity.getId().getIdIssue());
		
		return dataPojo;
	}
	
	@SuppressWarnings({ "unchecked" })
	@Transactional(rollbackFor = Exception.class)
	public GetIssueManagerDataPojo executeGetIssueManager(GetIssueManagerRequestPojo requestPojo) throws BusinessException {
		
		UserEntity userEntity = userRepository.findByUserName(requestPojo.getUserName());
		
		IssuesManagerEntityPk pk = new IssuesManagerEntityPk(requestPojo.getIdIssue(), userEntity.getId());
		IssuesManagerEntity issuesManagerEntity = (IssuesManagerEntity) genericPersistance.findById(IssuesManagerEntity.class, pk);
		
		if(issuesManagerEntity == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueNotRegistered());
		
		IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = issuesManagerEntity.getIssuesManagerTrackPropertiesEntity();
		
		CatalogIssuesEntityPojo catalogIssuesEntityPojo = mapEntityToPojoUtil.mapCatalogIssue(issuesManagerEntity.getCatalogIssueEntity(), null);
		IssueManagerTrackResumePojo getIssuePojo = new IssueManagerTrackResumePojo();
		
		getIssuePojo.setIdIssue(issuesManagerEntity.getId().getIdIssue());
		getIssuePojo.setIdStatusIssueQuick(issuesManagerEntity.getIdStatusIssueQuick());
		getIssuePojo.setIdStatusIssueTrading(issuesManagerEntity.getIdStatusIssueTrading());
		
		if (issuesManagerTrackPropertiesEntity != null) {
			getIssuePojo.setFairValue(issuesManagerTrackPropertiesEntity.getFairValue());
			getIssuePojo.setIsInvest(issuesManagerTrackPropertiesEntity.getIsInvest());
			getIssuePojo.setTrackBuyPrice(issuesManagerTrackPropertiesEntity.getTrackBuyPrice());
			getIssuePojo.setTrackSellPrice(issuesManagerTrackPropertiesEntity.getTrackSellPrice());
		}
		
		GetIssueManagerDataPojo dataPojo = new GetIssueManagerDataPojo();
		dataPojo.setIssue(catalogIssuesEntityPojo);
		dataPojo.setIssueManagerTrack(getIssuePojo);
		
		return dataPojo;
	}
}
