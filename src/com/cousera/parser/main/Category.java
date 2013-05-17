package com.cousera.parser.main;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cousera.parser.utils.DownloadFile;
import com.cousera.parser.utils.PropertiesFileConfig;

public class Category 
{
	
	public static Map<String,String> linksMap = new HashMap<String,String>();
	
	public static void main(String[] args)  
	{
		Category category=new Category();
		category.startProcessing();
	}

	private void startProcessing()
	{
		//read from properties file
		PropertiesFileConfig.setPropertyVarialbles();
		
		if(Boolean.parseBoolean(PropertiesFileConfig.isProxyRequired))
		{
			System.setProperty("http.proxyHost",PropertiesFileConfig.proxyHost);
			System.setProperty("http.proxyPort",PropertiesFileConfig.proxyPort);
		}
		
		int i=1;
		Document doc;
		for (String linkToParse : PropertiesFileConfig.linksList) 
		{
			//create folder for the first link to parse
			DownloadFile.createDirectory("Link "+i);
			try 
			{
				//sleeping for wait time period.
				try {
					Thread.sleep(Long.valueOf(PropertiesFileConfig.waitTime));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				//first trying with connect method
				doc = Jsoup.connect(linkToParse).timeout(10000000).get();

				//parse values
				parseLinks(doc,i);
			} catch (IOException e) 
			{
				e.printStackTrace();
				try 
				{
					//trying with parse method if connect throws exception 
					doc=Jsoup.parse(new URL(linkToParse), 1000000);
					parseLinks(doc, i);
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			i++;
			
		}
    
	}

	private  void parseLinks(Document doc, int i) 
	{
		DownloadFile download=new DownloadFile();
		
		//get subcategories for that category
		Elements subCategories=doc.select(".course-item-list h3");
		for(Element subCategory:subCategories)
		{
			//get name of each subcategory
			String subCategoryName=subCategory.text();
			//create folder for that subcategory
			DownloadFile.createDirectory("Link "+i+"/"+subCategoryName);
			
			//get all videos under it
			Elements links=doc.select(".course-item-list .course-lecture-item-resource a");
			for(Element link:links)
			{
				if(link.select("i").toString().contains("icon-download-alt resource"))
				{
					String title=link.select(".hidden").text();
					String href=link.attr("href");
					linksMap.put(title, href);
				}
			}
			//download mp4 for each subcategory
			download.downloadFromMap(linksMap,PropertiesFileConfig.baseFolder+"Link "+i+"/"+subCategoryName);
			linksMap.clear();
		}
		
	}

	

}
