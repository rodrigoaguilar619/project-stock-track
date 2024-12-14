package project.stock.track.app.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity;
import project.stock.track.app.beans.entity.IssuesLastPriceTmpEntity_;

@Repository
public class TempIssuesLastPriceRepositoryImpl {
	
	EntityManager em;
	
	public TempIssuesLastPriceRepositoryImpl(EntityManager em) {
		this.em = em;
	}
	
	public void deleteTempIssuesLastPrice() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		 
        CriteriaDelete<IssuesLastPriceTmpEntity> delete = cb.createCriteriaDelete(IssuesLastPriceTmpEntity.class);
        Root<IssuesLastPriceTmpEntity> root = delete.from(IssuesLastPriceTmpEntity.class);
        delete.where(cb.greaterThan(root.get(IssuesLastPriceTmpEntity_.idIssue), 0));
        
        this.em.createQuery(delete).executeUpdate();
	}
	
	public List<IssuesLastPriceTmpEntity> findLastPrices(LocalDateTime date) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesLastPriceTmpEntity> cq = cb.createQuery(IssuesLastPriceTmpEntity.class);
		Root<IssuesLastPriceTmpEntity> root = cq.from(IssuesLastPriceTmpEntity.class);
		
		cq.where( cb.greaterThanOrEqualTo(root.get(IssuesLastPriceTmpEntity_.timestamp).as(LocalDateTime.class), date ));
		
		return em.createQuery(cq).getResultList();
	}
	
	public List<IssuesLastPriceTmpEntity> findAll() {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<IssuesLastPriceTmpEntity> cq = cb.createQuery(IssuesLastPriceTmpEntity.class);
		Root<IssuesLastPriceTmpEntity> root = cq.from(IssuesLastPriceTmpEntity.class);
		
		root.fetch(IssuesLastPriceTmpEntity_.catalogIssuesEntity, JoinType.LEFT);
		
		return em.createQuery(cq).getResultList();
	}

}
