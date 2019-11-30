package com.zombie.game;
/**
 * This class will hold a save state of the player's progress in the game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/28/2019
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zombie.game.R;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;

import Database.DBTranslator;

import static com.zombie.game.R.layout.activity_save_state;

public class SaveState extends Activity {

    //Linked list that will hold current game's data to save or load a game state given this list.
    LinkedList<String> saveObjects = new LinkedList<String>();

    //Create the context needed for opening/editing files.
    Context ctx = this;

    //Names of the save files.
    final String FILE_NAME_1 = "save1";
    final String FILE_NAME_2 = "save2";
    final String FILE_NAME_3 = "save3";

    //Enumeration of button types for QOL.
    enum buttonType {
        LOAD, SAVE, DELETE;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Sets the related XML document on the screen.
        super.onCreate(savedInstanceState);
        setContentView(activity_save_state);

        //Buttons that write to the save files.
        final Button SAVE_FILE_1 = findViewById(R.id.button_save1);
        final Button SAVE_FILE_2 = findViewById(R.id.button_save2);
        final Button SAVE_FILE_3 = findViewById(R.id.button_save3);

        //Buttons that read to the save files.
        final Button LOAD_FILE_1 = findViewById(R.id.button_load1);
        final Button LOAD_FILE_2 = findViewById(R.id.button_load2);
        final Button LOAD_FILE_3 = findViewById(R.id.button_load3);

        //Buttons that clear the save files.
        final Button DELETE_FILE_1 = findViewById(R.id.button_delete1);
        final Button DELETE_FILE_2 = findViewById(R.id.button_delete2);
        final Button DELETE_FILE_3 = findViewById(R.id.button_delete3);

        //Create text files to save/load from.
        DBTranslator.createObject(ctx, FILE_NAME_1);
        DBTranslator.createObject(ctx, FILE_NAME_2);
        DBTranslator.createObject(ctx, FILE_NAME_3);

        //Set up the save buttons.
        createButton(SAVE_FILE_1, buttonType.SAVE);
        createButton(SAVE_FILE_2, buttonType.SAVE);
        createButton(SAVE_FILE_3, buttonType.SAVE);

        //Set up the load buttons.
        createButton(LOAD_FILE_1, buttonType.LOAD);
        createButton(LOAD_FILE_2, buttonType.LOAD);
        createButton(LOAD_FILE_3, buttonType.LOAD);

        //Set up the delete buttons.
        createButton(DELETE_FILE_1, buttonType.DELETE);
        createButton(DELETE_FILE_2, buttonType.DELETE);
        createButton(DELETE_FILE_3, buttonType.DELETE);
    }

    private void createButton(final Button _button, final buttonType _type) {

        //Create a button object and click response.
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set the button's function depending on given button type.
                if (_type.equals(buttonType.SAVE)) {
                    save(saveObjects, _button);
                } else if (_type.equals(buttonType.LOAD)){
                    load(_button);
                } else if (_type.equals(buttonType.DELETE)) {
                    delete(_button);
                }
            }
        });
    }

    //Given a button, return the file associated with it.
    private String getFileName(Button _button){

        try {
            if (_button.getTag().toString().contains("1")) {
                return FILE_NAME_1;
            } else
            if (_button.getTag().toString().contains("2")) {
                return FILE_NAME_2;
            } else
            if (_button.getTag().toString().contains("3")) {
                return FILE_NAME_3;
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Button does not correspond to a save file.");
        }
        return null;
    }

    //Method that saves the current game state to a file.
    public void save(LinkedList<String> _savedObjects, Button _button) {
        //Put objects gotten from current game state in a linked list.
        String fileName = getFileName(_button);

        //Dummy data
        _savedObjects.add("Test");
        _savedObjects.add("Data:340");

        //Pass all to translator.
        DBTranslator.updateObject(ctx, fileName, _savedObjects);

        //Clear the linked list after use.
        _savedObjects.clear();
    }

    //Method that overrides current game state with given save file.
    public void load(Button _button) {
        //Get information from button on which save slot is being loaded.
        String fileName = getFileName(_button);

        //Pass all to translator.
        saveObjects = DBTranslator.readObject(ctx, fileName);

        parser(saveObjects);
    }

    public void delete(Button _button) {
        String fileName = getFileName(_button);
        DBTranslator.deleteObject(ctx, fileName);
    }

    protected void parser(LinkedList _data){
        //Given linked list, set the current game's values from data.

        //Parses the dummy data
        for (Object dataObj : _data) {
            String element = dataObj.toString();

            if (element.contains("Test")) {
                //Save to current game.
                System.out.println(element);

            } else if (element.contains("Data")) {
                int index = element.indexOf(':');
                String dataElement = element.substring(index + 1);
                //Save to current game.
                System.out.println(dataElement);
            }
        }
    }
}