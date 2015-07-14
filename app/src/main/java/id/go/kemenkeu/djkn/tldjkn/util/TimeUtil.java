package id.go.kemenkeu.djkn.tldjkn.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by Hendra on 22/03/2015.
 */
public class TimeUtil
{
	public static long getCurrentDayDiff(String datetime)
	{
		long diffMillis = Math.abs(getNow() - getDateTime(datetime));
		long diff = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);

		return diff;
	}

	public static long getNow()
	{
		return Calendar.getInstance().getTimeInMillis();
	}

	public static long getDateTime(String datetime)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //2015-03-06 15:41:16
		try
		{
			return format.parse(datetime).getTime();
		} catch (ParseException e)
		{
			//e.printStackTrace();
		}
		return 0;
	}

	public static long getCurrentHourDiff(String datetime)
	{
		long diffMillis = Math.abs(getNow() - getDateTime(datetime));
		long diff = TimeUnit.HOURS.convert(diffMillis, TimeUnit.MILLISECONDS);

		return diff;
	}

	public static long getCurrentMinuteDiff(String datetime)
	{
		long diffMillis = Math.abs(getNow() - getDateTime(datetime));
		long diff = TimeUnit.MINUTES.convert(diffMillis, TimeUnit.MILLISECONDS);

		return diff;
	}

	public static long getCurrentSecoundDiff(String datetime)
	{
		long diffMillis = Math.abs(getNow() - getDateTime(datetime));
		long diff = TimeUnit.SECONDS.convert(diffMillis, TimeUnit.MILLISECONDS);

		return diff;
	}

	public static String getFormattedDateTimeID(long dateTime)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy - k:m", new Locale("in", "ID"));
		return sdf.format(new Date(dateTime));
	}
}
