/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package id.go.kemenkeu.djkn.tldjkn.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.go.kemenkeu.djkn.tldjkn.R;
import id.go.kemenkeu.djkn.tldjkn.model.Article;
import id.go.kemenkeu.djkn.tldjkn.util.TimeUtil;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class FeedsAdapter extends RecyclerView.Adapter<FeedsAdapter.ViewHolder>
{

	//	public static final int BERITA = 1;
	//	public static final int LELANG = 2;
	//	public static final int PENGUMUMAN = 3;

	private final Context mContext;

	private int mType;
	private ArrayList<Article> mDataSet;

	private IAdapter mIAdapter;


	/**
	 * Initialize the dataset of the Adapter.
	 *
	 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
	 */
	public FeedsAdapter(Context context, ArrayList<Article> dataSet, int type)
	{
		mDataSet = dataSet;
		mType = type;
		mContext = context;
		mIAdapter = (IAdapter) context;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position)
	{
		// Create a new view.
		View v = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.list_row_item, viewGroup, false);

		//		int back;
		//		if (mType == BERITA)
		//		{
		//			back = R.drawable.beritaback;
		//		}
		//		else if (mType == LELANG)
		//		{
		//			back = R.drawable.lelangback;
		//		}
		//		else
		//		{
		//			back = R.drawable.pengumumanback;
		//		}
		//		v.findViewById(R.id.linearLayoutItem).setBackgroundResource(back);

		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position)
	{

		final Article article = mDataSet.get(position);
		long day = TimeUtil.getCurrentDayDiff(article.post_date);
		long hour = TimeUtil.getCurrentHourDiff(article.post_date);
		final long minute = TimeUtil.getCurrentMinuteDiff(article.post_date);
		long second = TimeUtil.getCurrentSecoundDiff(article.post_date);

		viewHolder.getmUpdated().setText("dilihat " + article.viewed + " kali"
				//+" oleh "+article.author
				+ " / " + (day > 0 ? day + " hari"
				: hour > 0 ? hour + " jam" : minute > 0 ? minute + " menit" : second + " detik"));
		viewHolder.getmTitle().setText(article.title);
		viewHolder.getmContent().setText(Html.fromHtml(article.excerpt));
		if (article.thumbnail != null)
		{
			//viewHolder.getmImage().setVisibility(View.VISIBLE);
			Glide.with(mContext).load(mContext.getString(R.string.urlroot) + article.thumbnail)
					//.placeholder(R.drawable.logocolor)
					//.thumbnail(0.1f)
					//.centerCrop()
					.bitmapTransform(
							new RoundedCornersTransformation(Glide.get(mContext).getBitmapPool(), 8,
									0)).crossFade().into(viewHolder.getmImage());
		}
		else
		{
			//viewHolder.getmImage().setVisibility(View.GONE);
		}

		viewHolder.getmImage().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mIAdapter.gotoWeb(article, mType);
			}
		});

		viewHolder.getmCardContent().setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mIAdapter.gotoWeb(article, mType);
			}
		});
	}

	//	private void gotoWeb(Article detail)
	//	{
	//		Intent intent = new Intent(mContext, DetailActivity.class);
	//		intent.putExtra("FeedsDetail", detail);
	//		intent.putExtra("FeedsType", mType);
	//		mContext.startActivity(intent);
	//	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount()
	{
		if (mDataSet != null)
		{
			return mDataSet.size();
		}
		return 0;
	}

	public interface IAdapter
	{
		void gotoWeb(Article detail, int type);
	}

	/**
	 * Provide a reference to the type of views that you are using (custom viewholder)
	 */
	public static class ViewHolder extends RecyclerView.ViewHolder
	{
		private final ImageView mImage;
		private final TextView mTitle;
		private final TextView mContent;
		private final TextView mUpdated;
		private final LinearLayout mBack;
		//		private final CardView mCardImg;
		private final CardView mCardContent;

		public ViewHolder(View v)
		{
			super(v);
			mImage = (ImageView) v.findViewById(R.id.imageViewItem);
			mTitle = (TextView) v.findViewById(R.id.textViewTitleItem);
			mContent = (TextView) v.findViewById(R.id.textViewContentItem);
			mUpdated = (TextView) v.findViewById(R.id.textViewUpdatedItem);
			mBack = (LinearLayout) v.findViewById(R.id.linearLayoutItem);
			//			mCardImg = (CardView) v.findViewById(R.id.cardviewImage);
			mCardContent = (CardView) v.findViewById(R.id.cardviewContent);
		}

		public ImageView getmImage()
		{
			return mImage;
		}

		public TextView getmTitle()
		{
			return mTitle;
		}

		public TextView getmContent()
		{
			return mContent;
		}

		public TextView getmUpdated()
		{
			return mUpdated;
		}

		public LinearLayout getmBack()
		{
			return mBack;
		}

		//		public CardView getmCardImg()
		//		{
		//			return mCardImg;
		//		}

		public CardView getmCardContent()
		{
			return mCardContent;
		}
	}
}
