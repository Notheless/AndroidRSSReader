package mobcom.reader;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
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
<<<<<<< HEAD
        String html = "<html><head><title>"+path+fileName+"</title></head><body>"+entar;
=======
        String html = "<html><head><title>"+path+fileName+"</title>"+entar;
        html += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+entar;
        html += "<style>"+entar;
        html += "body {margin: 0;background-image: url(\"bg.jpg\");background-attachment: fixed;background-size: cover;background-repeat: no-repeat;}"+entar;
        html += ".head {position: fixed;display: block;width: 100vw;height: auto;z-index: 4;}"+entar;
        html += ".title {display: block;margin: 0;height: 18vw;background-color: white;color: gray;text-align: center;}"+entar;
        html += ".title h1 {font-size: 15vw;margin: 0;text-align: center;}"+entar;
        html += ".desc {display: block;margin: 1vw;color: white;text-align: center;}"+entar;
        html += ".desc p {font-size: 3vw;margin: 0;text-align: center;}"+entar;
        html += ".container {overflow: auto;overflow-x: hidden;top: 28vw;position: relative;display: block;border-spacing: 0 20px;}"+entar;
        html += ".main-box {display: block;margin: 3.5vw;background-color: rgba(1, 1, 1, 0.25);height: auto;width: 92%;}"+entar;
        html += ".pic-box {position: absolute;display: inline;margin: 2.5vw;height: 20vw;width: 30vw;}"+entar;
        html += ".pic-box img {margin: 0;height: 100%;width: 100%;}"+entar;
        html += ".text-box {position: relative;left: 34vw;display: inline;height: 20vw;width: 54vw;margin: 2.5vw;color: white;}"+entar;
        html += ".header {position: relative;display: inline;width: 54vw;border-bottom: 1px solid white;margin-top: 1px;font-size: 2vw;height: calc(11.5vw - 2px);}"+entar;
        html += ".para {text-align: justify;position: relative;display: block;width: 54vw;font-size: 1.7vw;margin-bottom: 1px;height: calc(9vw - 1px);}"+entar;
        html += ".href {position: absolute;height: 14vh;width: 82vh;z-index:5;}"+entar;
        html += "</style>"+entar;
        html += "</head><body>"+entar;
>>>>>>> b7016caf9d5e9a4d65b93191efd4359294b3e56c
        html += "<div class=\"head\">"+entar;
        html += "<div class=\"title\">"+entar;
        html += "<h1>HARI INI</h1>"+entar;
        html += "</div>"+entar;
        html += "</div>"+entar;
        html += "<div class=\"container\">"+entar;
<<<<<<< HEAD
        
        for (int i = 0; i < items.size(); i++) {
            html += "<div class=\"main-box\">"+entar;
            html += "<a href=\""+items.get(i).link+"\" style=\"display:block\">"+entar;
            html += "<div class=\"href\"></div>"+entar;
            html += "</a>"+entar;
            html += "<div class\"pic-box\">"+entar;
            html += "<img src=\"\" align=\"left\" hspace=\"7\" width=\"100\" />"+entar;
=======
        for (int i = 1; i < items.size(); i++) {
            html += "<div class=\"main-box\">"+entar;
            html += "<a href=\""+items.get(i).link+"\" style=\"display:block\">"+entar;
            html += "<div class\"pic-box\" >"+entar; //style=:"width 50px"
            html += items.get(i).img+entar;
            //html += "<img src=\""+items.get(i).link+"\" align=\"left\" hspace=\"7\" width=\"100\" />"+entar;
>>>>>>> b7016caf9d5e9a4d65b93191efd4359294b3e56c
            html += "</div>"+entar;
            html += "<div class=\"text-box\">"+entar;
            html += "<div class=\"header\">"+entar;
            html += "<h2>"+items.get(i).title+"</h2>"+entar;
            html += "</div>"+entar;
            html += "<div class=\"para\">"+entar;
            html += "<p>"+items.get(i).description+"</p>"+entar;
            html += "</div>"+entar;
            html += "</div>"+entar;
            html += "</div>";
<<<<<<< HEAD
=======
            html += "</a>"+entar;
            html += "</div>";
>>>>>>> b7016caf9d5e9a4d65b93191efd4359294b3e56c
        }
        html += "</div>"+entar;
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

    private class RSS_Process extends AsyncTask<String, Void, Boolean> {

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
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            htmlSave(items);

            return true;
        }
    }
}