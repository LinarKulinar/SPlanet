package com.study.splanet;


import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

class OrbitView extends View {
	
	final public double planstate=Math.PI*7/10; 
	protected float radius = 100;

	//protected Paint cpaint;
	//protected Path rect;
	
	protected int numOrbits=DEF_ORBITS;
	public static final int DEF_ORBITS=3;
	
	public static final int MAX_PLANETS = 10;
	
	protected float[] planetRadius = new float[MAX_PLANETS];
	protected double[] planetAngle = new double[MAX_PLANETS];
	protected double[] planetStartAngle = new double[MAX_PLANETS];
	protected float[] orbitRadius = new float[MAX_PLANETS];
	protected float[] planetSpeed = new float[MAX_PLANETS];
	protected Paint[] planetPaints = new Paint[MAX_PLANETS];
	protected int[] planetColors = {Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.GRAY, Color.YELLOW, Color.MAGENTA, Color.DKGRAY, Color.BLACK, Color.LTGRAY};
	
	ValueAnimator animator = ValueAnimator.ofFloat((float)0.0, (float)(2.0*Math.PI));
	
	public static final long ROUND_DURATION = 10000;
	//protected Planet planet ; 
	protected Planet sun;
	
	public OrbitView(Context context, AttributeSet attrs) {
        super(context, attrs);
      //  rect = new  Path();
      //  rect.addRect(0, 0,250, 150,Direction.CW);
        
        for(int i=0; i<MAX_PLANETS; i++ ){
	        planetPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
	        planetPaints[i].setColor(planetColors[i]);
	        planetPaints[i].setStyle(Paint.Style.STROKE);
	        planetPaints[i].setStrokeWidth(3);
	        
	        planetStartAngle[i]=2*i*Math.PI/MAX_PLANETS;
	        planetAngle[i]=planetStartAngle[i];
	        planetSpeed[i]=(float)(1.0+i);
        }
        
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.OrbitView,
                0, 0);

           try {
               numOrbits = a.getInteger(R.styleable.OrbitView_numOrbits, DEF_ORBITS);
           } finally {
               a.recycle();
         }        
         
         animator.setDuration(ROUND_DURATION);
         animator.setRepeatCount(ValueAnimator.INFINITE);
         animator.setRepeatMode(ValueAnimator.RESTART);
         animator.setInterpolator(new LinearInterpolator());
         animator.addUpdateListener(new OurAnimatorUpdateListener());
         animator.start();
         
         Resources res = context.getResources(); 
         AnimationDrawable drawable;

         
         drawable = (AnimationDrawable)res.getDrawable(R.drawable.sun);

         sun = new Planet(drawable, this);
         sun.start();
         
         try{
        	 Thread.sleep(10);
         }
         catch(Exception e){
        	 
         }
         
        /* drawable = (AnimationDrawable)res.getDrawable(R.drawable.planetanim); 
         
         planet = new Planet(drawable, this);
         planet.start();*/
         
            
    }
	private class OurAnimatorUpdateListener implements AnimatorUpdateListener{
		
      	 @SuppressLint("NewApi")
		public void onAnimationUpdate(ValueAnimator animation){
      		 for(int i=0; i<MAX_PLANETS; i++){
	     	        planetAngle[i]=planetStartAngle[i]+planetSpeed[i]*(animation.getAnimatedFraction()*Math.PI*2);
	     	        invalidate();
      		 }
 
      	 }
       
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	    // Account for padding
       float xpad = (float)(getPaddingLeft() + getPaddingRight());
       float ypad = (float)(getPaddingTop() + getPaddingBottom());

       float ww = (float)w - xpad;
       float hh = (float)h - ypad;
     
      

       // Figure out how big we can make the pie.
       radius = (float) (Math.min(ww, hh)/2.2);
       
       calcPlanetRadius();     
       calcOrbitRadius();
       super.onSizeChanged(w, h, oldw, oldh);
	}
	
	protected void calcOrbitRadius(){
       double step = radius*0.7/(getNumOrbits()-1);
       for(int i=0; i<=getNumOrbits()-1; i++){
			orbitRadius[i]=(float)(radius-step*i);
		}
	}

	protected void calcPlanetRadius(){
	       for(int i=0; i<=getNumOrbits()-1; i++){
				planetRadius[i]=radius/12;
			}
			//planetRadius[0]*=4;
			//planet.setRadius((int)planetRadius[0]);
			sun.setRadius((int)(radius*0.35-radius/24));
	}
	
	public int getNumOrbits() {
		   return numOrbits;
	}


	public void setNumOrbits(int num) {
	   numOrbits = num;
	   if(numOrbits<=0 || numOrbits>MAX_PLANETS)numOrbits=1;
	   
	   calcOrbitRadius();

	   invalidate();
	   requestLayout();
	}
		
	
    @Override
    protected void onDraw(Canvas canvas) {
            //Draw main orbit;

//    		canvas.drawCircle(getPaddingLeft()+radius, getPaddingTop()+radius, orbitRadius[0], planetPaints[0]);
//            canvas.drawCircle((float)Math.sin(planetAngle[0])*orbitRadius[0]+getPaddingLeft()+radius,getPaddingTop()+radius-(float)Math.cos(planetAngle[0])*orbitRadius[0] , planetRadius[0],planetPaints[0]);

//            if(getNumOrbits()>1){
    	float xc=(float)(getPaddingLeft()+radius*1.1);
    	float yc= (float)(getPaddingTop()+radius*1.1);
    	
            	for(int i=0; i<=getNumOrbits()-1; i++){
            		canvas.drawCircle(xc, yc , orbitRadius[i], planetPaints[i]);
                    canvas.drawCircle((float)Math.sin(planetAngle[i])*orbitRadius[i]+xc, yc -(float)Math.cos(planetAngle[i])*orbitRadius[i] , planetRadius[i] ,planetPaints[i]);
                    
            	}
          /*  		canvas.drawCircle(xc, yc , orbitRadius[0], planetPaints[0]);
    		int x=(int)Math.round(Math.sin(planetAngle[0])*orbitRadius[0]+xc);
    		int y=(int)Math.round(yc -(float)Math.cos(planetAngle[0])*orbitRadius[0]);
            planet.setCentralPosition(x, y);
            planet.draw(canvas);*/   
            sun.setCentralPosition((int)xc,(int)yc);
            sun.draw(canvas);
            	
    }    
    
    
    
    
  
}