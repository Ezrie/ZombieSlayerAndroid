package com.zombie.game;
/**
 * This class is the main menu of the game. There exists three buttons on it and
 * when one of the buttons is clicked, transition to another class.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zombie.game.R;

public class MainMenu extends Activity {

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        //Creates a Button and when clicked transition to the Load class.
        Button startGame = findViewById(R.id.startGame);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLoad = new Intent(getApplicationContext(), com.zombie.game.Load.class);
                startActivity(toLoad);
            }
        });

        //Creates a Button and when clicked transition to the Save State class.
        Button load = findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSaveState = new Intent(getApplicationContext(), com.zombie.game.SaveState.class);
                startActivity(toSaveState);
            }
        });

        //Creates a Button and when clicked transition to the Settings class.
        Button settings = findViewById(R.id.settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toSettings = new Intent(getApplicationContext(), Settings.class);
                startActivity(toSettings);
            }
        });


        MediaPlayer mediaPlayer= MediaPlayer.create(getApplicationContext(),R.raw.fornow);
        mediaPlayer.start();
    }

    @Override
    public void onWindowFocusChanged(boolean _hasFocus) {
        super.onWindowFocusChanged(_hasFocus);
        if (_hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
