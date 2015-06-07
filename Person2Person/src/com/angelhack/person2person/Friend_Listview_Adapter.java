package com.angelhack.person2person;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angelhack.person2person.helperclass.NotificationHelper;
import com.angelhack.person2person.helperclass.Utils;

public class Friend_Listview_Adapter extends ArrayAdapter<String> {

	private final Activity activity;
	private final String[] Title,Email,Phone;

	Typeface Roboto_medium, Roboto_bold;
	
	String[] profile,
			  telephone,
			  message,
			  whatsapp;
	
	TextView name;



	

	public Friend_Listview_Adapter(Activity activity, String[] Title,String[] Email,String[] Phone) {
		super(activity, R.layout.individual_listitem, Title);
		this.activity = activity;
		this.Title = Title;
		this.Email = Email;
		this.Phone = Phone;

	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		


		View rowView;

		rowView = inflater.inflate(R.layout.individual_listitem, parent,false);

		TextView name = (TextView) rowView.findViewById(R.id.name_tv_id);
		ImageView profile = (ImageView) rowView.findViewById(R.id.profile_iv_id);
		ImageView telephone = (ImageView) rowView.findViewById(R.id.telephone_tv_id);
		ImageView message = (ImageView) rowView.findViewById(R.id.message_tv_id);
		ImageView email = (ImageView) rowView.findViewById(R.id.email_tv_id);
		ImageView whatsapp = (ImageView) rowView.findViewById(R.id.whatsapp_tv_id);
		
		name.setText(Title[position]);
		
		telephone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone[position]));
				activity.startActivity(intent);
				
			}
		});
		
		whatsapp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Uri imageUri = Uri.parse("android.resource://com.angelhack.person2person/drawable/ic_launcher");
			
		
				Intent shareIntent = new Intent();
				shareIntent.setAction(Intent.ACTION_SEND);

				//Target whatsapp:
				shareIntent.setPackage("com.whatsapp");
				//Add text and then Image URI
			//	shareIntent.putExtra(Intent.EXTRA_TEXT, sharing_text);
				shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
				shareIntent.setType("image/jpeg");
				shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

			    
			    try {
			    	
					 activity.startActivity(shareIntent);
					 
					
			        
			    } catch (android.content.ActivityNotFoundException ex) {
			    	
			   	Toast.makeText(activity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
			    	
			    	
	
				
			}
				
			}
		});
		

		email.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, Email[position]);
		intent.putExtra(Intent.EXTRA_SUBJECT, "P2P Social");

		activity.startActivity(Intent.createChooser(intent, "Send Email"));
		
		}
		});


		message.setOnClickListener(new OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
		// TODO Auto-generated method stub
				
				Intent intentsms = new Intent( Intent.ACTION_VIEW, Uri.parse( "sms:" + Phone[position] ) );
	         //   intentsms.putExtra( "sms_body", "Test text..." );
	            activity.startActivity( intentsms );
		
	            //msg sent
			}
		});

		
		return rowView;
		

	}
}
