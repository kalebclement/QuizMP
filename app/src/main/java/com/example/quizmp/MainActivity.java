package com.example.quizmp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvRunning1, tvRunning2;
    private TextView tvquestion;
    private TextView upButton, downButton;
    private LinearLayout leftButton, rightButton;
    private TextView tvScore, tvClock;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;
    private  String urlQuestion = "https://mobileprogramming-kaleb.000webhostapp.com/question.php";
    JSONObject obj1, obj2, obj3, obj4;
    private String quest;
    private Integer totalscore = 0, i = 0;
    private JSONArray arr;
    private   CountDownTimer countdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fix);


        assignViews();
        // ASSIGN ON CLICK LISTENER FOR NAVIGATION BUTTON
        upButton.setOnClickListener(this);
        downButton.setOnClickListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
        tvAnswer1.setOnClickListener(this);
        tvAnswer2.setOnClickListener(this);
        tvAnswer3.setOnClickListener(this);
        tvAnswer4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == upButton.getId() || view.getId() == leftButton.getId() || view.getId() == rightButton.getId() || view.getId() == downButton.getId()) {
            showNavigateDialog(view);
        }
        else{
            try {
                if(quest == obj1.getString("question")){
                    checkAnswer(obj1, view);
                } else if(quest == obj2.getString("question")){
                    checkAnswer(obj2, view);
                } else if(quest == obj3.getString("question")){
                    checkAnswer(obj3, view);
                } else if(quest == obj4.getString("question")){
                    checkAnswer(obj4, view);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        alertDialog.setMessage("proceed to next question");

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

        tvquestion = findViewById(R.id.tv_question);
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

        tvRunning1.setSelected(true);
        tvRunning2.setSelected(true);

        getQuest(urlQuestion);
        timer(tvClock);
    }



    private void getQuest(String requestUrl){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, requestUrl, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    obj1 = response.getJSONObject(0);
                    Log.d("response1", obj1.toString());

                    obj2 = response.getJSONObject(1);
                    Log.d("response2", obj2.toString());

                    obj3 = response.getJSONObject(2);
                    Log.d("response3", obj3.toString());

                    obj4 = response.getJSONObject(3);
                    Log.d("response4", obj4.toString());

                    buildObject(i);
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("Err", error.getMessage());
            }
        });
        queue.add(jsonArrayRequest);
    }
    private void buildObject(Integer i) throws JSONException {
        arr = new JSONArray();
        arr.put(obj1);
        arr.put(obj2);
        arr.put(obj3);
        arr.put(obj4);

        JSONObject obj = arr.getJSONObject(i);
        build(obj);

    }

    private void build(JSONObject obj) throws JSONException {
        quest = obj.getString("question");
        String a1 = "1. " + obj.getString("option_1");
        String a2 = "2. " + obj.getString("option_2");
        String a3 = "3. " + obj.getString("option_3");
        String a4 = "4. " + obj.getString("option_4");
        String build = quest + "\n\n\n\n\n" + a1 + "\n\n" + a2 + "\n\n" + a3 + "\n\n" + a4;
        tvquestion.setText(build);

    }

    private void checkAnswer(JSONObject obj, View v) throws JSONException {
        String correctans = obj.getString("answer");
        String answer = null;
        if(v.getId() == tvAnswer1.getId()){
            answer = obj.getString("option_1");

        }
        else if(v.getId() == tvAnswer2.getId()){
            answer = obj.getString("option_2");
        }
        else if(v.getId() == tvAnswer3.getId()){
            answer = obj.getString("option_3");
        }
        else if(v.getId() == tvAnswer4.getId()){
            answer = obj.getString("option_4");
        }

        if(answer.equals(correctans)){
            totalscore = totalscore + 50;
            String ts = totalscore.toString();
            tvScore.setText(ts);
            Toast toast = Toast.makeText(this, "Your answer is correct", Toast.LENGTH_SHORT);
            toast.show();
            if(i < 4){
                i = i + 1;
                JSONObject Jobj = arr.getJSONObject(i);
                build(Jobj);
                countdown.cancel();
                timer(tvClock);
            }

        } else{
            Toast toast = Toast.makeText(this, "Your answer is inccorect", Toast.LENGTH_SHORT);
            toast.show();
            if(i < 4){
//                i = i + 1;
//                buildObject(i);
                i = i + 1;
                JSONObject Jobj = arr.getJSONObject(i);
                build(Jobj);
                countdown.cancel();
                timer(tvClock);
            }
        }
    }

    private void timer(TextView tvClock){
        tvClock.setText("40");
      countdown  = new CountDownTimer(40000, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                String countdown = String.valueOf(millisUntilFinished / 1000);
                tvClock.setText(countdown);
            }

            @Override
            public void onFinish() {
                showTimesUpDialog();
                if(i < 4){
                    i = i + 1;
                    JSONObject Jobj = null;
                    try {
                        Jobj = arr.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        build(Jobj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    countdown.cancel();
                    timer(tvClock);
                }
            }
        };
        countdown.start();
    }



}