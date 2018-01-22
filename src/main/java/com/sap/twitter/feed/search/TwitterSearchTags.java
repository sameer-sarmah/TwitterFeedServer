package com.sap.twitter.feed.search;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sap.twitter.feed.cache.PositiveTweetCache;
import com.sap.twitter.feed.comparators.ComparatorByTwitterFollower;
import com.sap.twitter.feed.sentiment.SentimentAnalysor;
import com.sap.twitter.feed.sentiment.SentimentType;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearchTags {

	public static void searchTweeets(String searchTerm) {
		final String consumerKey = "";//enter consumerKey of the account
		final String consumerSecret = "";//enter consumerSecret of the account
		final String accessToken = "";//enter accessToken of the account
		final String accessTokenSecret = "";//enter accessTokenSecret of the account

		// Set the system properties so that Twitter4j library used by twitter
		// stream
		// can use them to generat OAuth credentials
		System.setProperty("twitter4j.oauth.consumerKey", consumerKey);
		System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret);
		System.setProperty("twitter4j.oauth.accessToken", accessToken);
		System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret);
		String[] credentials = { consumerKey, consumerSecret, accessToken, accessTokenSecret };
		String[] filters = Arrays.copyOfRange(credentials, 4, credentials.length);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey).setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken).setOAuthAccessTokenSecret(accessTokenSecret);
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(searchTerm);
		int numberOfTweets = 190;
		long lastID = Long.MAX_VALUE;
		List<Status> tweets = new ArrayList<Status>();
		while (tweets.size() < numberOfTweets) {
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
				for (Status t : tweets)
					if (t.getId() < lastID)
						lastID = t.getId();

			}

			catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
			}
			;
			query.setMaxId(lastID - 1);
		}

		for (int i = 0; i < tweets.size(); i++) {
			Status status = tweets.get(i);
            if(status.getUser()!=null && status.getUser().getFollowersCount()>=0 && !status.getUser().getProfileImageURL().isEmpty()) {
            	SentimentType type = SentimentAnalysor.analyse(status.getText());
    			if (type.equals(SentimentType.POSITIVE)) {
    				System.out.println("Tweet added");
    				PositiveTweetCache.addTweet(status);
    			}
            }
		
		}
		tweets = PositiveTweetCache.getTweets();
		tweets.sort(new ComparatorByTwitterFollower());
//		for (int i = 0; i < tweets.size(); i++) {
//			Status status = tweets.get(i);
//			System.out.println("------------------------");
//			System.out.println("Tweet " + status.getText());
//			if (status.getPlace() != null && !status.getPlace().getFullName().isEmpty()) {
//				System.out.println("Place " + status.getPlace().getFullName());
//			}
//			if (status.getUser() != null && !status.getUser().getName().isEmpty()) {
//				System.out.println("User " + status.getUser().getName());
//			}
//
//			if (status.getCreatedAt() != null) {
//				DateFormat df = DateFormat.getDateInstance();
//				Date createDate = status.getCreatedAt();
//				System.out.println("Created at " + df.format(createDate));
//			}
//
//			System.out.println("Source: " + status.getSource());
//
//			System.out.println("Retweet count " + status.getRetweetCount());
//		}
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		Type type = new TypeToken<List<Status>>() {
		}.getType();
		String json = gson.toJson(tweets, type);
		System.out.println(json);
		// TwitterSearchTagsStream.searchTweetsAsStream(searchTerm);
	}

}
