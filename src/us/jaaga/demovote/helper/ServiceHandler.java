package us.jaaga.demovote.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class ServiceHandler {

	static String response = null;
	static String tokenResponse = null;
	static int voteResponse;
	static int tokenResponseCode;
	public final static int GET = 1;
	public final static int POST = 2;
	//static String REQUEST_CODE_401;
	static String REQUEST_CODE_403;
	String mToken;
	JSONObject mJson;
	private static final String TAG = "ServiceHandler";
	
	public ServiceHandler(String token) {
		
		if(token!=null){
			mToken = token;
			Log.i(TAG, "token is added to mToken" + mToken);
		}else{
			
			Log.i(TAG, "token empty");
		}
		
	}
	
	public ServiceHandler() {
		
	}	
	
	
	
	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * */
	public String makeServiceCall(String url, int method) {
		return this.makeServiceCall(url, method, null);
	}

	
	
	//Service call for authetication
	
	public String tokenAuthenticate(String token, String url){
		
		try{
			
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity mHttpEntity = null;
			HttpResponse mHttpResponse = null;
			
			HttpPost mHttpPost = new HttpPost(url);
			mHttpPost.setHeader("Authorization",token);
			
			mHttpResponse = httpClient.execute(mHttpPost);
			
			mHttpEntity = mHttpResponse.getEntity();
			
			tokenResponseCode = mHttpResponse.getStatusLine().getStatusCode();
			
			if(tokenResponseCode == 200 ){
				
				tokenResponse = EntityUtils.toString(mHttpEntity);
				
			}
			else if(tokenResponseCode == 403){
				
				tokenResponse = REQUEST_CODE_403;
				
			}/*else if(tokenResponseCode == 403){
				
				tokenResponse = REQUEST_CODE_403;
			}*/
			
			
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return tokenResponse;
		
	}
	
	/*
	 * Making service call
	 * @url - url to make request
	 * @method - http request method
	 * @params - http request params
	 * */
	
	public String makeServiceCall(String url, int method,
			List<NameValuePair> params) {
		try {
			// http client
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpEntity httpEntity = null;
			HttpResponse httpResponse = null;
			
			// Checking http request method type
			if (method == POST) {
				// adding post params
				/*if (params != null) {
					httpPost.setEntity(new UrlEncodedFormEntity(params));
				}*/
				HttpPost httpPost = new HttpPost(url);
				httpResponse = httpClient.execute(httpPost);

			} 
			else if (method == GET) {
				// appending params to url
				/*if (params != null) {
					String paramString = URLEncodedUtils
							.format(params, "utf-8");
					url += "?" + paramString;
				}*/
				
				HttpGet mHttpGet = new HttpGet(url);
				mHttpGet.setHeader("Authorization",mToken);
				httpResponse = httpClient.execute(mHttpGet);
				
				

			}
			
			httpEntity = httpResponse.getEntity();
			response = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;

	}

	
	//POST request for Voting
	public int makeServiceCall(String url, JSONObject object, int method) {
	 try {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost mHttpPost = new HttpPost(url);
		Log.i(TAG,"HttpPost initialized with url");
		
		String jsonString = object.toString();
		Log.i(TAG,"json Object converted to string");
		
		StringEntity se = new StringEntity(jsonString);
		se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
		
		//HttpPost mHttpPost = createPOSTForJSONObject(object,url);
		//Log.i(TAG, url);
			
		mHttpPost.setEntity(se);
		mHttpPost.setHeader("Authorization",mToken);
		mHttpPost.setHeader("Accept", "application/json");
		mHttpPost.setHeader("Content-Type", "application/json");
		Log.i(TAG,"Header set with token");
			
		//mHttpPost.setHeader("Content=type","json");
						
			
			
			
			
			//HTTP.CONTENT_TYPE, "application/json"
			
			//Log.i(TAG,"Entity is set with JsonString");
			
			//StringEntity se = new StringEntity("Hello Ansal !! What the hell is happening");
			//mHttpPost.setEntity(se);
			
			
			//mHttpPost.setParams("Hello Ansal !! What the hell is hapenning");
			
			HttpResponse mHttpResponse = httpClient.execute(mHttpPost);
			Log.i(TAG,"httppost is executed");
			
			//HttpEntity mHttpEntity = null;
			//HttpResponse mHttpResponse = null;
			
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			Log.i(TAG, "Entity Received"+mHttpEntity);
			
			response = EntityUtils.toString(mHttpEntity);
			Log.i(TAG,"converted response "+ response);
			
			voteResponse = mHttpResponse.getStatusLine().getStatusCode();
			Log.i(TAG,"statuscode "+voteResponse);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return voteResponse;

	}

	/*private HttpPost createPOSTForJSONObject(JSONObject object, String url) {
		HttpPost mHttpPost = new HttpPost(url);
		mHttpPost.setEntity(createStringEntity(object));
		
		return mHttpPost;
	}*/

	/*private HttpEntity createStringEntity(JSONObject object) {
		StringEntity se = null;
		try{
			
			se = new StringEntity(object.toString(), "UTF-8");
			se.setContentType("application/json; charset=UTF-8");
		} catch(UnsupportedEncodingException e){
			
			Log.e(TAG, "Failed to create StringEntity", e);
			e.printStackTrace();
		}
		
		return se;
	}*/
}


