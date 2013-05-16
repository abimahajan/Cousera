package com.cousera.parser.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class DownloadFile
{
	public void downloadFromMap(Map<String, String> linksMap, String fileToBeCreatedName) 
	{
		Iterator<Entry<String, String>> entries = linksMap.entrySet().iterator();
		while (entries.hasNext()) 
		{
		    Entry<String, String> entry = entries.next();
		    try {
				Thread.sleep(Long.parseLong(PropertiesFileConfig.waitTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			String fileName=entry.getKey()+".mp4";
			saveSong(entry.getValue(), fileToBeCreatedName+"/"+fileName);
			
		}
		
	}

	public static File createDirectory(String categoryName) 
	{
		File dir=new File(PropertiesFileConfig.baseFolder+categoryName);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		return dir;
	}
	
	private boolean saveSong(String url,String outputFileName)
	{
		boolean isSongDownloaded = false;
		try 
		{
			URL website = new URL(url);
			HttpURLConnection urlConnection = (HttpURLConnection) website.openConnection();
			urlConnection.setUseCaches(false);
			urlConnection.setDoOutput(true);
			ReadableByteChannel rbc  = Channels.newChannel(urlConnection.getInputStream());
	        FileOutputStream fos = new FileOutputStream(outputFileName);
	        fos.getChannel().transferFrom(rbc, 0, 1 << 27);
	        isSongDownloaded = true;
		} catch (SocketTimeoutException sock)
		{
		}
		catch (Exception e)
		{
		}
		return isSongDownloaded;
	}
	

}
