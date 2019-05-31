package manakov.sample.newsagg04.UrlItem;

import androidx.appcompat.app.AppCompatActivity;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddUrlItemActivity extends AppCompatActivity {
    private String LogTag = this.getClass().toString();
    private NewsAggApplication application;

    private EditText addUrlItemInputTitleView;
    private EditText addUrlItemInputLinkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_url_item);

        application = (NewsAggApplication) getApplication();

        addUrlItemInputTitleView = findViewById(R.id.addUrlInputTitleView);
        addUrlItemInputTitleView.setVisibility(View.VISIBLE);

        addUrlItemInputLinkView = findViewById(R.id.addUrlInputLinkView);
        addUrlItemInputLinkView.setVisibility(View.VISIBLE);
    }

    public void onConfirmClick(View view){
//        addValidation

        application.addNewUrlItem(
                new UrlItem(
                    addUrlItemInputTitleView.getText().toString(),
                    addUrlItemInputLinkView.getText().toString()
                )
        );
        setResult(RESULT_OK);
        finish();
    }

    public static Intent getCreatingIntent(Context context){
        return new Intent(context, AddUrlItemActivity.class);
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
        editor.apply();
    }
}
