package com.scalf.piratesignal;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class BeamParticle {
	private int xOffset;
	private int yOffset;
	private int Intensity;
	private int beamWidth;
	private static int BEAM_ALPHA = 200;
	
	public BeamParticle(int width)
	{
		xOffset = 0;
		yOffset = 0;
		beamWidth = width;
		Intensity = 0;
	}
	
	public void setIntensity(int Intensity)
	{
		this.Intensity = Intensity;
		if(this.Intensity<0)
		{
			this.Intensity=0;
		}
	}
	
	public void setOffset(int x, int y)
	{
		xOffset = x;
		yOffset = y;
	}
	
	public void Draw(Canvas canvas, int x, int y)
	{
		Paint beamPaint = new Paint();
		beamPaint.setStrokeWidth(beamWidth);		
		switch(Intensity)
		{
		case 0:
			beamPaint = null;
			break;
		case 1:
			beamPaint.setColor(Color.RED);
			break;
		case 2:
			beamPaint.setColor(Color.YELLOW);
			break;
		case 3:
			beamPaint.setColor(Color.WHITE);
			break;
		case 4:
			beamPaint.setColor(Color.CYAN);
			break;
		default:
			beamPaint.setColor(Color.BLUE);
			break;
		}
		
		//Currently causes crashes.. not sure why
		//beamPaint.setAlpha(BEAM_ALPHA);
		
		if(beamPaint != null)
		{
			canvas.drawLine(x+xOffset, y+yOffset, x+xOffset, 0, beamPaint);
		}
	}
}
