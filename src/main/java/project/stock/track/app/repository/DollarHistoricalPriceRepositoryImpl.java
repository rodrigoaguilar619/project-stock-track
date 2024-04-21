package project.stock.track.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity;
import project.stock.track.app.beans.entity.DollarHistoricalPriceEntity_;

@Repository
public class DollarHistoricalPriceRepositoryImpl {
	
	EntityManager em;
	
	@Autowired
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

}
