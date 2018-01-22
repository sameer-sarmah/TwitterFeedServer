package com.sap.twitter.feed.listener;

import com.sap.twitter.feed.cache.PositiveTweetCache;
import com.sap.twitter.feed.sentiment.SentimentAnalysor;
import com.sap.twitter.feed.sentiment.SentimentType;

import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

public class CustomTweeterStatusListener implements StatusListener {

	@Override
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
			SentimentType type=SentimentAnalysor.analyse(status.getText());
			if(type.equals(SentimentType.POSITIVE)){
				System.out.println("Tweet added in StatusListener");
				PositiveTweetCache.addTweet(status);
			}
		}
		
	}

	@Override
	public void onTrackLimitationNotice(int i) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void onScrubGeo(long l, long l1) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public void onException(Exception ex) {

	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {

	}

	@Override
	public void onStallWarning(StallWarning arg0) {

	}
}
