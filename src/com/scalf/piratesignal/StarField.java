package com.scalf.piratesignal;

import android.graphics.Canvas;

public class StarField {
	private static int STAR_COUNT = 30;
	
	private Star[] stars;
	
	public StarField(int cWidth, int cHeight)
	{
		stars = new Star[STAR_COUNT];
		
		for(int i=0; i<STAR_COUNT; i++)
		{
			stars[i] = new Star(cWidth, cHeight);
		}
	}
	
	public void Update()
	{
		for(int i=0; i<STAR_COUNT; i++)
		{
			stars[i].Move();
		}
	}
	
	public void Draw(Canvas canvas)
	{
		for(int i=0; i<STAR_COUNT; i++)
		{
			stars[i].Draw(canvas);
		}
	}
}
