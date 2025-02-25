package project.stock.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
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
import project.stock.track.app.vo.entities.CatalogStatusIssueMovementEnum;

@Repository
public class IssuesMovementsRepositoryImpl {

	EntityManager em;
	
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
	
	public List<Predicate> buildFiltersPredicateIssuesMovements(CriteriaBuilder cb, Root<IssuesMovementsEntity> root, Join<IssuesMovementsEntity, IssuesManagerEntity> joinIssuesManager, Join<IssuesManagerEntity, CatalogIssuesEntity> joinIssues, Integer idUser, IssuesMovementsFiltersPojo filters) {
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(joinIssuesManager.get(IssuesManagerEntity_.id).get(IssuesManagerEntityPk_.idUser), idUser));
		
		if (filters != null) {
			
			if (filters.getIdSector() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idSector), filters.getIdSector()));
			if (filters.getIdBroker() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idBroker), filters.getIdBroker()));
			if (filters.getIdStatusIssueMovement() != null)
				predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idStatus), filters.getIdStatusIssueMovement()));
			if (filters.getYear() != null) {
				Join<IssuesMovementsEntity, IssuesMovementsBuyEntity> joinBuy = root.join(IssuesMovementsEntity_.issuesMovementsBuys);
				predicatesAnd.add(cb.equal(cb.function("YEAR", Integer.class, joinBuy.get(IssuesMovementsBuyEntity_.buyDate)), filters.getYear()));
			}
			if (filters.getIdIndex() != null)
				predicatesAnd.add(cb.equal(joinIssues.get(CatalogIssuesEntity_.idIndex), filters.getIdIndex()));
			if (filters.getIsSold() != null) {
				Join<IssuesMovementsEntity, IssuesMovementsBuyEntity> joinBuy = root.join(IssuesMovementsEntity_.issuesMovementsBuys);
				if (filters.getIsSold())
					predicatesAnd.add(joinBuy.get(IssuesMovementsBuyEntity_.sellDate).isNotNull());
				else
					predicatesAnd.add(joinBuy.get(IssuesMovementsBuyEntity_.sellDate).isNull());
			}
		}
		
		return predicatesAnd;
	}
	
	public Predicate buildPredicateIssuesMovements(CriteriaBuilder cb, Root<IssuesMovementsEntity> root, Integer idUser, IssuesMovementsFiltersPojo filters, boolean isCountRows) {
		
		Join<IssuesMovementsEntity, IssuesManagerEntity> joinIssuesManager = root.join(IssuesMovementsEntity_.issuesManagerEntity, JoinType.LEFT);
		Join<IssuesManagerEntity, CatalogIssuesEntity> joinIssues = joinIssuesManager.join(IssuesManagerEntity_.catalogIssueEntity, JoinType.LEFT);
		
		if(!isCountRows) {
			root.fetch(IssuesMovementsEntity_.catalogStatusIssueMovementEntity);
			root.fetch(IssuesMovementsEntity_.issuesMovementsBuys);
			
			Fetch<IssuesMovementsEntity, CatalogBrokerEntity> fetchBroker = root.fetch(IssuesMovementsEntity_.catalogBrokerEntity);
			Fetch<IssuesMovementsEntity, IssuesManagerEntity> fetchIssuesManager = root.fetch(IssuesMovementsEntity_.issuesManagerEntity);
			Fetch<IssuesManagerEntity, CatalogIssuesEntity> fetchIssues = fetchIssuesManager.fetch(IssuesManagerEntity_.catalogIssueEntity);
			fetchIssuesManager.fetch(IssuesManagerEntity_.issuesManagerTrackPropertiesEntity);
			fetchIssues.fetch(CatalogIssuesEntity_.catalogSectorEntity);
			fetchIssues.fetch(CatalogIssuesEntity_.tempIssuesLastPriceEntity, JoinType.LEFT);
			fetchBroker.fetch(CatalogBrokerEntity_.catalogTypeCurrencyEntity);
		}
		
		List<Predicate> predicatesAnd = buildFiltersPredicateIssuesMovements(cb, root, joinIssuesManager, joinIssues, idUser, filters);
		
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
		
		cq.orderBy(cb.asc(root.get(IssuesMovementsEntity_.issuesManagerEntity).get(IssuesManagerEntity_.catalogIssueEntity).get(CatalogIssuesEntity_.initials)));
		
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
		predicatesAnd.add(cb.equal(root.get(IssuesMovementsEntity_.idStatus), CatalogStatusIssueMovementEnum.ACTIVE.getValue()));
		predicatesAnd.add(cb.notEqual(root.get(IssuesMovementsEntity_.id), idIssueMovement));
		
		cq.select(cb.count(root)).where(predicatesAnd.toArray(new Predicate[0]));
		
		return em.createQuery(cq).getSingleResult() > 0;
	}
	
	public void deleteIssueMovements(Integer idIssueMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<IssuesMovementsBuyEntity> delete = cb.createCriteriaDelete(IssuesMovementsBuyEntity.class);
		Root<IssuesMovementsBuyEntity> root = delete.from(IssuesMovementsBuyEntity.class);
		delete.where(cb.equal(root.get(IssuesMovementsBuyEntity_.id).get(IssuesMovementsBuyEntityPk_.idIssueMovement), idIssueMovement));
		this.em.createQuery(delete).executeUpdate();
		
		CriteriaBuilder cb2 = em.getCriteriaBuilder();
		CriteriaDelete<IssuesMovementsEntity> delete2 = cb2.createCriteriaDelete(IssuesMovementsEntity.class);
		Root<IssuesMovementsEntity> root2 = delete2.from(IssuesMovementsEntity.class);
		delete2.where(cb2.equal(root2.get(IssuesMovementsEntity_.id), idIssueMovement));
		this.em.createQuery(delete2).executeUpdate();
	}
	
}
