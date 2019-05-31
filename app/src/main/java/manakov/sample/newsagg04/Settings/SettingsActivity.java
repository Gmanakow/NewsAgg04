package manakov.sample.newsagg04.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.R;
import manakov.sample.newsagg04.Refresh.RefreshWorker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

public class SettingsActivity extends AppCompatActivity {
    private String LogTag = this.getClass().toString();

    private EditText settingsDelayInputView;
    private NewsAggApplication application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        application = (NewsAggApplication) getApplication();

        settingsDelayInputView = findViewById(R.id.settingsDelayInputView);
        settingsDelayInputView.setVisibility(View.VISIBLE);
    }

    public void onConfirmSettingsClick(View view){
        Long delay = null;
        try {
            delay = Long.parseLong(
                    settingsDelayInputView.getText().toString()
            );
        } catch (Exception e){
            Log.d(LogTag, e.getLocalizedMessage());
        }
        if (delay != null && delay>=1){
            application.setDelay(delay);
            setResult(
                    RESULT_OK
            );

            WorkManager workManager = WorkManager.getInstance();
            workManager.cancelAllWorkByTag("tag");
            workManager.enqueue(
                    new PeriodicWorkRequest.Builder(
                            RefreshWorker.class,
                            application.getDelay(),
                            TimeUnit.HOURS
                    ).build()
            );
            finish();
        }
    }

    public static Intent getCreatingIntent(Context context){
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences preferences = getSharedPreferences(NewsAggApplication.lastActivitySharedPreferences, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(
                NewsAggApplication.lastActivitiesTag,
                NewsAggApplication.lastActivityIsSettings
        );
        editor.apply();
    }
}
