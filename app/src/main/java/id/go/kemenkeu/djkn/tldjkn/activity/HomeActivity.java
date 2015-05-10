package id.go.kemenkeu.djkn.tldjkn.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import id.go.kemenkeu.djkn.tldjkn.R;
import id.go.kemenkeu.djkn.tldjkn.adapter.FeedsAdapter;
import id.go.kemenkeu.djkn.tldjkn.fragment.NavigationDrawerFragment;
import id.go.kemenkeu.djkn.tldjkn.fragment.SwipeRefreshListFragment;
import id.go.kemenkeu.djkn.tldjkn.model.Article;
import id.go.kemenkeu.djkn.tldjkn.service.ArticleStartReceiver;
import id.go.kemenkeu.djkn.tldjkn.service.ServiceDownArticle;


public class HomeActivity extends ActionBarActivity
		implements NavigationDrawerFragment.NavigationDrawerCallbacks, FeedsAdapter.IAdapter,
		ServiceDownArticle.IDownArticle
{
	public static final int DETAIL_REQUEST = 6371;

	private NavigationDrawerFragment mNavigationDrawerFragment;

	private CharSequence mTitle;
	private boolean mIsSearching;
	private String mSearch;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getString(R.string.title_section1);//getTitle();

		mNavigationDrawerFragment
				.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//setSupportActionBar(toolbar);
		toolbar.setNavigationIcon(R.mipmap.ic_drawer);
		toolbar.setLogo(R.mipmap.ic_launcher);
		//toolbar.setTitleTextColor(getResources().getColor(R.color.drawer));
		toolbar.setNavigationOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mNavigationDrawerFragment.openDrawer();
			}
		});

		toolbar.inflateMenu(R.menu.home);
		toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
		{
			@Override
			public boolean onMenuItemClick(MenuItem menuItem)
			{
				int id = menuItem.getItemId();

				return id == R.id.action_search;

			}
		});

		MenuItem searchItem = toolbar.getMenu().findItem(R.id.action_search);
		final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		searchView.setQueryHint("judul");
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
		{
			@Override
			public boolean onQueryTextSubmit(String s)
			{
				mSearch = s;
				showSearch(s);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String s)
			{
				return false;
			}
		});
		MenuItemCompat
				.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener()
						{
							@Override
							public boolean onMenuItemActionExpand(MenuItem item)
							{
								mIsSearching = true;
								return true;
							}

							@Override
							public boolean onMenuItemActionCollapse(MenuItem item)
							{
								refreshData();
								mIsSearching = false;
								return true;
							}
						});

		//onNavigationDrawerItemSelected(1);
		startActivity(new Intent(this, SplashActivity.class));
		startDownService();
	}

	private void showSearch(String query)
	{
		SwipeRefreshListFragment fragment = (SwipeRefreshListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.container);
		if (mTitle.equals(getString(R.string.title_section2)))
		{
			fragment.refreshData(Article.getAllStarSearch(query));
		}
		else
		{
			fragment.refreshData(Article.getAllSearch(query));
		}

	}

	@Override
	public void onNavigationDrawerItemSelected(int position)
	{
		SwipeRefreshListFragment fragment = new SwipeRefreshListFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("Menu", position);
		if (position == 41)
		{
			bundle.putString("Url", getString(R.string.urlnews));
		}
		else if (position == 42)
		{
			bundle.putString("Url", getString(R.string.urlarticles));
		}
		else
		{
			bundle.putString("Url", getString(R.string.urlall));
		}
		fragment.setArguments(bundle);

		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
	}

	public void onSectionAttached(int number)
	{
		switch (number)
		{
			case 1:
				mTitle = getString(R.string.title_section1);
				break;
			case 2:
				mTitle = getString(R.string.title_section2);
				break;
			case 41:
				mTitle = getString(R.string.title_section41);
				break;
			case 42:
				mTitle = getString(R.string.title_section42);
				break;
			case 5:
				mTitle = getString(R.string.title_section5);
				break;
		}

		TextView tvTitle = (TextView) findViewById(R.id.textViewTitle);
		if (tvTitle != null)
		{
			tvTitle.setText(mTitle);
		}
		//		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		//		toolbar.setTitle(mTitle);

	}

	//	public void restoreActionBar()
	//	{
	//		ActionBar actionBar = getSupportActionBar();
	//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	//		actionBar.setDisplayShowTitleEnabled(true);
	//		actionBar.setTitle(mTitle);
	//	}


	//	@Override
	//	public boolean onCreateOptionsMenu(Menu menu)
	//	{
	//		if (!mNavigationDrawerFragment.isDrawerOpen())
	//		{
	//			getMenuInflater().inflate(R.menu.home, menu);
	//			restoreActionBar();
	//			return true;
	//		}
	//		return super.onCreateOptionsMenu(menu);
	//	}

	//	@Override
	//	public boolean onOptionsItemSelected(MenuItem item)
	//	{
	//		int id = item.getItemId();
	//
	//		if (id == R.id.action_settings)
	//		{
	//			return true;
	//		}
	//
	//		return super.onOptionsItemSelected(item);
	//	}

	@Override
	public void gotoWeb(Article detail, int type)
	{
		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra("FeedsDetail", detail);
		intent.putExtra("FeedsType", type);
		//		if(type==2)
		//		{
		startActivityForResult(intent, DETAIL_REQUEST);
		//		}
		//		else
		//		{
		//			startActivity(intent);
		//		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		//Log.d("FLOW", "onActivityResult" + requestCode + "|" + resultCode);
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == DETAIL_REQUEST)
		{
			if (resultCode == Activity.RESULT_OK)
			{
				SwipeRefreshListFragment fragment
						= (SwipeRefreshListFragment) getSupportFragmentManager()
						.findFragmentById(R.id.container);
				fragment.mSwipeRefreshLayout.setRefreshing(true);
				if (mIsSearching)
				{
					showSearch(mSearch);
				}
				else
				{
					refreshData();
				}
				//fragment.mSwipeRefreshLayout.setRefreshing(false);
			}
		}
	}

	@Override
	public void refreshData()
	{
		SwipeRefreshListFragment fragment = (SwipeRefreshListFragment) getSupportFragmentManager()
				.findFragmentById(R.id.container);
		if (mTitle.equals(getString(R.string.title_section2)))
		{
			fragment.refreshData(Article.getAllStar());
		}
		else if (mTitle.equals(getString(R.string.title_section41)))
		{
			fragment.refreshData(Article.getAllNews());
		}
		else if (mTitle.equals(getString(R.string.title_section42)))
		{
			fragment.refreshData(Article.getAllArtc());
		}
		else if (mTitle.equals(getString(R.string.title_section5)))
		{
			fragment.refreshData(Article.getAllArch());
		}
		else
		{
			fragment.refreshData(Article.getAll());
		}
	}

	private void startDownService()
	{
		AlarmManager service = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		Intent i = new Intent(this, ArticleStartReceiver.class);
		PendingIntent pending = PendingIntent
				.getBroadcast(this, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
		service.setInexactRepeating(AlarmManager.ELAPSED_REALTIME, AlarmManager.INTERVAL_HOUR,
				AlarmManager.INTERVAL_HOUR, pending);
	}
}
