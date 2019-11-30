package Database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import android.content.Context;

import coreGame.View.Screens.PlayScreen;


public class BinaryConnector implements DBConnectorInterface {

    private FileOutputStream fos = null;

    //Create an empty save file, the default constructor that's called when first starting the app.
    //Uses FileOutputStream
    @Override
    public void createObject(Context ctx, String file) {
        try {
            String fileName = file + ".txt";
            //Will create file if non-existent. Otherwise will open it.
            fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //If stream is open, close it (prevent memory leaks).
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
    public LinkedList<String> readObject(Context ctx, String file) {
        String fileName = file + ".txt";

        //Opens file, parses data from file and inserts into linked list.
        LinkedList<String> dataFromFile = new LinkedList<>();

        //FileInputStream is used to write to the file.
        FileInputStream fis = null;

        try {
            fis = ctx.openFileInput(fileName);
            //Check if current data exists, if not then don't continue.
            File fileObj = new File(fileName);
            if(!fileObj.exists()){
                return dataFromFile;
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            System.out.println("" + sb);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataFromFile;
    }

    @Override
    public void updateObject(Context ctx, String file, LinkedList data) {
        String fileName = file + ".txt";

        try {
            //Will delete content if there's any. Otherwise will open a empty file.
            fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

            //Write to file
            String dataStr = data.toString();
            String elem = data.element().toString();
            fos.write(dataStr.getBytes());
            fos.write(elem.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //If stream is open, close it (prevent memory leaks).
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
    public void deleteObject(Context ctx, String file) {
        //Opens file, deletes content. Note: Does not delete file itself.
        String fileName = file + ".txt";

        try {
            //Will delete content if there's any.
            fos = ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}