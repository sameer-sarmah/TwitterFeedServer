package com.sap.twitter.feed.comparators;

import java.util.Comparator;

import twitter4j.Status;

public class ComparatorByDate implements Comparator<Status>{

	public int compare(Status s1, Status s2) {
		return s2.getCreatedAt().compareTo(s1.getCreatedAt());
	}

}
