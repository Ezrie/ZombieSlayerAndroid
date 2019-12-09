package com.example.zombie_slayer_android.View;
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
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.zombie_slayer_android.R;

import java.util.Locale;

public class Settings extends AppCompatActivity {

    private ToggleButton tggleMusic;
    private ToggleButton btnSoundEff;
    private Button btnLanguage;
    private FullScreen fullScreen = new FullScreen();
    private static boolean musicChecked = false;
    private Spinner language;
    private Locale myLocale;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                if (pos == 1) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Tamil", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("ta");
                } else if (pos == 2) {

                    Toast.makeText(parent.getContext(),
                            "You have selected Hindi", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("hi");
                } else if (pos == 3) {

                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    setLocale("en");
                }

            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });


        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }

    private void initialize() {
        this.tggleMusic = findViewById(R.id.toggleMusic);
        if(musicChecked){
            tggleMusic.setChecked(true);
        }

        language = (Spinner) findViewById(R.id.spinnerLanguage);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Languages, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        language.setAdapter(adapter);

    }

    public void setLocale(String lang) {

        myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang.toLowerCase()));
        res.updateConfiguration(conf, dm);
        Intent refresh = new Intent(this, Settings.class);
        finish();
        startActivity(refresh);
    }

    public void buttonClicked(View _view) {
        switch (_view.getId()) {
            case R.id.toggleMusic:

                //Button is ON
                if(tggleMusic.isChecked()){
                    Music.player.pause();
                    musicChecked = true;
                }
                else {
                    Music.player.start();
                    musicChecked = false;
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
