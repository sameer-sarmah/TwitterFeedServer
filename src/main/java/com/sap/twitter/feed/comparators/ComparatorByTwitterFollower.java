package com.sap.twitter.feed.comparators;

import java.util.Comparator;

import twitter4j.Status;

public class ComparatorByTwitterFollower implements Comparator<Status>{

	public int compare(Status s1, Status s2) {
		Integer f1=s1.getUser().getFollowersCount();
		Integer f2=s2.getUser().getFollowersCount();
		return f2.compareTo(f1);
	}

}
