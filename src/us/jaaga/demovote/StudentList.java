package us.jaaga.demovote;

import android.app.ListActivity;
import android.os.Bundle;

public class StudentList extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.students_list);
	}
}
