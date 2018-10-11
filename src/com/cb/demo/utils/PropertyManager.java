package com.cb.demo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
	
	private static Properties props;
	
	
	public PropertyManager(String fName) {
		try {
			props = new Properties();
			InputStream is = this.getClass().getResourceAsStream(fName);
			props.load(is);	
		} catch (IOException e) {
			System.out.println("Found file " + fName + " but encountered an IOException");
			e.printStackTrace();
			System.exit(2);
		}
	}
	
	public String getVal(String key) {
		return props.getProperty(key);
	}
	
	public String getValWithDefault(String key, String defVal) {
		String retVal = props.getProperty(key);
		
		return (retVal == null) ? defVal : retVal;  
	}
	
	public void dumpProps() {
		for (Object key : props.keySet()) {
			System.out.println("Prop dump: " + key + " :: " + props.getProperty((String) key)); 
		}
	}

}
