package project.stock.track.modules.business.issues;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lib.base.backend.enumerators.CrudOptionsEnum;
import lib.base.backend.exception.data.BusinessException;
import lib.base.backend.modules.security.jwt.entity.UserEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.pojos.business.issues.IssuesElementMultiplePojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityDesPojo;
import project.stock.track.app.beans.pojos.entity.CatalogIssuesEntityPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.data.GetIssuesListDataPojo;
import project.stock.track.app.beans.pojos.petition.data.UpdateIssueDataPojo;
import project.stock.track.app.beans.pojos.petition.request.AddMultipleIssuesRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssueRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.GetIssuesListRequestPojo;
import project.stock.track.app.beans.pojos.petition.request.UpdateIssueRequestPojo;
import project.stock.track.app.repository.IssuesRepositoryImpl;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusQuick;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogStatusTrading;
import project.stock.track.app.vo.catalogs.CatalogsErrorMessage;
import project.stock.track.modules.business.MainBusiness;

@Component
public class IssuesBusiness extends MainBusiness {

	IssuesRepositoryImpl issuesRepository;

	@Autowired
	public IssuesBusiness(IssuesRepositoryImpl issuesRepository) {
		this.issuesRepository = issuesRepository;
	}
	
	@SuppressWarnings("unchecked")
	public CatalogIssuesEntity setAddUpdateIssue(CatalogIssuesEntity catalogIssuesEntity, CatalogIssuesEntityPojo catalogIssuesEntityPojo, CrudOptionsEnum crudOptionsEnum) {
		
		catalogIssuesEntity = mapPojoToEntityUtil.mapCatalogIssue(catalogIssuesEntityPojo, catalogIssuesEntity);
		
		if (crudOptionsEnum == CrudOptionsEnum.SAVE)
			genericPersistance.save(catalogIssuesEntity);
		else if (crudOptionsEnum == CrudOptionsEnum.UPDATE)
			genericPersistance.update(catalogIssuesEntity);
		
		return catalogIssuesEntity;
	}
	
	@SuppressWarnings("unchecked")
	public void addIssuesToUsers(List<UserEntity> userEntities, Integer idIssue) {
		
		for (UserEntity userEntity: userEntities) {
			
			IssuesManagerEntity issuesManagerEntity = new IssuesManagerEntity(idIssue, userEntity.getId());
			issuesManagerEntity.setIdStatusIssueQuick(CatalogStatusQuick.INACTIVE);
			issuesManagerEntity.setIdStatusIssueTrading(CatalogStatusTrading.INACTIVE);

			IssuesManagerTrackPropertiesEntity issuesManagerTrackPropertiesEntity = new IssuesManagerTrackPropertiesEntity();
			issuesManagerTrackPropertiesEntity.setIssuesManagerEntity(issuesManagerEntity);
			issuesManagerTrackPropertiesEntity.setIsInvest(false);
			
			genericPersistance.save(issuesManagerEntity);
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	public GetIssuesListDataPojo executeGetIssuesList(GetIssuesListRequestPojo requestPojo) {
		
		List<CatalogIssuesEntity> issuesEntities = issuesRepository.findIssues(requestPojo.getFilters());
		List<CatalogIssuesEntityDesPojo> issueEntityPojos = new ArrayList<>();
		
		for (CatalogIssuesEntity issuesEntity: issuesEntities) {
			
			CatalogIssuesEntityDesPojo issueEntityPojo = mapEntityToPojoUtil.mapCatalogIssueWithDescriptions(issuesEntity, null);
			issueEntityPojos.add(issueEntityPojo);
		}
		
		GetIssuesListDataPojo dataPojo = new GetIssuesListDataPojo();
		dataPojo.setIssues(issueEntityPojos);
		
		return dataPojo;	
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public UpdateIssueDataPojo executeUpdateIssue(UpdateIssueRequestPojo requestPojo) throws BusinessException {
		
		if (!issuesRepository.existsIssue(requestPojo.getIssueData().getIdIssue()))
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueNotRegistered());
		
		CatalogIssuesEntity catalogIssuesEntity = (CatalogIssuesEntity) genericPersistance.findById(CatalogIssuesEntity.class, requestPojo.getIssueData().getIdIssue());
			
		setAddUpdateIssue(catalogIssuesEntity, requestPojo.getIssueData(), CrudOptionsEnum.UPDATE);
		
		UpdateIssueDataPojo dataPojo = new UpdateIssueDataPojo();
		dataPojo.setIdIssue(catalogIssuesEntity.getId());
		
		return dataPojo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void executeAddMultipleIssues(AddMultipleIssuesRequestPojo requestPojo) throws BusinessException {
		
		List<String> issuesRegitered = new ArrayList<>();
		
		for (IssuesElementMultiplePojo issueElementEntityPojo: requestPojo.getIssues()) {
			
			if (issuesRepository.existsIssueByInitials(issueElementEntityPojo.getInitials()))
				issuesRegitered.add(issueElementEntityPojo.getInitials());
		}
		
		if (!issuesRegitered.isEmpty())
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssuesRegistered(String.join(", ", issuesRegitered)));
		
		List<UserEntity> userEntities = userRepository.findAllStatusActive();
		
		for (IssuesElementMultiplePojo issueElementEntityPojo: requestPojo.getIssues()) {
			
			CatalogIssuesEntityPojo catalogIssuesEntityPojo = new CatalogIssuesEntityPojo();
			
			catalogIssuesEntityPojo.setInitials(issueElementEntityPojo.getInitials());
			catalogIssuesEntityPojo.setDescription(issueElementEntityPojo.getDescription());
			catalogIssuesEntityPojo.setIdTypeStock(requestPojo.getIdTypeStock());
			catalogIssuesEntityPojo.setIdSector(issueElementEntityPojo.getIdSector());
			catalogIssuesEntityPojo.setIdStatusIssue(requestPojo.getIdStatusIssue());
			catalogIssuesEntityPojo.setIsSp500(issueElementEntityPojo.getIsSp500());
			catalogIssuesEntityPojo.setHistoricalStartDate(requestPojo.getHistoricalStartDate());
			
			CatalogIssuesEntity catalogIssuesEntity = setAddUpdateIssue(new CatalogIssuesEntity(), catalogIssuesEntityPojo, CrudOptionsEnum.SAVE);
			
			addIssuesToUsers(userEntities, catalogIssuesEntity.getId());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = Exception.class)
	public GetIssueDataPojo executeGetIssue(GetIssueRequestPojo requestPojo) throws BusinessException {
		
		CatalogIssuesEntity catalogIssuesEntity = (CatalogIssuesEntity) genericPersistance.findById(CatalogIssuesEntity.class, requestPojo.getIdIssue());
		
		if(catalogIssuesEntity == null)
			throw new BusinessException(CatalogsErrorMessage.getErrorMsgIssueNotRegistered());
		
		CatalogIssuesEntityPojo catalogIssuesEntityPojo = mapEntityToPojoUtil.mapCatalogIssue(catalogIssuesEntity, null);
		
		GetIssueDataPojo dataPojo = new GetIssueDataPojo();
		dataPojo.setIssue(catalogIssuesEntityPojo);
		
		return dataPojo;
	}
}
