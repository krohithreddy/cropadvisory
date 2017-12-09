package com.hhcl.cropadvisory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Entity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utils.AppController;

/**
 * Created by Karthik Kumar K on 22-11-2017.
 */

public class LoginScreen extends Activity {
    private EditText mobile;
    private Button submit;
    ProgressDialog pDialog;
    String tag_json_obj = "json_obj_req";
    String regId;
    String url = "http://172.19.1.253/firebase/register.php/";
    Typeface datefont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        mobile = (EditText) findViewById(R.id.mobile);
        submit = (Button) findViewById(R.id.submit);
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", "");
        datefont = Typeface.createFromAsset(getApplicationContext().getAssets(), "Arial Bold.ttf");
        mobile.setTypeface(datefont);

        Log.d("Firebaseregid: ", regId);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (com.hhcl.cropadvisory.Networkcheck.isNetworkAvailable(getApplicationContext())) {
                    new Login().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "No internet connection", 10).show();
                }



            }
        });

    }

    public class Login extends AsyncTask<String, String, String> {
        String st;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginScreen.this);
            pDialog.setMessage("Loading...");
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {

                HttpClient client = new DefaultHttpClient();
                HttpPost post = new HttpPost("http://services.heterohcl.com/Testing/firebase/register.php");

                List pair = new ArrayList<NameValuePair>();
                pair.add(new BasicNameValuePair("phoneNumber", mobile.getText().toString()));
                pair.add(new BasicNameValuePair("fireBaseId", regId));
                Log.d("responseasync", pair.toString());
                post.setEntity(new UrlEncodedFormEntity(pair));
                HttpResponse response = client.execute(post);
                st = EntityUtils.toString(response.getEntity());
                Log.d("responseasync", st);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st;
        }

        @Override
        protected void onPostExecute(String st) {
            super.onPostExecute(st);


            pDialog.dismiss();
            try {
//                Log.d("loginresponse",st);
                JSONObject res = new JSONObject(st);
                String status = res.getString("status");
                if (status.equalsIgnoreCase("success")) {
                    Intent i = new Intent(LoginScreen.this, Mainactivity.class);
                    startActivity(i);
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("PROJECTNAME", android.content.Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("ISLOGGEDIN", true);
                    editor.commit();
                    SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor1 = pref.edit();
                    editor.putString("mobile", mobile.getText().toString());
                    editor.commit();
                } else {
                    Toast.makeText(getApplicationContext(), "try again..", 10).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
