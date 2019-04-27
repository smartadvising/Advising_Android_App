/*
    CODE IDEA TO CREATE POPUP AND ITS ATTRIBUTES ARE MOSTLY FROM Filip Vujovic ON YOUTUBE
 */

package com.example.advising_app_v4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePopup extends Activity {

    public static String username = "";
    public static String user_email = "";
    public static String user_school = "";
    public static String user_major = "";
    public static String user_advising_level = "";
    public static String user_advisor = "";
    public static TextView usernametextview;
    public static TextView emailtextview;
    public static TextView schooltextview;
    public static TextView majortextview;
    public static TextView advisingleveltextview;
    //public static TextView advisortextview;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_popup);

        DisplayMetrics DM = new DisplayMetrics(); //When the profile button is clicked, this should display a pop up window
        getWindowManager().getDefaultDisplay().getMetrics(DM);
        int width = DM.widthPixels;
        int height = DM.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.7));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 85;
        getWindow().setAttributes(params);

        FillProfileInfo();

        Button b1 = (Button) findViewById(R.id.pop_up_logout_btn); //First pop up button should just send the user back to the login screen
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ProfilePopup.this,R.style.AlertDialog)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to log out of Smart Advising?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ProfilePopup.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        Button b3 = (Button) findViewById(R.id.popup_closewindow_btn); //Will just close the window.
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfilePopup.this.finish();
            }
        });
    }

    private void FillProfileInfo()
    {
        usernametextview = findViewById(R.id.popup_username);
        usernametextview.setText("School ID: ");
        usernametextview.append(username);

        emailtextview = findViewById(R.id.popup_email);
        emailtextview.setText("Email: ");
        emailtextview.append(user_email);

        schooltextview = findViewById(R.id.popup_school);
        schooltextview.setText("School: ");
        schooltextview.append(user_school);

        majortextview= findViewById(R.id.popup_major);
        majortextview.setText("Major: ");
        majortextview.append(user_major);

        advisingleveltextview = findViewById(R.id.popup_advising_level);
        advisingleveltextview.setText("Advising Level: ");
        advisingleveltextview.append(user_advising_level);
    }
}
