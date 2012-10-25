package com.scalf.piratesignal;

import android.graphics.Canvas;
import android.util.Log;

import com.scalf.AudioVisual.FourierDBCollection;

public class AudioBeam {
	private BeamParticle[] Beam;
	private int x;
	private int y;
	private static int DEFAULT_WIDTH = 5;
	private static int THRESHOLD = 3;
	private static float DAMPENER = 0.35f;
	
	public AudioBeam(int x, int y)
	{
		this.x = x;
		this.y = y;
		Beam = new BeamParticle[6];
		for(int i = 0; i < 6; i++)
		{
			Beam[i] = new BeamParticle(DEFAULT_WIDTH);
			if(i<3)
			{
				Beam[i].setOffset((i-3)*DEFAULT_WIDTH, Math.abs((i-2)*DEFAULT_WIDTH)*-1);
			} else {
				Beam[i].setOffset((i-3)*DEFAULT_WIDTH, Math.abs((i-3)*DEFAULT_WIDTH)*-1);
			}
		}
	}
	
	public void ProcessAudioData(FourierDBCollection FFData)
	{
		int bI = 0;
		
		if(FFData.Items!=null)
		{
			if(FFData.Items.length>=16)
			{	
				//left side
				for(int i = 2; i >= 0; i--)
				{
					if(FFData.Items[i*4]!=null)
					{
						Beam[bI].setIntensity((int)(FFData.Items[i].getDB()*DAMPENER-THRESHOLD));
						bI++;
					}
				}
				
				//right side
				for(int i = 0; i < 3; i++)
				{
					if(FFData.Items[i*4]!=null)
					{
						Beam[bI].setIntensity((int)(FFData.Items[i].getDB()*DAMPENER-THRESHOLD));
						bI++;
					}
				}
			}
		}
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void Draw(Canvas canvas)
	{
		for(int i=0; i<6; i++)
		{
			Beam[i].Draw(canvas, x, y);
		}
	}
}
