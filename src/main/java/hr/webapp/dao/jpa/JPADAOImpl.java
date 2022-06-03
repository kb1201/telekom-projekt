package hr.webapp.dao.jpa;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.persistence.Query;
//import jakarta.persistence.criteria.CriteriaBuilder;
//import jakarta.persistence.criteria.CriteriaQuery;
//import jakarta.persistence.criteria.Root;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

//import Criteria
//import org.hibernate.Session;
//import org.hibernate.criterion.Example;

import hr.webapp.dao.DAO;
import hr.webapp.dao.DAOException;
import hr.webapp.models.Delivery;

public class JPADAOImpl implements DAO {

	@Override
	public Delivery create(Delivery t) {
		JPAEMProvider.getEntityManager().persist(t);
		return t;
	}

	@Override
	public void delete(Long id) {
		JPAEMProvider.getEntityManager().remove(id);
	}

	@Override
	public Delivery find(Long id) {
		return JPAEMProvider.getEntityManager().find(Delivery.class, id);
	}

	@Override
	public List getAll() {

		return JPAEMProvider.getEntityManager().createNamedQuery("Delivery.getAll").getResultList();

	}

	@Override
	public List<Delivery> getByCriteria(Map<String, Object> filters) throws DAOException {
		CriteriaBuilder builder = JPAEMProvider.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Delivery> criteria = builder.createQuery(Delivery.class);
		Root<Delivery> root = criteria.from(Delivery.class);
		criteria.select(root);
		for(var e: filters.entrySet()) {
			criteria.where(builder.equal(root.get(e.getKey()), e.getValue()));
		}
		
		Query query = JPAEMProvider.getEntityManager().createQuery(criteria);
		var results = query.getResultList();
		return results;
	}

	@Override
	public <T extends Comparable<T>> List<Delivery> getBetween(String attr, T t1, T t2) throws DAOException {
		CriteriaBuilder builder = JPAEMProvider.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Delivery> criteria = builder.createQuery(Delivery.class);
		Root<Delivery> root = criteria.from(Delivery.class);
		criteria.select(root);
		criteria.where(builder.between(root.get(attr), t1, t2));
		Query query = JPAEMProvider.getEntityManager().createQuery(criteria);
		var results = query.getResultList();
		return results;
	}


}