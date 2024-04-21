package project.stock.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.CatalogSectorEntity_;
import project.stock.track.app.beans.entity.CatalogStatusIssueEntity_;
import project.stock.track.app.beans.entity.CatalogTypeStockEntity_;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk_;
import project.stock.track.app.beans.entity.IssuesManagerEntity_;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity_;
import project.stock.track.app.beans.pojos.business.issues.IssuesManagerFiltersPojo;

@Repository
public class IssuesManagerRepositoryImpl {

	EntityManager em;
	
	@Autowired
	public IssuesManagerRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	public List<IssuesManagerEntity> findIssuesManagerList(Integer idUser, IssuesManagerFiltersPojo filtersPojo) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesManagerEntity> cq = cb.createQuery(IssuesManagerEntity.class);
		Root<IssuesManagerEntity> root = cq.from(IssuesManagerEntity.class);
		
		Join<IssuesManagerEntity, CatalogIssuesEntity> joinCatalogIssues = root.join(IssuesManagerEntity_.catalogIssueEntity, JoinType.LEFT);
		Join<IssuesManagerEntity, IssuesManagerTrackPropertiesEntity> joinManagerIsseTrackPropertiesIssues = root.join(IssuesManagerEntity_.issuesManagerTrackPropertiesEntity, JoinType.LEFT);
		Fetch<IssuesManagerEntity, CatalogIssuesEntity> fetchCatalogIssues = root.fetch(IssuesManagerEntity_.catalogIssueEntity, JoinType.LEFT);
		
		fetchCatalogIssues.fetch(CatalogIssuesEntity_.catalogTypeStockEntity, JoinType.LEFT);
		fetchCatalogIssues.fetch(CatalogIssuesEntity_.catalogSectorEntity, JoinType.LEFT);
		root.fetch(IssuesManagerEntity_.issuesManagerTrackPropertiesEntity, JoinType.LEFT);
		root.fetch(IssuesManagerEntity_.catalogStatusIssueQuickEntity, JoinType.LEFT);
		root.fetch(IssuesManagerEntity_.catalogStatusIssueTradingEntity, JoinType.LEFT);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idUser), idUser));
		
		if (filtersPojo != null) {
			
			if (filtersPojo.getIdStatusIssue() != null)
				predicatesAnd.add(cb.equal(joinCatalogIssues.get(CatalogIssuesEntity_.catalogStatusIssueEntity).get(CatalogStatusIssueEntity_.ID), filtersPojo.getIdStatusIssue()));
			if (filtersPojo.getIdSector() != null)
				predicatesAnd.add(cb.equal(joinCatalogIssues.get(CatalogIssuesEntity_.catalogSectorEntity).get(CatalogSectorEntity_.ID), filtersPojo.getIdSector()));
			if (filtersPojo.getIdTypeStock() != null)
				predicatesAnd.add(cb.equal(joinCatalogIssues.get(CatalogIssuesEntity_.catalogTypeStockEntity).get(CatalogTypeStockEntity_.ID), filtersPojo.getIdTypeStock()));
			if (filtersPojo.getIsInvest() != null) {
				
				List<Predicate> predicatesOr = new ArrayList<>();
				predicatesOr.add(cb.equal(joinManagerIsseTrackPropertiesIssues.get(IssuesManagerTrackPropertiesEntity_.isInvest), filtersPojo.getIsInvest()));
				
				if (filtersPojo.getIsInvest() != null && !filtersPojo.getIsInvest())
					predicatesOr.add(cb.isNull(joinManagerIsseTrackPropertiesIssues.get(IssuesManagerTrackPropertiesEntity_.isInvest)));
				
				predicatesAnd.add(cb.or(predicatesOr.toArray(new Predicate[0])));
			}
			if (filtersPojo.getIsSp500() != null)
				predicatesAnd.add(cb.equal(joinCatalogIssues.get(CatalogIssuesEntity_.isSp500), filtersPojo.getIsSp500()));
		}

		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		return em.createQuery(cq).getResultList();
	}

	public boolean existsIssueManager(Integer idIssue, Integer idUser) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<IssuesManagerEntity> root = cq.from(IssuesManagerEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idUser), idUser));
		
		cq.select(cb.count(root)).where(predicatesAnd.toArray(new Predicate[0]));
		return em.createQuery(cq).getSingleResult() > 0;
	}
}
