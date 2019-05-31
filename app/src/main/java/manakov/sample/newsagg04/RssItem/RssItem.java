package manakov.sample.newsagg04.RssItem;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import manakov.sample.newsagg04.UrlItem.UrlItem;

import static androidx.room.ForeignKey.CASCADE;

@Entity(
        tableName = "rss_items",
        foreignKeys = {
            @ForeignKey(
                    entity = UrlItem.class,
                    parentColumns = "id",
                    childColumns = "urlId",
                    onDelete = CASCADE
            )
        }
)
public class RssItem {
    @PrimaryKey(autoGenerate = true )
    public int id;

    @ColumnInfo
    private String  title;

    @ColumnInfo
    private String date;

    @ColumnInfo
    private String  link;

    @ColumnInfo
    private String  description;

    @ColumnInfo
    private int urlId;

    public RssItem(String title, String date, String link, String description, int urlId){
        this.title       = title;
        this.date        = date;
        this.link        = link;
        this.description = description;
        this.urlId       = urlId;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getLink() {
        return link;
    }
    public String getDate() {
        return date;
    }
    public int getUrlId() {
        return urlId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

}
