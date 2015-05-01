package id.go.kemenkeu.djkn.tldjkn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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

		//		wvDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		wvDetail.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		wvDetail.loadDataWithBaseURL(null,//file:///android_res/drawable/",
				formatDetail(article.title, updated, article.body,
						getString(R.string.urlroot) + article.slug), "text/html", "UTF-8", null);

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
		//		Display display = getWindowManager().getDefaultDisplay();
		//		int width=display.getWidth();
		//		String head="<head><meta name=\"viewport\"\"content=\"width="+width+", initial-scale=0.65 \" /></head>";

		StringBuilder htmlString = new StringBuilder();
		//		htmlString.append(head);
		htmlString
				.append("<html><style>img{display: inline;height: auto;max-width: 100%;}</style>");
		htmlString.append("<body><center><h2>" + title + "</h2></center>");
		htmlString.append("<em>" + updated + "</em>");
		htmlString.append("<p>" + content + "<p>");
		htmlString.append("<br><br><p><center><a href='");
		htmlString.append(link);
		htmlString.append("'>"
				//+ "<img src=\"visit.png\"/>"
				+ "<img src=\"data:image/png;base64,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\"/>"
				+ "</a></center></p></body></html>");

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
