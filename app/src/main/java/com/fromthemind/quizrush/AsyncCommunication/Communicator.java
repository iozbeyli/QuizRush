package com.fromthemind.quizrush.AsyncCommunication;

import org.json.JSONObject;

/**
 * Created by Melih on 20.05.2017.
 */

public interface Communicator {
    void successfulExecute(JSONObject jsonObject);
    void failedExecute();
}
