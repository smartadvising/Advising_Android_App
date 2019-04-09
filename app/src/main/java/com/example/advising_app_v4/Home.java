package com.example.advising_app_v4;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class Home extends AppCompatActivity
        implements  BottomNavigationView.OnNavigationItemSelectedListener{

    private TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mTextMessage = findViewById(R.id.message);

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction() // We we log in we want to see the Home fragment first
                .replace(R.id.fragment_container,new HomeFrag())
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch(menuItem.getItemId()) //Depending of what we choose in the navigation bar, we can
                                    // choose what fragment to display
        {
            case R.id.navigation_home:
                fragment = new HomeFrag();
                break;

            case R.id.navigation_FAQ:
                fragment = new FAQFrag();
                break;
        }
        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) //When We've choose the fragment e then load the fragment here
    {
        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
