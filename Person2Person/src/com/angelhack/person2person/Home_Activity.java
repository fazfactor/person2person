package com.angelhack.person2person;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



public class Home_Activity extends  SherlockFragmentActivity {
	
	Typeface Roboto_bold,Roboto_light;
	ImageView Signin_Gmail,
			  Signup_Facebook;
	
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
		
		
		
		Signin_Gmail=(ImageView) findViewById(R.id.signin_gmail_iv_id);
		Signup_Facebook=(ImageView) findViewById(R.id.signup_facebbok_iv_id);
		
		
		Signin_Gmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				
			}
		});
		
		
		Signup_Facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Toast.makeText(Home_Activity.this, "Under Construction...!", Toast.LENGTH_SHORT).show();
				
			}
		});
	
	}

}
