package hr.webapp.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import hr.webapp.dao.jpa.JPADAOImpl;

public class DAOProvider {

	private static DAO dao;

	static {
		System.out.println("I am in dao provider static");
		final String resourceName = "application.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String daoName = props.getProperty("server.daoImpl");
		dao = DAOFactory.getDAO(daoName);
	}

	public static DAO getDAO() {
		return dao;
	}

}