package com.sap.twitter.feed.comparators;

import java.util.Comparator;

import twitter4j.Status;

public class ComparatorByRetweets implements Comparator<Status> {

	public int compare(Status s1, Status s2) {
		Integer rt1=s1.getRetweetCount();
		Integer rt2=s2.getRetweetCount();
		return rt2.compareTo(rt1);
	}

}
