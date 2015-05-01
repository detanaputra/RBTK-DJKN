package id.go.kemenkeu.djkn.tldjkn.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import id.go.kemenkeu.djkn.tldjkn.R;

/**
 * Created by Hendra on 26/03/2015.
 */
public class Msg
{
	public static void showToast(Activity activity)
	{
		View layout = activity.getLayoutInflater().inflate(R.layout.toast_content,
				(ViewGroup) activity.findViewById(R.id.toast_root));

		Toast toast = new Toast(activity);
		toast.setGravity(Gravity.BOTTOM | Gravity.FILL_HORIZONTAL, 0, 0);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();

	}
}
