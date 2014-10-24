package us.jaaga.demovote.helper;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.jaaga.demovote.DeliverableListActivity;
import us.jaaga.demovote.models.ProjectListData;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncDeliv extends AsyncTask<Void, Void, ArrayList<ProjectListData>> {

	private static String url = "https://jaagademovote.herokuapp.com/api/v1/deliverables?user=";
	private static String url_votes = "&populate=votes";
	DeliverableListActivity mDeliverableList;
	ProgressDialog pDialog;
	
	private static String user_id;
	ArrayList<ProjectListData> delivData = new ArrayList<ProjectListData>();
	
	//JSON Node names for Deliverables Data
			
			private static final String TAG_DELIVERABLES_ID = "_id";	
			private static final String TAG_DELIVERED_STATUS = "delivered";
			private static final String TAG_DELIVERABLES_DESCRIPTION = "description";
			private static final String TAG_DELIVERABLES_TITLE = "title";
			private static final String TAG_VOTING_STATUS = "votingopen";
			
			private static final String TAG_VOTE_DETAILS = "votes";
			
	
			private static final String TAG = "AsyncDeliv";
			
			public int totalDeliv;
			String mToken;
	
	public AsyncDeliv(DeliverableListActivity activity, String userid, String token){
		
		mDeliverableList = activity;
		Log.i(TAG, "context set from received DeliverableListActivity");
		
		user_id = userid;
		Log.i(TAG, "userid is set from constructor recieved value");
		
		mToken = token;
		Log.i(TAG, "Token received and is set");
	}	
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pDialog = new ProgressDialog(mDeliverableList);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	@Override
	protected ArrayList<ProjectListData> doInBackground(Void... params) {
		
		String final_url = url+user_id+url_votes ;
		ServiceHandler sh = new ServiceHandler(mToken);
		String jsonData = sh.makeServiceCall(final_url, ServiceHandler.GET);
		
		try{
			JSONArray mainArray = new JSONArray(jsonData);
			
			if(mainArray.length() != 0 ){
				
				
				for(int i=0; i < mainArray.length(); i++){
					
					ProjectListData newData = new ProjectListData();
					Log.i(TAG, "ProjectListData model created for deliverable" + i);
					
					JSONObject delivObjects = mainArray.getJSONObject(i);
					
						String deliv_id = delivObjects.getString(TAG_DELIVERABLES_ID);
						newData.setDeliverableId(deliv_id);
						
						boolean deliv_status = delivObjects.getBoolean(TAG_DELIVERED_STATUS);
						newData.setDeliverableStatus(deliv_status);
						
						String deliv_descr = delivObjects.getString(TAG_DELIVERABLES_DESCRIPTION);
						newData.setDeliverablesDescription(deliv_descr);
						
						String deliv_title = delivObjects.getString(TAG_DELIVERABLES_TITLE);
						newData.setDeliverableTitle((i+1) + "."+deliv_title);
						
						
						boolean voting_status = delivObjects.getBoolean(TAG_VOTING_STATUS);
						newData.setVotingStatus(voting_status);
						
						//JSONArray mJSONArrayVote = new JSONArray(TAG_VOTE_DETAILS);
						JSONArray mJSONArrayVote = delivObjects.getJSONArray(TAG_VOTE_DETAILS);
						
							int upVoteCounter=0;
							int downVoteCounter=0;
							
							if(mJSONArrayVote.length() != 0){
								
								for(int j=0; j < mJSONArrayVote.length(); j++){
									
									JSONObject mJSONObjectVote = mJSONArrayVote.getJSONObject(j);
									
									if(mJSONObjectVote.getBoolean("vote") == true){
										
										upVoteCounter = upVoteCounter + 1;
										
									}else{
										
										downVoteCounter = downVoteCounter + 1;
									}
								}
								
							}
							
						newData.setTotalVotes(mJSONArrayVote.length());
						newData.setTotalUpVote(upVoteCounter);
						newData.setTotalDownVote(downVoteCounter);
							
						delivData.add(newData);
				}
				
			}else{
					
					ProjectListData newData = new ProjectListData();
					//newData.setNoDeliverableMessage("You have no past or upcoming deliverables. You better vacate ASAP");
					delivData.add(newData);
				}
					
			
		}catch(JSONException e){
			
			e.printStackTrace();
		}catch(RuntimeException e){
			
			e.printStackTrace();
		}
		
		return delivData;
	}
	
	@Override
	protected void onPostExecute(ArrayList<ProjectListData> result) {
		super.onPostExecute(result);
		if (pDialog.isShowing())
			pDialog.dismiss();
		
		mDeliverableList.dataDisplay(result);
	}

}
