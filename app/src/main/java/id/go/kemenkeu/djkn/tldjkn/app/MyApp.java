package id.go.kemenkeu.djkn.tldjkn.app;

import com.activeandroid.ActiveAndroid;

/**
 * Created by Hendra on 19/02/2015.
 */
public class MyApp extends com.activeandroid.app.Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		// Initialize Active Android
		ActiveAndroid.initialize(this);
		//		// Initialize the Prefs class
		//		Prefs.initPrefs(this);
	}
}
