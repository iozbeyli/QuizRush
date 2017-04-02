package com.fromthemind.quizrush.Loader;

import android.util.Log;

import com.fromthemind.quizrush.Category.QuizCategory;
import com.fromthemind.quizrush.Game.GameController;
import com.fromthemind.quizrush.Game.QuizGame;
import com.fromthemind.quizrush.Question.QuizQuestion;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class QuizLoader extends GameLoader{

    private static QuizLoader instance;

    private QuizLoader() {super();}

    /**
     * @return Instance of Class
     */
    public static QuizLoader getInstance() {
        if (instance == null) {
            instance = new QuizLoader();
        }
        return instance;
    }


    /**
     *
     * Reads saved GamePlay's data from a file, and constructs a new gameplay.
     * @param game am
     *
     * @requires File with the given name should be existed and should be in
     *           proper XML format.
     * @param game am
     *            the file, with this label, will be loadGame.
     * @throws ParserConfigurationException
     *             , IOException, SAXException; if the file is missing,
     *             corrupted, or not in proper format.
     */
    public void loadGame(QuizGame game) {
        Document doc = getDocument("game.xml", getContext().getAssets());

        // optional, but recommended
        // loadGame this -
        // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        NodeList categories = doc.getElementsByTagName("category");

        for (int c=0; c<categories.getLength();c++){
            Element category = (Element)categories.item(c);
            String topic = category.getAttribute("topic");
            QuizCategory cat = new QuizCategory(topic);
            NodeList questions = category.getElementsByTagName("question");

            for (int i = 0; i < questions.getLength(); i++) {
                Element question = (Element) questions.item(i);
                String definition = question.getAttribute("definition");
                int time  = Integer.parseInt(question.getAttribute("time"));
                Log.d("xmlTime", ""+time);
                QuizQuestion que = new QuizQuestion(time, definition, 100*(i+1));

                NodeList options = (NodeList) question.getElementsByTagName("option");
                for (int j=0; j<options.getLength(); j++){
                    Element option = (Element)options.item(j);
                    String label = option.getAttribute("label");
                    boolean isAnswer = Boolean.parseBoolean(option.getAttribute("isAnswer"));
                    que.setOption(label,j,isAnswer);
                }

                cat.addQuestion(que, i);
            }

            GameController.setCategory(cat, c);
        }

    }



}