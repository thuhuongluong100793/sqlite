package com.ltthuong.sqlite;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

public class NoteCustomAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int resource;
    private ArrayList<Note> arrNote;

    public NoteCustomAdapter(Context context, int resource, ArrayList<Note> arrNote) {
        super(context, resource, arrNote);
        this.context = context;
        this.resource = resource;
        this.arrNote = arrNote;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_one_item_listview, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.tvTiTle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tvContent);
            viewHolder.tvLabel = (TextView) convertView.findViewById(R.id.tvLabel);
            viewHolder.tvTime= (TextView) convertView.findViewById(R.id.tvTime);
            viewHolder.btnUpdate = (Button) convertView.findViewById(R.id.btnUpdate);
            viewHolder.btnDelete = (Button) convertView.findViewById(R.id.btnDelete);


            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note note = arrNote.get(position);
        viewHolder.tvTiTle.setText(note.getTitle());
        viewHolder.tvContent.setText(note.getContent());
        viewHolder.tvLabel.setText("(" + note.getLabel() + ")");
        viewHolder.tvTime.setText(note.getTime());

        viewHolder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), UpdateNoteActivity.class);
                intent.putExtra("key", note.getKey() );
                intent.putExtra("title", note.getTitle());
                intent.putExtra("content", note.getContent());
                intent.putExtra("label", note.getLabel());
                view.getContext().startActivity(intent);


            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
            @Override
            public void onClick(View view) {
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        MyDatabaseHelper db = new MyDatabaseHelper(getContext());
                        db.deleteNote(note);
                        Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        context.startActivity(intent);
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
        return convertView;
    }

    public class ViewHolder {
        TextView tvTiTle, tvContent, tvLabel, tvTime;
        Button btnUpdate, btnDelete;
    }
}
