package id.go.kemenkeu.djkn.tldjkn.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Hyua on 8/2/13.
 */
public class NotifUtil
{
	private static int sIdNotif;

	public static NotificationCompat.Builder createNotifBuilder(Context context, Intent intentLink,
																int idSmallIcon, boolean autoCancel,
																Bitmap largeIcon, String titleText,
																String contentText, String subText,
																String infoText, int number,
																int lightColor, int lightOnMs,
																int lightOffMs, long[] pattern,
																Uri alarmSound)
	{
		//		Intent intent = new Intent(Intent.ACTION_VIEW,
		//				Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentLink, 0);


		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

		if (idSmallIcon > -1)
		{
			builder.setSmallIcon(idSmallIcon);
		}

		if (pendingIntent != null)
		{
			builder.setContentIntent(pendingIntent);
		}

		builder.setAutoCancel(autoCancel);

		if (largeIcon != null)
		{
			builder.setLargeIcon(largeIcon);
		}

		if (titleText != null)
		{
			builder.setContentTitle(titleText);
		}

		if (contentText != null)
		{
			builder.setContentText(contentText);
		}

		if (subText != null)
		{
			builder.setSubText(subText);
		}

		if (infoText != null)
		{
			builder.setContentInfo(infoText);
		}

		if (number > -1)
		{
			builder.setNumber(number);
		}

		if (lightColor != 0)
		{
			if (lightOnMs <= 0)
			{
				lightOnMs = 500;
			}
			if (lightOffMs <= 0)
			{
				lightOffMs = 500;
			}

			builder.setLights(lightColor, lightOnMs, lightOffMs);

		}

		if (pattern != null)
		{
			builder.setVibrate(pattern);
		}

		if (alarmSound != null)
		{
			builder.setSound(alarmSound);
		}

		builder.setPriority(Notification.PRIORITY_MAX);

		return builder;
	}

	public static NotificationCompat.Builder createNotifBuilder(Context context, Intent intentLink,
																int idSmallIcon, boolean autoCancel,
																Bitmap largeIcon, String titleText,
																String contentText, String subText,
																String infoText, int number,
																int maxProgress, int progress,
																boolean isIndeterminate,
																int lightColor, int lightOnMs,
																int lightOffMs, long[] pattern,
																Uri alarmSound)
	{
		//		Intent intent = new Intent(Intent.ACTION_VIEW,
		//				Uri.parse("http://developer.android.com/reference/android/app/Notification.html"));
		//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentLink, 0);


		NotificationCompat.Builder builder = createNotifBuilder(context, intentLink, idSmallIcon,
				autoCancel, largeIcon, titleText, contentText, subText, infoText, number,
				lightColor, lightOnMs, lightOffMs, pattern, alarmSound);

		builder.setProgress(maxProgress, progress, isIndeterminate);

		return builder;
	}

	public static Notification createNotif(Context context, Intent intentLink, int idSmallIcon,
										   boolean autoCancel, Bitmap largeIcon, String titleText,
										   String contentText, String subText, String infoText,
										   int number, int lightColor, int lightOnMs,
										   int lightOffMs, long[] pattern, Uri alarmSound)
	{

		NotificationCompat.Builder builder = createNotifBuilder(context, intentLink, idSmallIcon,
				autoCancel, largeIcon, titleText, contentText, subText, infoText, number,
				lightColor, lightOnMs, lightOffMs, pattern, alarmSound);

		Notification notif = builder.build();

		return notif;
	}

	public static void sendNotif(Context context, int idNotif, Notification notif)
	{
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(idNotif, notif);
	}

	public static void cancelNotif(Context context, int idNotif)
	{
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(idNotif);
	}

	public static Notification setToOngoing(Notification notif)
	{
		notif.flags = Notification.FLAG_ONGOING_EVENT;

		return notif;
	}

	public static int getIdNotif()
	{
		return sIdNotif;
	}

	public static void incIdNotif()
	{
		sIdNotif++;
	}
}
