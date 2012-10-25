package com.scalf.piratesignal;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class EnemyBullet {
	private int x;
	private int y;

	private int dX;
	private int dY;
	
	private static int BULLET_SPEED = 5;
	private static int BULLET_WIDTH = 5;
	
	private Paint bulletPaint;
	
	public EnemyBullet(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		Random rand = new Random();
		dX = (int)((rand.nextFloat() * 2 - 1.0f) * BULLET_SPEED);
		dY = BULLET_SPEED;
		
		bulletPaint = new Paint();
		bulletPaint.setColor(Color.argb(255, 147, 112, 219));
		bulletPaint.setStrokeWidth(2);
	}
	
	public void Move()
	{
		x += dX;
		y += dY;
	}
	
	public void Draw(Canvas canvas)
	{
		canvas.drawCircle(x, y, BULLET_WIDTH, bulletPaint);
	}
}
