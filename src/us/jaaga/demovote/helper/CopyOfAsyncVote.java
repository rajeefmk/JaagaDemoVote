package us.jaaga.demovote.helper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import us.jaaga.demovote.ProjectDetail;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class CopyOfAsyncVote extends AsyncTask<String, Void, Integer> {
	
	ProjectDetail mProjectDetail;
	String delivId;
	String mToken, value;
	ProgressDialog pDialog;
	JSONObject newJSONObject;
	int voteResponse;
	private static String url = "http://192.168.3.103:3000/api/v1/votes";
	private static final String TAG = "AsyncVote";
	
	public CopyOfAsyncVote(ProjectDetail activity,String Id, String token){
		
		mProjectDetail = activity;
		delivId = Id;
		mToken = token;
		Log.i(TAG,"token as added to mToken");
		
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(mProjectDetail);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		
		DefaultHttpClient mHttpClient = new DefaultHttpClient();
		//HttpEntity mHttpEntity;
		HttpResponse mHttpResponse;
		
		String value = params[0];
		
		if(value == "false"){
			try{
				newJSONObject.put("_id", delivId);
				newJSONObject.put("vote",false);
			}catch(JSONException e){
				
				Log.i(TAG, "JSON Making exception");
				e.printStackTrace();
			}
		}
		
		else if(value == "true"){
			try{
				newJSONObject.put("_id", delivId);
				newJSONObject.put("vote", true);
			}catch(JSONException e){
				
				Log.i(TAG, "JSON Making exception");
				e.printStackTrace();
		}
			
		try{
			
			String jsonString = newJSONObject.toString();
			StringEntity se = new StringEntity(jsonString);
			
			HttpPost mHttpPost = new HttpPost(url);
			mHttpPost.setHeader("Authorization", mToken);
			mHttpPost.setHeader("Content-type","json");
			mHttpPost.setEntity(se);
			
			mHttpResponse = mHttpClient.execute(mHttpPost);
			
			//HttpEntity mHttpEntity = null;
			//HttpResponse mHttpResponse = null;
			
			//mHttpEntity = mHttpResponse.getEntity();
			
			voteResponse = mHttpResponse.getStatusLine().getStatusCode();
			
			
		}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//CopyOfServiceHandler sh = new CopyOfServiceHandler(mToken);
		//Log.i(TAG, "mToken is added to ServiceHandler contruct");
		//int voteReponse = sh.makeServiceCall(url,value,delivId,ServiceHandler.GET);
		
		return voteResponse;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		if (pDialog.isShowing())
			pDialog.dismiss();
		
		if(result == 200){
			
			mProjectDetail.setButtonChange("Hide");
		}
		else{
			
			mProjectDetail.setButtonChange("Error");
		}
		
		
	}
	

}
