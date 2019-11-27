
package Database;

import android.content.Context;

import java.util.LinkedList;

public interface DBConnectorInterface {

    //Create a new, blank object from a source (file name, SQL object name, etc.)
    void createObject(Context ctx, String source);

    //Read an object given a source. Returns a LL of data associated with object.
    LinkedList<String> readObject(Context ctx, String source);

    //Given a source and LL of object data, update the object in the DB.
    void updateObject(Context ctx, String source, LinkedList data);

    //Given a source, delete the object (empty file, mark as deleted on SQL, etc.)
    void deleteObject(Context ctx, String source);
}