 package com.angelhack.person2person;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.angelhack.person2person.helperclass.Utils;


public class MainSplash extends Activity {

		   
	
	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.splashscreen);
	 
	        new LoadViewTask().execute();
	        
	      

	        
	       

	       
	        
	 }     
	 //To use the AsyncTask, it must be subclassed  
	      private class LoadViewTask extends AsyncTask<Void, Integer, Void>  
	        {  
	            //Before running code in separate thread  
	            @Override  
	            protected void onPreExecute()  
	            {  
	            	// prograss bar rotation in splash page
	            	ImageView myAnimation = (ImageView)findViewById(R.id.myanimation);
	     	        final AnimationDrawable myAnimationDrawable = (AnimationDrawable)myAnimation.getDrawable();

	     	        myAnimation.post(
	     	        new Runnable(){

	     	          @Override
	     	          public void run() {
	     	           myAnimationDrawable.start();
	     	          }
	     	        });
	     	       
	            }  
	      
	            //The code to be executed in a background thread.  
	            @Override  
	            protected Void doInBackground(Void... params)  
	            {  
	            	
	            		 if (Utils.isNetworkAvailable(MainSplash.this))
	            		 {
	 	                	

	     	                /* This is just a code that delays the thread execution 4 times, 
	     	                 * during 850 milliseconds and updates the current progress. This 
	     	                 * is where the code that is going to be executed on a background 
	     	                 * thread must be placed. 
	     	                 */  
	     	                try  
	     	                {  
	     	                    //Get the current thread's token  
	     	                    synchronized (this)  
	     	                    {  
	     	                        //Initialize an integer (that will act as a counter) to zero  
	     	                        int counter = 0;  
	     	                        //While the counter is smaller than four  
	     	                        while(counter <= 4)  
	     	                        {  
	     	                            //Wait 400 milliseconds  
	     	                            this.wait(400);  
	     	                            //Increment the counter  
	     	                            counter++;  
	     	                            //Set the current progress.  
	     	                            //This value is going to be passed to the onProgressUpdate() method.  
	     	                            publishProgress(counter*25);  
	     	                        }  
	     	                    }  
	     	                }  
	     	                catch (InterruptedException e)  
	     	                {  
	     	                    e.printStackTrace();  
	     	                } 
	     	                

	     	  		
	 		     	  		
	 		     	  		
	            	}else
	            	{
						
					

	                /* This is just a code that delays the thread execution 4 times, 
	                 * during 600 milliseconds and updates the current progress. This 
	                 * is where the code that is going to be executed on a background 
	                 * thread must be placed. 
	                 */  
	                try  
	                {  
	                    //Get the current thread's token  
	                    synchronized (this)  
	                    {  
	                        //Initialize an integer (that will act as a counter) to zero  
	                        int counter = 0;  
	                        //While the counter is smaller than four  
	                        while(counter <= 4)  
	                        {  
	                            //Wait 850 milliseconds  
	                            this.wait(600);  
	                            //Increment the counter  
	                            counter++;  
	                            //Set the current progress.  
	                            //This value is going to be passed to the onProgressUpdate() method.  
	                            publishProgress(counter*25);  
	                        }  
	                    }  
	                }  
	                catch (InterruptedException e)  
	                {  
	                    e.printStackTrace();  
	                } 
	                
                    
	            	}
		     	      
	                return null;  
	               
	            }  
	      
	            //Update the progress  
	            @Override  
	            protected void onProgressUpdate(Integer... values)  
	            {  
	               
	            }  
	      
	            //after executing the code in the thread  
	            @Override  
	            protected void onPostExecute(Void result)  
	            {  
	            	
	            	Intent intent = new Intent(MainSplash.this,SocialMedia_New.class);
	    			startActivity(intent);
	        		
	        		finish();
	            }  
	        }  
	      
	  
	      @Override
	  	public void onPause() {
	  		// TODO Auto-generated method stub
	  		super.onPause();
	  		
	  	}
	  	@Override
	  	public void onDestroy()
	  	{
	  	        super.onDestroy();
	  	}
	  
	  		  
	  		
	  		}

