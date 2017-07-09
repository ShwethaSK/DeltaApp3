package com.example.dell.deltaapp3;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    EditText tv;
    TextView tv1;
    TextView tv2;
    RetrieveInfo rv;
    String text;
    String name;
    InputStreamReader inputStream;
    ImageView iv;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        tv=(EditText)findViewById(R.id.textView);
        tv1=(TextView)findViewById(R.id.textView2);
        tv2=(TextView)findViewById(R.id.textView3);
        iv=(ImageView)findViewById(R.id.imageView);
        Picasso.with(this).load("http://pokeapi.co/media/sprites/pokemon/25.png").into(iv);
        name=tv.getText().toString();
        try {
            url="http://pokeapi.co/api/v2/pokemon/"+ URLEncoder.encode(name,"UTF-8");//+URLEncoder.encode("/","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void display_alert(View view) {
       rv=new RetrieveInfo();
        rv.execute(url);

    }
    class RetrieveInfo extends AsyncTask<String,String ,String>
    {
        HttpURLConnection urlConnection =null;
        BufferedReader Content;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv1.setText("Output:");

        }

        @Override
        protected String doInBackground(String... params) {
      try {
                String link = params[0];
                URL url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream =new InputStreamReader(urlConnection.getInputStream());
                Content=new BufferedReader(inputStream);
                StringBuilder stringbuilder=new StringBuilder();
                String line;
                while((line=Content.readLine())!=null)
                stringbuilder.append(line);
                text=stringbuilder.toString();
                Content.close();
                inputStream.close();
          }
            catch (MalformedURLException e) {
                e.printStackTrace();
          }
            catch (ProtocolException e) {
                e.printStackTrace();
          }
            catch (IOException e) {
              e.printStackTrace();
          }
            return text;
          }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv2.setText(s);
            Log.i("json",s);

        }
    }
}