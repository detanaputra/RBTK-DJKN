package id.go.kemenkeu.djkn.tldjkn.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Hendra on 25/03/2015.
 */
public class ArticleReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(context, ArticleStartReceiver.class);
		PendingIntent pending = PendingIntent
				.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		service.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, AlarmManager.INTERVAL_HOUR,
				AlarmManager.INTERVAL_HOUR, pending);
	}
}