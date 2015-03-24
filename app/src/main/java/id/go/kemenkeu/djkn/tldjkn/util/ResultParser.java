package id.go.kemenkeu.djkn.tldjkn.util;

import com.google.gson.Gson;

import id.go.kemenkeu.djkn.tldjkn.model.ResultData;

/**
 * Created by Hendra on 21/03/2015.
 */
public class ResultParser
{
	//	public static final int MAXCONTENT = 80;

	public static ResultData getResult(String result)
	{
		return new Gson().fromJson(result, ResultData.class);
	}

	//	public static ResultData getFormattedResult(String result)
	//	{
	//		ResultData rData = getResult(result);
	//		format(rData);
	//
	//		return rData;
	//	}

	//	private static void format(ResultData rData)
	//	{
	//		for(Article article : rData.contents.article)
	//		{
	//			article.excerpt = formatExcerpt(article.excerpt);
	//		}
	//	}

	//	private static String formatExcerpt(String excerpt)
	//	{
	//		String part = excerpt.replaceAll("<(.*?)\\>", "");//Removes all items in brackets
	//		part = part.replaceAll("<(.*?)\\\n", "");//Must be undeneath
	//		part = part
	//				.replaceFirst("(.*?)\\>", "");//Removes any connected item to the last bracket
	//		part = part.replaceAll("&nbsp;", "");
	//		part = part.replaceAll("&amp;", "");
	//		part = part.substring(0, part.length() > MAXCONTENT ? MAXCONTENT : part.length())
	//				+ "...";
	//		return part;
	//	}
}
