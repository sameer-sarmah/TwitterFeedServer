package com.sap.twitter.feed.util;

import java.util.Arrays;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterUtil {
	private static	Lock lock =new ReentrantLock();
	private static Twitter twitterHandle=null;
	private static void populateTwitterHandle() {
		final String consumerKey = "";
		final String consumerSecret = "";
		final String accessToken = "";
		final String accessTokenSecret = "";
		System.setProperty("twitter4j.oauth.consumerKey", consumerKey);
		System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret);
		System.setProperty("twitter4j.oauth.accessToken", accessToken);
		System.setProperty("twitter4j.oauth.accessTokenSecret",
				accessTokenSecret);
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(consumerKey)
				.setOAuthConsumerSecret(consumerSecret)
				.setOAuthAccessToken(accessToken)
				.setOAuthAccessTokenSecret(accessTokenSecret);
		twitterHandle = new TwitterFactory(cb.build()).getInstance();
	}
	
	public static Twitter getTwitterHandle() {
		try{
			lock.lock();
			if(twitterHandle == null) {
				populateTwitterHandle();
			}
			return twitterHandle;
			}
			finally{
			lock.unlock();
			}

	}
}
