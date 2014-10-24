package us.jaaga.demovote.helper;

import java.io.IOException;

import us.jaaga.demovote.LoginActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncToken extends AsyncTask<Void, Void, String>{
	
	LoginActivity mActivity;
	//String mScope;
	String mEmail;
	String mToken;
	private static final String SCOPE = "audience:server:client_id:1093155095616-b90oicomnsltkfgc72na1l80nfv890td.apps.googleusercontent.com";
	private static final String TAG = "AsyncTaskToken";
	//url for sending token to th backend for verification
	private static final String url = "https://jaagademovote.herokuapp.com/api/android/authorize";
	static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1001;
	static String REQUEST_CODE_403;
	//static String REQUEST_CODE_401;
	static String sendbackToken;
	int count = 0;
	
	
	public String ResponseData;
	ProgressDialog pDialog;
	
	public AsyncToken(LoginActivity activity, String Email){
		
		Log.i(TAG,"Local variables are set from received arguments");
		
		this.mActivity = activity;
		//this.mScope = Scope;
		this.mEmail = Email;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		pDialog = new ProgressDialog(mActivity);
		pDialog.setMessage("Please wait...");
		pDialog.setCancelable(false);
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(Void... arg0) {
		
			try{
				
				Log.i(TAG,"fetchToken is called");
				String token = fetchToken();
				
				if(token != null){
					
					//Stuff to do with the token comes here - (Consider sending it to the backend;
					mToken = token;
					Log.i(TAG,"Token is stored in Global Variable");
					
					ServiceHandler sh = new ServiceHandler();
					
					String response = sh.tokenAuthenticate(mToken, url);
					
					if(response == REQUEST_CODE_403 ){
							
						invalidate();
						sendbackToken = "";
						// onActivityResult need to start !!
						
					}else{
						// TODO Process response - JSONData and check Expiry !! 
						//TODO if not expired  sendback token, if expired - fetchtoken
						sendbackToken = mToken;
						
					}
				}//TODO Define else if condition of token!=null
			
				
			}catch(IOException e){
				e.printStackTrace();
			}
		
		
		return sendbackToken;
	}
	
	@Override
	protected void onPostExecute(String result) {
		//super.onPostExecute(result);
		if (pDialog.isShowing())
			pDialog.dismiss();
		mActivity.setToken(result);
		
	}
	
	
	private String fetchToken() throws IOException {
		
		 try {
	          return GoogleAuthUtil.getToken(mActivity, mEmail, SCOPE);
	      } catch (GooglePlayServicesAvailabilityException playEx) {
	          // GooglePlayServices.apk is either old, disabled, or not present.
	          playEx.getConnectionStatusCode();
	      } catch (UserRecoverableAuthException userRecoverableException) {
	          // Unable to authenticate, but the user can fix this.
	          // Forward the user to the appropriate activity.
	          mActivity.startActivityForResult(userRecoverableException.getIntent(), REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
	      } catch (GoogleAuthException fatalException) {
	          //onError("Unrecoverable error " + fatalException.getMessage(), fatalException);
	    	  fatalException.printStackTrace();
				Log.i(TAG,"fataException found");
	      } catch(RuntimeException e){
	    	  
	    	  e.printStackTrace();
	      } catch(IOException e){
	    	  
	    	  e.printStackTrace();
	      }
		
	      return null;
	}
	
	private void invalidate() throws IOException{
		
		try{
			
			GoogleAuthUtil.invalidateToken(mActivity, mToken);
		} catch(RuntimeException e){
			
			e.printStackTrace();
		}
	}

	
}
