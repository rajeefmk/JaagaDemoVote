package us.jaaga.demovote.adapter;

import java.util.ArrayList;

import us.jaaga.demovote.R;
import us.jaaga.demovote.models.ProjectListData;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class DeliverableListAdapter extends ArrayAdapter<ProjectListData>  {
	
	private ArrayList<ProjectListData> projectList;
	private LayoutInflater mLayoutInflater;

	public DeliverableListAdapter(Activity activity, ArrayList<ProjectListData> data) {
		super(activity,R.layout.deliverable_row, data);
		
		mLayoutInflater = activity.getWindow().getLayoutInflater();
		projectList = data;
	}

	public DeliverableListAdapter (Activity activity, String[] str){
		super(activity, R.layout.deliverable_row);
		
		mLayoutInflater = activity.getWindow().getLayoutInflater();
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = mLayoutInflater.inflate(R.layout.deliverable_row, parent, false);
		ProjectListData currentProject = projectList.get(position);
		
		//Name of Deliverable
		String delivTitle = currentProject.getDeliverableTitle();
		TextView delivName = (TextView) row.findViewById(R.id.delivName);
		delivName.setText(delivTitle);
		
		//Voting Status of Deliverable
		boolean votingStatus = currentProject.isVotingStatus();
		TextView delivStatusView = (TextView) row.findViewById(R.id.delivStatus);
		if(votingStatus != false){
			delivStatusView.setText("Voting Status: Open");
		}
		else{
			delivStatusView.setText("Voting Status: Closed");
		}
		
		//No. of votes of Deliverable
		int voteCount = currentProject.getTotalVotes();
		TextView voteCountView = (TextView) row.findViewById(R.id.delivVote);
		voteCountView.setText("Total Votes: "+ voteCount);
		
		
		return row;
	}

}
