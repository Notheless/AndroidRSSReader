package com.example.admin.drawer;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    
    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    public void htmlSave (List<RssFeedModel> items, int n, String Name)
    {

        String entar = Character.toString ((char) 13);
        String path = Environment.getExternalStorageDirectory().getPath();
        String fileName = Name+".html";
        File file = new File(path, fileName);
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
        html += "<div class=\"head\">"+entar;
        html += "<div class=\"title\">"+entar;
        html += "<h1>HARI INI</h1>"+entar;
        html += "</div>"+entar;
        html += "</div>"+entar;
        html += "<div class=\"container\">"+entar;
        html += "<table>"+entar;
        for (int i = n; i < items.size(); i++) {
            String link = "<a href=\""+items.get(i).link+"\">";
            html += "<tr>"+entar;
            html += "<td>"+link+"<img src='"+items.get(i).img+"' width='100px'></a></td>"+entar;
            html += "<td>"+link+items.get(i).title+"</a></td>"+entar;
            html += "</tr><tr>"+entar;

            html += "<td colspan=\"2\">"+items.get(i).description+"</td>"+entar;
            html += "</tr>"+entar;

            /*
            html += "<div class=\"main-box\">"+entar;
            html += "<a href=\""+items.get(i).link+"\" style=\"display:block\">"+entar;
            html += "<div class\"pic-box\" >"+entar; //style=:"width 50px"
            html += items.get(i).img+">"+entar;
            //html += "<img src=\""+items.get(i).link+"\" align=\"left\" hspace=\"7\" width=\"100\" />"+entar;
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
            html += "</a>"+entar;
            html += "</div>";
            */
        }
        html += "</table>"+entar;
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


    //Nasional
    private class RSS_Process_N extends AsyncTask<String[], Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[]... x) {

            String[] linkurl = x[0];
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            String title = null;
            String link = null;
            String description = null;
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
                            link = result;
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
                        if (title != null && link != null && description != null) {
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
            htmlSave(items,n,"N");

            return true;
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
            String title = null;
            String link = null;
            String description = null;
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
                            link = result;
                        } else if (name.equalsIgnoreCase("description")) {
                            if (result.indexOf('>') > 0) {
                                int start = result.indexOf('>');
                                img = result.substring(0, start+1);
                                String s = "content url=\"";
                                int ix = img.indexOf(s)+s.length();
                                img = (img.substring(ix, img.indexOf("\"", ix+1)));

                                result = result.substring(start + 1, result.length());
                            }
                            description = result;
                        }
                        if (title != null && link != null && description != null) {
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
            htmlSave(items,n,"I");

            return true;
        }
    }


    //Olahraga
    private class RSS_Process_O extends AsyncTask<String[], Void, Boolean> {

        @Override
        protected Boolean doInBackground(String[]... x) {

            String[] linkurl = x[0];
            int n = linkurl.length;
            URL url = null;
            boolean isItem = false;
            String title = null;
            String link = null;
            String description = null;
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
                            link = result;
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
                        if (title != null && link != null && description != null) {
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
            htmlSave(items,n,"O");

            return true;
        }
    }



}
