package us.jaaga.demovote.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class CopyOfServiceHandler {

	static String response = null;
	static String tokenResponse = null;
	static int voteResponse;
	static int tokenResponseCode;
	public final static int GET = 1;
	public final static int POST = 2;
	//static String REQUEST_CODE_401;
	static String REQUEST_CODE_403;
	String mToken;
	
	private static final String TAG = "ServiceHandler";
	public CopyOfServiceHandler(String token) {
		
		mToken = token;
		Log.i("ServiceHandler", "token is added to mToken");
	}
	
	public CopyOfServiceHandler() {
		
	}	
	
	
	//POST request for Voting
	public int makeServiceCall(String url, String object,String delivId, int method) {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		try {
			
			HttpPost mHttpPost = new HttpPost(url);
			mHttpPost.setHeader("Authorization",mToken);
			mHttpPost.setEntity(new StringEntity(delivId));
			mHttpPost.setEntity(new StringEntity(object));
			
			HttpResponse mHttpResponse = httpClient.execute(mHttpPost);
			
			//HttpEntity mHttpEntity = null;
			//HttpResponse mHttpResponse = null;
			
			HttpEntity mHttpEntity = mHttpResponse.getEntity();
			response = EntityUtils.toString(mHttpEntity);
			
			voteResponse = mHttpResponse.getStatusLine().getStatusCode();
			
			
				
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return voteResponse;

	}

	private HttpPost createPOSTForJSONObject(JSONObject object, String url) {
		HttpPost mHttpPost = new HttpPost(url);
		mHttpPost.setEntity(createStringEntity(object));
		
		return mHttpPost;
	}

	private HttpEntity createStringEntity(JSONObject object) {
		StringEntity se = null;
		try{
			
			se = new StringEntity(object.toString(), "UTF-8");
			se.setContentType("application/json; charset=UTF-8");
		} catch(UnsupportedEncodingException e){
			
			Log.e(TAG, "Failed to create StringEntity", e);
			e.printStackTrace();
		}
		
		return se;
	}
}


