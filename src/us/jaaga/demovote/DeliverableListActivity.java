package us.jaaga.demovote;

import java.util.ArrayList;

import us.jaaga.demovote.adapter.DeliverableListAdapter;
import us.jaaga.demovote.helper.AsyncDeliv;
import us.jaaga.demovote.models.ProjectListData;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class DeliverableListActivity extends ListActivity {
	
	private static final String DEBUG_TAG = "NetworkStatus";
	
	//TODO Send Upvote and DownVote data to next activity.
	// TODO Pull to Refresh in this and in Project Detail activity

	//ArrayList<StudentListData> studentListData = new ArrayList<StudentListData>();
	ArrayList<ProjectListData> projectListData = new ArrayList<ProjectListData>();
	DeliverableListAdapter mDeliverableListAdapter;
	String user_id,name;
	Intent mIntent;
	boolean votingStatus, delivStatus;
	
	private static final String TAG = "DeliverableListActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.deliverable_list);
		mIntent = getIntent();
		
		if(mIntent.getExtras().getBoolean("Network_key")){
			
			SharedPreferences mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
			String test_token = mSharedPreferences.getString("token", null);
			Log.i(TAG, "Token is loaded");
			
			//Intent mIntent = getIntent();
			
			user_id = getIntent().getStringExtra("user_id");
			name = getIntent().getStringExtra("name");
			getActionBar().setTitle(name+"'s Deliverables");
			
			Log.i(TAG, "unique user obtained from intent");
			
			AsyncDeliv mAsyncDeliv = new AsyncDeliv(this,user_id,test_token );
			Log.i(TAG, "Context,user id and token is sent to AsyncDeliv");
			
			mAsyncDeliv.execute();
			Log.i(TAG, "AsyncDeliv is executed");
			
		}else{
			
			Toast.makeText(getApplicationContext(), "O Boy !! There ain't no D-E-L-I-V-E-R-A-B-L-E", Toast.LENGTH_LONG).show();
			
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		
		
			ProjectListData mProjectListData = (ProjectListData) getListAdapter().getItem(position);
			
			String projectId = mProjectListData.getDeliverableId();
			String projectName = mProjectListData.getDeliverableTitle();
			String projectDescription = mProjectListData.getDeliverablesDescription();
			votingStatus = mProjectListData.isVotingStatus();
			delivStatus = mProjectListData.getDeliverableStatus();
			int upVoteCount = mProjectListData.getTotalUpVote();
			int downVoteCount = mProjectListData.getTotalDownVote();
			
			
			Intent mIntent = new Intent(DeliverableListActivity.this, ProjectDetail.class);
			
			mIntent.putExtra("id", projectId);
			mIntent.putExtra("name", projectName);
			mIntent.putExtra("description", projectDescription);
			mIntent.putExtra("upVoteCount", upVoteCount);
			mIntent.putExtra("downVoteCount", downVoteCount);
			mIntent.putExtra("votingStatus", votingStatus);
			mIntent.putExtra("delivStatus", delivStatus);
			
			startActivity(mIntent);
		
		
		//StudentListData mStudentListData = (StudentListData) getListAdapter().getItem(position);
		//projectListData = mStudentListData.getDelivList();
		
		//Bundle mBundle = new Bundle();
		//mBundle.putSerializable("project", projectListData);
		//mIntent.putParcelableList("key", projectListData);
		
		//mIntent.putExtra("key", (Serializable)projectListData);
		
		/*SharedPreferences mSharedPreference = getSharedPreferences("delivdata", MODE_PRIVATE);
		Editor mEditor = mSharedPreference.edit();
			
		//mEditor.putString("deliv_data", ObjectSe)
*/		
		
		
		
	}
	
	public void dataDisplay(ArrayList<ProjectListData> data){
		
		mDeliverableListAdapter = new DeliverableListAdapter(this, data);
		setListAdapter(mDeliverableListAdapter);
		
	}
	
private boolean isDeviceOnline() {
		
		ConnectivityManager connMgr = (ConnectivityManager) 
		        getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI); 
		boolean isWifiConn = networkInfo.isConnected();
		networkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = networkInfo.isConnected();
		Log.d(DEBUG_TAG, "Wifi connected: " + isWifiConn);
		Log.d(DEBUG_TAG, "Mobile connected: " + isMobileConn);
		
		boolean network;
		
		if(isWifiConn == true){
			
			network = true;
			Log.i(TAG,"network is set true because of isWifiConn");
		}
		else if(isMobileConn == true){
			
			network = true;
			Log.i(TAG,"network is set true because of isMobileConn");
		}
		else {
			 network = false;
			 Log.i(TAG,"network is set false");
		}
		
		return network;
	}
}
