package com.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.classes.Errors;
import com.classes.Pharmacist;
import com.controller.ConnectionController;

public class PharmacistModel {

	ConnectionController controller = new ConnectionController();

	public boolean checkAvailabilityById(String id) {
		Connection con = controller.connect();
		String query = "select * from pharmacist where id=?";
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
	
	public boolean checkAvailabilityByEmail(String email) {
		Connection con = controller.connect();
		String query = "select * from pharmacist where email=?";
		int count = 0;
		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, email);
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
	public Map<String, Object> insertPharmacist(Pharmacist p) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();
		
		if (checkAvailabilityByEmail(p.getEmail()) == true) {
			em.setError_code(1);
			em.setError_message("Email is already exist");
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

		String query = "insert into pharmacist(f_name, l_name, contact_no, address, email, pass, roll) values(?,?,?,?,?,?,?)";

		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, p.getF_name());
			preparedStmt.setString(2, p.getL_name());
			preparedStmt.setString(3, p.getContact_no());
			preparedStmt.setString(4, p.getAddress());
			preparedStmt.setString(5, p.getEmail());
			preparedStmt.setString(6, p.getPass());
			preparedStmt.setString(7, p.getRoll());
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
	public Map<String, Object> updatePharmacist(Pharmacist p) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();

		if (checkAvailabilityById(p.getId()) == false) {
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

		String query = "update pharmacist set f_name=?,l_name=?,contact_no=?,address=?, pass=? where id=? ";

		try {
			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, p.getF_name());
			preparedStmt.setString(2, p.getL_name());
			preparedStmt.setString(3, p.getContact_no());
			preparedStmt.setString(4, p.getAddress());
			preparedStmt.setString(5, p.getPass());
			preparedStmt.setString(6, p.getId());
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
	public Map<String, Object> deletePharmacist(String id) {
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

		String query = "delete from pharmacist where id=?";

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

	// Get a pharmacist ===========================================================
	public Map<String, Object> searchPharmacist(String id) {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();
		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "select id, f_name, l_name, contact_no, address, email, roll, created_at from pharmacist where id=? ";
		Pharmacist p = new Pharmacist();
		int count = 0;
		try {

			PreparedStatement preparedStmt = con.prepareStatement(query);
			preparedStmt.setString(1, id);
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				count++;
				p.setId(rs.getString(1));
				p.setF_name(rs.getString(2));
				p.setL_name(rs.getString(3));
				p.setContact_no(rs.getString(4));
				p.setAddress(rs.getString(5));
				p.setEmail(rs.getString(6));
				p.setRoll(rs.getString(7));
				p.setCreated_at(rs.getString(8));
			}

			preparedStmt.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data Recieved");
			data.put("Error", em);
			data.put("NoOfData", count);
			if (count > 0) {
				data.put("Data", p);
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

	// Get all Data ===========================================================
	public Map<String, Object> searchAllPharmacist() {
		Errors em = new Errors();
		Map<String, Object> data = new HashMap<String, Object>();
		Connection con = controller.connect();
		if (con == null) {
			em.setError_code(1);
			em.setError_message("Database Connection Error");
			data.put("Error", em);
			return data;
		}

		String query = "select id, f_name, l_name, contact_no, address, email, roll, created_at from pharmacist";
		List<Pharmacist> pharmacists = new ArrayList<>();
		int count = 0;
		try {

			PreparedStatement preparedStmt = con.prepareStatement(query);
			ResultSet rs = preparedStmt.executeQuery();

			while (rs.next()) {
				count++;
				Pharmacist p = new Pharmacist();
				p.setId(rs.getString(1));
				p.setF_name(rs.getString(2));
				p.setL_name(rs.getString(3));
				p.setContact_no(rs.getString(4));
				p.setAddress(rs.getString(5));
				p.setEmail(rs.getString(6));
				p.setRoll(rs.getString(7));
				p.setCreated_at(rs.getString(8));
				pharmacists.add(p);
			}

			preparedStmt.close();
			rs.close();
			con.close();

			em.setError_code(0);
			em.setError_message("Data Recieved");
			data.put("Error", em);
			data.put("NoOfData", count);
			if (count > 0) {
				data.put("Data", pharmacists);
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
