package id.go.kemenkeu.djkn.tldjkn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import id.go.kemenkeu.djkn.tldjkn.R;

public class NavigationDrawerFragment extends Fragment
{

	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

	private NavigationDrawerCallbacks mCallbacks;

	private DrawerLayout mDrawerLayout;
	private ScrollView mDrawer;
	private View mFragmentContainerView;

	private int mCurrentSelectedPosition = 1;

	public NavigationDrawerFragment()
	{
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null)
		{
			mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
		}
		selectItem(mCurrentSelectedPosition);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		mDrawer = (ScrollView) inflater
				.inflate(R.layout.fragment_navigation_drawer, container, false);
		LinearLayout llHome = (LinearLayout) mDrawer.findViewById(R.id.LinearLayoutHome);
		llHome.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectItem(1);
			}
		});
		LinearLayout llStar = (LinearLayout) mDrawer.findViewById(R.id.LinearLayoutStar);
		llStar.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectItem(2);
			}
		});
		LinearLayout llNews = (LinearLayout) mDrawer.findViewById(R.id.LinearLayoutNews);
		llNews.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectItem(41);
			}
		});
		LinearLayout llArtc = (LinearLayout) mDrawer.findViewById(R.id.LinearLayoutArticles);
		llArtc.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectItem(42);
			}
		});
		LinearLayout llArch = (LinearLayout) mDrawer.findViewById(R.id.LinearLayoutArchieves);
		llArch.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				selectItem(5);
			}
		});

		return mDrawer;
	}

	public boolean isDrawerOpen()
	{
		return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
	}

	public void openDrawer()
	{
		if (mDrawerLayout != null)
		{
			mDrawerLayout.openDrawer(mFragmentContainerView);
		}
	}

	public void setUp(int fragmentId, DrawerLayout drawerLayout)
	{
		mFragmentContainerView = getActivity().findViewById(fragmentId);
		mDrawerLayout = drawerLayout;

		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		mDrawerLayout.setStatusBarBackground(R.color.primary_dark);
	}

	private void selectItem(int position)
	{
		mCurrentSelectedPosition = position;
		if (mDrawerLayout != null)
		{
			mDrawerLayout.closeDrawer(mFragmentContainerView);
		}
		if (mCallbacks != null)
		{
			mCallbacks.onNavigationDrawerItemSelected(position);
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			mCallbacks = (NavigationDrawerCallbacks) activity;
		} catch (ClassCastException e)
		{
			throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
		}
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		mCallbacks = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
	}

	//	@Override
	//	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	//	{
	//		if (mDrawerLayout != null && isDrawerOpen())
	//		{
	//			inflater.inflate(R.menu.global, menu);
	//			showGlobalContextActionBar();
	//		}
	//		super.onCreateOptionsMenu(menu, inflater);
	//	}
	//
	//	@Override
	//	public boolean onOptionsItemSelected(MenuItem item)
	//	{
	//		if (item.getItemId() == R.id.action_example)
	//		{
	//			Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
	//			return true;
	//		}
	//
	//		return super.onOptionsItemSelected(item);
	//	}

	//	private void showGlobalContextActionBar()
	//	{
	//		ActionBar actionBar = getActionBar();
	//		actionBar.setDisplayShowTitleEnabled(true);
	//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
	//		actionBar.setTitle(R.string.app_name);
	//	}

	//	private ActionBar getActionBar()
	//	{
	//		return ((ActionBarActivity) getActivity()).getSupportActionBar();
	//	}

	public interface NavigationDrawerCallbacks
	{
		void onNavigationDrawerItemSelected(int position);
	}
}
