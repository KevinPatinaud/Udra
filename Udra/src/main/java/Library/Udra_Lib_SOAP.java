package Library;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.*;

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
	
	
	
	



	
	protected static Udra createFromSOAPHTTPS(Udra result, String url, String body) {
		
		try {
			
			  // configure the SSLContext with a TrustManager
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        ctx.init(new KeyManager[0], new TrustManager[] {new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);

	        
	        HttpsURLConnection conn = (HttpsURLConnection) (new URL(url)).openConnection();
	        
	        
	        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
	        

	        //add reuqest header
	        conn.setRequestMethod("POST");

	        
	        // Send post request
	        conn.setDoOutput(true);
	        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
	            wr.writeBytes(body);
	            wr.flush();
	        }
	        

	        
	        
	        conn.setHostnameVerifier(new HostnameVerifier() {
	            @Override
	            public boolean verify(String arg0, SSLSession arg1) {
	                return true;
	            }
	        });
	        
	        

	        
	       	BufferedReader in = new BufferedReader( new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            
            result = result.createFromXML(response.toString());
	        
	        conn.disconnect();
	        
	        
		}catch(Exception e){ e.printStackTrace(); }
		
		
        //return result
        return result;

    }
	
	
	
	
	
	
	  private static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			// TODO Auto-generated method stub
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}

	    }
		
	
	
	
	
	
	
}
