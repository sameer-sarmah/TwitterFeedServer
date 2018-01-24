package com.sap.twitter.feed.model;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sap.twitter.feed.cache.PositiveTweetCache;
import com.sap.twitter.feed.operations.TwitterCreateStatus;
import com.sap.twitter.feed.operations.TwitterDeleteStatus;

import twitter4j.Status;

public class TweetModel {
    public String findAll() {
    	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Type type = new TypeToken<List<Status>>() {
		}.getType();
		List<Status> tweets=PositiveTweetCache.getTweets();
		String json = gson.toJson(tweets, type);
		return json;
    } 
    
    public String find(int fromIndexIncluding,int toIndexExcluding) throws IllegalArgumentException{
    	Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Type type = new TypeToken<List<Status>>() {
		}.getType();
		List<Status> tweets=PositiveTweetCache.getTweets();
		//from index is greater than list size 
		if(tweets.size()<fromIndexIncluding || fromIndexIncluding<0 || toIndexExcluding<0 || fromIndexIncluding > toIndexExcluding ) {
			throw new IllegalArgumentException("Invalid from index or to index");
		}
		//to index is greater than list size
		else if(tweets.size()<toIndexExcluding) {
			tweets=tweets.subList(fromIndexIncluding, tweets.size()-1);
		}
		else {
			tweets=tweets.subList(fromIndexIncluding, toIndexExcluding);		
		}

		String json = gson.toJson(tweets, type);
		return json;
    }
    
    public int findCont() {

		List<Status> tweets=PositiveTweetCache.getTweets();
		return tweets.size();
    } 
    
    public long createTweet(String text) {
    	return TwitterCreateStatus.createStatus(text).getId();
    }
    
    public long deleteTweet(long statusId) {
    	return TwitterDeleteStatus.deleteStatus(statusId).getId();
    }
}
