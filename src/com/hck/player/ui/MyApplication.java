package com.hck.player.ui;

import android.app.Application;

import com.hck.player.date.LocalDate;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.wole56.sdk.APP;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
	
	initImagerLoder();
	}

	private void initImagerLoder()
	{
		DisplayImageOptions  options = new DisplayImageOptions.Builder()            
        .cacheInMemory()                                             
        .cacheOnDisc()                                                   
        .displayer(new RoundedBitmapDisplayer(5))       
        .build();
     ImageLoaderConfiguration config2 = new ImageLoaderConfiguration.Builder(getApplicationContext())
        .threadPriority(Thread.NORM_PRIORITY - 2)
        .defaultDisplayImageOptions(options)
        .denyCacheImageMultipleSizesInMemory()
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
        .tasksProcessingOrder(QueueProcessingType.LIFO)
        .enableLogging() 
        .build();
       ImageLoader.getInstance().init(config2);
       
	}

}
