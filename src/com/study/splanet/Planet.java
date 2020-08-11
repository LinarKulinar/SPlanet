package com.study.splanet;

import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.SystemClock;
import android.view.View;

public class Planet extends AnimationDrawable {
	
	protected View parentView;
   protected int radius;
    
   public void setRadius(int r) {
    	radius=r;
    	Rect rect=getBounds();
    	setPosition(rect.left, rect.top);
    }
    
   
   public void setPosition(int left, int top){
	   setBounds(left, top, left+radius, top+radius);
   }
   
   public void setCentralPosition(int xc, int yc){
	   setPosition(xc-radius/2, yc-radius/2);
   }
   
	public Planet(AnimationDrawable aniDrawable, OrbitView v) {
		parentView=v;
	    for (int i = 0; i < aniDrawable.getNumberOfFrames(); i++) {
	        this.addFrame(aniDrawable.getFrame(i), aniDrawable.getDuration(i));
	    }
	    setOneShot(false);
	  //  setCallback(v.getCallback());
	    
     	setCallback(new Callback() {

    	    @Override
    	    public void unscheduleDrawable(Drawable who, Runnable what) {
    	    	parentView.removeCallbacks(what);
    	    }

    	    @Override
    	    public void scheduleDrawable(Drawable who, Runnable what, long when) {
    	    	parentView.postDelayed(what, when - SystemClock.uptimeMillis());
    	    }

    	    @Override
    	    public void invalidateDrawable(Drawable who) {
    	    	parentView.postInvalidate();
    	    }
    	});	    
	}

}
