package com.example.zombie_slayer_android.View;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


import com.example.zombie_slayer_android.R;

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
        this.progressBar = findViewById(R.id.progressbar);
        this.textView = findViewById(R.id.progressText);
        final Timer timer = new Timer();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                counter++;
                textView.setText("%" + counter);
                progressBar.setProgress(counter);
                if(counter == 100){
                    timer.cancel();
                    Intent load = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(load);
                }
            }
        };
        timer.schedule(timerTask,0,100);


    }
}
