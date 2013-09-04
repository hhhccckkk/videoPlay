package com.hck.player.utils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class HttpUtil {
	private static     AsyncHttpClient client =new AsyncHttpClient();    
    static
    {
        client.setTimeout(11000);   //设置链接超时，如果不设置，默认为10s
    }
    public static void get(String urlString,AsyncHttpResponseHandler res)    
    {
        client.get(urlString, res);
    }
    public static void get(String urlString,RequestParams params,AsyncHttpResponseHandler res)   
    {
        client.get(urlString, params,res);
    }
    public static void get(String urlString,JsonHttpResponseHandler res)   
    {
        client.get(urlString, res);
    }
    public static void get(String urlString,RequestParams params,JsonHttpResponseHandler res)  
    {
        client.get(urlString, params,res);
    }
    public static void get(String uString, BinaryHttpResponseHandler bHandler)   
    {
        client.get(uString, bHandler);
    }
    public static AsyncHttpClient getClient()
    {
        return client;
    }

}
