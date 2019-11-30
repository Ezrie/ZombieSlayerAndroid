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
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zombie.menu.Controller.GameController;
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
                counter++;
                textView.setText("%" + counter);
                progressBar.setProgress(counter);
                if(counter == 100){
                    t.cancel();
                    Intent load = new Intent(getApplicationContext(), GameController.class);
                    startActivity(load);
                }
            }
        };
        t.schedule(tt,0,100);


    }

    /**
     * This method adds a wait time before the load screen transitions to the game.
     */
    public void nextActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent = new Intent(Load.this, GameController.class);
                Load.this.startActivity(mainIntent);
                Load.this.finish();
            }
        }, timeWait);
    }
}
