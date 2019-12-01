package com.zombie.menu.Controller;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.zombie.menu.Models.GameModel;
import com.zombie.menu.Views.Window;

public class GameController extends AppCompatActivity {

    Context ctx = this;

    private Window windows = new Window();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameModel(ctx) );

        windows.hideSystem(this);
    }


}
