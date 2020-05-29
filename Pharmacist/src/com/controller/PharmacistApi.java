package com.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.classes.Medicine;
import com.classes.Pharmacist;

import com.classes.Medicine;
import com.google.gson.Gson;
import com.model.MedicineModel;

/**
 * Servlet implementation class PharmacistApi
 */
@WebServlet("/PharmacistApi")
public class PharmacistApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	MedicineModel model = new MedicineModel();
	Map<String, Object> data = new HashMap<String, Object>();
	Gson gson = new Gson();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PharmacistApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String med_id = request.getParameter("id");
		 String med_generic_name = request.getParameter("med_generic_name");
		 String med_brand_name = request.getParameter("med_brand_name");
		 String med_expiration_date = request.getParameter("med_expiration_date");
		 String med_unit_price = request.getParameter("med_unit_price");
		 String med_quantity = request.getParameter("med_quantity");
		 
		 Medicine m = new Medicine();
		 m.setId(med_id);
		 m.setGeneric_name(med_generic_name);
		 m.setBrand_name(med_brand_name);
		 m.setExpiration_date(med_expiration_date);
		 m.setUnit_price(med_unit_price);
		 m.setQuantity(med_quantity);
		 String outputJson = gson.toJson(model.insertMedicine(m));

		 String output = "{\"status\":\"success\", \"data\": \"" + outputJson + "\"}";
		 response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String med_id = request.getParameter("id");
		 String med_generic_name = request.getParameter("med_generic_name");
		 String med_brand_name = request.getParameter("med_brand_name");
		 String med_expiration_date = request.getParameter("med_expiration_date");
		 String med_unit_price = request.getParameter("med_unit_price");
		 String med_quantity = request.getParameter("med_quantity");
		 
		 Medicine m = new Medicine();
		 m.setId(med_id);
		 m.setGeneric_name(med_generic_name);
		 m.setBrand_name(med_brand_name);
		 m.setExpiration_date(med_expiration_date);
		 m.setUnit_price(med_unit_price);
		 m.setQuantity(med_quantity);
		 String outputJson = gson.toJson(model.updateMedicine(m));

		 String output = "{\"status\":\"success\", \"data\": \"" + outputJson + "\"}";
		 response.getWriter().write(output); 
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		model.deleteMedicine(request.getParameter("id"));
		 String output = "{\"status\":\"success\", \"data\": \"\"}";
		 response.getWriter().write(output); 
	}

}
