package com.scalf.AudioVisual;

public class FourierDBCollection {
	public FourierDB[] Items;
	private int mDivisions;
	private static int EXPECTED_DATA_LENGTH = 1024;
	
	public FourierDBCollection(int divisions)
	{
		mDivisions = divisions;
		Items = new FourierDB[EXPECTED_DATA_LENGTH / mDivisions];
	}
	
	public FourierDBCollection()
	{
		this(1);
	}
	
	public void ProcessFourierData(byte[] FFTData)
	{
		if(FFTData!= null)
		{			
			for(int i=0; i<FFTData.length/mDivisions; i++)
			{
	    	      byte rfk = FFTData[mDivisions * i];
	    	      byte ifk = FFTData[mDivisions * i + 1];
	    	      float magnitude = (rfk * rfk + ifk * ifk);	    	      
	    	      int dbValue = (int) (10 * Math.log10(magnitude));
	    	      if(Items[i] == null)
	    	      {
	    	    	  Items[i] = new FourierDB(dbValue);	    	    	  
	    	      } else {
	    	    	  Items[i].setMagnitude(dbValue);
	    	      }
			}
		}
	}
}
