/*
    MAJORITY OF CREDIT FOR THE AUTHENICATION CODE GOES TO Andrew Column AT MICROSOFT 365 ON YOUTUBE
*/
package com.example.advising_app_v4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.exception.MsalException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;


public class MainActivity extends AppCompatActivity implements MSALAuthenticationCallback {
    private final static String TAG = MainActivity.class.getSimpleName();


    private LinearLayout panel_signin;
    private LinearLayout panelEvents;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    public static String SchoolChoice = "";
    public static String MajorChoice = "";
    public static String LevelChoice = "";
    public static ArrayList<String> CollegeList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        panel_signin = findViewById(R.id.panel_signin);
        panelEvents = findViewById(R.id.panel_loadEvent);

        (findViewById(R.id.btn_signin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignin();
            }
        });
        (findViewById(R.id.Main_Exit_Btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { ExitOut();
            }
        });
        (findViewById(R.id.btn_signout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onSignout();
            }
        });
        setPanelVisibility(true,false);
        spinner2 = findViewById(R.id.major_spinner);

        // -- THis whole ass section is for the first spinner.
        final ArrayList<String> list1 = new ArrayList<String>();
        final fetchData asynchTask = (fetchData) new fetchData(new fetchData.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<String> output) {
                for(int i = 0; i < output.size(); i++)
                {
                    list1.add(output.get(i));
                }
            }
        }).execute();

        final ArrayList<String> list2 = new ArrayList<String>();




        String temp2 = "Select your School";
        list1.add(temp2);
        spinner1 = findViewById(R.id.school_spinner);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list1);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = adapterView.getItemAtPosition(i).toString();
                if(i == 2)
                {
                    ClearPopUpInfo();
                    list2.clear();
                    list2.add("No majors here, Pick Another School!");
                    final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list2);
                    spinner2.setAdapter(adapter2);
                }
                else {
                    if (i == 1) {
                        Toast.makeText(getBaseContext(), "Selected '" + temp + "' as your school", Toast.LENGTH_SHORT).show();
                        SchoolChoice = temp;
                        ProfilePopup.user_school = temp;

                        fetchData2 asynchTask2 = (fetchData2) new fetchData2(new fetchData2.AsyncResponse() {
                            @Override
                            public void processFinish(ArrayList<String> output) {
                                for (int i = 0; i < output.size(); i++) {
                                    list2.add(output.get(i));
                                }
                            }
                        }).execute(i);
                        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, list2);
                        spinner2.setAdapter(adapter2);
                    } else // If the person doesn't choose a school
                    {
                        SchoolChoice = "";
                        list2.clear();
                        list2.add("Select Your School First");
                        ClearPopUpInfo();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Major Section Starts Here
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = adapterView.getItemAtPosition(i).toString();

                if(i != 0) {
                    Toast.makeText(getBaseContext(), "Selected '" + temp + "' as your major", Toast.LENGTH_SHORT).show();
                    MajorChoice = temp;
                    ProfilePopup.user_major = temp;
                }
                else // If the person doesn't choose a Level
                {
                    MajorChoice = "";
                    ProfilePopup.user_major = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //School level starts here
        final ArrayList<String> list3 = new ArrayList<String>();
        list3.add("Select your Academic Level");
        list3.add("Undergraduate");
        list3.add("Graduate");
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list3);
        spinner3 = findViewById(R.id.level_spinner);
        spinner3.setAdapter(adapter3);
        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String temp = adapterView.getItemAtPosition(i).toString();

                if(i != 0) {
                    Toast.makeText(getBaseContext(), "Selected '" + temp + "' as your level", Toast.LENGTH_SHORT).show();
                    LevelChoice = temp;
                    ProfilePopup.user_advising_level = temp;
                }
                else // If the person doesn't choose a Level
                {
                    LevelChoice = "";
                    ProfilePopup.user_advising_level = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void ExitOut(){
        new AlertDialog.Builder(this,R.style.AlertDialog)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing Activity")
                .setMessage("Are you sure you want to close Smart Advising?")
                .setPositiveButton("Yes",  new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0); // TO GET AROUND THIS JUST ADD EXIT OPTION IN TITLE SCREEN
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void onSignin(){
        if(SchoolChoice == "" || LevelChoice == "" || MajorChoice == "") {
            Toast.makeText(getBaseContext(), "Please make sure all sections are filled", Toast.LENGTH_SHORT).show();
        }
        else
        {
            AuthenticationController authController = AuthenticationController.getInstance(this);
            authController.doAcquireToken(this, this);
        }
    }

    private void onSignout(){

        Toast.makeText(MainActivity.this,"Goodbye! ",Toast.LENGTH_SHORT ).show();
        AuthenticationController authController = AuthenticationController.getInstance(this);
        authController.signout();
        setPanelVisibility(true,false);
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (AuthenticationController.getInstance(this).getPublicClient() != null) {
            AuthenticationController.getInstance(this).getPublicClient().handleInteractiveRequestRedirect(requestCode, resultCode, data);
        }
    }

    private void setPanelVisibility(Boolean showSignIn, Boolean showEvent){
        panel_signin.setVisibility(showSignIn ? View.VISIBLE :View.GONE);
        panelEvents.setVisibility(showEvent ? View.VISIBLE :View.GONE);
    }

    @Override
    public void onMsalAuthSuccess(AuthenticationResult authenticationResult) {
        ProfilePopup.user_email = authenticationResult.getAccount().getUsername();
        String [] temp = ProfilePopup.user_email.split("@");
        ProfilePopup.username = temp[0];
        Toast.makeText(MainActivity.this,"Welcome! ",Toast.LENGTH_SHORT ).show();


/*
        final postData asynchTask = (postData) new postData(new postData.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<String> output) {

            }
        }).execute();
        */



        openHomeActivity(); // Go to the fragments
    }

    @Override
    public void onMsalAuthError(MsalException exception) {

        Log.d(TAG,"Cancel Authenticated",exception);
    }

    @Override
    public void onMsalAuthError(Exception exception) {
        Log.d(TAG,"Cancel Authenticated",exception);
    }

    @Override
    public void onMsalAuthCancel() {
        Log.d(TAG,"Cancel Authenticated");
    }

    public void openHomeActivity()
    {
        Intent intent = new Intent(this,Home.class);
        startActivity(intent);
    }

    private void ClearPopUpInfo()
    {
        ProfilePopup.user_school = "";
        ProfilePopup.user_advising_level = "";
        ProfilePopup.user_advisor = "";
        ProfilePopup.user_email = "";
        ProfilePopup.user_major = "";
        ProfilePopup.user_major = "";
    }


}
