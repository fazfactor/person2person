/**
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.angelhack.person2person;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.GoogleAuthUtil;

/**
 * Display personalized greeting. This class contains boilerplate code to
 * consume the token but isn't integral to getting the tokens.
 */
public abstract class AbstractGetNameTask extends AsyncTask<Void, Void, Void> {
	private static final String TAG = "TokenInfoTask";
	protected Activity mActivity;
   public static String GOOGLE_USER_DATA="No_data";
	protected String mScope;
	protected String mEmail;
	protected int mRequestCode;
	ProgressDialog dialog;
	
	ImageView profile;

	AbstractGetNameTask(Activity activity, String email, String scope) {
		this.mActivity = activity;
		this.mScope = scope;
		this.mEmail = email;
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		
		dialog = new ProgressDialog(mActivity, R.style.StyledDialog);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(" Downloading in Progress, Please Wait....");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
	}

	@Override
	protected Void doInBackground(Void... params) {
		try {
			fetchNameFromProfileServer();

		} catch (IOException ex) {
			onError("Following Error occured, please try again. "
					+ ex.getMessage(), ex);
		} catch (JSONException e) {
			onError("Bad response: " + e.getMessage(), e);
		}
		return null;
	}

	protected void onError(String msg, Exception e) {
		if (e != null) {
			Log.e(TAG, "Exception: ", e);
		}
	}

	/**
	 * Get a authentication token if one is not available. If the error is not
	 * recoverable then it displays the error message on parent activity.
	 */
	protected abstract String fetchToken() throws IOException;

	/**
	 * Contacts the user info server to get the profile of the user and extracts
	 * the first name of the user from the profile. In order to authenticate
	 * with the user info server the method first fetches an access token from
	 * Google Play services.
	 * @return 
	 * @return 
	 * 
	 * @throws IOException
	 *             if communication with user info server failed.
	 * @throws JSONException
	 *             if the response from the server could not be parsed.
	 */
	private void fetchNameFromProfileServer() throws IOException, JSONException {
		String token = fetchToken();
		URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo?access_token="+ token);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		int sc = con.getResponseCode();
		if (sc == 200) {
			InputStream is = con.getInputStream();
			GOOGLE_USER_DATA = readResponse(is);
			is.close();

			/*Intent intent=new Intent(mActivity,Socialmedia_Activity.class);
			intent.putExtra("email_id", mEmail);
			mActivity.startActivity(intent);
			mActivity.finish();*/

			
			return;
		} else if (sc == 401) {
			GoogleAuthUtil.invalidateToken(mActivity, token);
			onError("Server auth error, please try again.", null);
			//Toast.makeText(mActivity, "Please try again", Toast.LENGTH_SHORT).show();
			//mActivity.finish();
			return;
		} else {
			onError("Server returned the following error code: " + sc, null);
			return;
		}
	}

	/**
	 * Reads the response from the input stream and returns it as a string.
	 */
	private static String readResponse(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] data = new byte[2048];
		int len = 0;
		while ((len = is.read(data, 0, data.length)) >= 0) {
			bos.write(data, 0, len);
		}
		return new String(bos.toByteArray(), "UTF-8");
	}
	
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		

		try {
			System.out.println("On Home Page***"+ AbstractGetNameTask.GOOGLE_USER_DATA);
			JSONObject profileData = new JSONObject(
					AbstractGetNameTask.GOOGLE_USER_DATA);

			if (profileData.has("picture")) {
				SocialMedia_New.userImageUrl = profileData.getString("picture");
				
			}
			if (profileData.has("name")) {
				SocialMedia_New.textName = profileData.getString("name");
		//		SocialMedia_New.textViewName.setText(SocialMedia_New.textName);
			}
			
			
			
			dialog.dismiss();
			
			final Dialog alert_dialog=new Dialog(mActivity);
    		alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    		alert_dialog.setContentView(R.layout.dialog_custom);
    		
    		TextView content=(TextView) alert_dialog.findViewById(R.id.content_tv_id);
    		TextView ok=(TextView) alert_dialog.findViewById(R.id.ok_tv_id);
    		 profile=(ImageView) alert_dialog.findViewById(R.id.image_id);
    		 
    		 new GetImageFromUrl().execute(SocialMedia_New.userImageUrl);
  		
    		content.setText(" Welcome  "+SocialMedia_New.textName);
    		ok.setText("Stay Connect with Us..!");
    		
    		ok.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					
					Intent intent = new Intent(mActivity,Home_Activity.class);
					mActivity.startActivity(intent);
					
					mActivity.finish();
					
				}
			});
    		
    		alert_dialog.show();
			
			

	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	}
	
	
	public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
			}
			return map;
		}

		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			profile.setImageBitmap(result);
		}

		// Creates Bitmap from InputStream and returns it
		private Bitmap downloadImage(String url) {
			Bitmap bitmap = null;
			InputStream stream = null;
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			try {
				stream = getHttpConnection(url);
				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return bitmap;
		}

		// Makes HttpURLConnection and returns InputStream
		private InputStream getHttpConnection(String urlString)
				throws IOException {
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stream;
		}
	}
}