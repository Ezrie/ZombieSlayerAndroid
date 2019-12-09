package com.example.zombie_slayer_android.Database;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class BinaryConnector implements DBConnectorInterface{

    FileOutputStream fos = null;
    Context ctx;

    //Create an empty save file, the default constructor that's called when first starting the app.
    @Override
    public void createObject(Context ctx, String file) {
        try {
            String fileName = file + ".txt";
            //Will create file if non-existent. Otherwise will open it.
            fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //If stream is open, close it (prevent leaks).
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public LinkedList<String> readObject(String file) {
        //Check if current data exists, if so then proceed.

        LinkedList<String> dataFromFile = new LinkedList<>();
        //Opens file, parses data from file and inserts into linked list.

        return dataFromFile;
    }

    @Override
    public void updateObject(String file, LinkedList data) {
        //If no object exists yet, create one first then update.

        //Opens file, deletes content, calls readObject.

    }

    @Override
    public void deleteObject(String file) {
        //Opens file, deletes content. Note: Does not delete file itself.

    }
}
