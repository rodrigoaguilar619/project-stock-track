package project.stock.track.app.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.stock.track.app.beans.entity.TransactionMoneyEntity;
import project.stock.track.app.beans.entity.TransactionMoneyEntity_;

@Repository
public class TransactionMoneyRepositoryImpl {

	EntityManager em;
	
	@Autowired
	public TransactionMoneyRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public BigDecimal getTotalMovementMoney(Integer movementType, int idBroker, int idUser) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> cq = cb.createQuery(BigDecimal.class);
		Root<TransactionMoneyEntity> root = cq.from(TransactionMoneyEntity.class);
		cq.select(cb.sum(root.<BigDecimal>get(TransactionMoneyEntity_.amount)));
			
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idTypeMovement), movementType));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idBroker), idBroker));
		predicatesAnd.add(cb.equal(root.get(TransactionMoneyEntity_.idUser), idUser));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		TypedQuery<BigDecimal> typedQuery = em.createQuery(cq);
		BigDecimal result = typedQuery.getSingleResult();
		return result == null ? BigDecimal.valueOf(0) : result;
		
	}
}
