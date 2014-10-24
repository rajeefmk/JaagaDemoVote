package us.jaaga.demovote;

import org.json.JSONObject;

import us.jaaga.demovote.helper.AsyncVote;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ProjectDetail extends Activity{

	Intent mIntent;
	TextView delivName, delivDescription;
	int upVoteCount, downVoteCount;
	String delivId;
	String test_token;
	private static final String TAG = "ProjectDetail";
	
	JSONObject mJSONObject;
	AsyncVote mAsyncVote;
	//Button upVote,downVote;
	TextView currentVotedetail;
	boolean votingStatus, delivStatus;
	String selectedVote;
	TextView delivDownVoteCount;
	TextView delivUpVoteCount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_detail);
		
		//Token Retrieval from shared preference
		SharedPreferences mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
		test_token = mSharedPreferences.getString("token",null);
		if(test_token!= null){
			Log.i(TAG, "Token Added");
		}
		else{
			
			Log.i(TAG, "Token Empty");
		}
		
		
		//TextViews are inflated
		TextView delivName = (TextView) findViewById(R.id.delivName);
		TextView delivDescription = (TextView) findViewById(R.id.delivDescription);
		TextView delivStatusText = (TextView) findViewById(R.id.delivStatus);
		delivUpVoteCount = (TextView) findViewById(R.id.upvote);
		delivDownVoteCount = (TextView) findViewById(R.id.downvote);
		currentVotedetail = (TextView) findViewById(R.id.currentVoteDetail);
		
		//Obtaining data from previous activity
		mIntent = getIntent();
		
		//Setting value for TextView based on data intent data
		delivName.setText(mIntent.getStringExtra("name"));
		delivDescription.setText(mIntent.getStringExtra("description"));
		delivUpVoteCount.setText("Total Upvotes: " + mIntent.getIntExtra("upVoteCount", upVoteCount));
		delivDownVoteCount.setText("Total DownVotes: " + mIntent.getIntExtra("downVoteCount", downVoteCount));
		
		//Actionbar title
		getActionBar().setTitle(mIntent.getStringExtra("name"));
		
		votingStatus = mIntent.getExtras().getBoolean("votingStatus");
		delivStatus = mIntent.getExtras().getBoolean("delivStatus");
		if(delivStatus == true){
			
			delivStatusText.setText("Status: Delivered");
		}else{
			
			delivStatusText.setText("Status: Not Delivered");
		}
		
		
		
		/*//Inflating Button
		upVote = (Button) findViewById(R.id.upvoteButton);
		downVote = (Button) findViewById(R.id.downvoteButton);
		
		if(votingStatus == false){
			upVote.setVisibility(View.INVISIBLE);
			downVote.setVisibility(View.INVISIBLE);
			currentVotedetail.setText("Oh! Voting seems to be closed");
		}else{
			
			delivId = mIntent.getStringExtra("id");
			//Inflating Button Elements
			upVote = (Button) findViewById(R.id.upvoteButton);
			downVote = (Button) findViewById(R.id.downvoteButton);
			
			//mJSONObject = new JSONObject();
			try{
				//mJSONObject.put("vote",true);
				mJSONObject.put("_id", delivId);
				
			}catch(JSONException e){
				
				e.printStackTrace();
			}
			
			mAsyncVote = new AsyncVote(this, delivId, test_token);
			Log.i(TAG, "AsyncVote constructor is passed token");
			
			//when upvote button pressed
			upVote.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					selectedVote = "upvote";
					mAsyncVote.execute("true");
				}
			});
			//when downvote button is pressed
			downVote.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					selectedVote = "downvote";
					mAsyncVote.execute("false");
					
				}
			});
			
		}
		*/
		
	}
	
	/*public void setButtonChange(String value){
		
		if(value == "hide"){
			
			upVote.setVisibility(View.INVISIBLE);
			downVote.setVisibility(View.INVISIBLE);
			currentVotedetail.setText("Yay !! Thanks for voting");
			if(selectedVote == "upvote"){
				
			delivUpVoteCount.setText("Total Upvotes: " + (mIntent.getIntExtra("upVoteCount", upVoteCount)+1) );
			}
			
			else if(selectedVote == "downvote"){
				
			delivDownVoteCount.setText("Total DownVotes: " + (mIntent.getIntExtra("downVoteCount", downVoteCount)+1) );
			}
		}else{
			
			Toast.makeText(ProjectDetail.this, "Please Try Again", Toast.LENGTH_LONG).show();
			finish();
		}
			
	}*/
}
