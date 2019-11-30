package com.zombie.menu.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zombie.menu.Models.ApplicationModel;
import com.zombie.menu.R;
import com.zombie.menu.Views.Load;
import com.zombie.menu.Views.MainMenu;
import com.zombie.menu.Views.SaveState;
import com.zombie.menu.Views.Settings;

public class MainMenuController extends AppCompatActivity {

    private Load load= new Load();
    private MainMenu mainMenu = new MainMenu();
    private SaveState saveState = new SaveState();
    private Settings settings = new Settings();
    private ApplicationModel appModel = new ApplicationModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        //main menu buttons
       // appModel.transition(mainMenu.getApplicationContext(), load.getClass(), mainMenu.startGame);

    }
}
