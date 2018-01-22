package com.sap.twitter.feed.listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sap.twitter.feed.operations.TwitterSearchTags;
@WebListener
public class CustomServletContextListener
               implements ServletContextListener{

	public void contextDestroyed(ServletContextEvent context) {
		
		System.out.println("Servlet context destroyed");

	}

	public void contextInitialized(ServletContextEvent context) {
		System.out.println("Servlet context created");
		TwitterSearchTags.searchTweeets("#AUSvENG");
	}


}