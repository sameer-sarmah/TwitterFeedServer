package com.sap.twitter.feed.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

public class PropertyHandler {

    private static PropertyHandler instance = null;

    private Properties props = new Properties();

    private Map<String, List<String>> propertyValueMap = new HashMap<String, List<String>>();
    final static Logger logger = Logger.getLogger(PropertyHandler.class);

    private PropertyHandler() {
        InputStream propertiesInput = null;
        try {
            propertiesInput = getPropertyStream();
            if (propertiesInput != null) {
                props.load(propertiesInput);
                Enumeration<?> e = props.propertyNames();
                while (e.hasMoreElements()) {
                    String key = (String) e.nextElement();
                    String value = props.getProperty(key);
                    propertyValueMap.put(key, Arrays.asList(value.split("\\s*,\\s*")));
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.trace(e.getMessage(), e);
        } finally {
            if (propertiesInput != null) {
                try {
                    propertiesInput.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    logger.trace(e.getMessage(), e);            }
            }
        }
    }

    public static synchronized PropertyHandler getInstance() {
        if (instance == null)
            instance = new PropertyHandler();
        return instance;
    }

    public List<String> getValue(String propKey) {
        if (propertyValueMap.containsKey(propKey)) {
            return propertyValueMap.get(propKey);
        }
        return null;
    }

    private InputStream getPropertyStream() {
    	Bundle bundle = FrameworkUtil.getBundle(PropertyHandler.class);
        InputStream propertiesInput = null;
        try {
            propertiesInput = bundle.getResource("twitter.properties").openStream();
        } catch (IOException e) {
            logger.error(e.getMessage());
            logger.trace(e.getMessage(), e);         }
        return propertiesInput;
    }
}
