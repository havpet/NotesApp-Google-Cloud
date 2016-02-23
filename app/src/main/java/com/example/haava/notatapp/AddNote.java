package com.example.haava.notatapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddNote extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Button button = (Button) findViewById(R.id.addButton);
    }

    public void saveNote(View view) {
        EditText title = (EditText) findViewById(R.id.title);
        EditText text = (EditText) findViewById(R.id.text);
        String[] liste = {title.getText().toString(), text.getText().toString()};

        new EndpointsAsyncTask().execute(new Pair<Context, String[]>(this, liste));
    }
}
