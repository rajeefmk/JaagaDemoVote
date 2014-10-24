package us.jaaga.demovote;


import us.jaaga.demovote.helper.AsyncToken;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
	static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
	private static final String DEBUG_TAG = "NetworkStatus";
	private static final String TAG = "LoginActivity";
	
	SignInButton mSignInButton;
	
	public String mEmail;
	//String mToken;

	ProgressBar pb;
	
	SharedPreferences mSharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		
		mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
		String test_token = mSharedPreferences.getString("token", null);
		if(test_token != null){
			Log.i(TAG, "Skipping signIn since token is present");
			Intent mIntent = new Intent(LoginActivity.this, StudentListActivity.class);
		    startActivity(mIntent);
			finish();
		}
		
		getActionBar().setTitle(R.string.login_page);
		
		
			
		
		
		mSignInButton = (SignInButton) findViewById(R.id.btn_sign_in);
		
		mSignInButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				getUsername();
				Log.i(TAG,"getUsername is called");
				
			}
		});
		
		
		/*if(mToken != null){
			
			
			Toast.makeText(LoginActivity.this, "token received", Toast.LENGTH_LONG).show();
		    runIntent();
			
		}
		
		else if(mToken == null){
			
			Toast.makeText(LoginActivity.this, "Error Signing In. Please Try Again", Toast.LENGTH_LONG).show();
		}*/
		
	}
	
	public void setToken(String Token){
		
		mSharedPreferences = getSharedPreferences("demo_vote", MODE_PRIVATE);
		Editor mEditor = mSharedPreferences.edit();
		
		mEditor.putString("token", Token);
		mEditor.commit();
		
		if(Token!= null){
			
			runIntent();
		}
		else{
			
			Toast.makeText(LoginActivity.this,"Please Login Again", Toast.LENGTH_LONG).show();
		}
		
	}

	protected void runIntent(){
	
		Log.i(TAG,"runIntent is called");
		Intent mIntent = new Intent(LoginActivity.this, StudentListActivity.class);
	    startActivity(mIntent);
	}
	
	private void getUsername(){
		
		if(mEmail == null){
			Log.i(TAG,"pickUserAccount is called form getUsername");
			pickUserAccount();
			
		}else {
			
			if(isDeviceOnline()){
				
				new AsyncToken(LoginActivity.this,mEmail).execute();
				//new AsyncToken().execute(mEmail,LoginActivity.this);
				Log.i(TAG,"AsyncToken Async task is passed arguments from getUsername");
			}
		}
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

	private void pickUserAccount() {
	    String[] accountTypes = new String[]{"com.google"};
	    Intent intent = AccountPicker.newChooseAccountIntent(null, null,
	            accountTypes, false, null, null, null, null);
	    startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REQUEST_CODE_PICK_ACCOUNT){
			
			if(resultCode == RESULT_OK){
				
				mEmail = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
				
				mSharedPreferences = getSharedPreferences("email", MODE_PRIVATE);
				    Editor mEditor = mSharedPreferences.edit();
				    mEditor.putString("email", mEmail);
				    mEditor.commit();
				//ForTokenPOJO.setEmail(mEmail);
				getUsername();
			}
			
			else if(resultCode == RESULT_CANCELED){
				
				Toast.makeText(this, "Please pick an account", Toast.LENGTH_LONG).show();
			}
			
			
		} else if((requestCode == REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR) && resultCode == RESULT_OK) {
	      
			// Receiving a result that follows a GoogleAuthException, try auth again
	        getUsername();
		
		 }
	}


}
