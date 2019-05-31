package manakov.sample.newsagg04.UrlItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import manakov.sample.newsagg04.UrlItem.UrlItem;

@Dao
public interface UrlItemDao {
    @Query("select * from url_items")
    public List<UrlItem> getAll();

    @Query("select link from url_items where id=:urlItemId")
    public String getUrlItemLinkByUrlItemId(int urlItemId);

    @Query("delete from url_items where id=:urlItemId")
    public void deleteUrlItemByUrlItemId(int urlItemId);

    @Insert
    public void insert(UrlItem urlItem);

    @Insert
    public void insertAll(UrlItem... urlItems);

}
