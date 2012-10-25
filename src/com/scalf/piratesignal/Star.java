package com.scalf.piratesignal;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Star {
	private int x;
	private int y;
	//z for a faux 3D effect
	private int z;
	
	private int canvasWidth;
	private int canvasHeight;
	
	private Paint starPaint;
	
	private static int BUFFER_HEIGHT = 25;
	
	public Star(int cWidth, int cHeight)
	{
		
		this.canvasWidth = cWidth;
		this.canvasHeight = cHeight;
		
		Randomize(true);
	}
	
	public Star()
	{
		this(480, 690);
	}
	
	public void Move()
	{
		//The closer a star is the faster it moves in the sky
		y += z;
		if(y > canvasHeight + BUFFER_HEIGHT)
		{
			Randomize(false);
		}
	}
	
	private void Randomize(boolean setY)
	{
		Random rand = new Random();
		
		x = rand.nextInt(canvasWidth);
		if(setY)
		{
			y = rand.nextInt(canvasHeight);
		} else {
			y = 0 - BUFFER_HEIGHT;
		}
		z = rand.nextInt(3) + 1;
		
		starPaint = new Paint();
		starPaint.setColor(Color.WHITE);
		starPaint.setStrokeWidth((float)z);
	}
	
	public void Draw(Canvas canvas)
	{
		canvas.drawPoint(x, y, starPaint);
	}
}
