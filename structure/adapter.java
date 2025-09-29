package structure;

//Target interface
interface MediaPlayer {
 void play(String type, String file);
}

//Adaptee
class AdvancedMediaPlayer {
 public void playMp4(String file) { System.out.println("Playing mp4: " + file); }
 public void playVlc(String file) { System.out.println("Playing vlc: " + file); }
}

//Adapter
class MediaAdapter implements MediaPlayer {
 private AdvancedMediaPlayer advanced = new AdvancedMediaPlayer();
 public void play(String type, String file) {
     if(type.equalsIgnoreCase("mp4")) advanced.playMp4(file);
     else if(type.equalsIgnoreCase("vlc")) advanced.playVlc(file);
     else System.out.println("Format not supported");
 }
}

//Client
class AudioPlayer implements MediaPlayer {
 private MediaAdapter adapter = new MediaAdapter();
 public void play(String type, String file) { adapter.play(type, file); }
}

//Demo
public class adapter {
 public static void main(String[] args) {
     AudioPlayer player = new AudioPlayer();
     player.play("mp4", "video.mp4");
     player.play("vlc", "movie.vlc");
     player.play("mp3", "song.mp3");
 }
}
