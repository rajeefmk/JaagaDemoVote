package us.jaaga.demovote;

import java.util.ArrayList;

import us.jaaga.demovote.adapter.DeliverableListAdapter;
import us.jaaga.demovote.helper.AsyncDeliv;
import us.jaaga.demovote.models.ProjectListData;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class DeliverableList extends ListActivity {

	//ArrayList<StudentListData> studentListData = new ArrayList<StudentListData>();
	ArrayList<ProjectListData> projectListData = new ArrayList<ProjectListData>();
	DeliverableListAdapter mDeliverableListAdapter;
	String user_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.deliverable_list);
		
		SharedPreferences mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
		String test_token = mSharedPreferences.getString("token", null);
		
		//Intent mIntent = getIntent();
		
		user_id = getIntent().getStringExtra("user_id");
		
		AsyncDeliv mAsyncDeliv = new AsyncDeliv(this,user_id,test_token );
		mAsyncDeliv.execute();
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		//super.onListItemClick(l, v, position, id);
		
		ProjectListData mProjectListData = (ProjectListData) getListAdapter().getItem(position);
		
		String project_name = mProjectListData.getDeliverableName();
		String project_description = mProjectListData.getDeliverablesDescription();
		
		Intent mIntent = new Intent(DeliverableList.this, ProjectDetail.class);
		mIntent.putExtra("name", project_name);
		mIntent.putExtra("description", project_description);
		
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
}
