package com.cousera.parser.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileConfig {
	

	public static String[] linksList;
	public static String isProxyRequired;
	public static String proxyHost;
	public static String proxyPort;
	public static String baseFolder;
	public static String[] stopCharacterFolder;
	public static  String[] companyNameList;
	public static String companyBaseUrl;
	public static String categoryDivForPages;
	public static String categoryDivForLinks;
	public static String categorySpanForPages;
	public static String companyClassForPages;
	public static String companyClassForLinks;
	public static String[] exceptionCompanyList;
	public static String waitTime;

	public static void setPropertyVarialbles()
	{
		Properties prop = new Properties();
		 
    	try
    	{
            //load a properties file
    		prop.load(new FileInputStream("./src/cousera.properties"));
 
            //get the different property values
    		if(prop.containsKey("LINKS_LIST"))
    			linksList=prop.getProperty("LINKS_LIST").split("@");
    		
    		if(prop.containsKey("COMPANY_NAME"))
    			companyNameList=prop.getProperty("COMPANY_NAME").split("@");
    		
    		if(prop.containsKey("EXCEPTION_COMPANY_NAME"))
    			exceptionCompanyList=prop.getProperty("EXCEPTION_COMPANY_NAME").split("@");
    		
    		if(prop.containsKey("COMPANY_BASE_URL"))
    			companyBaseUrl=prop.getProperty("COMPANY_BASE_URL");
    		
    		if(prop.containsKey("IS_PROXY_NEEDED"))
    			isProxyRequired=prop.getProperty("IS_PROXY_NEEDED");
    		
    		if(prop.containsKey("PROXY_HOST"))
    			proxyHost=prop.getProperty("PROXY_HOST");
    		
    		if(prop.containsKey("PROXY_PORT"))
    			proxyPort=prop.getProperty("PROXY_PORT");
    		
    		if(prop.containsKey("CATEGORY_DIV_FOR_PAGES"))
    			categoryDivForPages=prop.getProperty("CATEGORY_DIV_FOR_PAGES");
    		
    		if(prop.containsKey("CATEGORY_SPAN_FOR_PAGES"))
    			categorySpanForPages=prop.getProperty("CATEGORY_SPAN_FOR_PAGES");
    		
    		if(prop.containsKey("CATEGORY_DIV_FOR_LINKS"))
    			categoryDivForLinks=prop.getProperty("CATEGORY_DIV_FOR_LINKS");
    		
    		if(prop.containsKey("COMPANY_CLASS_FOR_PAGES"))
    			companyClassForPages=prop.getProperty("COMPANY_CLASS_FOR_PAGES");
    		
    		if(prop.containsKey("COMPANY_CLASS_FOR_LINKS"))
    			companyClassForLinks=prop.getProperty("COMPANY_CLASS_FOR_LINKS");
    		
    		if(prop.containsKey("BASE_FOLDER"))
    			baseFolder=prop.getProperty("BASE_FOLDER");
    		
    		if(prop.containsKey("WAIT_TIME"))
    			waitTime=prop.getProperty("WAIT_TIME");
    		else
    			waitTime="1";
    		
    		if(prop.containsKey("STOP_CHARACTER_FOLDER"))
    			stopCharacterFolder=prop.getProperty("STOP_CHARACTER_FOLDER").split("@");
    		
           
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
 
	}
}
