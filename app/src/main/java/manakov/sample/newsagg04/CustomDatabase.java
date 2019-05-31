package manakov.sample.newsagg04;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import manakov.sample.newsagg04.RssItem.RssItem;
import manakov.sample.newsagg04.RssItem.RssItemDao;
import manakov.sample.newsagg04.UrlItem.UrlItem;
import manakov.sample.newsagg04.UrlItem.UrlItemDao;

@Database(
        entities = {
                UrlItem.class,
                RssItem.class
        },
        version = 2,
        exportSchema = false
)
public abstract class CustomDatabase extends RoomDatabase {
    public abstract UrlItemDao getUrlItemDao();
    public abstract RssItemDao getRssItemDao();
}
