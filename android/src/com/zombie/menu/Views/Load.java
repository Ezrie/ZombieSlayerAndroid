package com.zombie.menu.Views;
/**
 * This class is used as a transition from pressing Start -> Game.
 *
 * @author Ezrie Brant
 * @author David Chan
 * @author Francis Ynoa
 * Last Updated: 10/02/2019
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zombie.menu.Controller.GameLauncher;
import com.zombie.menu.R;

import java.util.Timer;
import java.util.TimerTask;

public class Load extends AppCompatActivity {
    private int timeWait = 1000; //This number is used for milliseconds.
    private Window window = new Window();
    private ProgressBar progressBar;
    private TextView textView;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        //nextActivity();

        window.hideSystem(this);

        progress();
    }

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
