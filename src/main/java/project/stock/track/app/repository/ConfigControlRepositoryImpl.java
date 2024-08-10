package project.stock.track.app.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lib.base.backend.modules.security.jwt.entity.ConfigControlEntity;
import lib.base.backend.modules.security.jwt.entity.ConfigControlEntity_;

@Repository
public class ConfigControlRepositoryImpl {

	EntityManager em;
	
	@Autowired
	public ConfigControlRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public ConfigControlEntity getParameterValue(String reference) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ConfigControlEntity> cq = cb.createQuery(ConfigControlEntity.class);
		Root<ConfigControlEntity> root = cq.from(ConfigControlEntity.class);
				
		List<Predicate> predicatesAnd = new ArrayList<>();
		predicatesAnd.add(cb.like(root.get(ConfigControlEntity_.reference), reference));
		
		cq.where( predicatesAnd.toArray(new Predicate[0]) );
		
		List<ConfigControlEntity> resultList = em.createQuery(cq).setMaxResults(1).getResultList();
		return (resultList.isEmpty()) ? null : resultList.get(0);

	}
}
