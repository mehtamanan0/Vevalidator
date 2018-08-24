package com.example.manan.vevalidator;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class GoogleSIgnInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    EditText aadhar;
    String saadhar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);
        final ProgressBar prog = (ProgressBar)findViewById(R.id.progressBar4);
        prog.setVisibility(View.GONE);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        aadhar = (EditText)findViewById(R.id.editText);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sign_in_button:
                        saadhar = aadhar.getText().toString();
                        if (saadhar.matches("")) {
                            Toast.makeText(GoogleSIgnInActivity.this, "Please enter aadhar",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            prog.setVisibility(View.VISIBLE);
                            signIn();
                        }
                break;
                }
            }
        });

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri Profilepicture = acct.getPhotoUrl();

            Intent i = new Intent(this, NavigationDrawer.class);
            i.putExtra("name",personName);
            i.putExtra("email",personEmail);
            i.putExtra("photo",Profilepicture.toString());
            SharedPreferences sharedpreferences = getSharedPreferences("signin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("name",personName);
            editor.putString("email",personEmail);
            editor.putString("photo",Profilepicture.toString());
            editor.putString("signedin","logedin");
            editor.putString("aadhar",saadhar);
            editor.apply();
            RequestQueue queue = Volley.newRequestQueue(this);
            String abc = null;
            try {
                abc = String.format("http://bb496fe1.ngrok.io/det?name=%s&aadhar=%s&salary=20000&married=yes&age=12&gender=male&scheme=",
                        URLEncoder.encode(personName, "UTF-8"),
                        URLEncoder.encode(saadhar, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                Toast.makeText(getApplicationContext(), "badwa request", Toast.LENGTH_LONG).show();
            }
            String url =abc;
// Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "sent to db", Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "error in db", Toast.LENGTH_LONG).show();
                }
            });
// Add the request to the RequestQueue.
            queue.add(stringRequest);
            startActivity(i);

        } else {
            // Signed out, show unauthenticated UI.
            Toast.makeText(this, "Could not log in ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

