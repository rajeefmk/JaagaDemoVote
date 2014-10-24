package us.jaaga.demovote.helper;

import org.json.JSONException;
import org.json.JSONObject;

import us.jaaga.demovote.ProjectDetail;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncVote extends AsyncTask<String, Void, Integer> {
	
	ProjectDetail mProjectDetail;
	String delivId;
	String mToken;
	ProgressDialog pDialog;
	JSONObject mJSONObject;
	private static String url = "https://jaagademovote.herokuapp.com/api/v1/votes";
	private static final String TAG = "AsyncVote";
	
	public AsyncVote(ProjectDetail activity,String Id, String token){
		
		mProjectDetail = activity;
		delivId = Id;
		if(delivId != null){
			Log.i(TAG,"deliverable id is added");
		}
		else{
			Log.i(TAG, "id is empty");
		}
		mToken = token;
		Log.i(TAG,"token as added to mToken");
		
	}
	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(mProjectDetail);
		pDialog.setMessage("Please wait...");
		Log.i("TAG", "DialogMessage set");
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	@Override
	protected Integer doInBackground(String... params) {
		mJSONObject = new JSONObject();
		String value = params[0];
		Log.i(TAG,"string value is stored");
		
		if(value == "false"){
			
			try {
				
				mJSONObject.put("vote",false);
				mJSONObject.put("_id", delivId);
				
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
			
			
		}else if(value == "true"){
					
			try{
				mJSONObject.put("vote", true);
				mJSONObject.put("_id",delivId);
				
			}catch(JSONException e){
				
				e.printStackTrace();
			}
		}
		
		
		ServiceHandler sh = new ServiceHandler(mToken);
		Log.i(TAG, "mToken is added to ServiceHandler contruct");
		int voteReponse = sh.makeServiceCall(url,mJSONObject,ServiceHandler.POST);
		
		return voteReponse;
	}
	
	/*@Override
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
		
		
	}*/
	

}
