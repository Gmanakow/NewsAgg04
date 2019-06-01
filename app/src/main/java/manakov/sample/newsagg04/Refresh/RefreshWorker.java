package manakov.sample.newsagg04.Refresh;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import androidx.core.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.R;
import manakov.sample.newsagg04.UrlItem.UrlItem;

import static android.content.Context.NOTIFICATION_SERVICE;

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

        doNotify();

        ArrayList<UrlItem> urlItems = application.getAllUrlItems();
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

    private void doNotify(){
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(
                        application,
                        NewsAggApplication.notificationChannelId
                ).setSmallIcon(
                        R.mipmap.ic_launcher
                ).setContentTitle(
                        NewsAggApplication.notificationTitle
                ).setContentText(
                        NewsAggApplication.notificationText
                );

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager =
                (NotificationManager)
                        application.getSystemService(
                                NOTIFICATION_SERVICE
                        );

        notificationManager.notify(
                NewsAggApplication.notificationId,
                notification
        );

    }
}
