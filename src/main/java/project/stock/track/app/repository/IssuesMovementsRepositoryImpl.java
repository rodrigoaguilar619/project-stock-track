package project.stock.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.stock.track.app.beans.entity.CatalogBrokerEntity;
import project.stock.track.app.beans.entity.CatalogBrokerEntity_;
import project.stock.track.app.beans.entity.CatalogIssuesEntity;
import project.stock.track.app.beans.entity.CatalogIssuesEntity_;
import project.stock.track.app.beans.entity.IssuesManagerEntity;
import project.stock.track.app.beans.entity.IssuesManagerEntityPk_;
import project.stock.track.app.beans.entity.IssuesManagerEntity_;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntityPk_;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity_;
import project.stock.track.app.beans.entity.IssuesMovementsEntity;
import project.stock.track.app.beans.entity.IssuesMovementsEntity_;
import project.stock.track.app.beans.pojos.business.issues.IssuesMovementsFiltersPojo;
import project.stock.track.app.vo.catalogs.CatalogsEntity;

@Repository
public class IssuesMovementsRepositoryImpl {

	EntityManager em;
	
	@Autowired
	public IssuesMovementsRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public IssuesMovementsBuyEntity getIssueMovementBuy(Integer idUser, Integer idIssueMovement, Integer idBuyTransaction) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesMovementsBuyEntity> cq = cb.createQuery(IssuesMovementsBuyEntity.class);
		Root<IssuesMovementsBuyEntity> root = cq.from(IssuesMovementsBuyEntity.class);
		
		Join<IssuesMovementsBuyEntity, IssuesMovementsEntity> joinIssuesMovements = root.join(IssuesMovementsBuyEntity_.issuesMovementsEntity, JoinType.LEFT);
		
		List<Predicate> predicatesAnd = new ArrayList<>();

		predicatesAnd.add(cb.equal(joinIssuesMovements.get(IssuesMovementsEntity_.idUser), idUser));
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsBuyEntity_.id).get(IssuesMovementsBuyEntityPk_.buyTransactionNumber), idBuyTransaction));
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsBuyEntity_.id).get(IssuesMovementsBuyEntityPk_.idIssueMovement), idIssueMovement));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<IssuesMovementsBuyEntity> movementsIssueBuyList = em.createQuery(cq).setMaxResults(1).getResultList();

		if (movementsIssueBuyList != null && !movementsIssueBuyList.isEmpty())
			return movementsIssueBuyList.get(0);
		else
			return null;
	}
	
	public Predicate buildPredicateIssuesMovements(CriteriaBuilder cb, Root<?> root, Integer idUser, IssuesMovementsFiltersPojo filters, boolean isCountRows) {
		
		Join<IssuesMovementsEntity, IssuesManagerEntity> joinIssuesManager = root.join(IssuesMovementsEntity_.ISSUES_MANAGER_ENTITY, JoinType.LEFT);
		Join<IssuesManagerEntity, CatalogIssuesEntity> joinIssues = joinIssuesManager.join(IssuesManagerEntity_.CATALOG_ISSUES_ENTITY, JoinType.LEFT);
		
		if(!isCountRows) {
			root.fetch(IssuesMovementsEntity_.CATALOG_STATUS_ISSUE_MOVEMENT_ENTITY);
			root.fetch(IssuesMovementsEntity_.MOVEMENTS_ISSUE_BUYS);
			
			Fetch<IssuesMovementsEntity, CatalogBrokerEntity> fetchBroker = root.fetch(IssuesMovementsEntity_.CATALOG_BROKER_ENTITY);
			Fetch<IssuesMovementsEntity, IssuesManagerEntity> fetchIssuesManager = root.fetch(IssuesMovementsEntity_.ISSUES_MANAGER_ENTITY);
			Fetch<IssuesManagerEntity, CatalogIssuesEntity> fetchIssues = fetchIssuesManager.fetch(IssuesManagerEntity_.CATALOG_ISSUES_ENTITY);
			fetchIssuesManager.fetch(IssuesManagerEntity_.ISSUES_MANAGER_TRACK_PROPERTIES_ENTITY);
			fetchIssues.fetch(CatalogIssuesEntity_.CATALOG_SECTOR_ENTITY);
			fetchIssues.fetch(CatalogIssuesEntity_.TEMP_ISSUES_LAST_PRICE_ENTITY);
			fetchBroker.fetch(CatalogBrokerEntity_.CATALOG_TYPE_CURRENCY_ENTITY);
		}
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(joinIssuesManager.get(IssuesManagerEntity_.ID).get(IssuesManagerEntityPk_.ID_USER), idUser));
		
		if (filters != null) {
			
			if (filters.getIdSector() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.ID_SECTOR), filters.getIdSector()));
			if (filters.getIdBroker() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.ID_BROKER), filters.getIdBroker()));
			if (filters.getIdStatusIssueMovement() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.ID_STATUS), filters.getIdStatusIssueMovement()));
		}
		
		return cb.and(predicatesAnd.toArray(new Predicate[0]));
	}
	
	public Long findCountIssuesMovements(Integer idUser, IssuesMovementsFiltersPojo filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<IssuesMovementsEntity> root = cq.from(IssuesMovementsEntity.class);
		
		Predicate predicateAnd = buildPredicateIssuesMovements(cb, root, idUser, filters, true);
		
		cq.where( predicateAnd );
		cq.groupBy(root.get(IssuesMovementsEntity_.id));
		cq.select(cb.count(root.get(IssuesMovementsEntity_.id)));

		return (long)(em.createQuery(cq).getResultList().size());
	}
	
	public List<IssuesMovementsEntity> findAllIssuesMovements(Integer idUser, IssuesMovementsFiltersPojo filters) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesMovementsEntity> cq = cb.createQuery(IssuesMovementsEntity.class);
		Root<IssuesMovementsEntity> root = cq.from(IssuesMovementsEntity.class);
		
		Predicate predicateAnd = buildPredicateIssuesMovements(cb, root, idUser, filters, false);
		
		cq.where( predicateAnd );

		return em.createQuery(cq).getResultList();
	}
	
	public boolean existIssueMovementInvested(Integer idUser, Integer idIssue, Integer idIssueMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<IssuesMovementsEntity> root = cq.from(IssuesMovementsEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idIssue), idIssue));
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idUser), idUser));
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idStatus), CatalogsEntity.StatusIssueMovement.ACTIVE));
		predicatesAnd.add(cb.notEqual(root.get(IssuesMovementsEntity_.id), idIssueMovement));
		
		cq.select(cb.count(root)).where(predicatesAnd.toArray(new Predicate[0]));
		
		return em.createQuery(cq).getSingleResult() > 0;
	}
	
}
