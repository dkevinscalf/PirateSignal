package com.scalf.GameGraphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class Graphic2D {
	private Bitmap drawImage;
	private Point position;
	
	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Graphic2D(Bitmap img)
	{
		drawImage = img;
		position = new Point(0,0);
	}
	
	public void Draw(Canvas canvas)
	{
		canvas.drawBitmap(drawImage, position.x-getWidth()/2, position.y-getHeight()/2, null);
	}
	
	public int getWidth() {
		return drawImage.getWidth();
	}

	public int getHeight() {
		return drawImage.getHeight();
	}
	
	public Rect getHitBox()
	{
		Rect hitBox = new Rect();
		hitBox.left = position.x-getWidth()/2;
		hitBox.right = position.x+getWidth()/2;
		hitBox.top = position.y-getHeight()/2;
		hitBox.bottom = position.y+getHeight()/2;
		
		return hitBox;
	}
}
