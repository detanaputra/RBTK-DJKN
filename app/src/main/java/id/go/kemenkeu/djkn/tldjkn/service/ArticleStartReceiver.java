package id.go.kemenkeu.djkn.tldjkn.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hendra on 25/03/2015.
 */
public class ArticleStartReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Intent service = new Intent(context, ArticleService.class);
		context.startService(service);
	}
}
