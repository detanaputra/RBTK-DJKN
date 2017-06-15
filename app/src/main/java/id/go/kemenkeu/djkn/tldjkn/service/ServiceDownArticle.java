package id.go.kemenkeu.djkn.tldjkn.service;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.List;

import id.go.kemenkeu.djkn.tldjkn.R;
import id.go.kemenkeu.djkn.tldjkn.activity.DetailActivity;
import id.go.kemenkeu.djkn.tldjkn.model.Article;
import id.go.kemenkeu.djkn.tldjkn.util.NotifUtil;
import id.go.kemenkeu.djkn.tldjkn.util.ResultParser;
import id.go.kemenkeu.djkn.tldjkn.util.VolleySingleton;

/**
 * Created by Hendra on 24/03/2015.
 * Updated and Maintained by Deta PMO DJKN 15/06/2017
 */
public class ServiceDownArticle
{
	public static void downToDB(final Context context, final String url)
	{

		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
				new Response.Listener<String>()
				{
					@Override
					public void onResponse(String response)
					{
						onDownComplete(context, url, response);
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

	public static void cancelDown(Context context, String url)
	{
		VolleySingleton.getInstance(context).getRequestQueue().cancelAll(url);
	}

	private static void onDownComplete(final Context context, final String url, String result)
	{
		new AsyncTask<String, Void, ArrayList>()
		{

			@Override
			protected ArrayList doInBackground(String... results)
			{
				return parseResult(url, results[0]);
			}

			private ArrayList parseResult(String url, String result)
			{
				if (url.endsWith("news"))
				{
					return ResultParser.getResultNews(result).contents.news;
				}
				else if (url.endsWith("article"))
				{
					return ResultParser.getResultArtc(result).contents.article;
				}
				else
				{
					return ResultParser.getResultAll(result).contents.all;
				}
			}

			@Override
			protected void onPostExecute(ArrayList data)
			{
				if (data != null)
				{
					saveToDB(context, data);
					if (IDownArticle.class.isInstance(context))
					{
						((IDownArticle) context).refreshData();
					}
				}
				else
				{
					Toast.makeText(context, "Data Error", Toast.LENGTH_LONG).show();
				}
			}
		}.execute(result);
	}

	private static void saveToDB(Context context, ArrayList<Article> data)
	{
		//for (Article article : data)
		for (int i = 0; i < data.size(); i++)
		{
			Article article = data.get(i);
			int type = isNew(article);
			if (type == 1 || type == 2) // new or update, 0 = old
			{
				article.save();
				showNotif(context, article, type);
			}
		}
	}

	private static void showNotif(Context context, Article article, int type)
	{
		Intent linkIntent = new Intent(context, DetailActivity.class);
		linkIntent.putExtra("FeedsDetail", article);

		long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		String title;
		if (type == 1)
		{
			title = "NEW " + article.category.toUpperCase();
		}
		else
		{
			title = "UPDATE " + article.category.toUpperCase();
		}
		Notification notif = NotifUtil
				.createNotif(context, linkIntent, NotifUtil.getIdNotif(), 0, R.mipmap.ic_launcher,
						true, null, title,
						article.title, null, null, -1, Color.GREEN, 500, 500, pattern, alarmSound);

		NotifUtil.sendNotif(context, NotifUtil.getIdNotif(), notif);
		NotifUtil.incIdNotif();
	}

	private static int isNew(Article article)
	{
		List<Article> list = Article.getAllId(article.id);
		if (list == null || (list != null && list.isEmpty()))
		{
			return 1;
		}
		else
		{
			if (!list.get(0).post_date.equals(article.post_date))
			{
				return 2;
			}
		}
		return 0;
	}

	public interface IDownArticle
	{
		void refreshData();
	}
}
