package id.go.kemenkeu.djkn.tldjkn.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Hendra on 21/03/2015.
 */
@Table(name = "Objek")
public class Article extends Model implements Serializable
{
	@Column(name = "IdArticle", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
	public String id;
	@Column(name = "Category")
	public String category;
	@Column(name = "Title")
	public String title;
	@Column(name = "Slug")
	public String slug;
	@Column(name = "Excerpt")
	public String excerpt;
	@Column(name = "Body")
	public String body;
	@Column(name = "Author")
	public String author;
	@Column(name = "PostDate")
	public String post_date;
	@Column(name = "Thumbnail")
	public String thumbnail;
	@Column(name = "Viewed")
	public long viewed;
	@Column(name = "Star")
	public boolean star;
	@Column(name = "Readed")
	public boolean readed;

	public static boolean isEmpty()
	{
		return (new Select().from(Article.class).executeSingle() == null);
	}

	public static List<Article> getAll()
	{
		return new Select().from(Article.class).orderBy("PostDate DESC").execute();
	}

	public static List<Article> getAllNews()
	{
		return new Select().from(Article.class).where("Category = ?", "news")
				.orderBy("PostDate DESC").execute();
	}

	public static List<Article> getAllArtc()
	{
		return new Select().from(Article.class).where("Category = ?", "article")
				.orderBy("PostDate DESC").execute();
	}

	public static List<Article> getAllSearch(String search)
	{
		return new Select().from(Article.class).where("Title LIKE '%" + search + "%'")
				.orderBy("PostDate DESC").execute();
	}

	public static List<Article> getAllStarSearch(String search)
	{
		return new Select().from(Article.class).where("Star = ?", true)
				.where("Title LIKE '%" + search + "%'").orderBy("PostDate DESC").execute();
	}

	public static List<Article> getAllArch()
	{
		return new Select().from(Article.class).where("Readed = ?", true).orderBy("PostDate DESC")
				.execute();
	}

	public static List<Article> getAllUnread()
	{
		return new Select().from(Article.class).where("Readed = ?", false).orderBy("PostDate DESC")
				.execute();
	}

	public static List<Article> getAllId(String id)
	{
		return new Select().from(Article.class).where("IdArticle = ?", id).execute();
	}

	public static List<Article> getAllStar()
	{
		return new Select().from(Article.class).where("Star = ?", true).orderBy("PostDate DESC")
				.execute();
	}

	public static void delete(Article article)
	{
		new Delete().from(Article.class).where("IdArticle = ?", article.id).execute();
	}
}
