package project.stock.track.app.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity_;

@Repository
public class DollarHistoricalPriceRepositoryImpl {
	
	EntityManager em;
	
	public DollarHistoricalPriceRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public DollarHistoricalPriceEntity findLastRecord() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DollarHistoricalPriceEntity> cq = cb.createQuery(DollarHistoricalPriceEntity.class);
		Root<DollarHistoricalPriceEntity> root = cq.from(DollarHistoricalPriceEntity.class);
		
		cq.orderBy(cb.desc(root.get(DollarHistoricalPriceEntity_.idDate)));
		
		List<DollarHistoricalPriceEntity> list = em.createQuery(cq).setMaxResults(1).getResultList();
		
		return list.isEmpty() ? null : list.get(0);
	}
	
	public DollarHistoricalPriceEntity findByDate(LocalDate date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<DollarHistoricalPriceEntity> cq = cb.createQuery(DollarHistoricalPriceEntity.class);
		Root<DollarHistoricalPriceEntity> root = cq.from(DollarHistoricalPriceEntity.class);
		
		List<Predicate> predicatesAnd = new ArrayList<>();
		
		predicatesAnd.add(cb.equal(root.get(DollarHistoricalPriceEntity_.idDate).as(LocalDate.class), date));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<DollarHistoricalPriceEntity> list = em.createQuery(cq).getResultList();
		
		return list.isEmpty() ? null : list.get(0);
	}
	

}
