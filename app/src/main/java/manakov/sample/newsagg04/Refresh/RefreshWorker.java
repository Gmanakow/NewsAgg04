package manakov.sample.newsagg04.Refresh;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.UrlItem.UrlItem;

public class RefreshWorker extends Worker {
    private String LogTag = this.getClass().toString();
    private NewsAggApplication application;

    public RefreshWorker(
            @NonNull Context context,
            @NonNull WorkerParameters parameters
            ){
        super(context, parameters);
    }

    @Override
    public Result doWork(){
        application = (NewsAggApplication) getApplicationContext();
        ArrayList<UrlItem> urlItems = application.getAllUrlItems();
        Log.d(LogTag, "doworkTag");
        for (int i = 0; i< urlItems.size(); i++){
            application.startService(
                    RefreshService.getCreatingIntent(
                            application,
                            urlItems.get(i).getId()
                    )
            );
        }
        return Result.success();
    }
}
