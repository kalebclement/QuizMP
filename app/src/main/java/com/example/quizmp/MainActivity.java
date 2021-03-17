package com.example.quizmp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvRunning1, tvRunning2;
    private TextView upButton, downButton;
    private LinearLayout leftButton, rightButton;
    private TextView tvScore, tvClock;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private  String urlQuestion = "https://mobileprogramming-kaleb.000webhostapp.com/question.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fix);


        HttpURLConnection urlConnection = null;
        try {
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);
           urlConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }

        assignViews();
//        getQuestion(urlQuestion);

        // ASSIGN ON CLICK LISTENER FOR NAVIGATION BUTTON
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        if (view.getId() == upButton.getId() || view.getId() == leftButton.getId() || view.getId() == rightButton.getId() || view.getId() == downButton.getId()) {
            showNavigateDialog(view);
        }
    }

    private void showNavigateDialog(View view) {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        TextView tv = null;
        LinearLayout ll = null;
        String title = null;

        if (view.getId() != leftButton.getId() & view.getId() != rightButton.getId()){
            tv = (TextView) view;
        }else {
            ll = (LinearLayout) view;
        }

        if (tv != null){
            title = tv.getText() + " button clicked";
        } else if (ll != null) {
            if (ll.getId() == leftButton.getId()) title = "Left button clicked";
            else if(ll.getId() == rightButton.getId()) title  = "Right button clicked";
        }
        alertDialog.setTitle(title);
        alertDialog.setMessage(title);

        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    // Alert dialog action goes here
                    // onClick button code here
                    dialog.dismiss();// use dismiss to cancel alert dialog
                });
        alertDialog.show();
    }


    private void showTimesUpDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        String title = "times up";

        alertDialog.setTitle(title);
        alertDialog.setMessage("this is your message");

        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> {
                    // Alert dialog action goes here
                    // onClick button code here
                    dialog.dismiss();// use dismiss to cancel alert dialog
                });
        alertDialog.show();
    }

    private void assignViews() {
        tvRunning1 = findViewById(R.id.tv_running_1);
        tvRunning2 = findViewById(R.id.tv_running_2);

        upButton = findViewById(R.id.btn_up);
        downButton = findViewById(R.id.btn_down);
        leftButton = findViewById(R.id.btn_left);
        rightButton = findViewById(R.id.btn_right);

        tvAnswer1 = findViewById(R.id.answer_1);
        tvAnswer2 = findViewById(R.id.answer_2);
        tvAnswer3 = findViewById(R.id.answer_3);
        tvAnswer4 = findViewById(R.id.answer_4);

        tvClock = findViewById(R.id.tv_timer);
        tvScore = findViewById(R.id.tv_score);
    }

    public final List getQuestion(String requestUrl) {
        URL url = this.createUrl(requestUrl);
        String jsonResponse = (String) null;

        try {
            jsonResponse = this.makeHttpRequest(url);
        } catch (IOException var5) {
            Log.e(this.TAG, "Problem making the HTTP request.", (Throwable)var5);
        }

//        return this.extractFeatureFromJSON(jsonResponse);

        return null;
    }


    private final URL createUrl(String stringUrl) {
        URL url = (URL)null;

        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException var4) {
            Log.e(this.TAG, "Problem building the URL.", (Throwable)var4);
        }
        return url;
    }

    private final List extractFeatureFromJSON(String responseJSON) {
        if (TextUtils.isEmpty((CharSequence)responseJSON)) {
            return null;
        } else {
            List exploreList = (List)(new ArrayList());

            try {
                JSONObject baseJsonResponse = new JSONObject(responseJSON);
                JSONArray resultArray = baseJsonResponse.getJSONArray("result");
                int i = 0;

                for(int var6 = resultArray.length(); i < var6; ++i) {
                    JSONObject currentExplore = resultArray.getJSONObject(i);
                    String var10002 = currentExplore.getString("imageURL");
                    String var10003 = currentExplore.getString("place_name");
                    String var10004 = currentExplore.getString("place_uid");

                }
            } catch (JSONException var9) {
                Log.e(this.TAG, "Problem parsing the news JSON results", (Throwable)var9);
            }

            return exploreList;
        }
    }

    private final String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            }
            BufferedReader reader = new BufferedReader((Reader)inputStreamReader);

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                output.append(line);
            }
        }

        String var10000 = output.toString();
        Log.e(this.TAG, var10000);
        return var10000;
    }

    private final String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        } else {

            InputStream in;
            try {
                URL link = new URL(urlQuestion);
                HttpURLConnection urlConnection = (HttpURLConnection) link.openConnection();
                if (urlConnection == null) {
                    throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
                }

              urlConnection.connect(); // ERROR DISNI !!! ERROR DISNI !!! ERROR DISNI !!! ERROR DISNI !!! ERROR DISNI !!!
//                if (urlConnection.getResponseCode() == 200) {
//                    Log.e("connection Sucess", "connection Sucess");
////                    jsonResponse = this.readFromStream(inputStream);
//                } else {
//                    Log.e(this.TAG, "Error response code: " + urlConnection.getResponseCode());
//                }
//                BufferedReader bin = new BufferedReader(new InputStreamReader(in));

//                String inputline;
//                while ((inputline = bin.readLine()) != null){
//                    sb.append(inputline);
//                }
            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = (InputStream)null;
//
//            try {
//                URLConnection connection = url.openConnection();
//                if (connection == null) {
//                    throw new NullPointerException("null cannot be cast to non-null type java.net.HttpURLConnection");
//                }
//
//                urlConnection = (HttpURLConnection)connection;
//
//                urlConnection.connect();
////                if (urlConnection.getResponseCode() == 200) {
//                    inputStream = urlConnection.getInputStream();
//                    Log.e("connection Sucess", "connection Sucess");
////                    jsonResponse = this.readFromStream(inputStream);
//                } else {
//                    Log.e(this.TAG, "Error response code: " + urlConnection.getResponseCode());
//                }
//            } catch (IOException var12) {
//                Log.e(this.TAG, "Problem retrieving the news JSON results.", (Throwable)var12);
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//
//                if (inputStream != null) {
//                    inputStream.close();
//                }
//
//            }

            return jsonResponse;
        }
    }

}