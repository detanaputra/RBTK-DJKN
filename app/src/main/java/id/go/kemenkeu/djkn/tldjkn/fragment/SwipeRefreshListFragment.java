/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package id.go.kemenkeu.djkn.tldjkn.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import id.go.kemenkeu.djkn.tldjkn.R;
import id.go.kemenkeu.djkn.tldjkn.activity.HomeActivity;
import id.go.kemenkeu.djkn.tldjkn.adapter.FeedsAdapter;
import id.go.kemenkeu.djkn.tldjkn.model.Article;
import id.go.kemenkeu.djkn.tldjkn.service.ServiceDownArticle;
import id.go.kemenkeu.djkn.tldjkn.util.ConnUtil;
import id.go.kemenkeu.djkn.tldjkn.util.Msg;

public class SwipeRefreshListFragment extends Fragment
{
	public SwipeRefreshLayout mSwipeRefreshLayout;
	protected RecyclerView mRecyclerView;
	protected RecyclerView.Adapter mAdapter;
	protected RecyclerView.LayoutManager mLayoutManager;
	protected ArrayList<Article> mDataset = new ArrayList<>();
	String mUrl;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_list, container, false);

		//swipeRefresh
		mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
		mSwipeRefreshLayout
				.setColorSchemeResources(R.color.background, R.color.drawer, R.color.primary_dark,
						R.color.primary);

		//recyclerView
		mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
		//mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(getActivity());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mAdapter = new FeedsAdapter(getActivity(), mDataset, getArguments().getInt("Menu"));
		mRecyclerView.setAdapter(mAdapter);

		mUrl = getArguments().getString("Url");
		//Log.i("FLOW","URL:"+mUrl);

		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);

		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				doCheckRefresh();
				//initDownRefresh();//initRefreshFilter();//initiateRefresh(mUrl);
			}
		});

		mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
						getResources().getDisplayMetrics()));
		mSwipeRefreshLayout.setRefreshing(true);

		initRefreshFilter();
	}

	private void doCheckRefresh()
	{
		if (ConnUtil.isNetConnected(getActivity()))
		{
			initDownRefresh();
		}
		else
		{
			mSwipeRefreshLayout.setRefreshing(false);
			Msg.showNoConnection(getActivity());
		}
	}

	private void initDownRefresh()
	{
		ServiceDownArticle.downToDB(getActivity(), mUrl);
	}

	public void initRefreshFilter()
	{
		//Log.d("FLOW", "initRefreshFilter" + getArguments().getInt("Menu"));
		//Log.d("FLOW->",""+new Select().from(Article.class).executeSingle());
		if (getArguments().getInt("Menu") == 2)
		{
			//			Set<String> stars = Prefs.getStringSet(getString(R.string.pref_star), null);
			//			if (stars == null || (stars != null && stars.isEmpty()))
			//			{
			//				mSwipeRefreshLayout.setRefreshing(false);
			//			}
			//			else
			//			{
			if (Article.isEmpty())
			{
				doCheckRefresh();
			}
			else
			{
				refreshData(Article.getAllStar());
			}
			//initiateRefresh(mUrl);
			//			}
		}
		else
		{
			if (Article.isEmpty())
			{
				doCheckRefresh();
			}
			else
			{
				refreshData(Article.getAll());
			}
			//initiateRefresh(mUrl);
		}
	}

	//	public void initiateRefresh()
	//	{
	//		initiateRefresh(mUrl);
	//	}
	//
	//	private void initiateRefresh(String url)
	//	{
	//
	//		StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
	//				new Response.Listener<String>()
	//				{
	//					@Override
	//					public void onResponse(String response)
	//					{
	//						//Log.i("FLOW","response:"+response);
	//						onRefreshComplete(response);
	//					}
	//				}, new Response.ErrorListener()
	//		{
	//			@Override
	//			public void onErrorResponse(VolleyError error)
	//			{
	//				Log.e("FLOW", "volleyError:" + error.toString());
	//				Toast.makeText(getActivity(), "Connection Error", Toast.LENGTH_LONG).show();
	//				//						Toast.makeText(getActivity(),"Connection Error:"+error.toString(),
	//				//								Toast.LENGTH_LONG).show();
	//				initiateRefresh(mUrl);
	//			}
	//		});
	//		stringRequest.setTag(mUrl);
	//		VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
	//	}

	//	private void onRefreshComplete(String result)
	//	{
	//		new AsyncTask<String, Void, ArrayList>()
	//		{
	//
	//			@Override
	//			protected ArrayList doInBackground(String... results)
	//			{
	//				return parseResult(results[0]);
	//			}
	//
	//			private ArrayList parseResult(String result)
	//			{
	//				return ResultParser.getResult(result).contents.article;
	//			}
	//
	//			@Override
	//			protected void onPostExecute(ArrayList data)
	//			{
	//				if (data != null)
	//				{
	//					refreshData(data);
	//				}
	//				else
	//				{
	//					Toast.makeText(getActivity(), "Data Error", Toast.LENGTH_LONG).show();
	//				}
	//			}
	//		}.execute(result);
	//	}

	public void refreshData(List data)
	{
		//		if(!isDetached())
		//		{
		mDataset.clear();
		mDataset.addAll(data);

		//			if (getArguments().getInt("Menu") == 2)
		//			{
		//				filterStar();
		//			}

		mAdapter.notifyDataSetChanged();
		// Stop the refreshing indicator
		mSwipeRefreshLayout.setRefreshing(false);
		//		}
	}

	//	private void filterStar()
	//	{
	//		Set<String> stars = Prefs.getStringSet(getString(R.string.pref_star), null);
	//		for(int i=0;i<mDataset.size();i++)
	//		{
	//			if(!stars.contains(mDataset.get(i).id))
	//			{
	//				mDataset.remove(i--);
	//			}
	//		}
	//	}

	//	@Override
	//	public void onStop()
	//	{
	//		Log.d("FLOW", "onStop" + getArguments().getInt("Menu"));
	//		super.onStop();
	//		//VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(mUrl);
	//	}

	@Override
	public void onAttach(Activity activity)
	{
		Log.d("FLOW", "onAttach" + getArguments().getInt("Menu"));
		super.onAttach(activity);
		((HomeActivity) activity).onSectionAttached(getArguments().getInt("Menu"));
	}

	@Override
	public void onDetach()
	{
		Log.d("FLOW", "onDetach" + getArguments().getInt("Menu"));
		//VolleySingleton.getInstance(getActivity()).getRequestQueue().cancelAll(mUrl);
		ServiceDownArticle.cancelDown(getActivity(), mUrl);
		mSwipeRefreshLayout.setRefreshing(false);
		super.onDetach();
	}

}
