package hr.wepapp.init;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.webapp.dao.DAOProvider;
import hr.webapp.dao.jpa.JPAEMFProvider;

@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		System.out.println("I am starting...");
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza", getProperties());
		sce.getServletContext().setAttribute("my.application.emf", emf);
		JPAEMFProvider.setEmf(emf);
		new DAOProvider();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		System.out.println("I am shutting down...");
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute("my.application.emf");
		if (emf != null) {
			emf.close();
		}
	}

	private Map getProperties() {
		Map result = new HashMap();
		
		final String resourceName = "META-INF/config.properties";
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
			props.load(resourceStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		result.put("hibernate.connection.username", props.getProperty("username"));
		result.put("hibernate.connection.password", props.getProperty("password"));

		
		
		return result;
	}
}