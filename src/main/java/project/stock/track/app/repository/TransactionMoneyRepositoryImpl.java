package project.stock.track.app.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import project.stock.track.app.beans.entity.CatalogBrokerEntity_;
import project.stock.track.app.beans.entity.TransactionMoneyEntity;
import project.stock.track.app.beans.entity.TransactionMoneyEntity_;
import project.stock.track.app.vo.catalogs.CatalogsEntity.CatalogTypeCurrency;

@Repository
public class TransactionMoneyRepositoryImpl {

	EntityManager em;
	
	public TransactionMoneyRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public BigDecimal findTotalMovementMoney(Integer idMovementType, int idBroker, int idUser) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionMoneyEntity> root = cq.from(TransactionMoneyEntity.class);
		
		Expression<Object> amountSelection = cb.selectCase()
				.when(cb.equal(root.get(TransactionMoneyEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.USD), root.get(TransactionMoneyEntity_.amount))
	            .when(cb.equal(root.get(TransactionMoneyEntity_.catalogBrokerEntity).get(CatalogBrokerEntity_.idTypeCurrency), CatalogTypeCurrency.MXN), root.get(TransactionMoneyEntity_.amountMxn))
	            .otherwise(cb.nullLiteral(BigDecimal.class));
		
		cq.select(cb.sum(amountSelection.as(BigDecimal.class)));
			
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idTypeMovement), idMovementType));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idUser), idUser));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		return result == null ? BigDecimal.valueOf(0) : result;
		
	}
	
	public TransactionMoneyEntity findTransactionMoney(Integer idUser, Integer idIssue, int idBroker, LocalDateTime date, Integer idTypeMovement) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TransactionMoneyEntity> cq = cb.createQuery(TransactionMoneyEntity.class);
		Root<TransactionMoneyEntity> root = cq.from(TransactionMoneyEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idUser), idUser));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idTypeMovement), idTypeMovement));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.dateTransaction).as(LocalDateTime.class), date));
		
		if(idIssue == null)
			predicatesAnd.add(cb.isNull(root.get(TransactionMoneyEntity_.idIssue)));
		else
			predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idIssue), idIssue));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<TransactionMoneyEntity> transactionIssueEntities = em.createQuery(cq).setMaxResults(1).getResultList();
		
		return transactionIssueEntities.isEmpty() ? null : transactionIssueEntities.getFirst();
	}
}
