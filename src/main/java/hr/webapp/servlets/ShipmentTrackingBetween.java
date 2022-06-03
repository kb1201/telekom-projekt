package hr.webapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Response;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import hr.webapp.dao.DAO;
import hr.webapp.dao.DAOException;
import hr.webapp.dao.DAOProvider;
import hr.webapp.models.Delivery;


@WebServlet("/shipmentTracking/between")
public class ShipmentTrackingBetween extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String attr = req.getParameter("attribute");
		String d1 = req.getParameter("first");
		String d2 = req.getParameter("second");
		System.out.println(attr);
		System.out.println(d1);
		System.out.println(d2);
		
		if(attr == null || d1 == null || d2 == null) {
			resp.setStatus(Response.SC_BAD_REQUEST);
			out.flush();
			return;
		}
		
		DAO dao = DAOProvider.getDAO();
		if("createdAt".equals(attr)) {
			var format =  new SimpleDateFormat("dd/MM/yyyy");
			List<Delivery> deliveries;
			try {
				deliveries = dao.getBetween(attr, format.parse(d1), format.parse(d2));
				for (var d : deliveries)
					out.print(new Gson().toJson(d));
			} catch (DAOException | ParseException e) {
				resp.setStatus(Response.SC_BAD_REQUEST);
			}	
			out.flush();
			return;
		}else if("id".equals(attr)) {
			List<Delivery> deliveries;
			try {
				deliveries = dao.getBetween(attr, Long.parseLong(d1), Long.parseLong(d2));
				for (var d : deliveries)
					out.print(new Gson().toJson(d));
			} catch (DAOException | NumberFormatException e) {
				resp.setStatus(Response.SC_BAD_REQUEST);
			}	
			out.flush();
			return;
		}
		
		if(!"adress".equals(attr) && !"status".equals(attr)) {
			resp.setStatus(Response.SC_BAD_REQUEST);
			out.flush();
			return;
		}
		
		List<Delivery> deliveries = dao.getBetween(attr, d1, d2);
		for (var d : deliveries)
			out.print(new Gson().toJson(d));
		
		out.flush();

	}
	
}