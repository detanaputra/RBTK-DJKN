package id.go.kemenkeu.djkn.tldjkn.util;

import com.google.gson.Gson;

import id.go.kemenkeu.djkn.tldjkn.model.ResultAll;
import id.go.kemenkeu.djkn.tldjkn.model.ResultArtc;
import id.go.kemenkeu.djkn.tldjkn.model.ResultNews;

/**
 * Created by Hendra on 21/03/2015.
 */
public class ResultParser
{
	//	public static final int MAXCONTENT = 80;

	public static ResultAll getResultAll(String result)
	{
		return new Gson().fromJson(result, ResultAll.class);
	}

	public static ResultNews getResultNews(String result)
	{
		return new Gson().fromJson(result, ResultNews.class);
	}

	public static ResultArtc getResultArtc(String result)
	{
		return new Gson().fromJson(result, ResultArtc.class);
	}

	//	public static ResultAll getFormattedResult(String result)
	//	{
	//		ResultAll rData = getResult(result);
	//		format(rData);
	//
	//		return rData;
	//	}

	//	private static void format(ResultAll rData)
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
