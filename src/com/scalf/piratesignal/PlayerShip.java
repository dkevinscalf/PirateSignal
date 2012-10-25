package com.scalf.piratesignal;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.scalf.AudioVisual.FourierDBCollection;
import com.scalf.GameGraphics.Graphic2D;

public class PlayerShip {
	private Graphic2D shipImage;
	private boolean touchHeld = false;
	
	private AudioBeam LonginusBeam;
	private MissileBattery FireStorm;
	
	public PlayerShip(Bitmap shipImg)
	{
		this.shipImage = new Graphic2D(shipImg);
		LonginusBeam = new AudioBeam(shipImage.getPosition().x, shipImage.getPosition().y-shipImage.getHeight()/2);
		FireStorm = new MissileBattery(shipImage.getPosition().x, shipImage.getPosition().y-shipImage.getHeight()/3);
	}
	
	public void Draw(Canvas canvas)
	{
		shipImage.Draw(canvas);
		LonginusBeam.Draw(canvas);
		FireStorm.Draw(canvas);
	}
	
	public void HandleTouchEvent(MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN)
		{		
			//I tried the holding the ship shit at first but its a bit sluggish
			//Player will like more responsive ship movement better I think.
//			Rect hitBox = shipImage.getHitBox();
//			if(hitBox.contains((int)event.getX(), (int)event.getY()))
//			{
//				touchHeld = true;
//			}
			
			setPosition((int)event.getX(), (int)event.getY());
		} 
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
//			if(touchHeld)
//			{
//				setPosition((int)event.getX(), (int)event.getY());
//			}
			
			setPosition((int)event.getX(), (int)event.getY());
		}
//		else if(event.getAction() == MotionEvent.ACTION_UP)
//		{	
//			touchHeld = false;
//		}
	}
	
	public void setPosition(int x, int y)
	{
		shipImage.setPosition(new Point(x,y));
		LonginusBeam.setPosition(x, y-shipImage.getHeight()/2);
		FireStorm.setPosition(x, y-shipImage.getHeight()/3);
	}
	
	public void ProcessAudioData(FourierDBCollection FFData)
	{
		LonginusBeam.ProcessAudioData(FFData);
		FireStorm.ProcessAudioData(FFData);
	}
	
	public void Update()
	{
		FireStorm.Update();
	}
}
