package com.ltthuong.sqlite;

public class Note {
    public int ID;
    public String title;
    public String content;
    public String label;
    public String time;

    public Note() {
    }
    public Note(int key, String title, String content, String label, String time) {

        this.ID = key;
        this.title = title;
        this.content = content;
        this.label = label;
        this.time = time;
    }
    public Note(String title, String content, String label, String time) {

        this.title = title;
        this.content = content;
        this.label = label;
        this.time = time;
    }

    public int getKey( )
    {
        return ID;
    }
    public String getTitle( )
    {
        return title;
    }
    public String getContent( )
    {
        return content;
    }
    public String getLabel( )
    {
        return label;
    }
    public String getTime( )
    {
        return time;
    }

    public void setKey (int key)
    {
        this.ID = key;
    }
    public void setTitle(String title )
    {
        this.title = title;
    }
    public void setContent(String content )
    {
        this.content = content;
    }
    public void setLabel(String label )
    {
        this.label = label;
    }
    public void setTime(String time )
    {
        this.time = time;
    }

}

