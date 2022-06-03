package hr.webapp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import hr.webapp.models.Delivery;

public interface DAO {

	Delivery create(Delivery t) throws DAOException;

	void delete(Long id) throws DAOException;

	Delivery find(Long id) throws DAOException;

	List<Delivery> getAll() throws DAOException;

	List<Delivery> getByCriteria(Map<String, Object> filters) throws DAOException;

	 <T extends Comparable<T>> List<Delivery> getBetween(String attr, T t1, T t2) throws DAOException;

}
