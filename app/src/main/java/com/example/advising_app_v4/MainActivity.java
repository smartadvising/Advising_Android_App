/*
    MAJORITY OF CREDIT FOR THE AUTHENICATION CODE GOES TO Andrew Column AT MICROSOFT 365 ON YOUTUBE
*/
package com.example.advising_app_v4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.IAccount;
import com.microsoft.identity.client.exception.MsalException;


public class MainActivity extends AppCompatActivity implements MSALAuthenticationCallback {
    private final static String TAG = MainActivity.class.getSimpleName();

    private LinearLayout panel_signin;
    private LinearLayout panelEvents;

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

        (findViewById(R.id.btn_signout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {onSignout();
            }
        });

        setPanelVisibility(true,false);
    }

    private void onSignin(){

        AuthenticationController authController = AuthenticationController.getInstance(this);
        authController.doAcquireToken(this,this);
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

        Toast.makeText(MainActivity.this,"Welcome! ",Toast.LENGTH_SHORT ).show();
        //setPanelVisibility(false,true);
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
}
