package com.example.advising_app_v4;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


//Contains the FAQ fragment view. Will add more later
public class FAQFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_faq,container,false);

        final TextView textView = v.findViewById(R.id.FAQ_VIEW_STUFF);
        textView.setText("");
        final fetchData4 asynchTask = (fetchData4) new fetchData4(new fetchData4.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<String> output) {
                textView.setText("");
                for(int i = 0; i < output.size(); i++)
                {
                    textView.append(output.get(i));
                }
            }
        }).execute();
        return v;
    }
}
