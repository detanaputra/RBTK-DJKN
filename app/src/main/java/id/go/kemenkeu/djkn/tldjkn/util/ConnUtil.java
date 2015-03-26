package id.go.kemenkeu.djkn.tldjkn.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Hendra on 05/03/2015.
 */
public class ConnUtil
{
	public static boolean isNetConnected(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

		return isConnected;
	}

}
