package Database;

import android.content.Context;

import java.util.LinkedList;

public class DBTranslator {

    private static final DBConnectorInterface connector = new BinaryConnector();

    //CRUD operations
    public static void createObject(Context ctx, String source){
        connector.createObject(ctx, source);
    }

    public static LinkedList<String> readObject(Context ctx, String file){
        return connector.readObject(ctx, file);
    }

    public static void updateObject(Context ctx, String file, LinkedList data){
        connector.updateObject(ctx, file, data);
    }

    public static void deleteObject(Context ctx, String file){
        connector.deleteObject(ctx, file);
    }
}