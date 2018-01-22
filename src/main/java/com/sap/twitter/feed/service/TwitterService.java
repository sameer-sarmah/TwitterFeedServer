package com.sap.twitter.feed.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.sap.twitter.feed.model.TweetModel;

@Path("/tweets")
public class TwitterService {
	@GET
	@Path("/all")
	@Produces({ "application/json" })
	public Response getTweets() {
		String jsonString = new TweetModel().findAll();
		return Response.status(200).entity(jsonString).build();

	}

	@GET
	@Path("/sublist")
	@Produces({ "application/json" })
	public Response getUsers(@QueryParam("from") int from, @QueryParam("to") int to) {

		String jsonString = new TweetModel().find(from, to + 1);
		return Response.status(200).entity(jsonString).build();

	}
	
	@GET
	@Path("/count")
	@Produces({ "application/json" })
	public Response getTweetsCount() {
		Integer count = new TweetModel().findCont();
		return Response.status(200).entity(count.toString()).build();

	}
}
