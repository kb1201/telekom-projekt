package hr.webapp.dao;

import java.lang.reflect.InvocationTargetException;

public class DAOFactory {

	public static DAO getDAO(String daoName) {
		try {
			Class<?> c =  Class.forName(daoName);
			DAO dao = (DAO)c.getDeclaredConstructor().newInstance();
			return dao;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
