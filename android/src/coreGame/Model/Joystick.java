package coreGame.Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import coreGame.View.Scenes.HUD;

public class Joystick {

    private Touchpad joystick;
    private Touchpad.TouchpadStyle joystickStyle;
    private Skin joystickSkin;
    private Drawable joystickBackground;
    private Drawable joystickKnob;

    public Joystick(int _radius, int _x, int _y, int _width, int _height) {
        //Joystick movement controls.
        joystickSkin = new Skin();
        joystickSkin.add("touchBackground", new Texture("touchpad.png"));
        joystickSkin.add("touchKnob", new Texture("touchpad-knob.png"));
        joystickSkin.getDrawable("touchKnob").setMinHeight(joystickSkin.getDrawable("touchKnob").getMinHeight() / 2);
        joystickSkin.getDrawable("touchKnob").setMinWidth(joystickSkin.getDrawable("touchKnob").getMinWidth() / 2);
        joystickStyle = new Touchpad.TouchpadStyle();
        joystickBackground = joystickSkin.getDrawable("touchBackground");
        joystickKnob = joystickSkin.getDrawable("touchKnob");
        joystickStyle.background = joystickBackground;
        joystickStyle.knob = joystickKnob;

        joystick = new Touchpad(_radius, joystickStyle);
        joystick.setBounds(_x, _y, _width, _height);
    }

    public Touchpad getJoystick() {
        return this.joystick;
    }

    public Vector2 handleJoystickInput(HUD _hud) {
        if (_hud.joystick.joystick.getKnobPercentX() == 0 && _hud.joystick.joystick.getKnobPercentY() == 0) {
            return new Vector2(0, 0);
        } else {
            return new Vector2(_hud.joystick.joystick.getKnobPercentX(), _hud.joystick.joystick.getKnobPercentY());
        }
    }
}
