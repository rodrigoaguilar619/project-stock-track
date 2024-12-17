package project.stock.track.app.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import project.stock.track.app.beans.entity.CatalogIndexEntity_;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.CatalogSectorEntity_;
import project.stock.track.app.beans.entity.CatalogStatusIssueEntity_;
import project.stock.track.app.beans.entity.CatalogTypeStockEntity_;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntityId_;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity_;
import project.stock.track.app.beans.pojos.business.issues.IssuesFiltersPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;

@Repository
public class IssuesRepositoryImpl {

	EntityManager em;
	
	public IssuesRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public List<CatalogIssuesEntity> findAll(Integer idStatusIssue, Integer idTypeStock) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CatalogIssuesEntity> cq = cb.createQuery(CatalogIssuesEntity.class);
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);
		
		cq.groupBy(root.get(CatalogIssuesEntity_.id));
		
		root.fetch(CatalogIssuesEntity_.tempIssuesLastPriceEntity, JoinType.LEFT);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.idStatusIssue), idStatusIssue));
		predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.idTypeStock), idTypeStock));
		
		cq.where(predicatesAnd.toArray(new Predicate[0]));

		return em.createQuery(cq).getResultList();
	}

	public List<CatalogIssuesEntity> findIssues(IssuesFiltersPojo filtersPojo) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CatalogIssuesEntity> cq = cb.createQuery(CatalogIssuesEntity.class);
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);

		root.fetch(CatalogIssuesEntity_.catalogTypeStockEntity, JoinType.LEFT);
		root.fetch(CatalogIssuesEntity_.catalogSectorEntity, JoinType.LEFT);
		root.fetch(CatalogIssuesEntity_.catalogStatusIssueEntity, JoinType.LEFT);
		root.fetch(CatalogIssuesEntity_.tempIssuesLastPriceEntity, JoinType.LEFT);

		List<Predicate> predicatesAnd = new ArrayList<>();

		if (filtersPojo != null) {

			if (filtersPojo.getIdStatusIssue() != null)
				predicatesAnd.add(cb.equal(
						root.get(CatalogIssuesEntity_.catalogStatusIssueEntity).get(CatalogStatusIssueEntity_.ID),
						filtersPojo.getIdStatusIssue()));
			if (filtersPojo.getIdSector() != null)
				predicatesAnd
						.add(cb.equal(root.get(CatalogIssuesEntity_.catalogSectorEntity).get(CatalogSectorEntity_.ID),
								filtersPojo.getIdSector()));
			if (filtersPojo.getIdTypeStock() != null)
				predicatesAnd.add(
						cb.equal(root.get(CatalogIssuesEntity_.catalogTypeStockEntity).get(CatalogTypeStockEntity_.ID),
								filtersPojo.getIdTypeStock()));
			if (filtersPojo.getIdIndex() != null)
				predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.catalogIndexEntity).get(CatalogIndexEntity_.ID),
						filtersPojo.getIdIndex()));
		}

		cq.where(predicatesAnd.toArray(new Predicate[0]));
		return em.createQuery(cq).getResultList();
	}
	
	public CatalogIssuesEntity findByInitials(String issueInitials) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<CatalogIssuesEntity> cq = cb.createQuery(CatalogIssuesEntity.class);
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.initials), issueInitials));
		cq.where( predicatesAnd.toArray(new Predicate[0]) );

		List<CatalogIssuesEntity> issuesManagerEntities = em.createQuery(cq).getResultList();
		
		return issuesManagerEntities != null && !issuesManagerEntities.isEmpty() ? issuesManagerEntities.get(0) : null;
	}

	public boolean existsIssue(Integer idIssue) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);

		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.ID), idIssue));

		cq.select(cb.count(root)).where(predicatesAnd.toArray(new Predicate[0]));
		return em.createQuery(cq).getSingleResult() > 0;
	}
	
	public boolean existsIssueByInitials(String initials) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(CatalogIssuesEntity_.initials), initials));
		
		cq.select(cb.count(root)).where(predicatesAnd.toArray(new Predicate[0]));
		return em.createQuery(cq).getSingleResult() > 0;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CatalogIssuesEntity> findAllWithStatusActive(boolean isGetWithHistoricalNull) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> cq = cb.createTupleQuery();
		Root<CatalogIssuesEntity> root = cq.from(CatalogIssuesEntity.class);
		
		Subquery sub = cq.subquery(LocalDateTime.class);
		Root<IssuesHistoricalEntity> subRoot = sub.from(IssuesHistoricalEntity.class);
		sub.select(cb.max(subRoot.get(IssuesHistoricalEntity_.issuesHistoricalEntityId).get(IssuesHistoricalEntityId_.ID_DATE)));
		sub.where(cb.equal(subRoot.get(IssuesHistoricalEntity_.issuesHistoricalEntityId).get(IssuesHistoricalEntityId_.ID_ISSUE), root.get(CatalogIssuesEntity_.ID)));
		
		Join<CatalogIssuesEntity, IssuesHistoricalEntity> joinIssuesManager = root.join(CatalogIssuesEntity_.issuesHistoricalEntities, JoinType.LEFT);
		root.fetch(CatalogIssuesEntity_.TEMP_ISSUES_LAST_PRICE_ENTITY, JoinType.LEFT);
		cq.multiselect(root, joinIssuesManager);
		
		List<Predicate> predicatesOr = new ArrayList<>();
		
		if(isGetWithHistoricalNull)
			predicatesOr.add(cb.isNull(sub));
		else
			predicatesOr.add(cb.equal(joinIssuesManager.get(IssuesHistoricalEntity_.issuesHistoricalEntityId).get(IssuesHistoricalEntityId_.idDate), sub));
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.and(cb.equal(root.get(CatalogIssuesEntity_.idStatusIssue), CatalogsEntity.CatalogStatusIssue.ACTIVE), cb.or(predicatesOr.toArray(new Predicate[0]))));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );

		List<Tuple> issuesManagerEntitiesTuple = em.createQuery(cq).getResultList();
		
		List<CatalogIssuesEntity> catalogIssuesEntities = new ArrayList<>();
		
		for (Tuple tuple: issuesManagerEntitiesTuple) {
			
			CatalogIssuesEntity catalogIssuesEntity = (CatalogIssuesEntity) tuple.get(0);
			List<IssuesHistoricalEntity> issuesHistoricalEntities = new ArrayList<>();
			issuesHistoricalEntities.add((IssuesHistoricalEntity) tuple.get(1));
			catalogIssuesEntity.setIssuesHistoricalEntities(issuesHistoricalEntities);
			catalogIssuesEntities.add(catalogIssuesEntity);
		}
		
		return catalogIssuesEntities;
	}
	
	public List<CatalogIssuesEntity> findAllWithStatusActive() {
		 return Stream.concat(findAllWithStatusActive(true).stream(), findAllWithStatusActive(false).stream()).toList();
	}

}
