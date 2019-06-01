package manakov.sample.newsagg04.UrlItem;

import androidx.appcompat.app.AppCompatActivity;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddUrlItemActivity extends AppCompatActivity {
    private String LogTag = this.getClass().toString();
    private NewsAggApplication application;
    private SharedPreferences sharedPreferences;

    private EditText addUrlItemInputTitleView;
    private EditText addUrlItemInputLinkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url_item);

        application = (NewsAggApplication) getApplication();
        sharedPreferences = getSharedPreferences(
                NewsAggApplication.lastActivitySharedPreferences,
                MODE_PRIVATE
        );

        addUrlItemInputTitleView = findViewById(R.id.addUrlInputTitleView);
        addUrlItemInputTitleView.setVisibility(View.VISIBLE);

        addUrlItemInputLinkView = findViewById(R.id.addUrlInputLinkView);
        addUrlItemInputLinkView.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        String titleString = null;
        String linkString = null;

        try{
            titleString = intent.getExtras().getString(
                    NewsAggApplication.titleString
            );
            linkString = intent.getExtras().getString(
                    NewsAggApplication.linkString
            );
        } catch (Exception e){
            //
        }

        if (titleString != null && linkString != null){
            addUrlItemInputTitleView.setText(
                    titleString
            );
            addUrlItemInputLinkView.setText(
                    linkString
            );
        }
    }

    public void onConfirmClick(View view){
//        addValidation

        application.addNewUrlItem(
                new UrlItem(
                    addUrlItemInputTitleView.getText().toString(),
                    addUrlItemInputLinkView.getText().toString()
                )
        );

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsTitle
        );
        editor.apply();

        setResult(RESULT_OK);
        finish();
    }

    public static Intent getCreatingIntent(Context context, String titleString, String linkString){
        Intent intent = new Intent(context, AddUrlItemActivity.class);

        intent.putExtra(NewsAggApplication.titleString, titleString);
        intent.putExtra(NewsAggApplication.linkString, linkString);

        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences(NewsAggApplication.lastActivitySharedPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsAddUrlItem
        );
        editor.putString(
                NewsAggApplication.titleString,
                addUrlItemInputTitleView.getText().toString()
        );
        editor.putString(
                NewsAggApplication.linkString,
                addUrlItemInputLinkView.getText().toString()
        );
        editor.apply();
    }
}
