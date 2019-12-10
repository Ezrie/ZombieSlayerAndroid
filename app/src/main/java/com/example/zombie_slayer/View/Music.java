package com.example.zombie_slayer.View;


import android.content.Context;
import android.media.MediaPlayer;

public class Music{
    public static MediaPlayer player;
    private static int leftVolume = 100;
    private static int rightVolume = 100;

    public static void soundPlayer(Context ctx, int raw_id) {
        player = MediaPlayer.create(ctx, raw_id);
        player.setLooping(true); // Set looping
        player.setVolume(leftVolume, rightVolume);

        player.start();
    }
}
