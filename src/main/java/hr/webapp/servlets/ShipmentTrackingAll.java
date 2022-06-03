package hr.webapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import hr.webapp.dao.DAO;
import hr.webapp.dao.DAOProvider;
import hr.webapp.models.Delivery;

@WebServlet("/shipmentTracking")
public class ShipmentTrackingAll extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		System.out.println("all shipments");

		List deliveries = DAOProvider.getDAO().getAll();

		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		System.out.println(deliveries);
		for (var d : deliveries)
			out.print(new Gson().toJson(d));
		out.flush();

	
	}

}