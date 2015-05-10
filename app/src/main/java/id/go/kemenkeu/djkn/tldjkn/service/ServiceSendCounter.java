package id.go.kemenkeu.djkn.tldjkn.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import id.go.kemenkeu.djkn.tldjkn.util.VolleySingleton;

/**
 * Created by Hendra on 10/05/2015.
 */
public class ServiceSendCounter
{
	public static void sendToDB(final Context context, final String url)
	{

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						if (ISendCounter.class.isInstance(context))
						{
							((ISendCounter) context).incViewedData();
						}
					}
				}, new Response.ErrorListener()
		{
			@Override
			public void onErrorResponse(VolleyError error)
			{
				Log.e("FLOW", "volleyError:" + error.toString());
				//Toast.makeText(context, "Connection Error", Toast.LENGTH_LONG).show();
				//downToDB(context, url);
			}
		});
		stringRequest.setTag(url);
		VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
	}

	public interface ISendCounter
	{
		void incViewedData();
	}

}
