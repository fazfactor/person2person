package com.angelhack.person2person;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FriendList_Activity extends SherlockFragmentActivity {
	
	
	ListView list;
	
	String Name[] = {"Amal Dev S I","Anto Jose","Rahul Harikumar","Rohan Harikumar","Jerry Mathew"};
	String E_mail[]={"amaldevsi@gmail.com","rahulharikr@gmail.com","antojose.th@gmail.com","rohanharikumar80@gmail.com","studiojerryco@gmail.com"};
	String Phone[]={"9746567542","8089325286","995113870","9497268220","9876543210"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friendlist_activity);
		
		 list=(ListView) findViewById(R.id.listView1);
		

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
		
		
		
		com.angelhack.person2person.Friend_Listview_Adapter adapter = new com.angelhack.person2person.Friend_Listview_Adapter(FriendList_Activity.this,Name,E_mail,Phone);
		list.setAdapter(adapter);
		
		
		
		
	}

}
