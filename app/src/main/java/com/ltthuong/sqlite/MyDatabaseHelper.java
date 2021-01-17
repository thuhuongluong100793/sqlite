package com.ltthuong.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Note.db";

    // Table name: Note.
    private static final String TABLE_NOTE = "Note";

    private static final String COLUMN_NOTE_KEY ="ID";
    private static final String COLUMN_NOTE_TITLE ="Title";
    private static final String COLUMN_NOTE_CONTENT = "Content";
    private static final String COLUMN_NOTE_LABEL = "Label";
    private static final String COLUMN_NOTE_TIME = "Time";


    public MyDatabaseHelper(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_NOTE + " ("
                + COLUMN_NOTE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NOTE_TITLE + " TEXT,"
                + COLUMN_NOTE_CONTENT + " TEXT," + COLUMN_NOTE_LABEL + " TEXT," +  COLUMN_NOTE_TIME + " TEXT"
                + ")";

        // Execute Script.
        System.out.println(script);
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);

        // Create tables again
        onCreate(db);
    }


    public void addNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + note.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getContent());
        values.put(COLUMN_NOTE_LABEL, note.getLabel());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = dateFormat.format(calendar.getTime());

        values.put(COLUMN_NOTE_TIME, currentTime.toString());

        // Inserting Row
        db.insert(TABLE_NOTE, null, values);

        // Closing database connection
        db.close();
    }


    public Note getNote(int key) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + key);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[] { COLUMN_NOTE_KEY,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT, COLUMN_NOTE_LABEL, COLUMN_NOTE_TIME }, COLUMN_NOTE_KEY + "=?",
                new String[] { String.valueOf(key) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return note
        return note;
    }


    public ArrayList<Note> getAllNotes() {
        Log.i(TAG, "MyDatabaseHelper.getAllNotes ... " );

        ArrayList<Note> noteList = new ArrayList<Note>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Note note = new Note();
            note.setKey(Integer.parseInt(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_NOTE_KEY))));
            note.setTitle(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_NOTE_TITLE)));
            note.setContent(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_NOTE_CONTENT)));
            note.setLabel(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_NOTE_LABEL)));
            note.setTime(cursor.getString(cursor.getColumnIndex(MyDatabaseHelper.COLUMN_NOTE_TIME)));
            // Adding note to list
            noteList.add(note);
            cursor.moveToNext();
        }
        cursor.close();

        // return note list
        return noteList;
    }

    public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.updateNote ... "  + note.getTitle());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TITLE, note.getTitle());
        values.put(COLUMN_NOTE_CONTENT, note.getContent());
        values.put(COLUMN_NOTE_LABEL, note.getLabel());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentTime = dateFormat.format(calendar.getTime());
        values.put(COLUMN_NOTE_TIME, currentTime.toString());

        // updating row
        return db.update(TABLE_NOTE, values, COLUMN_NOTE_KEY + " = ?",
                new String[]{String.valueOf(note.getKey())});
    }

    public void deleteNote(Note note) {
        Log.i(TAG, "MyDatabaseHelper.deleteNote ... " + note.getTitle() );

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, COLUMN_NOTE_KEY + " = ?",
                new String[] { String.valueOf(note.getKey()) });
        db.close();
    }

}
