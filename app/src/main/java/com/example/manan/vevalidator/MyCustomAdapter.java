package com.example.manan.vevalidator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by manan on 11/10/17.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;



    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.my_custom_list_layout, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button applyBtn = (Button)view.findViewById(R.id.apply_btn);
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);


        applyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                SharedPreferences sharedpreferences = v.getContext().getSharedPreferences("signin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String scheme = listItemText.getText().toString();
                String aadhar = sharedpreferences.getString("aadhar","");
                if (sharedpreferences.getString("scheme","").equals(listItemText.getText().toString())){
                    Toast.makeText(v.getContext(), "You have already applied for this scheme", Toast.LENGTH_SHORT).show();
                }
                else {
                    editor.putString("scheme", listItemText.getText().toString());
                    editor.apply();
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    String url = null;
                    try {
                        url = String.format("http://bb496fe1.ngrok.io/ins?scheme=%s&aadhar=%s",
                                URLEncoder.encode(scheme, "UTF-8"),
                                URLEncoder.encode(aadhar, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
// Add the request to the RequestQueue.
                    queue.add(stringRequest);
                    Toast.makeText(v.getContext(), "Applied for Scheme : " + listItemText.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                SharedPreferences sharedpreferences = v.getContext().getSharedPreferences("signin", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String scheme = listItemText.getText().toString();
                String aadhar = sharedpreferences.getString("aadhar","");
                if (sharedpreferences.getString("scheme","").equals(listItemText.getText().toString())){
                    editor.putString("scheme", null);
                    editor.apply();
                    RequestQueue queue = Volley.newRequestQueue(v.getContext());
                    String url = null;
                    try {
                        url = String.format("http://bb496fe1.ngrok.io/del?scheme=%s&aadhar=%s",
                                URLEncoder.encode(scheme, "UTF-8"),
                                URLEncoder.encode(aadhar, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }


// Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    // Display the first 500 characters of the response string.

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    queue.add(stringRequest);
                    Toast.makeText(v.getContext(), "You have unapplied from this scheme", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(v.getContext(), "You have not applied for scheme", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        });
        return view;
    }
}