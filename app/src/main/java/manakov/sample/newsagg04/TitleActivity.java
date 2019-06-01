package manakov.sample.newsagg04;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import manakov.sample.newsagg04.Settings.SettingsActivity;
import manakov.sample.newsagg04.UrlItem.AddUrlItemActivity;
import manakov.sample.newsagg04.UrlItem.UrlItem;
import manakov.sample.newsagg04.UrlItem.UrlItemDisplayActivity;
import manakov.sample.newsagg04.UrlItem.UrlItemRecyclerAdapter;

public class TitleActivity extends AppCompatActivity {
    private String LogTag = this.getClass().toString();

    private NewsAggApplication application;
    private SharedPreferences sharedPreferences;

    private RecyclerView urlItemRecyclerView;
    private UrlItemRecyclerAdapter urlItemRecyclerAdapter;
    private ArrayList<UrlItem> urlItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.title_activity);

        application = (NewsAggApplication) getApplication();
        sharedPreferences = getSharedPreferences(NewsAggApplication.lastActivitySharedPreferences, MODE_PRIVATE);


        urlItems = application.getAllUrlItems();

        urlItemRecyclerView = findViewById(R.id.titleUrlItemListRecycleView);
        urlItemRecyclerView.setVisibility(View.VISIBLE);
        urlItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        urlItemRecyclerAdapter = new UrlItemRecyclerAdapter(urlItems);
        urlItemRecyclerAdapter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
                        int position = viewHolder.getAdapterPosition();

                        startActivityForResult(
                                UrlItemDisplayActivity.getCreatingIntent(
                                        TitleActivity.this,
                                        urlItems.get(position).getId()
                                ),
                                NewsAggApplication.getUrlItemDisplayRequestCode
                        );
                    }
                }
        );

        urlItemRecyclerView.setAdapter(urlItemRecyclerAdapter);
        application.refreshOnce();

       // manageSavedState();
    }

    public void onAddUrlItemClick(View view) {
        startActivityForResult(
                AddUrlItemActivity.getCreatingIntent(this),
                NewsAggApplication.addUrlItemRequestCode
        );

    }

    public void onSetSettingsActivity(View view) {
        startActivityForResult(
                SettingsActivity.getCreatingIntent(this),
                NewsAggApplication.setSettingsRequestCode
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            urlItems = application.getAllUrlItems();
            urlItemRecyclerAdapter.setList(urlItems);
        }
    }

    public void onSetUpClick(View view) {
        application.setUp();
        urlItems = application.getAllUrlItems();
        urlItemRecyclerAdapter.setList(urlItems);

        application.refreshOnce();
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsTitle
        );
        editor.apply();
    }

    public void manageSavedState(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int lastActivityTag = sharedPreferences.getInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsTitle
        );

        if (lastActivityTag == NewsAggApplication.lastActivityIsAddUrlItem){

        }
    }
}