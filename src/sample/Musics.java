package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Musics {
    public static MediaPlayer enemy_die;
    public static MediaPlayer game;
    public static MediaPlayer bombang;
    public static MediaPlayer bomber_die;
    public static MediaPlayer win;
    public static MediaPlayer new_bom;
    public static MediaPlayer pickItem;

    private static MediaPlayer createMusic(String url) {
        MediaPlayer mediaPlayer = new MediaPlayer(
                new Media(Musics.class.getResource(url).toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        return mediaPlayer;
    }

    private static MediaPlayer createMusicOne(String url) {
        return new MediaPlayer(new Media(Musics.class.getResource(url).toString()));
    }

    public static void initMusics() {
        Musics.enemy_die = createMusicOne("/res/music/enemy_die.wav");
        Musics.bombang = createMusicOne("/res/music/bomb_bang.wav");
        Musics.bomber_die = createMusicOne("/res/music/bomber_die.wav");
        Musics.new_bom = createMusicOne("/res/music/putBomb.wav");
        Musics.win = createMusicOne("/res/music/win.wav");
        Musics.pickItem = createMusicOne("/res/music/pickItem.wav");
        Musics.game = createMusic("/res/music/stage.wav");
    }



}
