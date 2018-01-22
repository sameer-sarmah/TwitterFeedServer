package com.sap.twitter.feed.sentiment;

public enum SentimentType{
	POSITIVE("Positive"),NEUTRAL("Neutral"),NEGATIVE("Negative");
	private String type;
	private SentimentType(String type) {
		this.type=type;
	}
	public String getType() {
		return type;
	}	
	
	
}