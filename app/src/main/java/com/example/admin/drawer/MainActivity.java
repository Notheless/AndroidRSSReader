package com.example.admin.drawer;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.view.ViewManager;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;


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
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    ArrayAdapter<String> adapter;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;


    char[] arrayset = new char[9];
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }


    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        Fragment fragment=null;
        FragmentManager fragmentManager = getSupportFragmentManager(); // For AppCompat use getSupportFragmentManager

        switch (number) {
            case 1:
                mTitle = getString(R.string.Menu1);
                fragment = new Home();
                break;
            case 2:
                mTitle = getString(R.string.Menu2);
                fragment = new Setting();
                break;
            case 3:
                mTitle = getString(R.string.Menu3);
                fragment = new About();
                break;
            case 4:
                mTitle = getString(R.string.Menu4);
                System.exit( 0 );
                break;
        }
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

    public void loadweb(String Name){

        WebView myWebView = (WebView)findViewById(R.id.webView);
        String sart = "file:///";
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/"+Name+".html";
        String linnk = sart+path+fileName;
        myWebView.loadUrl(linnk);
    }


    public void RSS_Nasional(View view)
    {

        Button nationalButton = (Button) findViewById(R.id.fetchA);
        Button internationalButton = (Button) findViewById(R.id.fetchB);
        Button olahragaButton = (Button) findViewById(R.id.fetchC);
        Button nationalButton2 = (Button) findViewById(R.id.fetchA2);
        Button internationalButton2 = (Button) findViewById(R.id.fetchB2);
        Button olahragaButton2 = (Button) findViewById(R.id.fetchC2);

        nationalButton.setVisibility(View.VISIBLE);
        internationalButton.setVisibility(View.VISIBLE);
        olahragaButton.setVisibility(View.VISIBLE);

        nationalButton2.setVisibility(View.GONE);
        internationalButton2.setVisibility(View.GONE);
        olahragaButton2.setVisibility(View.GONE);

        readSetting();
        List<String> list = new ArrayList<String>();
        if(arrayset[0]=='1')list.add("http://rss.vivanews.com/get/nasional");
        if(arrayset[1]=='1')list.add("https://rss.detik.com");
        if(arrayset[2]=='1')list.add("https://www.antaranews.com/rss/nas.xml");
        //String[] strArray = new String[]{"http://rss.vivanews.com/get/nasional","https://rss.detik.com","https://www.antaranews.com/rss/nas.xml"};
        String[] strArray = new String[list.size()];
        strArray = list.toArray(strArray);

        new RSS_Process_N().execute(strArray);
    }
    public void RSS_Internasional(View view)
    {

        Button nationalButton = (Button) findViewById(R.id.fetchA);
        Button internationalButton = (Button) findViewById(R.id.fetchB);
        Button olahragaButton = (Button) findViewById(R.id.fetchC);
        Button nationalButton2 = (Button) findViewById(R.id.fetchA2);
        Button internationalButton2 = (Button) findViewById(R.id.fetchB2);
        Button olahragaButton2 = (Button) findViewById(R.id.fetchC2);

        nationalButton.setVisibility(View.VISIBLE);
        internationalButton.setVisibility(View.VISIBLE);
        olahragaButton.setVisibility(View.VISIBLE);

        nationalButton2.setVisibility(View.GONE);
        internationalButton2.setVisibility(View.GONE);
        olahragaButton2.setVisibility(View.GONE);

        readSetting();
        List<String> list = new ArrayList<String>();
        if(arrayset[3]=='1')list.add("http://rss.nytimes.com/services/xml/rss/nyt/World.xml");
        if(arrayset[4]=='1')list.add("http://feeds.skynews.com/feeds/rss/world.xml");
        if(arrayset[5]=='1')list.add("http://www1.cbn.com/app_feeds/rss/news/rss.php?section=world");
        String[] strArray = new String[list.size()];
        strArray = list.toArray(strArray);
        //String[] strArray = new String[]{"http://rss.nytimes.com/services/xml/rss/nyt/World.xml","http://feeds.bbci.co.uk/news/world/rss.xml","http://www1.cbn.com/app_feeds/rss/news/rss.php?section=world"};
        String Name ="I";
        new RSS_Process_I().execute(strArray);
    }
    public void RSS_Olahraga(View view)
    {

        Button nationalButton = (Button) findViewById(R.id.fetchA);
        Button internationalButton = (Button) findViewById(R.id.fetchB);
        Button olahragaButton = (Button) findViewById(R.id.fetchC);
        Button nationalButton2 = (Button) findViewById(R.id.fetchA2);
        Button internationalButton2 = (Button) findViewById(R.id.fetchB2);
        Button olahragaButton2 = (Button) findViewById(R.id.fetchC2);

        nationalButton.setVisibility(View.VISIBLE);
        internationalButton.setVisibility(View.VISIBLE);
        olahragaButton.setVisibility(View.VISIBLE);

        nationalButton2.setVisibility(View.GONE);
        internationalButton2.setVisibility(View.GONE);
        olahragaButton2.setVisibility(View.GONE);

        readSetting();
        List<String> list = new ArrayList<String>();
        if(arrayset[6]=='1')list.add("https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU");
        if(arrayset[7]=='1')list.add("http://www.espn.com/espn/rss/news");
        if(arrayset[8]=='1')list.add("https://nypost.com/sports/feed/");
        String[] strArray = new String[list.size()];
        strArray = list.toArray(strArray);
        //String[] strArray = new String[]{"https://api.foxsports.com/v1/rss?partnerKey=zBaFxRyGKCfxBagJG9b8pqLyndmvo7UU","http://www.espn.com/espn/rss/news","https://nypost.com/sports/feed/"};
        String Name ="O";
        new RSS_Process_O().execute(strArray);
    }

    public void htmlSave (List<RssFeedModel> items, int n, String Name, String KategoriName)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateF = new SimpleDateFormat("dd - MMM - yyyy");
        String tanggal = dateF.format(cal.getTime());

        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = Name+".html";
        File file = new File(path, fileName);
        String html = "<html><head><title>"+path+fileName+"</title>"+entar;
        html += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">"+entar;
        html += "<style>"+entar;
        html += "body {margin: 0; background-color: #2e75b7; background-attachment: fixed; background-size: cover; background-repeat: no-repeat;}"+entar;
        html += ".head {position: fixed; display: block; width: 100vw; height: auto; z-index: 4;}"+entar;
        html += ".title {display: block; margin: 0; height: 18vw; background-color: white; color: gray; text-align: center;}"+entar;
        html += ".title h1 {font-size: 15vw; margin: 0; text-align: center;}"+entar;
        html += ".detail {position: fixed; display: block; top: 14%; height: 10%; width: 96%; margin: 0 2vw 0 2vw; overflow: none; border-bottom: 1px solid black; z-index: 2;}"+entar;
        html += ".container {position: fixed; display: block; top: 24%; height: 76%; width: 100%;overflow-y: auto; overflow-x: hidden; display: block; border-spacing: 0 20px;}"+entar;
        html += "table {width: 96%; margin: 3% 2%; border-collapse: collapse;}"+entar;
        html += "tr:nth-child(odd) {background-color: #548ec4;}"+entar;
        html += "tr:nth-child(even) {height: 15px;}"+entar;
        html += "a {display: table; height: 60px; margin: 0; text-decoration: none;}"+entar;
        html += "a p {margin: auto; vertical-align: middle; display: table-cell; color: white;}"+entar;
        html += "img {height: 50px; width: 100px; object-fit: cover; margin: 5px; color: white;}"+entar;
        html += ".endline {text-align: center; vertical-align: middle; height: 6%; color: white;}"+entar;
        html += "h3 {text-align: center; font-weight: 3; margin: 3px 0 0 0; color: white;}"+entar;
        html += "h4 {text-align: center; font-weight: 3; margin: 2px 0 4px 0; color: white;}"+entar;
        html += "</style>"+entar;
        html += "</head><body>"+entar;
        html += "<div class=\"head\">"+entar;
        html += "<div class=\"title\">"+entar;
        html += "<h1>HARI INI</h1>"+entar;
        html += "</div>"+entar;
        html += "</div>"+entar;
        html += "<div class=\"detail\">"+entar;
        html += "<h3>Kategori : Berita "+KategoriName+"</h3>"+entar;
        html += "<h4>"+tanggal+"</h4>"+entar;
        html += "</div>"+entar;
        html += "<div class=\"container\">"+entar;
        html += "<table>"+entar;
        for (int i = n; i < items.size(); i++) {
            String link = "<a href=\""+items.get(i).link+"\">";
            html += "<tr>"+entar;
            html += "<td>"+link+"<img src='"+items.get(i).img+"' width='100px'></a></td>"+entar;
            html += "<td>"+link+"<p>"+items.get(i).title+"</p></a></td>"+entar;
            html += "</tr>"+entar+"<tr></tr>"+entar;
        }
        html += "<tr><td class=\"endline\" colspan=\"2\">-- Akhir Berita Hari Ini --</td></tr>"+entar;
        html += "</table>"+entar;
        html += "</div>"+entar;
        html += "</body></html>"+entar;
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


    //Nasional
    private class RSS_Process_N extends AsyncTask<String[], Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[]... x) {
            int skiplink = 0;
            String[] linkurl = x[0];
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            String title = "";
            String link = "";
            String description = "";
            String img = "";
            List<RssFeedModel> items = new ArrayList<>();
            int size =0;
            int pos =0;
            try {

                for (int i = 0; i < n; i++) {
                    url = new URL(linkurl[i]);
                    InputStream inputStream = url.openConnection().getInputStream();

                    if(i!=0){
                        size = items.size();
                        pos =0;
                    }
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
                            if(result.toLowerCase().contains("api.foxsports.com/v1/rss")){
                                if(skiplink<2){
                                    skiplink += 1;
                                }
                                else {
                                    link = result;
                                }
                            }
                            else {
                                link = result;
                            }
                            if(result.toLowerCase().contains("www.espn.com"))img ="http://a.espncdn.com/combiner/i?img=/i/mobile/website/img/flm-onehouse-apptile-180.png";
                        } else if (name.equalsIgnoreCase("description")) {
                            if (result.indexOf('>') > 0) {
                                int start = result.indexOf('>');
                                img = result.substring(0, start+1);
                                String s = "<img src=\"";
                                int ix = img.indexOf(s)+s.length();
                                img = (img.substring(ix, img.indexOf("\"", ix+1)));

                                result = result.substring(start + 1, result.length());
                            }
                            description = result;
                        }

                        else if(name.equalsIgnoreCase("media:content")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }


                        else if(name.equalsIgnoreCase("media:thumbnail")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }
                        else if(name.equalsIgnoreCase("enclosure")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){
                                img = xmlPullParser.getAttributeValue(0);
                                if(img.toLowerCase().contains("?")){
                                    int z= img.indexOf('?');
                                    img = img.substring(0,z);
                                }
                            }
                        }

                        else if (name.equalsIgnoreCase("url")) {
                            img = result;
                        }
                        else if (name.equalsIgnoreCase("image")) {
                            img = result;
                        }

                        if (!title.equalsIgnoreCase("") && !link.equalsIgnoreCase("") && !description.equalsIgnoreCase("")&& !img.equalsIgnoreCase("")) {

                            if (isItem) {
                                RssFeedModel item = new RssFeedModel(title, link, description, img);
                                if(pos>size) {
                                    items.add(item);
                                }
                                else{
                                    items.add(pos,item);
                                    pos += n;
                                }
                            }
                            title = "";
                            link = "";
                            description = "";
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

            for(int i=items.size()-1;i>=n;i--){
                String a = items.get(i-n).title;
                items.get(i).title = a;
                Log.i("mas",items.get(i).title);
            }
            htmlSave(items,n,"N", "Nasional");

            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadweb("N");
        }
    }


    //Interasional
    private class RSS_Process_I extends AsyncTask<String[], Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[]... x) {

            String[] linkurl = x[0];
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            int firstimg = 0;
            String title = "";
            String link = "";
            String description = "";
            String img = "";
            List<RssFeedModel> items = new ArrayList<>();
            int size =0;
            int pos =0;
            int skiplink =0;
            try {

                for (int i = 0; i < n; i++) {
                    url = new URL(linkurl[i]);
                    InputStream inputStream = url.openConnection().getInputStream();

                    if(i!=0){
                        size = items.size();
                        pos =0;
                    }
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
                            if(result.toLowerCase().contains("news.sky")){
                                if(skiplink<3){
                                    skiplink += 1;
                                }
                                else {
                                    link = result;
                                }
                            }
                            else {
                                link = result;
                            }
                        } else if (name.equalsIgnoreCase("description")) {
                            if (result.indexOf('>') > 0) {
                                int start = -1;
                                start = result.indexOf('>');
                                img = result.substring(0, start+1);
                                String s = "content url=\"";
                                int ix = img.indexOf(s)+s.length();
                                img = (img.substring(ix, img.indexOf("\"", ix+1)));

                                result = result.substring(start + 1, result.length());
                            }
                            description = result;
                        }


                        else if(name.equalsIgnoreCase("media:content")) {
                            if (result.indexOf('>') > 0) {
                                int start = result.indexOf('>');
                                img = result.substring(0, start + 1);
                                String s = "url=\"";
                                int ix = img.indexOf(s) + s.length();
                                img = (img.substring(ix, img.indexOf("\"", ix + 1)));
                            }
                        }
                        else if(name.equalsIgnoreCase("media:content")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }


                        else if(name.equalsIgnoreCase("media:thumbnail")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }
                        else if(name.equalsIgnoreCase("enclosure")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){
                                img = xmlPullParser.getAttributeValue(0);
                                if(img.toLowerCase().contains("?")){
                                    int z= img.indexOf('?');
                                    img = img.substring(0,z);
                                }
                            }
                        }

                        else if (name.equalsIgnoreCase("url")) {
                            img = result;
                        }
                        else if (name.equalsIgnoreCase("image")) {
                            img = result;
                        }
                        else if (name.equalsIgnoreCase("thumbnail")) {
                            img = result;
                        }

                        if (!title.equalsIgnoreCase("") && !link.equalsIgnoreCase("") && !description.equalsIgnoreCase("")&& !img.equalsIgnoreCase("")) {

                            if(img.toLowerCase().contains("{width}x{height}")){
                                img = img.replace("{width}x{height}","288x216");
                            }
                            if (isItem) {
                                RssFeedModel item = new RssFeedModel(title, link, description, img);
                                if(pos>size) {
                                    items.add(item);
                                }
                                else{
                                    items.add(pos,item);
                                    pos += n;
                                }
                            }
                            img="";
                            title = "";
                            link = "";
                            description = "";
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

            for(int i=items.size()-1;i>=n;i--){
                String a = items.get(i-n).title;
                items.get(i).title = a;
                Log.i("mas",items.get(i).title);
            }

            htmlSave(items,n,"I", "Internasional");

            return true;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadweb("I");
        }
    }


    //Olahraga
    private class RSS_Process_O extends AsyncTask<String[], Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[]... x) {
            int skiplink = 0;
            String[] linkurl = x[0];
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            String title = "";
            String link = "";
            String description = "";
            String img = "";
            List<RssFeedModel> items = new ArrayList<>();
            int size =0;
            int pos =0;
            try {

                for (int i = 0; i < n; i++) {
                    url = new URL(linkurl[i]);
                    InputStream inputStream = url.openConnection().getInputStream();

                    if(i!=0){
                        size = items.size();
                        pos =0;
                    }
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
                            if(result.toLowerCase().contains("api.foxsports.com/v1/rss")){
                                if(skiplink<2){
                                    skiplink += 1;
                                }
                                else {
                                    link = result;
                                }
                            }
                            else {
                                link = result;
                            }
                            if(result.toLowerCase().contains("www.espn.com"))img ="http://a.espncdn.com/combiner/i?img=/i/mobile/website/img/flm-onehouse-apptile-180.png";
                        } else if (name.equalsIgnoreCase("description")) {
                            if (result.indexOf('>') > 0) {
                                int start = result.indexOf('>');
                                img = result.substring(0, start+1);
                                String s = "<img src=\"";
                                int ix = img.indexOf(s)+s.length();
                                img = (img.substring(ix, img.indexOf("\"", ix+1)));

                                result = result.substring(start + 1, result.length());
                            }
                            description = result;
                        }

                        else if(name.equalsIgnoreCase("media:content")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }


                        else if(name.equalsIgnoreCase("media:thumbnail")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){

                                img = xmlPullParser.getAttributeValue(0);
                                int z = img.indexOf("?");
                                img = img.substring(0,z);
                            }
                        }
                        else if(name.equalsIgnoreCase("enclosure")) {
                            if(!(xmlPullParser.getAttributeValue(0).equalsIgnoreCase(("html")))){
                                img = xmlPullParser.getAttributeValue(0);
                               if(img.toLowerCase().contains("?")){
                                   int z= img.indexOf('?');
                                   img = img.substring(0,z);
                               }
                            }
                        }

                        else if (name.equalsIgnoreCase("url")) {
                            img = result;
                        }
                        else if (name.equalsIgnoreCase("image")) {
                            img = result;
                        }

                        if (!title.equalsIgnoreCase("") && !link.equalsIgnoreCase("") && !description.equalsIgnoreCase("")&& !img.equalsIgnoreCase("")) {

                            if (isItem) {
                                RssFeedModel item = new RssFeedModel(title, link, description, img);
                                if(pos>size) {
                                    items.add(item);
                                }
                                else{
                                    items.add(pos,item);
                                    pos += n;
                                }
                            }
                            title = "";
                            link = "";
                            description = "";
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
            htmlSave(items,n,"O", "Olahraga");

            return true;
        }
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            loadweb("O");
        }
    }


    public void readSetting(){

        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);


        if(file.exists()) {
            StringBuilder text = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {
                    text.append(line);
                    text.append('\n');
                }
                br.close();
            } catch (IOException e) {

            }
            String sett = text.toString();
            char[] x = sett.toCharArray();
            for (int i = 0; i < 9; i++) {
                arrayset[i] = x[i];
            }
        }
        else{
            writeInitialSetting();
        }
    }
    public void writeInitialSetting(){


        String setting = "111111111";
        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);
        try
        {

            FileOutputStream out = new FileOutputStream(file);
            byte[] data = setting.getBytes();
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
        readSetting();
    }
    public void saveSetting(){

        arrayset = new char[9];
        arrayset[0] = getSwitchValue((Switch) findViewById(R.id.Bindo1));
        arrayset[1] = getSwitchValue((Switch) findViewById(R.id.Bindo2));
        arrayset[2] = getSwitchValue((Switch) findViewById(R.id.Bindo3));

        arrayset[3] = getSwitchValue((Switch) findViewById(R.id.Bing1));
        arrayset[4] = getSwitchValue((Switch) findViewById(R.id.Bing2));
        arrayset[5] = getSwitchValue((Switch) findViewById(R.id.Bing3));

        arrayset[6] = getSwitchValue((Switch) findViewById(R.id.bol1));
        arrayset[7] = getSwitchValue((Switch) findViewById(R.id.bol2));
        arrayset[8] = getSwitchValue((Switch) findViewById(R.id.bol3));



        String setting = new String(arrayset);

        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = "/Setting.cfg";
        File file = new File(path, fileName);
        try
        {

            FileOutputStream out = new FileOutputStream(file);
            byte[] data = setting.getBytes();
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
        readSetting();
    }
    char getSwitchValue(Switch s){
        char res ='0';
        if(s.isChecked()) res = '1';
        return res;
    }


    public void changeSwitchstate(View view){
        saveSetting();
    }
}
