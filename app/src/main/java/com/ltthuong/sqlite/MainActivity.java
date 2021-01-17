package com.ltthuong.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView lstView;
    public NoteCustomAdapter arrayAdapterNote;
    public ArrayList<Note> arrayListNote = new ArrayList<>();

    public EditText txtTitle;
    public EditText txtContent;
    public EditText txtLabel;
    public Button btnSave;
    public Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyDatabaseHelper db = new MyDatabaseHelper(this);

        //Show
        List<Note> list=  db.getAllNotes();
        this.arrayListNote.addAll(list);
        lstView = (ListView) findViewById(R.id.lstView);
        arrayAdapterNote = new NoteCustomAdapter(this, R.layout.layout_one_item_listview, arrayListNote);
        lstView.setAdapter(arrayAdapterNote);
        arrayAdapterNote.notifyDataSetChanged();

        //Add
        txtTitle = (EditText) findViewById(R.id.txtTitle);
        txtContent = (EditText) findViewById(R.id.txtContent);
        txtLabel = (EditText) findViewById(R.id.txtLabel);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String title = txtTitle.getText().toString();
                String content = txtContent.getText().toString();
                String label = txtLabel.getText().toString();

                Note note = new Note(title, content, label, "time");
                db.addNote(note);
                arrayAdapterNote.notifyDataSetChanged();
                //Show
                //runOnUiThread(run);

                Toast.makeText(getApplicationContext(), "Thêm thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        //Cancel
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                txtTitle.setText("");
                txtContent.setText("");
                txtLabel.setText("");
            }
        });
    }

}