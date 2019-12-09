package com.zombie.menu.Views;
/**
 * This class is the main menu of the game. There exists various buttons on it and
 * when one of the buttons is clicked, it will clicked what the user sees on
 * the screen.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 11/19/2019
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.zombie.menu.R;


public class MainMenu extends AppCompatActivity {

    private Button btnStartGame;
    private Button btnLoad;
    private Button btnSettings;
    private Window window = new Window();
    private GoogleSignInAPI googleSignIn;


    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);

        //How the class will look
        setContentView(R.layout.activity_main_menu);

        //Initialize all the button fields of this class.
        initialize();

        //Sets the Google sign in button to launch the signin method whenever it is clicked.
        googleSignIn.signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignIn.signIn();
            }
        });

        //Plays Music throughout the application.
        Music.soundPlayer(this,R.raw.zombi);

        this.window.hideSystem(this);
    }

    /*
    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer.start();
    }*/

    /**
     * Initialize the class fields.
     */
    private void initialize() {
        this.btnStartGame = findViewById(R.id.btnStartGame);
        this.btnLoad = findViewById(R.id.btnLoad);
        this.btnSettings = findViewById(R.id.btnSettings);
        this.googleSignIn = new GoogleSignInAPI(this);
    }

    /**
     * When one of the button is clicked, a switch statement then decides what button
     * was clicked and then executes an appropriate method for it.
     *
     * @param _view Object that the user sees, like a button on screen
     */
    public void clicked(View _view) {
        switch (_view.getId()) {
            case R.id.btnStartGame:
                Intent toLoad = new Intent(getApplicationContext(), Load.class);
                startActivity(toLoad);
                break;
            case R.id.btnLoad:
                Intent toSaveState = new Intent(getApplicationContext(), SaveState.class);
                startActivity(toSaveState);
                break;
            case R.id.btnSettings:
                Intent toSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(toSettings);
                break;
        }
    }
}