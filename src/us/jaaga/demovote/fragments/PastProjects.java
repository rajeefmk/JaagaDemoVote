package us.jaaga.demovote.fragments;



import us.jaaga.demovote.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PastProjects extends Fragment {

	//ArrayList<ProjectListData> data = new ArrayList<ProjectListData>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View fragmentView = inflater.inflate(R.layout.past_project, container, false);
		return fragmentView;
	}

	
	
	
}
