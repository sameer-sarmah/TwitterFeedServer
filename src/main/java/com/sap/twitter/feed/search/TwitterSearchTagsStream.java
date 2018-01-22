package com.sap.twitter.feed.search;

import com.sap.twitter.feed.listener.CustomTweeterStatusListener;

import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearchTagsStream {
	private String[] relevantTopics = { "#ArtificialIntelligence", "AI",
			"MachineLearning" };

	public static void main(String[] args) throws Exception {
		searchTweetsAsStream("AUSvENG");
	}

	public static void searchTweetsAsStream(String filter) {

		final String consumerKey = "JPNElKtPeRdidsP2qSPGUKkIE";
		final String consumerSecret = "PaZgYYql9NaGwieONpTjgkMoS2D9uQu9uRY1yTITPTl8PbouCd";
		final String accessToken = "953999807374225409-4c554pgV3r0qfp0QDDYmUyoVIAL6rko";
		final String accessTokenSecret = "BbZrJEaVOtIWqwSWakb8qe8srI1rCrKrtChfm0dtVwddJ";

		// Set the system properties so that Twitter4j library used by twitter
		// stream
		// can use them to generat OAuth credentials
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
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build())
				.getInstance();
		FilterQuery fq = new FilterQuery();

		String keywords[] = { filter };

		fq.track(keywords);

		twitterStream.addListener(new CustomTweeterStatusListener());
		twitterStream.filter(fq);
	}
}
