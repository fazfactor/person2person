package com.angelhack.person2person;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.angelhack.person2person.helperclass.Utils;
import com.google.android.gms.auth.GoogleAuthUtil;

public class SocialMedia_New extends SherlockFragmentActivity {
	
	ImageView Signin_Gmail,
	  Signup_Facebook;
	
	 Context mContext = SocialMedia_New.this; 
	    AccountManager mAccountManager;
	    String token;
	    int serverCode;
	    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	    
	    public static String textName,userImageUrl;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.socialmedia_new);
		
		
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
		Signup_Facebook=(ImageView) findViewById(R.id.signup_facebook_iv_id);

		
		  

		
		Signin_Gmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				  syncGoogleAccount();
				
			}
		});
		

		Signup_Facebook.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(SocialMedia_New.this, "Under Construction...!", Toast.LENGTH_SHORT).show();
				
				

            	Intent intent = new Intent(SocialMedia_New.this,Home_Activity.class);
    			startActivity(intent);
        		
        		finish();
				
			}
		});
	
		
		
	}

	
	private String[] getAccountNames() {
  		
	        mAccountManager = AccountManager.get(this);
	
	        Account[] accounts = mAccountManager
	
	                .getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
	
	        String[] names = new String[accounts.length];

	        for (int i = 0; i < names.length; i++) {
	
	            names[i] = accounts[i].name;
	
	        }
	
	        return names;
	
	    }
	
	 
	
	    private AbstractGetNameTask getTask(Activity activity, String email,
	
	            String scope) {
	
	        return new GetNameInForeground(activity, email, scope);
	
	 
	
	    }
	
	 
	
	    public void syncGoogleAccount() {
	
	        if (Utils.isNetworkAvailable(SocialMedia_New.this) == true) {
	
	            String[] accountarrs = getAccountNames();
	
	            if (accountarrs.length > 0) {
	
	                //you can set here account for login
	
	                getTask(SocialMedia_New.this, accountarrs[0], SCOPE).execute();
	
	            } else {
	
	                Toast.makeText(SocialMedia_New.this, "No Google Account Sync!",
	
	                        Toast.LENGTH_SHORT).show();
	
	            }
	
	        } else {
	
	            Toast.makeText(SocialMedia_New.this, "No Network Service!",
	
	                    Toast.LENGTH_SHORT).show();
	
	        }
	
	    }

}
