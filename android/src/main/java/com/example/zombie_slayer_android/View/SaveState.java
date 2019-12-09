package com.example.zombie_slayer_android.View;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_slayer_android.Database.DBTranslator;
import com.example.zombie_slayer_android.R;

import java.util.Iterator;
import java.util.LinkedList;

public class SaveState extends AppCompatActivity {

    //Names of the save files.
    private final String FILE_SAVE_1 = "save1";
    private final String FILE_SAVE_2 = "save2";
    private final String FILE_SAVE_3 = "save3";

    //Create the context needed for opening/editing files.
    private final Context ctx = this;

    //Linked list that will hold current game's data to save or load a game state given this list.
    public LinkedList<String> saveObjects = new LinkedList<>();

    public String defaultFile = "1";
    private Button loadFile1;
    private Button loadFile2;
    private Button loadFile3;
    private Button saveFile1;
    private Button saveFile2;
    private Button saveFile3;
    private FullScreen fullScreen = new FullScreen();


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_save_state);

        initialize();

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }

    private void initialize() {
        loadFile1 = findViewById(R.id.button_load1);
        loadFile2 = findViewById(R.id.button_load2);
        loadFile3 = findViewById(R.id.button_load3);
        saveFile1 = findViewById(R.id.button_save1);
        saveFile2 = findViewById(R.id.button_save2);
        saveFile3 = findViewById(R.id.button_save3);

    }

    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.button_save1:
                save(saveObjects, loadFile1);
                break;
            case R.id.button_save2:
                save(saveObjects, loadFile2);
                break;
            case R.id.button_save3:
                save(saveObjects, loadFile3);
                break;
            case R.id.button_load1:
                DBTranslator.createObject(ctx, FILE_SAVE_1);
                load(loadFile1);
                break;
            case R.id.button_load2:
                DBTranslator.createObject(ctx, FILE_SAVE_2);
                load(loadFile2);
                break;
            case R.id.button_load3:
                DBTranslator.createObject(ctx, FILE_SAVE_3);
                load(loadFile3);
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }

    //Method that saves the current game state to a file.
    public void save(LinkedList savedObjects, Button saveFile) {
        //Put objects gotten from current game state in a linked list.
        String buttonFile = Integer.toString(saveFile.getId());

        //Dummy data
        saveObjects.add("Test");
        saveObjects.add("Data:340");

        //Pass all to translator.
        DBTranslator.updateObject(buttonFile, savedObjects);
    }

    //Method that overrides current game state with given save file.
    public void load(Button loadFile) {
        //Get information from button on which save slot is being loaded.
        String buttonFile = Integer.toString(loadFile.getId());

        //Pass all to translator.
        LinkedList<String> loadedData = DBTranslator.readObject(buttonFile);

        parser(loadedData);
    }

    protected void parser(LinkedList data) {
        //Given linked list, set the current game's values from data.
        Iterator dataIterator = data.iterator();

        //Parses the dummy data
        while (dataIterator.hasNext()) {
            String element = data.element().toString();
            if (element.contains("Test")) {
                //Save to current game.
                System.out.println(element);

            } else if (element.contains("Data")) {
                int index = element.indexOf(':');
                String dataElement = element.substring(index + 1);
                //Save to current game.
                System.out.println(dataElement);
            } else {
            }
            dataIterator.next();
        }
    }


}
