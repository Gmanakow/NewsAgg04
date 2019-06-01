package manakov.sample.newsagg04.UrlItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.R;
import manakov.sample.newsagg04.RssItem.RssItem;
import manakov.sample.newsagg04.RssItem.RssItemRecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class UrlItemDisplayActivity extends AppCompatActivity {
    private String LogTag = this.getClass().toString();
    private NewsAggApplication application;
    private SharedPreferences sharedPreferences;

    private TextView urlItemDisplayUrlItemLinkTextView;

    private RecyclerView rssItemRecyclerView;
    private RssItemRecyclerAdapter rssItemRecyclerAdapter;
    private ArrayList<RssItem> rssItems;

    private int currentUrlItemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url_item_display);

        application = (NewsAggApplication) getApplication();

        Intent intent = getIntent();
        try {
            currentUrlItemId = intent.getExtras().getInt(
                    NewsAggApplication.urlItemKey
            );

            if (currentUrlItemId == NewsAggApplication.urlItemKeyRetrieveFail){
                throw new Exception();
            }
            urlItemDisplayUrlItemLinkTextView = findViewById(R.id.urlItemDisplayUrlItemLinkTextView);
            urlItemDisplayUrlItemLinkTextView.setVisibility(View.VISIBLE);
            urlItemDisplayUrlItemLinkTextView.setText(
                    application.getUrlItemLinkByUrlItemId(
                            currentUrlItemId
                    )
            );

            rssItems = application.getAllRssItemsByUrlId(
                    currentUrlItemId
            );

            rssItemRecyclerView = findViewById(R.id.urlItemDisplayRssItemListRecycleView);
            rssItemRecyclerView.setVisibility(View.VISIBLE);
            rssItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            rssItemRecyclerAdapter = new RssItemRecyclerAdapter(rssItems);
            rssItemRecyclerView.setAdapter(rssItemRecyclerAdapter);

        } catch (Exception e){
            setResult(RESULT_OK);
            finish();
        }
    }

    public void onDeleteUrlItemClick(View view){
        application.deleteUrlItemByUrlItemId(
                currentUrlItemId
        );
        setResult(RESULT_OK);
        finish();
    }

    public static Intent getCreatingIntent(Context context, int currentId){
        Intent intent = new Intent(context, UrlItemDisplayActivity.class);
        intent.putExtra(NewsAggApplication.urlItemKey, currentId);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences(NewsAggApplication.lastActivitySharedPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsUrlItemDisplay
        );
        editor.putInt(
                NewsAggApplication.urlItemKey,
                currentUrlItemId
        );
        editor.apply();
    }
}
