package manakov.sample.newsagg04.RssItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import manakov.sample.newsagg04.RssItem.RssItem;

@Dao
public interface RssItemDao {
    @Query("select * from rss_items")
    public List<RssItem> getAll();

    @Insert
    public void insert(RssItem rssItem);

    @Insert
    public void insertAll(RssItem... rssItems);

    @Query("select * from rss_items where urlId=:urlId")
    public List<RssItem> getAllRssItemsByUrlId(int urlId);

    @Query("delete from rss_items where urlId=:urlId")
    public void deleteAllRssItemsByUrlItemId(int urlId);
}
