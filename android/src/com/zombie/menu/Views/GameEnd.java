package com.zombie.menu.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zombie.menu.Controller.GameLauncher;
import com.zombie.menu.R;

public class GameEnd extends AppCompatActivity {

    private TextView endText;
    private boolean textType;
    private FullScreen fullScreen = new FullScreen();

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_game_end);

        endText = findViewById(R.id.endText);
        textType = getIntent().getExtras().getBoolean("isWin");
        if (textType) {
            //It's a win.
            endText.setText("You Win!");
        } else {
            //It's a lose.
            endText.setText("You Died");
        }

        this.fullScreen.hideSystem(this);
        this.fullScreen.checkSystem(this);
    }


    public void clicked(View _view) {
        switch (_view.getId()) {
            case R.id.btnReplay:
                Intent toGameLauncher = new Intent(getApplicationContext(), Load.class);
                toGameLauncher.putExtra("repeatLevel", true);
                startActivity(toGameLauncher);
                finish();
                break;
            case R.id.btnBack:
                Intent toMainMenu = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(toMainMenu);
                finish();
                break;
        }
    }
}
