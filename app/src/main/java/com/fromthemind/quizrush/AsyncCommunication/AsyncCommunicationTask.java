package com.fromthemind.quizrush.AsyncCommunication;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Melih on 20.05.2017.
 */

/**
 * SAMPLE USAGE
 *  JSONObject postData = new JSONObject();
 *  try {
 *      postData.put("operation","1");
 *      } catch (JSONException e) {
 *      e.printStackTrace();
 *      }
 *  userDataTask = new AsyncCommunicationTask(Constants.apiURL+"user",postData,this);
 *  userDataTask.execute((Void)null);
 */
public class AsyncCommunicationTask extends AsyncTask<Void,Void,Boolean> {
    String apiURL = "https://us-central1-quiz-6b08b.cloudfunctions.net/sendNotification";
    String resultsToDisplay = "";
    JSONObject postData;
    Communicator communicator;
    public AsyncCommunicationTask(String apiURL,JSONObject postData,Communicator communicator) {

        if(apiURL != null)this.apiURL=apiURL;
        this.postData = postData;
        this.communicator=communicator;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        // TODO: attempt authentication against a network service.

        InputStream in = null;
        try {
            URL url = new URL(apiURL);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json");
            //urlConnection.addRequestProperty("Authorization", "Bearer " + Session.token);
            urlConnection.addRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            System.out.print(urlConnection.toString());

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(postData.toString());
            out.close();

            urlConnection.connect();

            in = new BufferedInputStream(urlConnection.getInputStream());

        } catch (Exception e) {
            System.out.println(e.getMessage());

            return false;
        }

        resultsToDisplay = getStringFromInputStream(in);
        //to [convert][1] byte stream to a string
        System.out.print(resultsToDisplay);

        return true;
    }

    @Override
    protected void onPostExecute(final Boolean success) {

        try {
            JSONObject jsonObject = new JSONObject(resultsToDisplay);
            Boolean successBool = true;//(Boolean)jsonObject.get("success");

            if(successBool){
                communicator.successfulExecute(jsonObject);
            }else{
                communicator.failedExecute();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            /*if (success) {
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
    }

    @Override
    protected void onCancelled() {
        //  courseListTask = null;
        //  showProgress(false);
    }

    private String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }
}
