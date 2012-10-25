package com.scalf.piratesignal;

import java.util.ArrayList;
import java.util.List;

public class EnemyBulletBattery {
	private int canvasWidth;
	private int canvasHeight;
	
	private List<EnemyBullet> bullets;
	
	public EnemyBulletBattery(int cWidth, int cHeight)
	{
		this.canvasWidth = cWidth;
		this.canvasHeight = cHeight;
		
		bullets = new ArrayList<EnemyBullet>();
	}	
}
