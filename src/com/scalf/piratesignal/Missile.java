package com.scalf.piratesignal;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class Missile {
	public int state; // 2 - Fired; 1 - hit; 0 - Expired
	
	private int slot;
	private int sideWinding;
	
	private int x;
	private int y;
	
	private int yTarget;
	
	private int dX;
	private int dY;
	
	private List<Point> trail;
	private static int MAX_TRAIL_LENGTH = 5;
	private static int MIN_SIDEWINDING = 2;
	private static float PROJECTILE_SPEED = 10.0f;
	
	private Paint trailPaint;
	private Paint shellPaint;
	
	public Missile(int slot, int originX, int originY, int spacing, int yTarget)
	{
		state = 2;
		
		sideWinding = slot + MIN_SIDEWINDING;
		
		trail = new ArrayList<Point>();
		
		if(slot%2==0)
		{
			dX = 1;
		}
		else
		{
			dX = -1;
		}
		
		dY = -1;
		
		x = originX + (dX*spacing);
		y = originY + slot * spacing;
		
		this.yTarget = yTarget;
		
		shellPaint = new Paint();
		shellPaint.setColor(Color.YELLOW);
		shellPaint.setStrokeWidth(3.0f);
		
		trailPaint = new Paint();
		trailPaint.setColor(Color.YELLOW);
		trailPaint.setStrokeWidth(1.0f);		
	}	
	
	public void Move()
	{
		if(sideWinding!=0)
		{
			sideWinding--;
		} else {
			dX=0;
		}
		
		x+=dX*PROJECTILE_SPEED;
		y+=dY*PROJECTILE_SPEED;
		
		if(y <= yTarget)
		{
			state = 1;
		}
		
		Trail();
	}
	
	public void Trail()
	{
		if(trail!=null)
		{
			if(state == 2)
			{
				trail.add(new Point(x,y));
			}
			else if(state == 1)
			{
				Explode();
			}
			if(trail.size()>MAX_TRAIL_LENGTH || (state != 2 && trail.size() != 0))
			{
				trail.remove(0);
			}
			if(trail.size() == 0)
			{
				state = 0;
			}
		}
	}
	
	private void Explode()
	{
		shellPaint.setColor(Color.RED);
		shellPaint.setStrokeWidth(shellPaint.getStrokeWidth()+ 1.0f);
	}
	
	public void Draw(Canvas canvas)
	{
		if(trail.size() >=2)
		{
			canvas.drawLines(ConvertToFloatArray(trail.toArray(new Point[0])), trailPaint);
		}
		
		canvas.drawPoint(x, y, shellPaint);
	}
	
	private float[] ConvertToFloatArray(Point[] pArray)
	{
		float[] fArray = new float[pArray.length*2];
		for(int i = 0; i<pArray.length; i++)
		{
			fArray[i*2] = (float)pArray[i].x;
			fArray[i*2+1] = (float)pArray[i].y;
		}
		
		return fArray;
	}
}
