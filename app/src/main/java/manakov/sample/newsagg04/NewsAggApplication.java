package manakov.sample.newsagg04;

import android.app.Application;

import java.util.ArrayList;

import androidx.room.Room;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import manakov.sample.newsagg04.Refresh.RefreshWorker;
import manakov.sample.newsagg04.RssItem.RssItem;
import manakov.sample.newsagg04.UrlItem.UrlItem;

public class NewsAggApplication extends Application {

    private CustomDatabase database;
    private long delay;

    public static String item = "item";
    public static String urlItemKey ="urlItemId";

    public static String rssItemTitleTag            = "title";
    public static String rssItemDateTag             = "date";
    public static String rssItemLinkTag             = "link";
    public static String rssItemDescriptionTag      = "description";

    public static int addUrlItemRequestCode         = 101;
    public static int setSettingsRequestCode        = 102;
    public static int getUrlItemDisplayRequestCode  = 103;

    public static String lastActivitySharedPreferences = "NewsAgg04SP";
    public static String lastActivitiesTag = "lastActivitiesTag";

    public static int lastActivityIsTitle            = 0;
    public static int lastActivityIsUrlItemDisplay   = 1;
    public static int lastActivityIsAddUrlItem       = 2;
    public static int lastActivityIsSettings         = 3;

    @Override
    public void onCreate(){
        super.onCreate();

        database = Room.databaseBuilder(
                getApplicationContext(),
                CustomDatabase.class,
                "Main Database"
        )
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();

        delay = 1;
    }

    public CustomDatabase getDatabase() {
        return database;
    }

    public ArrayList<UrlItem> getAllUrlItems(){
        return
                (ArrayList<UrlItem>)
                    database
                    .getUrlItemDao()
                    .getAll();
    }

    public ArrayList<RssItem> getAllRssItemsByUrlId(int urlItemId){
        return
                (ArrayList<RssItem>)
                        database
                        .getRssItemDao()
                        .getAllRssItemsByUrlId(
                                urlItemId
                        );
    }

    public String getUrlItemLinkByUrlItemId(int urlItemId){
        return
                database
                .getUrlItemDao()
                .getUrlItemLinkByUrlItemId(
                        urlItemId
                );
    }

    public void addNewUrlItem(UrlItem urlItem){
        database
                .getUrlItemDao()
                .insert(
                        urlItem
                );
    }

    public void deleteUrlItemByUrlItemId(int urlItemId){
        database
                .getUrlItemDao()
                .deleteUrlItemByUrlItemId(
                        urlItemId
                );
    }

    public void resetRssItemsByUrlItemId(int urlItemId, ArrayList<RssItem> items){
        database
                .getRssItemDao()
                .deleteAllRssItemsByUrlItemId(
                        urlItemId
                );

        for (int i = 0; i<items.size(); i++){
            database
                    .getRssItemDao()
                    .insert(
                            items.get(i)
                    );
        }
    }

    public void refreshOnce(){
        WorkManager workManager = WorkManager.getInstance();
        workManager.enqueue(
                new OneTimeWorkRequest
                        .Builder(
                        RefreshWorker.class
                )
                        .build()
        );
    }

    public void setUp(){
        database.clearAllTables();

        fillUrlItems();
    }
    public void fillUrlItems(){
        ArrayList<UrlItem> urlItems = new ArrayList<>();
        urlItems.add(
                new UrlItem(
                        "News",
                        "https://news.mail.ru/rss/main/91/"
                )
        );
        urlItems.add(
                new UrlItem(
                        "Economics",
                        "https://news.mail.ru/rss/economics/91/"
                )
        );
        for(int i=0; i<urlItems.size(); i++){
            database
                    .getUrlItemDao()
                    .insert(
                            urlItems.get(i)
                    );
        }
        urlItems.clear();
        urlItems = getAllUrlItems();

        for (int i = 0; i< urlItems.size(); i++){
            UrlItem urlItem = urlItems.get(i);
            for (int j = 0; j< 10; j++){
                database
                        .getRssItemDao()
                        .insert(
                                new RssItem(
                                        "title"     + j + " " + i,
                                        "date"      + j + " " + i,
                                        "link"       + j + " " + i,
                                        "desc" + j + " " + i,
                                        urlItem.getId()
                                )
                        );
            }
        }



    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
    public long getDelay() {
        return delay;
    }
}
