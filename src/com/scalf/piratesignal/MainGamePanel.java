package com.scalf.piratesignal;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.text.Editable.Factory;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.scalf.piratesignal.R;
import com.scalf.AudioVisual.*;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback {

	private static final String TAG = MainGamePanel.class.getSimpleName();
	
	private int ticks;
	
	private AudioAnalyzer mAudioAnalyzer;
	
	private PlayerShip sawtooth;
	
	private StarField galaxy;

	private MainThread thread;
	private MediaPlayer mPlayer;

	public MainGamePanel(Context context) {
		super(context);
		// adding the callback (this) to the surface holder to intercept events
		getHolder().addCallback(this);
	
		ticks = 0;
	
		// create the game loop thread
		thread = new MainThread(getHolder(), this);
		
		mPlayer = MediaPlayer.create(context, R.raw.test);
        mPlayer.setLooping(true);
        //mPlayer.start();

        // We need to link the visualizer view to the media player so that
        // it displays something
	    mAudioAnalyzer = new AudioAnalyzer();
	    mAudioAnalyzer.link(mPlayer);	
	    
	    
	     
	    sawtooth = new PlayerShip(BitmapFactory.decodeResource(getResources(), R.drawable.ikaruga));
	    sawtooth.setPosition(240, 400);
		// make the GamePanel focusable so it can handle events
		setFocusable(true);
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
	 int height) {
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	// at this point the surface is created and
	// we can safely start the game loop
	thread.setRunning(true);
	thread.start();
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	Log.d(TAG, "Surface is being destroyed");
	// tell the thread to shut down and wait for it to finish
	// this is a clean shutdown
	boolean retry = true;
	while (retry) {
	 try {
	  thread.join();
	  retry = false;
	 } catch (InterruptedException e) {
	  // try again shutting down the thread
	 }
	}
	Log.d(TAG, "Thread was shut down cleanly");
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		sawtooth.HandleTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			  if (event.getY() > getHeight() - 50) 
			  {
			    thread.setRunning(false);
			    mPlayer.stop();
			    mPlayer.release();
			    ((Activity)getContext()).finish();
			}
		}
	return true;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	// fills the canvas with black
		canvas.drawColor(Color.BLACK);
		if(galaxy == null)
		{
			galaxy = new StarField(getWidth(), getHeight());
		}
		galaxy.Draw(canvas);
		Paint flashPaint = new Paint();
		flashPaint.setColor(Color.WHITE);
		Paint fadePaint = new Paint();
		fadePaint.setColor(Color.BLUE);
		
		
		
		//canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ikaruga), 240, 100, null);
		
		mAudioAnalyzer.DrawFFT(canvas, new Rect(canvas.getWidth()/2,600,canvas.getWidth(), 690), flashPaint, fadePaint, false, false);
		mAudioAnalyzer.DrawFFT(canvas, new Rect(0,600,canvas.getWidth()/2, 690), flashPaint, fadePaint, false, true);
		
		if(mAudioAnalyzer.FourierData!=null)
		{
			sawtooth.Draw(canvas);
		}
	}
	
	public void update()
	{
		 ticks++;
		 
		 if(galaxy == null)
		 {
			 galaxy = new StarField(getWidth(), getHeight());
		 }
		 galaxy.Update();
		 
		 if(mAudioAnalyzer.FourierData!=null)
		 {
			 sawtooth.ProcessAudioData(mAudioAnalyzer.FourierData);
		 }
		 
		 sawtooth.Update();
	}
}