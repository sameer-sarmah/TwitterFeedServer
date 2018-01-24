package com.sap.twitter.feed.cache;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import twitter4j.Status;
public class PositiveTweetCache {
private static	Lock lock =new ReentrantLock();
private static List<Status> tweets=new ArrayList<Status>();

public static List<Status> getTweets() {
	try{
	lock.lock();
	return tweets;
	}
	finally{
	lock.unlock();
	}
}

public static void addTweet(Status status) {
	 lock.lock();
	 tweets.add(status);
	 lock.unlock();
}

public static void prependTweet(Status status) {
	 lock.lock();
	 tweets.add(0,status);
	 lock.unlock();
}

}
