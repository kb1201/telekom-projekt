package hr.webapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import hr.webapp.dao.DAO;
import hr.webapp.dao.DAOProvider;
import hr.webapp.models.Delivery;


@WebServlet("/shipmentTracking/*")
public class ShipmentTracking extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();
		String attr = path.substring(1);
		
		
		if("filter".equals(attr)) {
			req.getRequestDispatcher("filter").forward(req, resp);
		}
		
		if("between".equals(attr)) {
			req.getRequestDispatcher("between").forward(req, resp);
		}
		

		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		
		String regex = "[0-9]+";
		Pattern pattern = Pattern.compile(regex);
		DAO dao = DAOProvider.getDAO();
		if (pattern.matcher(attr).matches()) {	
			out.print(new Gson().toJson(dao.find(Long.parseLong(attr))));
			out.flush();
			return;
		}

		//baf request
		resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		out.flush();
		

		
	}
	
}