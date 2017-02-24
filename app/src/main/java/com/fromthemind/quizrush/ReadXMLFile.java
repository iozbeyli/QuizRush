package com.fromthemind.quizrush;

import android.app.Activity;
import android.content.res.AssetManager;
import android.util.Log;

import com.fromthemind.quizrush.GameController;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

public class ReadXMLFile{

    private static ReadXMLFile instance;

    private ReadXMLFile() {}

    /**
     * @return Instance of Class
     */
    public static ReadXMLFile getInstance() {
        if (instance == null) {
            instance = new ReadXMLFile();
        }
        return instance;
    }


    /**
     *
     * Reads saved GamePlay's data from a file, and constructs a new gameplay.
     * @param is
     *
     * @requires File with the given name should be existed and should be in
     *           proper XML format.
     * @param is
     *            the file, with this label, will be read.
     * @throws ParserConfigurationException
     *             , IOException, SAXException; if the file is missing,
     *             corrupted, or not in proper format.
     */
    public void read(InputStream is ) {
        GameController gc = GameController.getInstance();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        Document doc = null;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            String theString = IOUtils.toString(is, "UTF-8");
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
        if(doc==null)
         Log.d("error", "null");

        // optional, but recommended
        // read this -
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();

        System.out.println("Root element :"
                + doc.getDocumentElement().getNodeName());

        NodeList categories = doc.getElementsByTagName("category");
        for (int c=0; c<categories.getLength();c++){
            Node category = categories.item(c);
            String topic = category.getAttributes().getNamedItem("topic").getNodeValue();
            Category cat = new Category(topic);
            NodeList questions = ((Element)category).getElementsByTagName("question");


            for (int i = 0; i < questions.getLength(); i++) {
                Element question = (Element) questions.item(i);
                String definition = question.getAttribute("definition");
                int time  = Integer.parseInt(question.getAttribute("time"));
                Question que = new Question(time, definition);
                NodeList options = (NodeList) question.getElementsByTagName("option");
                for (int j=0; j<options.getLength(); j++){
                    Node option = options.item(j);
                    String label = option.getAttributes().getNamedItem("label").getNodeValue();
                    boolean isAnswer = Boolean.parseBoolean(option.getAttributes().getNamedItem("isAnswer").getNodeValue());
                    que.setOption(label,j,isAnswer);
                }
                cat.addQuestion(que, i);

            }

            gc.setCategory(cat, c);
        }





    }



}