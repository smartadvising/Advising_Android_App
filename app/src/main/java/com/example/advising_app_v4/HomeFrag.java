package com.example.advising_app_v4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

//Contains the Home fragment view
public class HomeFrag extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        ImageButton B = v.findViewById(R.id.Profile_Btn);
        ImageButton B2 = v.findViewById(R.id.Add_Person_Btn);
        final TextView textView = v.findViewById(R.id.TESTQUEUE);
        textView.setText("");
        final ArrayList<String> QueueList = new ArrayList<String>();


        fetchData3 asynchTask = (fetchData3) new fetchData3(new fetchData3.AsyncResponse() {
            @Override
            public void processFinish(ArrayList<String> output) {
                if(output.size() == 0) {
                    textView.append("Queue Is Currently Empty!\n Please Add Yourself" +
                            " to the Queue to See An Advisor");
                }else

                for (int i = 0; i < output.size(); i++) {
                    QueueList.add(output.get(i));
                    textView.append(output.get(i));
                }

                textView.setText("");
                textView.append("There is currently " + output.size() + " people in the queue.");
                textView.append("\n\n");
                textView.append("Estimated Wait Time: " + output.size() * 10 + " minutes");
                textView.append("\n\n");
                textView.append("To add yourself to the Queue, please press the icon to the top right.");
            }
        }).execute();


        //Currently only has the profile button set to do anything, will add/chance more later
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ProfilePopup.class));
            }
        });

        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        new AlertDialog.Builder(getContext(),R.style.AlertDialog)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Adding to the Queue")
                                .setMessage("Are you sure you want to be added to the queue?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(getContext(), "Feature Currently Unavailable", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();
            }
        });
        return v;
    }

}
