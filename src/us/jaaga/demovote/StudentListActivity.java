package us.jaaga.demovote;

import java.util.ArrayList;

import us.jaaga.demovote.adapter.StudentListAdapter;
import us.jaaga.demovote.helper.AsyncData;
import us.jaaga.demovote.models.StudentListData;
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

public class StudentListActivity extends ListActivity {

	private static final String DEBUG_TAG = "NetworkStatus";
	//Picasa library for image loading
	ArrayList<StudentListData> studentListData = new ArrayList<StudentListData>();
	//ArrayList<ProjectListData> projectListData = new ArrayList<ProjectListData>();
	StudentListAdapter mStudentListAdapter;
	private static final String TAG = "StudentListActivity";
	Intent mIntent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.students_list);
		
		getActionBar().setTitle(R.string.student_list_page);
		
		if(isDeviceOnline()){
		
			SharedPreferences mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
			String test_token = mSharedPreferences.getString("token", null);
			Log.i(TAG, "Token is loaded");
			
			AsyncData mAsyncData = new AsyncData(this, test_token);
			Log.i(TAG, "Async constructer has been loaded with activity and token");
			
			mAsyncData.execute();
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		Log.i(TAG, "Onclicked item is called");
		
		if(isDeviceOnline()){
			StudentListData mStudentListData = (StudentListData) getListAdapter().getItem(position);
			Log.i(TAG, "StudentListData model is initialized based on the object selected from list");
			
			if(!mStudentListData.isDeliverableExist()){
				
				mIntent = new Intent(StudentListActivity.this, DeliverableListActivity.class);
				mIntent.putExtra("Network_key", false);
				Log.i(TAG,"Network key is set false");
				
			}else{
			
				String user_id = mStudentListData.getId();
				String name = mStudentListData.getName();
				Log.i(TAG, "unique user id obtained from above objec & stored in local variable");
				
				mIntent = new Intent(StudentListActivity.this, DeliverableListActivity.class);
				mIntent.putExtra("user_id", user_id);
				mIntent.putExtra("name", name);
				Log.i(TAG, "unique user-id is added to intent");
				
				mIntent.putExtra("Network_key", true);
				Log.i(TAG,"Network key is set true");
				
			}
			
			startActivity(mIntent);
			Log.i(TAG, "Intent Is Started");
		}else{
			
			Toast.makeText(getApplicationContext(), "Not Connected to World Wide Web", Toast.LENGTH_LONG).show();
		}
		///projectListData = mStudentListData.getDelivList();
		
		//Bundle mBundle = new Bundle();
		//mBundle.putSerializable("project", projectListData);
		//mIntent.putParcelableList("key", projectListData);
		
		//mIntent.putExtra("key", (Serializable)projectListData);
		
		/*SharedPreferences mSharedPreference = getSharedPreferences("delivdata", MODE_PRIVATE);
		Editor mEditor = mSharedPreference.edit();
			
		//mEditor.putString("deliv_data", ObjectSe)
*/		
		
		
	}
	
	public void dataDisplay(ArrayList<StudentListData> data){
		
		mStudentListAdapter = new StudentListAdapter(this, data);
		Log.i(TAG, "Context and ArrayList obt from AsyncData passed to constructor of StudentListAdapter");
		
		setListAdapter(mStudentListAdapter);
		Log.i(TAG, "ListAdapter is set");
		
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
