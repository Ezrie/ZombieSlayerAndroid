package com.example.zombie_slayer_android.Database;

import android.content.Context;

import java.util.LinkedList;

public class DBTranslator {

    private static final DBConnectorInterface connector = new BinaryConnector();

    //CRUD operations
    public static void createObject(Context ctx, String source){
        connector.createObject(ctx, source);
    }

    public static LinkedList<String> readObject(String file){
        return connector.readObject(file);
    }

    public static void updateObject(String file, LinkedList data){
        connector.updateObject(file, data);
    }

    public static void deleteObject(String file){
        connector.deleteObject(file);
    }
}
