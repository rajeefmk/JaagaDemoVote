package us.jaaga.demovote.helper;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import us.jaaga.demovote.StudentListActivity;
import us.jaaga.demovote.models.StudentListData;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncWrite extends AsyncTask<ArrayList<StudentListData>, Void, Void>{
	
	StudentListActivity mStudentListActivity;
	
	ArrayList<StudentListData> studentData = new ArrayList<StudentListData>();
	
	private static final String STUDENTSLIST_CACHE_FILE = "students_list.ser";
	
	public AsyncWrite(StudentListActivity mStudentListActivity) {
		//super();
		this.mStudentListActivity = mStudentListActivity;
	}


	@Override
	protected Void doInBackground(ArrayList<StudentListData>... params) {
		
		try{
			Thread.sleep(5000);
			Log.d("Thread Sleep Called", "Called");
			
			FileOutputStream fos = mStudentListActivity.openFileOutput(STUDENTSLIST_CACHE_FILE, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(studentData);
			oos.close();
			fos.close();
			
			Log.d("File Path",mStudentListActivity.getFileStreamPath(STUDENTSLIST_CACHE_FILE).getAbsolutePath().toString());
		}catch(Exception e){
			
			Log.d("Error in Asynch Task",""+e.toString());
		}
		
		return null;
	}
	
	

}
