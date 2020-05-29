package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.classes.Errors;
import com.classes.Medicine;
import com.controller.ConnectionController;

public class MedicineModel {

	ConnectionController controller = new ConnectionController();
	public boolean checkAvailabilityById(String id) {
		Connection con = controller.connect();
		String query = "select * from medicine where id=?";
		int count = 0;
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				count++;
			}

			rs.close();
			preparedStmt.close();
			con.close();

			if (count > 0)
				return true;
			else
				return false;

		} catch (Exception e) {
			return true; // If any error then main function will handle it
		}
	}

	// Insert ===========================================================
	public Map<String, Object> insertMedicine(Medicine m) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();

		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "insert into medicine(generic_name, brand_name, expiration_date, unit_price, quantity) values(?,?,?,?,?)";

		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, m.getGeneric_name());
			preparedStmt.setString(2, m.getBrand_name());
			preparedStmt.setString(3, m.getExpiration_date());
			preparedStmt.setString(4, m.getUnit_price());
			preparedStmt.setString(5, m.getQuantity());
			preparedStmt.executeUpdate();

			preparedStmt.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data inserted");
			data.put("Error", em);
			return data;

		} catch (Exception ex) {
			System.out.println(ex);
			em.setError_code(1);
			em.setError_message("Some error occured");
			data.put("Error", em);
			return data;
		}
	}

	// Update ===========================================================
	public Map<String, Object> updateMedicine(Medicine m) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();

		if (checkAvailabilityById(m.getId()) == false) {
			em.setError_code(1);
			em.setError_message("Data not available");
			data.put("Error", em);
			return data;
		}

		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "update medicine set generic_name=?,brand_name=?,expiration_date=?,unit_price=?,quantity=? where id=? ";

		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, m.getGeneric_name());
			preparedStmt.setString(2, m.getBrand_name());
			preparedStmt.setString(3, m.getExpiration_date());
			preparedStmt.setString(4, m.getUnit_price());
			preparedStmt.setString(5, m.getQuantity());
			preparedStmt.setString(6, m.getId());
			preparedStmt.executeUpdate();		

			preparedStmt.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data updated");
			data.put("Error", em);
			return data;

		} catch (Exception ex) {
			System.out.println(ex);
			em.setError_code(1);
			em.setError_message("Some error occured");
			data.put("Error", em);
			return data;
		}
	}

	// Delete ===========================================================
	public Map<String, Object> deleteMedicine(String id) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();

		if (checkAvailabilityById(id) == false) {
			em.setError_code(1);
			em.setError_message("Data not available");
			data.put("Error", em);
			return data;
		}

		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "delete from medicine where id=?";

		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			preparedStmt.executeUpdate();

			preparedStmt.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data deleted");
			data.put("Error", em);
			return data;

		} catch (Exception ex) {
			System.out.println(ex);
			em.setError_code(1);
			em.setError_message("Some error occured");
			data.put("Error", em);
			return data;
		}
	}

	// Get a medicine ===========================================================
	public Map<String, Object> searchMedicine(String id) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();
		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "select id, generic_name, brand_name, expiration_date, unit_price, quantity from medicine where id=?";
		Medicine m = new Medicine();
		int count = 0;
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				count++;
				m.setId(rs.getString(1));
				m.setGeneric_name(rs.getString(2));
				m.setBrand_name(rs.getString(3));
				m.setExpiration_date(rs.getString(4));
				m.setUnit_price(rs.getString(5));
				m.setQuantity(rs.getString(6));
			}

			preparedStmt.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data Recieved");
			data.put("Error", em);
			data.put("NoOfData", count);
			if (count > 0) {
				data.put("Data", m);
			}
			return data;

		} catch (Exception ex) {
			System.out.println(ex);
			em.setError_code(1);
			em.setError_message("Some error occured");
			data.put("Error", em);
			return data;
		}
	}

	// Get all medicines ===========================================================
	public Map<String, Object> searchAllMedicine() {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();
		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "select id, generic_name , brand_name , expiration_date , unit_price, quantity from medicine";
		List<Medicine> medicines = new ArrayList<>();
		int count = 0;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			while (rs.next()) {
				count++;
				Medicine m = new Medicine();
				m.setId(rs.getString(1));
				m.setGeneric_name(rs.getString(2));
				m.setBrand_name(rs.getString(3));
				m.setExpiration_date(rs.getString(4));
				m.setUnit_price(rs.getString(5));
				m.setQuantity(rs.getString(6));
				medicines.add(m);
			}

			stmt.close();
			rs.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data Recieved");
			data.put("Error", em);
			data.put("NoOfData", count);
			if (count > 0) {
				data.put("Data", medicines);
			}
			return data;

		} catch (Exception ex) {
			System.out.println(ex);
			em.setError_code(1);
			em.setError_message("Some error occured");
			data.put("Error", em);
			return data;
		}
	}
}
