package com.angelhack.person2person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.angelhack.person2person.helperclass.NotificationHelper;
import com.angelhack.person2person.helperclass.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class Home_Activity extends  SherlockFragmentActivity {
	
	Typeface Roboto_bold,Roboto_light;

	
	Button download;
	
	ProgressDialog dialog;
	
	private NotificationHelper mNotificationHelper;
	NotificationManager mNotifyManager;
	NotificationCompat.Builder mBuilder;
	int id = 1;
	
	String file_name = "Android Animated Logo";
	
	String file_path="/sdcard/" + "Download/"+ "Android Videos" + file_name + ".mp4";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		
	//	Roboto_light = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf"); 
	//	Roboto_bold = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf"); 
		
		
		// custom action bar layout
		
		ActionBar bar = getSupportActionBar();
		getSupportActionBar().setDisplayOptions(bar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.actionbar_homepage);
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
		
		TextView title_head=(TextView)findViewById(R.id.content_title);
	    title_head.setText("PERSON 2 PERSON");
	    
	 //   title_head.setTypeface(Roboto_bold,Typeface.BOLD);

		// back action
		RelativeLayout action_back = (RelativeLayout) findViewById(R.id.back_button);
		action_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			//	finish();
				
			//	overridePendingTransition(R.anim.your_left_to_right, R.anim.your_right_to_left);

			}
		});
		
		
		
	
		download=(Button) findViewById(R.id.readmore_bt_id);
		
		mNotificationHelper = new NotificationHelper(Home_Activity.this);
		
		
		download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(Utils.isNetworkAvailable(Home_Activity.this))
			 	{
			 		new Getvideo(Home_Activity.this).execute();
			 	}
				
			}
		});
	
	}

public class Getvideo extends AsyncTask<Void, Integer, Void> {
		
		
		private Activity activity;

		public Getvideo(Activity activity) {
			
			this.activity = activity;

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			
			dialog = new ProgressDialog(activity, R.style.StyledDialog);
	        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        dialog.setMessage(" Fetching Informations, Please Wait....");
	        dialog.setIndeterminate(true);
	        dialog.setCanceledOnTouchOutside(false);
	        dialog.show();
			
			
			
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub


			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			
					new ApplicationForm_download(activity).execute();
				
		}
	}

	
	
	public class ApplicationForm_download extends AsyncTask<Void, Void, Void> {

		private Activity activity;

		public ApplicationForm_download(Activity activity) {
			this.activity = activity;
			
			

		}

		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			
			
			final Dialog alert_dialog=new Dialog(Home_Activity.this);
			alert_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alert_dialog.setContentView(R.layout.dialog_exit);
			alert_dialog.setCanceledOnTouchOutside(false);
			alert_dialog.show();
			
			
			TextView content=(TextView) alert_dialog.findViewById(R.id.tittle_tv_id);
			TextView yes=(TextView) alert_dialog.findViewById(R.id.yes_tv_id);
			TextView no=(TextView) alert_dialog.findViewById(R.id.no_tv_id);

			
			content.setText(Home_Activity.this.getResources().getString(R.string.dialog_tittle));
			yes.setText(Home_Activity.this.getResources().getString(R.string.dialog_yes));
			no.setText(Home_Activity.this.getResources().getString(R.string.dialog_no));
			
			yes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
				
					
					
				}
			});
			
			
			no.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					
					// Create the notification in the statusbar
					mNotificationHelper.createNotification();
					finish();
					
				}
			});
			   
	

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			
			Download(file_name, "http://antolabs.com/animation-video.mp4");

		
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			if(dialog.isShowing())
			{
				

				// downloading notification complete
				mNotificationHelper.completed();
			dialog.dismiss();
			}


			// Prepare intent which is triggered if the
			// notification is selected

			Intent toLaunch = new Intent();
			toLaunch.setAction(android.content.Intent.ACTION_VIEW);
			toLaunch.setDataAndType(Uri.fromFile(new File(file_path)), MimeTypeMap.getSingleton().getMimeTypeFromExtension("video/mp4")); // you can also change jpeg to other types
			PendingIntent contentIntent = PendingIntent.getActivity(activity,0, toLaunch, 0);

			mNotifyManager = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
			mBuilder = new NotificationCompat.Builder(activity);
			mBuilder.setContentTitle("Android Videos")
					.setContentText("SDcard/"+ "Download/"+file_name)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentIntent(contentIntent)
					.setAutoCancel(true);
			mBuilder.setProgress(0, 0, false);
			mNotifyManager.notify(id, mBuilder.build());
			

			
			
			



		}

	}

	// download operation
	public void Download(String file_name, String location) {
		try {
			URL url = new URL(location);

			// create the new connection

			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();

			// set up some things on the connection

			urlConnection.setRequestMethod("GET");

			urlConnection.setDoOutput(true);

			// and connect!

			urlConnection.connect();

			// set the path where we want to save the file

			// in this case, going to save it on the root directory of the

			// sd card.

			File SDCardRoot = new File("/sdcard/" + "Download/");

			// create a new file, specifying the path, and the filename

			// which we want to save the file as.

			File file = new File(SDCardRoot, "Android Videos"
					+ file_name + ".mp4");

			// this will be used to write the downloaded data into the file we
			// created

			FileOutputStream fileOutput = new FileOutputStream(file);

			// this will be used in reading the data from the internet

			InputStream inputStream = urlConnection.getInputStream();

			// this is the total size of the file

			int totalSize = urlConnection.getContentLength();

			// variable to store total downloaded bytes

			int downloadedSize = 0;

			// create a buffer...

			byte[] buffer = new byte[1024];

			int bufferLength = 0; // used to store a temporary size of the
									// buffer

			// now, read through the input buffer and write the contents to the
			// file

			while ((bufferLength = inputStream.read(buffer)) > 0)

			{

				// add the data in the buffer to the file in the file output
				// stream (the file on the sd card

				fileOutput.write(buffer, 0, bufferLength);

				// add up the size so we know how much is downloaded

				downloadedSize += bufferLength;

				int progress = (int) (downloadedSize * 100 / totalSize);

				// for notification download percent

				mNotificationHelper.progressUpdate(progress);

				// this is where you would do something to report the prgress,
				// like this maybe

				// updateProgress(downloadedSize, totalSize);

			}

			// close the output stream when done

			fileOutput.close();
			// catch some possible errors...
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




}

	


