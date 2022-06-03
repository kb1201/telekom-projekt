package hr.webapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import hr.webapp.dao.DAOProvider;
import hr.webapp.models.Delivery;

@WebServlet("/shipmentTracking/filter")
public class ShipmentTrackingFilter extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		String filtersString = req.getParameter("filter");

		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(filtersString, Map.class);

		for (var e : map.entrySet()) {
			if (!"adress".equals(e.getKey()) && !"status".equals(e.getKey()) && !"id".equals(e.getKey())
					&& !"createdAt".equals(e.getKey())) {
				resp.setStatus(Response.SC_BAD_REQUEST);
				out.flush();
				return;
			}
			if ("id".equals(e.getKey())) {
				try {
					Long.parseLong(e.getValue().toString());
				}catch(NumberFormatException exc) {
					resp.setStatus(Response.SC_BAD_REQUEST);
					out.flush();
					return;
				}
			}
			if ("createdAt".equals(e.getKey())) {
				var format =  new SimpleDateFormat("dd/MM/yyyy");
				try {
					format.parse(e.getValue().toString());
				} catch (ParseException e1) {
					resp.setStatus(Response.SC_BAD_REQUEST);
					out.flush();
					return;
				}
			}
		}

		DAO dao = DAOProvider.getDAO();

		var deliveries = dao.getByCriteria(map);
		for (var d : deliveries)
			out.print(new Gson().toJson(d));

		out.flush();

	}

}