package com.zombie.menu.Views;
/**
 * This class will hold different options that the user can interact with to change some
 * attributes of the game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.zombie.menu.R;

public class Settings extends AppCompatActivity {

    private ToggleButton tggleMusic;
    private ToggleButton btnSoundEff;
    private Button btnLanguage;
    private Window window = new Window();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();

        this.window.hideSystem(this);
    }

    private void initialize() {
        this.tggleMusic = findViewById(R.id.toggleMusic);
    }

    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.toggleMusic:

                //Button is ON
                if(tggleMusic.isChecked()){
                    Music.player.pause();
                }
                else {
                    Music.player.start();
                }
                break;
            case R.id.btnSoundEffect:
                break;
            case R.id.btnBack:
                finish();
                break;
        }
    }
}