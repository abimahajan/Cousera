package com.cousera.parser.main;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
		//read from properties file
		PropertiesFileConfig.setPropertyVarialbles();
		
		if(Boolean.parseBoolean(PropertiesFileConfig.isProxyRequired))
		{
			System.setProperty("http.proxyHost",PropertiesFileConfig.proxyHost);
			System.setProperty("http.proxyPort",PropertiesFileConfig.proxyPort);
		}
		
		DownloadFile download=new DownloadFile();
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
				
				doc = Jsoup.connect(linkToParse).get();
				
				System.out.println(doc.html());
				
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
					//download mp3 for each subcategory
					download.downloadFromMap(linksMap,PropertiesFileConfig.baseFolder+"Link "+i+"/"+subCategoryName);
					linksMap.clear();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
			
		}
    }

	

}
