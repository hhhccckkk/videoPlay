package com.hck.player.utils;

import org.apache.commons.net.ntp.TimeStamp;

public class MyTools {
	public static String changeTime(Long time)
	{
		return new TimeStamp(time*100).toString();
	}
  public  static String changeTime2(Long time)
  {
	   float f=time/1000/60;
	   float m=time%(1000*60);
	   return (int)f+":"+String.valueOf(m).substring(0, 2);
	
  }
}
