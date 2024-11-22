package project.stock.track.app.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.Root;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntityPk_;
import project.stock.track.app.beans.entity.IssuesMovementsBuyEntity_;

@Repository
public class IssuesMovementsBuyRepositoryImpl {

	EntityManager em;
	
	public IssuesMovementsBuyRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	public void deleteIssueMovementBuys(Integer idIssueMovement) {

		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaDelete<IssuesMovementsBuyEntity> delete = cb.createCriteriaDelete(IssuesMovementsBuyEntity.class);
		Root<IssuesMovementsBuyEntity> root = delete.from(IssuesMovementsBuyEntity.class);
		delete.where(cb.equal(root.get(IssuesMovementsBuyEntity_.id).get(IssuesMovementsBuyEntityPk_.idIssueMovement),
				idIssueMovement));

		this.em.createQuery(delete).executeUpdate();
	}
}
