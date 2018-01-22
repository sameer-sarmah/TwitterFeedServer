package com.sap.twitter.feed.service;

import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sap.twitter.feed.model.CreateStatus;
import com.sap.twitter.feed.model.TweetModel;

import twitter4j.Status;

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
	
	@POST
	@Path("/tweet")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createTweet(String json) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Type type = new TypeToken<CreateStatus>() {}.getType();
		CreateStatus status=gson.fromJson(json, type);
		String text=status.getText();
		Long statusID = new TweetModel().createTweet(text);
		return Response.status(200).entity(statusID.toString()).build();

	}
	

}
