package com.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.classes.Medicine;
import com.google.gson.Gson;
import com.model.MedicineModel;

@Path("/medicine")
public class MedicineController {

	MedicineModel model = new MedicineModel();
	Map<String, Object> data = new HashMap<String, Object>();
	Gson gson = new Gson();

	// INSERT
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String insertMedicine(String json) {
		Medicine m = gson.fromJson(json, Medicine.class);
		return gson.toJson(model.insertMedicine(m));
	}

	// UPDATE
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String updateMedicine(String json) {
		Medicine m = gson.fromJson(json, Medicine.class);
		return gson.toJson(model.updateMedicine(m));
	}

	// DELETE
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteMedicine(@PathParam("id") String id) {
		return gson.toJson(model.deleteMedicine(id));
	}

	// SEARCH
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String searchMedicine(@PathParam("id") String id) {
		return gson.toJson(model.searchMedicine(id));
	}

	// SEARCH
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String searchAllMedicine() {
		return gson.toJson(model.searchAllMedicine());
	}

}
