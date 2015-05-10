package id.go.kemenkeu.djkn.tldjkn.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import id.go.kemenkeu.djkn.tldjkn.R;

/**
 * Created by Hendra on 25/03/2015.
 */
public class ArticleService extends Service
{
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		ServiceDownArticle.downToDB(this, getString(R.string.urlall));
		//Toast.makeText(this, "Service Article Start", Toast.LENGTH_LONG).show();
		return Service.START_NOT_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

}
