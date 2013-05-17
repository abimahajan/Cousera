package com.cousera.parser.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


public class DownloadFile
{
	public static void main(String[] args)
	{
		System.setProperty("http.proxyHost","115.112.231.106");
		System.setProperty("http.proxyPort","80");
		downloadStreamData("https://class.coursera.org/algo/lecture/download.mp4?lecture_id=20", "E:/OnMobile/SPU/PollenStudio/eclipse_umdb_backend/workspace/Cousera/Database/file.mp4");
	}
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
			downloadStreamData(entry.getValue(), fileToBeCreatedName+"/"+fileName);
			
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
	
	 private static void downloadStreamData(String url, String fileName)
	 {
	        URL tU;
			try {
				tU = new URL(url);
				 HttpURLConnection conn = (HttpURLConnection) tU.openConnection();

			        String type = conn.getContentType();
			        InputStream ins = conn.getInputStream();
			        FileOutputStream fout = new FileOutputStream(new File(fileName));
			        byte[] outputByte = new byte[4096];
			        int bytesRead;
			        int length = conn.getContentLength();
			        int read = 0;
			        while ((bytesRead = ins.read(outputByte, 0, 4096)) != -1) {
			            read += bytesRead;
			            System.out.println(read + " out of " + length);
			            fout.write(outputByte, 0, bytesRead);
			        }
			        fout.flush();
			        fout.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	       
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
