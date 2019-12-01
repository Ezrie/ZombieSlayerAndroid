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

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import com.zombie.menu.R;


public class Settings extends AppCompatActivity {

    private Button difficulty;
    private Button bColor;
    private Button pColor;
    private Window window = new Window();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        window.hideSystem(this);
    }
}
