package com.study.splanet;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.*;

public class MainActivity extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.pavlik.planetapp.MESSAGE";
	 
	class StartListener implements OnClickListener{
		
		public StartListener(){
		}
		
		public void onClick(View v){
			Button start=(Button)findViewById(R.id.buttonStart);
			if(v==start){
				startGame();
			}
		}
		
	}
	

	
	protected void startGame(){
		Intent intent = new Intent(this, DisplayMessageActivity.class);
		EditText editText = (EditText) findViewById(R.id.editTextOrbits);
		String message = editText.getText().toString();
		
		intent.putExtra(EXTRA_MESSAGE, message);	
		startActivity(intent);		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button start=(Button)findViewById(R.id.buttonStart);
		start.setOnClickListener(new StartListener());
		
		
	}

}
