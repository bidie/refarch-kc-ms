package it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.google.gson.Gson;

import ibm.labs.kc.model.Fleet;

public class TestFleetAPIsIT extends BaseIntegrationTest {

	 private String endpoint = "/fleets";
	 private String url = getBaseUrl() + endpoint;
	    
	@Test
	public void testGettingFleetsFromREST() throws InterruptedException {
		 System.out.println("Testing endpoint " + url);
	        int maxCount = 5;
	        int responseCode = 0;
	        for(int i = 0; (responseCode != 200) && (i < maxCount); i++) {
	          System.out.println("Response code : " + responseCode + ", retrying ... (" + i + " of " + maxCount + ")");
	          Thread.sleep(5000);
	          Client client = ClientBuilder.newClient();
		      Invocation.Builder invoBuild = client.target(url).request();
		      Response response = invoBuild.get();
		      if (response.hasEntity()) {
			      String fleetsAsString=response.readEntity(String.class);
			      Gson parser = new Gson();
			      Fleet[] fa = parser.fromJson(fleetsAsString,Fleet[].class);
			      assertNotNull(fleetsAsString);
			      for (Fleet f : fa) {
			    	  System.out.println(f.toString());
			      }
		      }
		      
		      responseCode = response.getStatus();
	        }
	        assertTrue("Incorrect response code: " + responseCode, responseCode == 200);
	}

}