package coreGame.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class ActionButton {

    public Button button;
    private Button.ButtonStyle buttonStyle;
    private Skin buttonSkin;
    private Drawable buttonReleased;
    private Drawable buttonPressed;

    public ActionButton(int _xPos, int _yPos, int _width, int _height) {
        //ActionButton for firing projectiles.
        buttonSkin = new Skin();
        buttonSkin.add("buttonReleased", new Texture("button-arcade.png"));
        buttonSkin.add("buttonPressed", new Texture("button-arcade-pressed.png"));
        buttonSkin.getDrawable("buttonReleased").setMinHeight(buttonSkin.getDrawable("buttonReleased").getMinHeight() / 2);
        buttonSkin.getDrawable("buttonReleased").setMinWidth(buttonSkin.getDrawable("buttonReleased").getMinWidth() / 2);
        buttonSkin.getDrawable("buttonPressed").setMinHeight(buttonSkin.getDrawable("buttonPressed").getMinHeight() / 2);
        buttonSkin.getDrawable("buttonPressed").setMinWidth(buttonSkin.getDrawable("buttonPressed").getMinWidth() / 2);
        buttonStyle = new com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle();
        buttonReleased = buttonSkin.getDrawable("buttonReleased");
        buttonPressed = buttonSkin.getDrawable("buttonPressed");
        buttonStyle.up = buttonReleased;
        buttonStyle.down = buttonPressed;

        button = new Button(buttonReleased, buttonPressed);
        button.setBounds(_xPos, _yPos, _width, _height);
    }

    public boolean isButtonPressed() {
        return button.isPressed();
    }

    public Button getButton() {
        return this.button;
    }
}
