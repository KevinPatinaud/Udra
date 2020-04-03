package Library;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import udra.Udra;

public class Udra_Lib_SOAP {

	public static Udra createFromSOAP(Udra result, String url , String soapAction, String body) {

		
		try {
			
        HttpURLConnection httpClient = (HttpURLConnection) new URL(url).openConnection();
        httpClient.setRequestProperty("SOAPAction", soapAction);
        httpClient.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

        //add reuqest header
        httpClient.setRequestMethod("POST");

        
        
        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(body);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + body);
        System.out.println("Response Code : " + responseCode);

       BufferedReader in = new BufferedReader( new InputStreamReader(httpClient.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            
            result = result.createFromXML(response.toString());

		}catch(Exception e){ e.printStackTrace(); }
		
		
        //return result
        return result;

    }
	
}
