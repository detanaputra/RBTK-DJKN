package id.go.kemenkeu.djkn.tldjkn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import id.go.kemenkeu.djkn.tldjkn.R;
import id.go.kemenkeu.djkn.tldjkn.model.Article;

public class DetailActivity extends ActionBarActivity
{
	private ShareActionProvider mShareActionProvider;

	private boolean isStarChanged = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.ic_back);
		toolbar.setLogo(R.mipmap.ic_launcher);
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});

		//		toolbar.setTitleTextColor(getResources().getColor(R.color.drawer));
		//		toolbar.setBackgroundColor(getResources().getColor(R.color.primary));
		//		toolbar.setTitle("DETAIL");
		TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
		tvTitle.setText("DETAIL");

		toolbar.inflateMenu(R.menu.detail);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem menuItem)
			{
				int id = menuItem.getItemId();

				if (id == R.id.action_star)
				{
					doChangeStar();
					isStarChanged = true;
					return true;
				}
				else if (id == R.id.action_share)
				{
					doShare();
					return true;
				}

				return false;
			}
		});


		Article article = (Article) getIntent().getSerializableExtra("FeedsDetail");

		//		long day = TimeUtil.getCurrentDayDiff(article.post_date);
		//		long hour = TimeUtil.getCurrentHourDiff(article.post_date);
		//		long minute = TimeUtil.getCurrentMinuteDiff(article.post_date);
		//		long second = TimeUtil.getCurrentSecoundDiff(article.post_date);

		String updated = "<p>telah dilihat " + article.viewed + " kali<br>" + article.post_date
				+ " oleh " + article.author + "</p>"
				//				+ " / " + (day > 0 ? day + " hari"
				//				: hour > 0 ? hour + " jam" : minute > 0 ? minute + " menit" : second + " detik")
				//				+" yang lalu"
				;

		WebView wvDetail = (WebView) findViewById(R.id.webViewDetail);

		//		wvDetail.setInitialScale(1);
		//		wvDetail.getSettings().setJavaScriptEnabled(true);
		//		wvDetail.getSettings().setLoadWithOverviewMode(true);
		//		wvDetail.getSettings().setUseWideViewPort(true);
		//		wvDetail.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		//		wvDetail.setScrollbarFadingEnabled(false);

		wvDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		wvDetail.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		wvDetail.loadData(formatDetail(article.title, updated, article.body,
				getString(R.string.urlroot) + article.slug), "text/html; charset=UTF-8", null);

		initStar();
		incViewed();
	}

	private void initStar()
	{
		Article article = (Article) getIntent().getSerializableExtra("FeedsDetail");
		setStar(article.star);
		//		Set<String> stars = Prefs.getStringSet(getString(R.string.pref_star), null);
		//		if(stars==null)
		//		{
		//			setStar(false);
		//		}
		//		else
		//		{
		//			setStar(stars.contains(article.id));
		//		}
	}

	private void setStar(boolean isStar)
	{
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		MenuItem item = toolbar.getMenu().findItem(R.id.action_star);
		if (isStar)
		{
			item.setIcon(R.mipmap.ic_action_star);
		}
		else
		{
			item.setIcon(R.mipmap.ic_action_nostar);
		}
	}


	private void doChangeStar()
	{

		//		Set<String> stars = Prefs.getStringSet(getString(R.string.pref_star),null);
		//		if(stars==null)
		//		{
		//			stars = new HashSet<>();
		//		}
		Article article = (Article) getIntent().getSerializableExtra("FeedsDetail");
		article.star = !article.star;
		article.save();
		setStar(article.star);
		//		if(!stars.contains(article.id))
		//		{
		//			stars.add(article.id);
		//			Prefs.putStringSet(getString(R.string.pref_star), stars);
		//			setStar(true);
		//		}
		//		else
		//		{
		//			stars.remove(article.id);
		//			Prefs.putStringSet(getString(R.string.pref_star), stars);
		//			setStar(false);
		//		}
	}

	private String formatDetail(String title, String updated, String content, String link)
	{
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<center><h1>" + title + "</h1></center>");
		htmlString.append("<em>" + updated + "</em>");
		htmlString.append("<p>" + content + "<p>");
		htmlString.append("<center><a href='");
		htmlString.append(link);
		htmlString.append("'><h3>" + "Lihat Versi Web" + "</h3></a></center>");

		return htmlString.toString();
	}

	private void doShare()
	{
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("text/plain");

		Article article = (Article) getIntent().getSerializableExtra("FeedsDetail");
		intent.putExtra(Intent.EXTRA_TEXT,
				article.title + "\n" + getString(R.string.urlroot) + article.slug);

		startActivity(Intent.createChooser(intent, "Berbagi Ke... "));
	}

	@Override
	public void onBackPressed()
	{
		//if(isStarChanged)
		setResult(RESULT_OK);
		//else
		//	setResult(RESULT_CANCELED);
		super.onBackPressed();
	}

	private void incViewed()
	{
		Article article = (Article) getIntent().getSerializableExtra("FeedsDetail");
		article.viewed++;
		article.save();
	}
}
