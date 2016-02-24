package com.example.haava.notatapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.haava.myapplication.backend.myApi.MyApi;
import com.example.haava.myapplication.backend.myApi.model.Entity;
import com.example.haava.myapplication.backend.myApi.model.JsonMap;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.json.JsonParser;

import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haava on 2/17/2016.
 */
class AsyncGetNotes extends AsyncTask<Pair<Context, String>, Void, String> {
    private static MyApi myApiService = null;
    private Context context;
    private List<Entity> list;
    private Activity mActivity;

    AsyncGetNotes(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        context = params[0].first;
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        try {
            return myApiService.getNotes(deviceId).execute().toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String res) {
        ListView listview = (ListView) mActivity.findViewById(R.id.notelist);
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(res);
            JSONObject jsonObj = (JSONObject) obj;
            JSONArray arr = (JSONArray) jsonObj.get("items");

            String[] retur = new String[arr.size()];



            for (int i = 0; i < arr.size(); i++) {
                JSONObject cur = (JSONObject) arr.get(i);
                JSONObject prop = (JSONObject) cur.get("properties");
                retur[i] = (String) prop.get("title");
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mActivity,
                    android.R.layout.simple_list_item_1, android.R.id.text1, retur);

            listview.setAdapter(adapter);
        }

        catch(Exception e) {
            e.printStackTrace();
        }

    }
}