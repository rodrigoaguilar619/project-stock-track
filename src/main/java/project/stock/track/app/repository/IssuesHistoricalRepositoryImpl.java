package project.stock.track.app.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity;
import project.stock.track.app.beans.entity.IssuesHistoricalEntityPk_;
import project.stock.track.app.beans.entity.IssuesHistoricalEntity_;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity_;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk_;
import project.stock.track.app.beans.entity.IssuesManagerEntity_;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity;
import project.stock.track.app.beans.entity.IssuesManagerTrackPropertiesEntity_;
import project.stock.track.app.beans.pojos.business.historical.IssuesHistoricalFilterPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;

@Repository
public class IssuesHistoricalRepositoryImpl {
	
	EntityManager em;
	
	public IssuesHistoricalRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public List<IssuesHistoricalEntity> findIssueHistorical(Integer idIssue, LocalDateTime startDate) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesHistoricalEntity> cq = cb.createQuery(IssuesHistoricalEntity.class);
		Root<IssuesHistoricalEntity> root = cq.from(IssuesHistoricalEntity.class);
		
		Predicate predicateAnd = cb.and( 
				cb.equal(root.get(IssuesHistoricalEntity_.id).get(IssuesHistoricalEntityPk_.idIssue), idIssue),
				cb.greaterThanOrEqualTo(root.get(IssuesHistoricalEntity_.id).get(IssuesHistoricalEntityPk_.idDate).as(LocalDateTime.class), startDate));
		
		cq.where( predicateAnd );
		
		return em.createQuery(cq).getResultList();
		
	}
	
	public List<IssuesHistoricalEntity> findIssueHistoricalDateGreater(LocalDateTime startDate) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesHistoricalEntity> cq = cb.createQuery(IssuesHistoricalEntity.class);
		Root<IssuesHistoricalEntity> root = cq.from(IssuesHistoricalEntity.class);
		
		Predicate predicateAnd = cb.and( 
				cb.greaterThanOrEqualTo(root.get(IssuesHistoricalEntity_.id).get(IssuesHistoricalEntityPk_.idDate).as(LocalDateTime.class), startDate));
		
		cq.where( predicateAnd );
		
		return em.createQuery(cq).getResultList();
		
	}
	
	public IssuesHistoricalEntity findLastRecord(Integer idIssue) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesHistoricalEntity> cq = cb.createQuery(IssuesHistoricalEntity.class);
		Root<IssuesHistoricalEntity> root = cq.from(IssuesHistoricalEntity.class);
		
		cq.where(cb.equal(root.get(IssuesHistoricalEntity_.id).get(IssuesHistoricalEntityPk_.idIssue), idIssue));
		cq.orderBy(cb.desc(root.get(IssuesHistoricalEntity_.id).get(IssuesHistoricalEntityPk_.idDate).as(LocalDateTime.class)));
		
		List<IssuesHistoricalEntity> issuesHistoricalEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		return (issuesHistoricalEntities.isEmpty()) ? null : issuesHistoricalEntities.get(0);
		
	}
	
	public void buildPredicateFetchWithStatusActive(Root<IssuesManagerEntity> root) {
		
		Fetch<IssuesManagerEntity, CatalogIssuesEntity> fetchIssues = root.fetch(IssuesManagerEntity_.catalogIssueEntity, JoinType.LEFT);
		fetchIssues.fetch(CatalogIssuesEntity_.tempIssuesLastPriceEntity, JoinType.LEFT);
		fetchIssues.fetch(CatalogIssuesEntity_.catalogSectorEntity, JoinType.LEFT);
		fetchIssues.fetch(CatalogIssuesEntity_.catalogTypeStockEntity, JoinType.LEFT);
		fetchIssues.fetch(CatalogIssuesEntity_.catalogStatusIssueEntity, JoinType.LEFT);
		root.fetch(IssuesManagerEntity_.issuesManagerTrackPropertiesEntity, JoinType.LEFT);
		
	}

	public List<Predicate> buildPredicateWithStatusActive(CriteriaBuilder cb, Root<IssuesManagerEntity> root, IssuesHistoricalFilterPojo filters) {
		
		List<Predicate> predicatesAnd = new ArrayList<>();	
		
		Join<IssuesManagerEntity, CatalogIssuesEntity> joinIssues = root.join(IssuesManagerEntity_.catalogIssueEntity, JoinType.LEFT);
		Join<IssuesManagerEntity, IssuesManagerTrackPropertiesEntity> joinIssuesManagerTrackProperties = root.join(IssuesManagerEntity_.issuesManagerTrackPropertiesEntity, JoinType.LEFT);
		Join<CatalogIssuesEntity, IssuesLastPriceTmpEntity> joinTempIssueLastPrice = joinIssues.join(CatalogIssuesEntity_.tempIssuesLastPriceEntity, JoinType.LEFT);
			
		if (filters != null) {
			
			if (filters.getIdSector() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idSector), filters.getIdSector()));
			if (filters.getIdTypeStock() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idTypeStock), filters.getIdTypeStock()));
			if (filters.getIdIndex() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idIndex), filters.getIdIndex()));
			if (filters.getIsInvest() != null)
				predicatesAnd.add(cb.equal(joinIssuesManagerTrackProperties.get(IssuesManagerTrackPropertiesEntity_.isInvest), filters.getIsInvest()));
			if (filters.getIdStatusQuick() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.idStatusIssueQuick), filters.getIdStatusQuick()));	
			if (filters.getIdStatusTrading() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.idStatusIssueTrading), filters.getIdStatusTrading()));
			if (filters.getFairValuePriceOverPercentage() != null) {
				
				Expression<Number> expresionUnder = cb.diff(joinIssuesManagerTrackProperties.get(IssuesManagerTrackPropertiesEntity_.fairValue), joinTempIssueLastPrice.get(IssuesLastPriceTmpEntity_.last));
				expresionUnder = cb.quot(expresionUnder, joinTempIssueLastPrice.get(IssuesLastPriceTmpEntity_.last));
				expresionUnder = cb.prod(expresionUnder, cb.literal(100));
				
				predicatesAnd.add(cb.ge(expresionUnder, filters.getFairValuePriceOverPercentage()));
			}
		}
		
		predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idStatusIssue), CatalogsEntity.CatalogStatusIssue.ACTIVE));
		
		return predicatesAnd;
	}
	
	public List<IssuesManagerEntity> findAllWithStatusActive(int idUser, int startRow, int totalRow, IssuesHistoricalFilterPojo filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesManagerEntity> cq = cb.createQuery(IssuesManagerEntity.class);
		Root<IssuesManagerEntity> root = cq.from(IssuesManagerEntity.class);
		
		buildPredicateFetchWithStatusActive(root);
		List<Predicate> predicatesAnd = buildPredicateWithStatusActive(cb, root, filters);
		predicatesAnd.add(cb.equal(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idUser), idUser));
		
		cq.where(cb.and(predicatesAnd.toArray(new Predicate[0])));
		cq.groupBy(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idIssue));

		return em.createQuery(cq).setFirstResult(startRow).setMaxResults(totalRow).getResultList();
	}
	
	public Long findCountWithStatusActive(IssuesHistoricalFilterPojo filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<IssuesManagerEntity> root = cq.from(IssuesManagerEntity.class);
		
		List<Predicate> predicatesAnd = buildPredicateWithStatusActive(cb, root, filters);
		
		cq.where(cb.and(predicatesAnd.toArray(new Predicate[0])));
		cq.select(cb.count(root.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idIssue)));

		return em.createQuery(cq).getSingleResult();
		
	}
}
