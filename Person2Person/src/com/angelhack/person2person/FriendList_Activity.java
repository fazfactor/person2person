package com.angelhack.person2person;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FriendList_Activity extends SherlockFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		

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
		
	}

}
