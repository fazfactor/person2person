package com.angelhack.person2person;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.angelhack.person2person.helperclass.Utils;
import com.google.android.gms.auth.GoogleAuthUtil;

public class SocialMedia_New extends Activity {
	
	ImageView Signin_Gmail,
	  Signup_Facebook;
	
	 Context mContext = SocialMedia_New.this; 
	    AccountManager mAccountManager;
	    String token;
	    int serverCode;
	    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
	    
	    public static String textName,userImageUrl;
	    public static ImageView imageProfile;
	    public static TextView textViewName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.socialmedia_new);
		
		Signin_Gmail=(ImageView) findViewById(R.id.signin_gmail_iv_id);
		imageProfile = (ImageView) findViewById(R.id.imageView1);
		textViewName = (TextView) findViewById(R.id.textViewNameValue);
		
		
		  

		
		Signin_Gmail.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				  syncGoogleAccount();
				
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
