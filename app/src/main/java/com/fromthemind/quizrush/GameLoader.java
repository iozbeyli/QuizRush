package com.fromthemind.quizrush;

import android.content.res.AssetManager;
import android.util.Log;

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


import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class GameLoader {

    private static GameLoader instance;

    private GameLoader() {}

    /**
     * @return Instance of Class
     */
    public static GameLoader getInstance() {
        if (instance == null) {
            instance = new GameLoader();
        }
        return instance;
    }


    /**
     *
     * Reads saved GamePlay's data from a file, and constructs a new gameplay.
     * @param gamexml am
     *
     * @requires File with the given name should be existed and should be in
     *           proper XML format.
     * @param gamexml am
     *            the file, with this label, will be loadGame.
     * @throws ParserConfigurationException
     *             , IOException, SAXException; if the file is missing,
     *             corrupted, or not in proper format.
     */
    public void loadGame(String gamexml, AssetManager am) {

        Document doc = getDocument(gamexml, am);

        // optional, but recommended
        // loadGame this -
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        NodeList categories = doc.getElementsByTagName("category");

        for (int c=0; c<categories.getLength();c++){
            Element category = (Element)categories.item(c);
            String topic = category.getAttribute("topic");
            Category cat = new Category(topic);
            NodeList questions = category.getElementsByTagName("question");

            for (int i = 0; i < questions.getLength(); i++) {
                Element question = (Element) questions.item(i);
                String definition = question.getAttribute("definition");
                int time  = Integer.parseInt(question.getAttribute("time"));
                Question que = new Question(time, definition, 100*(i+1));

                NodeList options = (NodeList) question.getElementsByTagName("option");
                for (int j=0; j<options.getLength(); j++){
                    Element option = (Element)options.item(j);
                    String label = option.getAttribute("label");
                    boolean isAnswer = Boolean.parseBoolean(option.getAttribute("isAnswer"));
                    que.setOption(label,j,isAnswer);
                }

                cat.addQuestion(que, i);
            }

            GameController.getInstance().setCategory(cat, c);
        }

    }

    private Document getDocument(String gamexml, AssetManager am){
        Document doc = null;
        try {
            DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String theString = IOUtils.toString(am.open(gamexml), "UTF-8");

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

        return doc;
    }



}