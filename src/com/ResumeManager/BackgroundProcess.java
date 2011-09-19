package com.ResumeManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class BackgroundProcess {

	private static String URL;
	static ArrayList<String> data_received = new ArrayList<String>();
	static String result[];
	static int Number_of_stmt = 0;

	public static void makeConnection(String username, String password) {
		// URL = "http://localhost/authetication_page.php?user=" + username +
		// "&pass=" + password;
		// Piggyback the connection and socket timeout parameters onto the
		// HttpPost request
		// try{
		// HttpParams httpParameters = new BasicHttpParams();
		// int timeoutConnection = 10000;
		// HttpConnectionParams.setConnectionTimeout(httpParameters,
		// timeoutConnection);
		// int timeoutSocket = 25000;
		// HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

		URL = "http://10.42.43.1/Android/authetication_page.php";

		try {
			System.out.println("reached the background class");
			HttpClient client = new DefaultHttpClient();
			HttpPost PostRequest = new HttpPost(URL);
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params);
			PostRequest.setEntity(ent);

			HttpResponse ResponsePost = client.execute(PostRequest);// connection
																	// established
																	// actuallly....
			int Response_code = ResponsePost.getStatusLine().getStatusCode();

			System.out.println("After the request" + Response_code);

			if (Response_code == HttpStatus.SC_OK) 
			{
				Log.d("connection done", "in Background process");
				if (ResponsePost.getEntity() != null) 
				{
					String receivedCode = "SUCC";
					Log.d("connection done", "some data received");

					InputStream in = ResponsePost.getEntity().getContent();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder str = new StringBuilder();
					
					String line = null;
					int i = 0;
					try {
						while ((line = reader.readLine()) != null) {
							data_received.add(line);
							//str.append(line + "\n");
							i++;
//							System.out.print(str);
							}
					in.close();
						// result[0] = str.toString();
						// System.out.print(result);
						//System.out.print(result);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			 return;
			 } 
			
			else
			{
				result[0] = "server_error";
			return;
			}
		} catch (Exception e) {
			result[0] = "Error";
			e.printStackTrace();
		}
	}

}
