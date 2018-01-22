package com.sap.twitter.feed.operations;

import com.sap.twitter.feed.util.TwitterUtil;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TwitterCreateStatus {
	public static Status createStatus(String text) {
		Twitter twitter =TwitterUtil.getTwitterHandle();
		Status status=null;
		try {
			status=twitter.updateStatus(text);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return status;
	}
}
