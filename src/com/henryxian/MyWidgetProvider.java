package com.henryxian;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.util.Log;
import android.widget.RemoteViews;


public class MyWidgetProvider extends AppWidgetProvider{
	public static int Tag;
	public int max;
	public int current;
	
	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		
		ComponentName thisWidget = new ComponentName(context, MyWidgetProvider.class);
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ui);
		AppWidgetManager appmanager = AppWidgetManager.getInstance(context);
		Log.d("UPUP", intent.getAction());
		auc(context, 0);
		views.setProgressBar(R.id.widget_ProgressBar, max, current, false);
		appmanager.updateAppWidget(thisWidget, views);
		//Tag = current;
		if (intent.getAction().equals("com.henryxian.widgetdemo.UP")){
			Tag += 1;
			Log.d("tagdd", Integer.toString(Tag));
			if (Tag > max){
				Tag = max;
			}
			auc(context, 1);
			views.setProgressBar(R.id.widget_ProgressBar, max, Tag, false);
			appmanager.updateAppWidget(thisWidget, views);
		}
		if (intent.getAction().equals("com.henryxian.widgetdemo.DOWN")){
			Tag -= 1;
			if (Tag < 0){
				Tag = 0;
			}
			auc(context, 1);
			views.setProgressBar(R.id.widget_ProgressBar, 
					max, Tag, false);
			appmanager.updateAppWidget(thisWidget, views);
		}
		}
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		//super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		final int N = appWidgetIds.length;
		auc(context, 0);
		Log.d("UPUP", "2222222");
		for (int appWidgetId : appWidgetIds){
			RemoteViews views = new RemoteViews(context.getPackageName(), 
					R.layout.widget_ui);
			
			Intent UPintent = new Intent("com.henryxian.widgetdemo.UP");
			Intent DOWNintent = new Intent("com.henryxian.widgetdemo.DOWN");
			PendingIntent pendingIntentUp = PendingIntent.getBroadcast(context, 0, 
					UPintent, 0);
			PendingIntent pendingIntentDOWN = PendingIntent.getBroadcast(context, 0, 
					DOWNintent, 0);
			
			views.setOnClickPendingIntent(R.id.widget_BT_up, pendingIntentUp);
			views.setOnClickPendingIntent(R.id.widget_BT_down, pendingIntentDOWN);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	public void auc(Context context, int i){
		AudioManager am = null;
		am = (AudioManager)context.getSystemService(context.AUDIO_SERVICE);
		max = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		current = am.getStreamVolume(AudioManager.STREAM_MUSIC);
		Log.d("MUSIC", "max:" + max + "current:" + current);
		current = current + i;
		am.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
	}
}
