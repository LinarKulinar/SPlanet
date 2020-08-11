package com.study.splanet;


import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DisplayMessageActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dm);
		
	    Intent intent = getIntent();
	    String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
		
	    OrbitView ow = (OrbitView) findViewById(R.id.orbitView);	    
		
		try{
			int no=Integer.parseInt(message);
			if(no>0 && no<10){

				ow.setNumOrbits(no);
			}
			
		}catch(Exception e){}
		
		TextView tw = (TextView) findViewById(R.id.textView1);
		tw.setText("Orbits: " + ow.getNumOrbits());
		
	    // TODO Auto-generated method stub
	}

}
