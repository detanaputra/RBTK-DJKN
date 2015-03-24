package id.go.kemenkeu.djkn.tldjkn.activity;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;

import id.go.kemenkeu.djkn.tldjkn.R;

/**
 * Created by Hendra on 19/03/2015.
 */
public class SplashActivity extends ActionBarActivity
{
	//	private float mLastTouchX;
	//	private float mLastTouchY;
	//	private float mPosX;
	//	private float mPosY;

	private GestureDetectorCompat mDetector;

	private boolean isUp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
	}

	//	private int mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	//
	//	@Override
	//	public boolean onTouchEvent(MotionEvent ev) {
	//
	//		final int action = MotionEventCompat.getActionMasked(ev);
	//
	//		switch (action) {
	//			case MotionEvent.ACTION_DOWN: {
	//				final int pointerIndex = MotionEventCompat.getActionIndex(ev);
	//				final float x = MotionEventCompat.getX(ev, pointerIndex);
	//				final float y = MotionEventCompat.getY(ev, pointerIndex);
	//
	//				// Remember where we started (for dragging)
	//				mLastTouchX = x;
	//				mLastTouchY = y;
	//				// Save the ID of this pointer (for dragging)
	//				mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
	//				break;
	//			}
	//
	//			case MotionEvent.ACTION_MOVE: {
	//				// Find the index of the active pointer and fetch its position
	//				final int pointerIndex =
	//						MotionEventCompat.findPointerIndex(ev, mActivePointerId);
	//
	//				final float x = MotionEventCompat.getX(ev, pointerIndex);
	//				final float y = MotionEventCompat.getY(ev, pointerIndex);
	//
	//				// Calculate the distance moved
	//				final float dx = x - mLastTouchX;
	//				final float dy = y - mLastTouchY;
	//
	//				mPosX += dx;
	//				mPosY += dy;
	//
	//				WindowManager.LayoutParams params =
	//						getWindow().getAttributes();
	//				//params.x = Math.round(mPosX);
	//				//params.height = 100;
	//				//params.width = 250;
	//				params.y = Math.round(mPosY);
	//				this.getWindow().setAttributes(params);
	//				if(dy>0)
	//				this.finish();
	//
	//				// Remember this touch position for the next move event
	//				mLastTouchX = x;
	//				mLastTouchY = y;
	//
	//				break;
	//			}
	//
	//			case MotionEvent.ACTION_UP: {
	//				mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	//				break;
	//			}
	//
	//			case MotionEvent.ACTION_CANCEL: {
	//				mActivePointerId = MotionEvent.INVALID_POINTER_ID;
	//				break;
	//			}
	//
	//			case MotionEvent.ACTION_POINTER_UP: {
	//
	//				final int pointerIndex = MotionEventCompat.getActionIndex(ev);
	//				final int pointerId = MotionEventCompat.getPointerId(ev, pointerIndex);
	//
	//				if (pointerId == mActivePointerId) {
	//					// This was our active pointer going up. Choose a new
	//					// active pointer and adjust accordingly.
	//					final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
	//					mLastTouchX = MotionEventCompat.getX(ev, newPointerIndex);
	//					mLastTouchY = MotionEventCompat.getY(ev, newPointerIndex);
	//					mActivePointerId = MotionEventCompat.getPointerId(ev, newPointerIndex);
	//				}
	//				break;
	//			}
	//		}
	//		return true;
	//	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	@Override
	public void finish()
	{
		super.finish();
		if (isUp)
		{
			overridePendingTransition(0, R.anim.activity_up_translate);
		}
		else
		{
			overridePendingTransition(0, R.anim.activity_down_translate);
		}
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener
	{
		//		private static final String DEBUG_TAG = "Gestures";

		@Override
		public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX,
							   float velocityY)
		{
			//			Log.d(DEBUG_TAG + velocityX + "|" + velocityY,
			//					"onFling: " + event1.toString() + event2.toString());
			if (Math.abs(velocityY) > Math.abs(velocityX))
			{
				if (velocityY < 0)
				{
					isUp = true;
				}
				finish();
			}
			return true;
		}
	}
}
