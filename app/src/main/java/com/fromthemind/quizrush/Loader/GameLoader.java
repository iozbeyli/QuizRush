package com.fromthemind.quizrush.Loader;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
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
    private static String dataRaw = "";
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

    protected Document loadDocument(String DocLocation){
        Document doc = null;
        try {
            FirebaseStorage fs = FirebaseStorage.getInstance();
            Log.d("Firebase read File1", "onSuccess: "+fs);
            StorageReference sf = fs.getReference().child(DocLocation);
            Log.d("Firebase read File2", "onSuccess: "+sf);


            final long ONE_MB = 1024*1024;
            Task<byte[]> task = sf.getBytes(ONE_MB);

            while(!task.isComplete()){

            }
            dataRaw = IOUtils.toString(task.getResult(), "UTF-8");
            Log.d("Comm", "getDocument: "+task);
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource iso = new InputSource(new StringReader(dataRaw));
            doc = dBuilder.parse(iso);
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return doc;
    }

}
