package id.go.kemenkeu.djkn.tldjkn.app;

import com.activeandroid.ActiveAndroid;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by Hendra on 19/02/2015.
 */
public class MyApp extends com.activeandroid.app.Application
{
	private static final String PROPERTY_ID = "UA-50746201-7";

	synchronized Tracker getTracker()
	{
		GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
		analytics.getLogger().setLogLevel(Logger.LogLevel.VERBOSE);
		Tracker t = analytics.newTracker(PROPERTY_ID);
		return t;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		// Initialize Active Android
		ActiveAndroid.initialize(this);
		//		// Initialize the Prefs class
		//		Prefs.initPrefs(this);

		Tracker t = getTracker();
		t.enableAutoActivityTracking(true);
		t.enableExceptionReporting(true);
	}
}
