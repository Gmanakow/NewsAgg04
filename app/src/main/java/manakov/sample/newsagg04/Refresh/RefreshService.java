package manakov.sample.newsagg04.Refresh;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import manakov.sample.newsagg04.NewsAggApplication;
import manakov.sample.newsagg04.RssItem.RssItem;

public class RefreshService extends IntentService {
    private String LogTag = this.getClass().toString();
    private int urlItemId;
    private boolean flag;
    private NewsAggApplication application;

    public RefreshService(){
        super(RefreshService.class.getSimpleName());
    }

    @Override
    public void onHandleIntent(Intent intent){
        this.urlItemId = intent.getExtras().getInt(NewsAggApplication.urlItemKey);
        application = (NewsAggApplication) getApplication();


        InputStream inputStream = null;
        ArrayList<RssItem> items = null;
        flag(false);

        try{
            URL url = new URL(
                    application
                            .getUrlItemLinkByUrlItemId(
                                    urlItemId
                            )
            );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK ){
                inputStream = conn.getInputStream();

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                Document document = builder.parse(inputStream);
                Element element = document.getDocumentElement();

                NodeList nodeList = element.getElementsByTagName(NewsAggApplication.item);

                items = new ArrayList<>();

                for (int j = 0; j < nodeList.getLength(); j++) {
                    Element entry = (Element) nodeList.item(j);

                    Element elementTitle =          (Element) entry.getElementsByTagName(NewsAggApplication.rssItemTitleTag      ).item(0);
                    Element elementDate =           (Element) entry.getElementsByTagName(NewsAggApplication.rssItemDateTag       ).item(0);
                    Element elementLink =           (Element) entry.getElementsByTagName(NewsAggApplication.rssItemLinkTag       ).item(0);
                    Element elementDescription =    (Element) entry.getElementsByTagName(NewsAggApplication.rssItemDescriptionTag).item(0);

                    RssItem item = new RssItem(
                            elementTitle        .getFirstChild().getNodeValue(),
                            elementDate         .getFirstChild().getNodeValue(),
                            elementLink         .getFirstChild().getNodeValue(),
                            elementDescription  .getFirstChild().getNodeValue(),
                            urlItemId
                    );
                    items.add(item);
                }
                flag(true);
            }
        } catch (Exception e){
            Log.d(LogTag, e.getLocalizedMessage());
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                Log.e(LogTag, e.getLocalizedMessage());
            }
        }
        if (flag){
            application
                    .resetRssItemsByUrlItemId(
                            urlItemId,
                            items
                    );
        }

    }

    public static Intent getCreatingIntent(Context context, int urlItemId){
        Intent intent = new Intent(context, RefreshService.class);
        intent.putExtra(NewsAggApplication.urlItemKey, urlItemId);
        return intent;
    }

    public void flag(boolean flag){
        this.flag = flag;
    }
}
