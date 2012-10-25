package com.scalf.piratesignal;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.util.Log;

import com.scalf.AudioVisual.FourierDBCollection;

public class MissileBattery {
	private static int MISSILE_SPACING = 4;

	private List<Missile> missiles;
	
	private int x;
	private int y;
	
	private static int THRESHOLD = 5;
	private static float DAMPENER = 0.5f;
	
	public MissileBattery(int x, int y)
	{
		this.x = x;
		this.y = y;
		
		missiles = new ArrayList<Missile>();
	}
	
	private void Fire(int slot)
	{
		if(missiles!=null)
		{
			missiles.add(new Missile(slot, x, y, 20, MISSILE_SPACING));
		}
	}
	
	public void Update()
	{
		int state;
		if(missiles!=null)
		{
			for(int i = 0; i < missiles.size(); i++)
			{
				state = missiles.get(i).state;
				if(state == 2)
				{
					missiles.get(i).Move();
				} else if(state == 1)
				{
					missiles.get(i).Trail();
				} else {
					missiles.remove(i);
				}
			}
		}
	}
	
	public void Draw(Canvas canvas)
	{
		if(missiles != null)
		{
			for(int i = 0; i < missiles.size(); i++)
			{
				missiles.get(i).Draw(canvas);
			}
		}
	}
	
	public void ProcessAudioData(FourierDBCollection FFData)
	{
		if(FFData.Items!=null)
		{
			if(FFData.Items.length>=16)
			{		
				for(int i = 6; i < 16; i++)
				{
					if(FFData.Items[i]!=null)
					{
						if(FFData.Items[i].getDB()*DAMPENER > THRESHOLD && FFData.Items[i].getDB() > FFData.Items[i].getODB())
						{
							Fire(i-6);
						}
					}
				}
			}
		}
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
