package com.zombie.menu.Views;
/**
 * This class will hold the state of a player's progress in the game by saving to and from the database.
 *
 * @author Ezrie Brant
 * @author Francis Ynoa
 * Last Updated: 11/24/2019
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zombie.menu.R;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import Database.DBTranslator;

public class SaveState extends Activity {

    //Linked list that will hold current game's data to save or load a game state given this list.
    //Used by save and load methods.
    LinkedList<String> saveObjects = new LinkedList<>();

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

    /**
     * Sets the screen to the related XML, creates the save files, and initializes the button functions.
     *
     * @param _savedInstanceState
     */
    @Override
    protected void onCreate(Bundle _savedInstanceState) {

        //Sets the related XML document on the screen.
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_save_state);

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

    /**
     * Given a button and button type, apply the button's function when it's clicked.
     *
     * @param _button
     * @param _type
     */
    private void createButton(final Button _button, final buttonType _type) {

        //Create a button click response.
        _button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Set the button's function depending on given button type.
                if (_type.equals(buttonType.SAVE)) {
                    save(saveObjects, _button);
                } else if (_type.equals(buttonType.LOAD)) {
                    load(_button);
                } else if (_type.equals(buttonType.DELETE)) {
                    delete(_button);
                }
            }
        });
    }

    /**
     * Given a button, return the file associated with it.
     * NOTE: Searches for the file number in reverse order so, for example, 11 is found before 1.
     *
     * @param _button
     * @return FILE_NAME_#
     */
    private String getFileName(Button _button) {

        try {
            if (_button.getTag().toString().contains("3")) {
                return FILE_NAME_3;
            } else
            if (_button.getTag().toString().contains("2")) {
                return FILE_NAME_2;
            } else
            if (_button.getTag().toString().contains("1")) {
                return FILE_NAME_1;
            } else {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Button does not correspond to a save file.");
        }
        return null;
    }

    /**
     * Method that saves the current game state to a file.
     * Currently hold dummy data.
     *
     * @param _savedObjects
     * @param _button
     */
    public void save(LinkedList<String> _savedObjects, Button _button) {
        //Put objects gotten from current game state in a linked list.
        String fileName = getFileName(_button);

        _savedObjects.add("test");
        _savedObjects.add("");

        //Pass all to translator.
        DBTranslator.updateObject(ctx, fileName, _savedObjects);

        //Clear the linked list after use.
        _savedObjects.clear();
    }

    /**
     * Method that overrides current game state with given save file.
     *
     * @param _button
     */
    public void load(Button _button) {
        //Get information from button on which save slot is being loaded.
        String fileName = getFileName(_button);

        //Pass all to translator.
        saveObjects = DBTranslator.readObject(ctx, fileName);
    }

    /**
     * Clears the file associated with the button pressed.
     *
     * @param _button
     */
    public void delete(Button _button) {
        String fileName = getFileName(_button);
        DBTranslator.deleteObject(ctx, fileName);
    }
}