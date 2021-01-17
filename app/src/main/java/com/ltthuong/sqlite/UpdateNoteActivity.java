package com.ltthuong.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UpdateNoteActivity extends AppCompatActivity {
    EditText txtTitle;
    EditText txtContent;
    EditText txtLabel;
    EditText txtKey;
    Button btnSaveUpdate;
    Button btnBack;
    public ListView lstView;
    public NoteCustomAdapter arrayAdapterNote;
    public ArrayList<Note> arrayListNote = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_note);
        //Update
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null)
        {
            txtKey = (EditText) findViewById(R.id.updateKey);
            txtTitle = (EditText) findViewById(R.id.updateTitle);
            txtContent = (EditText) findViewById(R.id.updateContent);
            txtLabel = (EditText) findViewById(R.id.updateLabel);
            txtKey.setText(bundle.get("key").toString());
            txtTitle.setText(bundle.get("title").toString());
            txtContent.setText(bundle.get("content").toString());
            txtLabel.setText(bundle.get("label").toString());
        }
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        btnSaveUpdate = (Button) findViewById(R.id.btnSaveUpdate);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSaveUpdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                int key = Integer.parseInt(txtKey.getText().toString()) ;
                String title = txtTitle.getText().toString();
                String content = txtContent.getText().toString();
                String label = txtLabel.getText().toString();
                Note note = new Note(key, title, content, label, "time");
                db.updateNote(note);
                Toast.makeText(getApplicationContext(), "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                //reLoad();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);


            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v)
           {
               finish();
           }
        });

    }
}