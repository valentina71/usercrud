package it.zanichelli.garotta.usercrud.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import it.zanichelli.garotta.usercrud.*;
import it.zanichelli.garotta.usercrud.model.ListAccount;
import it.zanichelli.garotta.usercrud.model.Response;
import it.zanichelli.garotta.usercrud.reponse.MessageResponse;


public class GetByUsernameTest {
	@Test
	public void getCorrectlistAllUser() throws ParseException, IOException{
		final String username="admin";
		String url = "http://localhost:8080/usercrud/rest/account/getByUsername/"+username;
		BufferedReader in=null;
		try
		{
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			
			int responseCode = con.getResponseCode();
	
			if (responseCode==HttpURLConnection.HTTP_OK){
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();
				Response json = new ObjectMapper().readValue(response.toString(), Response.class);
				
				assertEquals(json.getCode(),MessageResponse.OK.getCode());
			}
			else
				assertTrue(false);
		}finally{if (in!=null)in.close();}
	}
	
	
}
