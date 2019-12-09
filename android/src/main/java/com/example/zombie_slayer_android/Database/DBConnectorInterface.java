package com.example.zombie_slayer_android.Database;

import android.content.Context;

import java.util.LinkedList;

public interface DBConnectorInterface {

    //Create a new, blank object from a source (file name, SQL object name, etc.)
    public abstract void createObject(Context ctx, String source);

    //Read an object given a source. Returns a LL of data associated with object.
    public abstract LinkedList<String> readObject(String source);

    //Given a source and LL of object data, update the object in the DB.
    public abstract void updateObject(String source, LinkedList data);

    //Given a source, delete the object (empty file, mark as deleted on SQL, etc.)
    public abstract void deleteObject(String source);
}
