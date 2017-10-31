package mobcom.reader;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



class RssFeedModel {

    public String title;
    public String link;
    public String description;

    public RssFeedModel(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }
}



public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void RSS_button(View view)
    {

        new RSS_Process().execute((Void) null);

        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("file:///storage/emulated/0/test.html");
    }
    public  void test (String path,String name)
    {
        File file = new File(path, name +".html");
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = name.getBytes();
            out.write(data);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public void htmlSave ( List<RssFeedModel> items)
    {

        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "Test.html";
        File file = new File(path, fileName);
        String html = "<html><head><title>"+path+fileName+"</title></head><body>";
        for (int i = 0; i < items.size(); i++) {
            html += "<div>";
            html += "<a href=\""+items.get(i).link+"\">";
            html += items.get(i).title + "<br>"+items.get(i).description+"</a>";
            html += "</div><br>";
        }
        html+= "</body></html>";
        try
        {
            FileOutputStream out = new FileOutputStream(file);
            byte[] data = html.getBytes();
            out.write(data);
            out.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private class RSS_Process extends AsyncTask<Void, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... voids) {

            String linkurl = "https://rss.detik.com";
            URL url = null;
            boolean isItem = false;
            String title = null;
            String link = null;
            String description = null;
            List<RssFeedModel> items = new ArrayList<>();
            try {
                url = new URL(linkurl);
                InputStream inputStream = url.openConnection().getInputStream();

                XmlPullParser xmlPullParser = Xml.newPullParser();
                xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                xmlPullParser.setInput(inputStream, null);
                xmlPullParser.nextTag();
                while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                    int eventType = xmlPullParser.getEventType();

                    String name = xmlPullParser.getName();
                    if (name == null)
                        continue;

                    if (eventType == XmlPullParser.END_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = false;
                        }
                        continue;
                    }

                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equalsIgnoreCase("item")) {
                            isItem = true;
                            continue;
                        }
                    }
                    String result = "";
                    if (xmlPullParser.next() == XmlPullParser.TEXT) {
                        result = xmlPullParser.getText();
                        xmlPullParser.nextTag();
                    }
                    if (name.equalsIgnoreCase("title")) {
                        title = result;
                    } else if (name.equalsIgnoreCase("link")) {
                        link = result;
                    } else if (name.equalsIgnoreCase("description")) {
                        description = result;
                    }
                    if (title != null && link != null && description != null) {
                        if (isItem) {
                            RssFeedModel item = new RssFeedModel(title, link, description);
                            items.add(item);
                        }
                        title = null;
                        link = null;
                        description = null;
                        isItem = false;
                    }
                }

                inputStream.close();
                //mRecyclerView.setAdapter(new RssFeedListAdapter(items));
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (XmlPullParserException e)
            {
                e.printStackTrace();
            }
            htmlSave(items);

        return true;
        }


    }
}