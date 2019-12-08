package com.udemy.hltv_org;

import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class Hltv_livescore {

    private static JSONArray scoresArray = new JSONArray();

    static Document getHtml(String url) throws IOException {
    	String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
    	Document doc = Jsoup.connect("https://www.hltv.org/matches").userAgent(USER_AGENT).get();
        return doc;
    }
    
    static JSONObject createJsonObject(String teamOne, String teamTwo, String header) {
        JSONObject obj = new JSONObject();
        obj.put("Team №1", teamOne);
        obj.put("Team №2", teamTwo);
        obj.put("Match Header", header);
        return obj;
    }

    static void jsonToFile() throws IOException {
        FileWriter file = new FileWriter("hltv_livescore.json");
        file.write(scoresArray.toJSONString());
        file.close();
    }

    public static void main(String[] args) throws IOException {
		Document doc = getHtml("https://www.hltv.org/matches");
		int a = 1;
		Object OBJECT_HLTV = new Object();
		 try {
		  synchronized (OBJECT_HLTV) {
		   while (a>0) {for (Element result : doc.select("body > div.bgPadding > div > div.colCon > div.contentCol > div > div.live-matches")) {
	            String teamOne = result.text();
	            String header = result.text();
	            String teamTwo = result.text();
	            JSONObject scoreObject = createJsonObject(teamOne, teamTwo, header);
	            scoresArray.add(scoreObject);
	        }
			System.out.println(scoresArray);
	        jsonToFile();
		
			   OBJECT_HLTV.wait(10000);
		   }
		  }
		 } catch (InterruptedException ex) {
		   
		 }
		
}
}