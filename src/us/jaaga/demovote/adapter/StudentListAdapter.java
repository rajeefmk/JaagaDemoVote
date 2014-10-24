package us.jaaga.demovote.adapter;

import java.util.ArrayList;

import us.jaaga.demovote.R;
import us.jaaga.demovote.models.StudentListData;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class StudentListAdapter extends ArrayAdapter<StudentListData>  {
	
	private ArrayList<StudentListData> studentList;
	private LayoutInflater mLayoutInflater;

	public StudentListAdapter(Activity activity, ArrayList<StudentListData> List) {
		super(activity,R.layout.student_row, List);
		
		mLayoutInflater = activity.getWindow().getLayoutInflater();
		studentList = List;
	}

	public StudentListAdapter (Activity activity, String[] str){
		super(activity, R.layout.student_row);
		
		mLayoutInflater = activity.getWindow().getLayoutInflater();
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = mLayoutInflater.inflate(R.layout.student_row, parent, false);
		StudentListData currentStudent = studentList.get(position);
		
		//Name of Student
		TextView studentName = (TextView) row.findViewById(R.id.studentName);
		studentName.setText(currentStudent.getName());
		
		//Total Deliverables
		TextView totalDeliv = (TextView) row.findViewById(R.id.projectNumbers);
		totalDeliv.setText("Deliverables" + ": " + currentStudent.getTotalDeliverables());
		
		//Setting Picture from url
		// TODO Inflate image url here . Possible use of AsyncImage needed to get image from url
		//TODO check with picasa library for loading image
		return row;
	}

}
