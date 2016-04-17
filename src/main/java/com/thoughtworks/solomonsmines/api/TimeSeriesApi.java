package com.thoughtworks.solomonsmines.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;

import com.thoughtworks.solomonsmines.dao.MinesDao;
import com.thoughtworks.solomonsmines.model.MinesModel;

@Path("phone")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TimeSeriesApi {
	@Autowired
	private MinesDao<MinesModel> minesDao;

	@GET
	public Response get(@QueryParam("type") String type, @QueryParam("start_time") Long startTimeMillis,
			@QueryParam("end_time") Long endTimeMillis) {

		return Response.ok().entity(new ListResponseWrapper(minesDao.listMines()).toJson()).build();
	}

}
