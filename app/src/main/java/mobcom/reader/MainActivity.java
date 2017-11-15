package mobcom.reader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
    public String img;

    public RssFeedModel(String title, String link, String description,String img) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.img = img;
    }
}



public class MainActivity extends AppCompatActivity
{
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;

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

    public  void message()

    {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public void RSS_button(View view)
    {

        new RSS_Process().execute("https://rss.detik.com");

        WebView myWebView = (WebView) findViewById(R.id.webView);
        String sart = "file:///";
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Test.html";
        String linnk = sart+path+fileName;
        myWebView.loadUrl(linnk);
    }

    public void htmlSave ( List<RssFeedModel> items)
    {

        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "Test.html";
        File file = new File(path, fileName);
        String html = "<html><head><title>"+path+fileName+"</title></head><body>"+entar;
        html += "<div>"+entar;
        html += "<table border=\"1\">"+entar;
        for (int i = 1; i < items.size(); i++) {
            html += "<tr>"+entar;
            html += "<td><a href=\""+items.get(i).link+"\">Link</a></td>"+entar;
            html += "<td>"+items.get(i).title+"</td>"+entar;
            html += "<td>"+items.get(i).description+"</td>"+entar;
            html += "<td>."+items.get(i).img+"</td>"+entar;
            html += "</tr>"+entar;
        }
        html += "</table></div><br>"+entar;
        html+= "</body></html>"+entar;
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

    private class RSS_Process extends AsyncTask<String, Void, Boolean>
    {

        @Override
        protected Boolean doInBackground(String... x) {

            String[] linkurl = x;
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            String title = null;
            String link = null;
            String description = null;
            String img = "";
            List<RssFeedModel> items = new ArrayList<>();
            try {

                for (int i = 0; i < n; i++) {
                    url = new URL(linkurl[i]);
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
                            if (result.indexOf('>') > 0) {
                                int start = result.indexOf('>');
                                img = result.substring(0, start);
                                ;
                                result = result.substring(start + 1, result.length());
                            }
                            description = result;
                        }
                        if (title != null && link != null && description != null) {
                            if (isItem) {
                                RssFeedModel item = new RssFeedModel(title, link, description, img);
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
                }
                catch(MalformedURLException e)
                {
                    e.printStackTrace();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                catch(XmlPullParserException e)
                {
                    e.printStackTrace();
                }
            htmlSave(items);

        return true;
        }


    }
}