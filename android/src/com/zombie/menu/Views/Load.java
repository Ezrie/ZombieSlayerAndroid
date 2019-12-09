package com.zombie.menu.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zombie.menu.Controller.GameLauncher;
import com.zombie.menu.R;

import java.util.Timer;
import java.util.TimerTask;

public class Load extends AppCompatActivity {
    private FullScreen fullScreen = new FullScreen();
    private ProgressBar progressBar;
    private TextView textView;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_load);

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);

        progress();
    }

    /**
     * Creates a timed loop. With each second passed, the proggressBar and Textview are
     * updated to reflect that.
     */
    private void progress() {
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.progressText);
        final Timer t = new Timer();

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (counter < 100) {
                    counter++;
                    textView.setText("" + counter + "%");
                    progressBar.setProgress(counter);
                }
                if (counter == 100) {
                    t.cancel();
                    Intent launch = new Intent(getApplicationContext(), GameLauncher.class);
                    startActivity(launch);
                }
            }
        };
        t.schedule(tt, 0, 50);
    }
}

