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

public class ProfilePopup extends Activity {

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

        Button b2 = (Button) findViewById(R.id.popup_exit_btn); // Should exit the user, but instead just sends them back to the login, will fix later
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(ProfilePopup.this,R.style.AlertDialog)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Are you sure you want to close Smart Advising?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ProfilePopup.this.finish();
                                System.exit(0); // TO GET AROUND THIS JUST ADD EXIT OPTION IN TITLE SCREEN
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
}
