package com.example.quizmp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {
    private final String TAG = this.getClass().getSimpleName();
    private TextView tvRunning1, tvRunning2;
    private TextView upButton, downButton;
    private LinearLayout leftButton, rightButton;
    private TextView tvScore, tvClock;
    private TextView tvAnswer1, tvAnswer2, tvAnswer3, tvAnswer4;

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

}