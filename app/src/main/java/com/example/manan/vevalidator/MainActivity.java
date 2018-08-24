package com.example.manan.vevalidator;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;

public class MainActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.activity_main, container, false);
        super.onCreate(savedInstanceState);
        Toolbar toolbar=(Toolbar) view.findViewById(R.id.my_toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Schemes");
            toolbar.setTitleTextColor(Color.WHITE);
        }
        ArrayList<String> list = new ArrayList<String>();
        list.add("Pradhan Mantri Suraksha Bima Yojana (PMSBY)");
        list.add("Atal Pension Yojana (APY)");
        list.add("Pradhan Mantri Awas Yojana (PMAY)");
        list.add("Sansad Adarsh Gram Yojana (SAGY)");
        list.add("Pradhan Mantri Fasal Bima Yojana (PMFBY)");
        list.add("Pradhan Mantri Gram Sinchai Yojana (PMGSY)");
        list.add("Pradhan Mantri Garib Kalyan Yojanay (PMGKY)");
        list.add("Pradhan Mantri Jan Aushadhi Yojana (PMJAY)");
        list.add("Pradhan Mantri Jan Dhan Yojana (PMJDY)");
        list.add("National Apprenticeship Promotion Scheme");
        list.add("Gangajal Delivery Scheme");
        list.add("Pradhan Mantri Surakshit Matritva Abhiyan");
        list.add("Vidyanjali Yojana");
        list.add("Standup India Loan Scheme");
        list.add("Gram Uday Se Bharat Uday Abhiyan");
        list.add("Samajik Aadhikarita Shivir");
        list.add("Railway Travel Insurance Scheme");
        list.add("Smart Ganga City");
        list.add("Mission Bhagiratha in Telangana");
        list.add("Vidyalakshmi Loan Scheme");
        list.add("Swayam Prabha");
        list.add("Pradhan Mantri Surakshit Sadak Yojana");



        //instantiate custom adapter
        MyCustomAdapter adapter = new MyCustomAdapter(list, getActivity().getApplicationContext());

        //handle listview and assign adapter
        ListView lView = (ListView)view.findViewById(R.id.my_listview);
        lView.setAdapter(adapter);


        return view;
    }
}
