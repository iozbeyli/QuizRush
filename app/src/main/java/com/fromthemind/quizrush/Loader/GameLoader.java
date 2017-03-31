package com.fromthemind.quizrush.Loader;

import android.content.Context;
import android.content.res.AssetManager;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by MEHMET on 31.03.2017.
 */
public class GameLoader {

    private static GameLoader instance;
    private static Context context;

    protected GameLoader() {}

    /**
     * @return Instance of Class
     */
    public static GameLoader getInstance() {
        if (instance == null) {
            instance = new GameLoader();
        }
        return instance;
    }

    public static void setContext(Context con){
        context = con;
    }

    public static Context getContext(){
        return context;
    }

    protected Document getDocument(String DocLocation, AssetManager am){
        Document doc = null;
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String theString = IOUtils.toString(am.open(DocLocation), "UTF-8");

            InputSource iso = new InputSource(new StringReader(theString));
            doc = dBuilder.parse(iso);
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doc;
    }

}
