package com.sap.twitter.feed.listener;

import com.sap.twitter.feed.cache.PositiveTweetCache;
import com.sap.twitter.feed.sentiment.SentimentAnalysor;
import com.sap.twitter.feed.sentiment.SentimentType;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class CustomTweeterStatusListener implements StatusListener {

	public void onException(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	public void onStatus(Status status) {
		if(PositiveTweetCache.getTweets().size()<1000){
			System.out.println("------------------------");
			System.out.println("Tweet " + status.getText());
			if (status.getPlace() != null
					&& !status.getPlace().getFullName().isEmpty()) {
				System.out.println("Place " + status.getPlace().getFullName());
			}
			if (status.getUser() != null && !status.getUser().getName().isEmpty()) {
				System.out.println("User " + status.getUser().getName());
			}

           if(status.getUser()!=null && status.getUser().getFollowersCount()>=0 && !status.getUser().getProfileImageURL().isEmpty()) {
            	SentimentType type = SentimentAnalysor.analyse(status.getText());
    			if (type.equals(SentimentType.POSITIVE)) {
    				System.out.println("Tweet added in StatusListener");
    				PositiveTweetCache.prependTweet(status);
    			}
            }
		}
	}

	public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
		// TODO Auto-generated method stub
		
	}

	public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
		// TODO Auto-generated method stub
		
	}

	public void onScrubGeo(long userId, long upToStatusId) {
		// TODO Auto-generated method stub
		
	}

	public void onStallWarning(StallWarning warning) {
		// TODO Auto-generated method stub
		
	}




}
