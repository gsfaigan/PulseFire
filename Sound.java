import java.io.*;
import javax.sound.sampled.*;
/**
 * making a bunch of sound file methods that can be run to play sound effects
 * 
 * @Gabriel Faigan
 * @Dec 2024
 */
public class Sound{
    private static Clip loopClip; // clip is an audio clip
    // only accesible within this class, and universally shared between
    // instances so the background music can be started and stopped from
    // different methods
    /*
     * synchronized means only one thread can excecute this method at
     * a time
     * used for thread safety so multiple threads cannot play at
     * the same time
     */ 
    public static synchronized void playSound(final String url){
        
        // thread can be run concurrently so main program doesn't stop
        // to play the sound effect
        
        // runnable is an interface that has a task that can be excecuted
        // by a thread
        
        // run is the method excecuted by the thread
        new Thread(new Runnable(){
            public void run(){
                try{
                    Clip clip = AudioSystem.getClip(); // hold and play the audio
                    
                    // class for reading byte data
                    // getResourceAsStream loads the audio file as a stream from
                    // the classpath
                    InputStream audioSrc = Sound.class.getResourceAsStream(url);
                    
                    // bufferedinputstream improves performance when reading
                    // larger audio files
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    
                    // represents data that is read by inputstream
                    // converts audio files to format useable by clip objects
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                    
                    clip.open(audioStream);
                    clip.start();
                }catch(Exception e){}
            }
        }).start(); // starts the thread which invokes the run method to play the sound in a separate thread
    }
    
    public static synchronized void playLoopSound(final String url){
        new Thread(new Runnable(){
            public void run(){
                try{
                    loopClip = AudioSystem.getClip();
                    
                    InputStream audioSrc = Sound.class.getResourceAsStream(url);
                    
                    InputStream bufferedIn = new BufferedInputStream(audioSrc);
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                    
                    loopClip.open(audioStream);
                    loopClip.loop(Clip.LOOP_CONTINUOUSLY);
                    loopClip.start();
                }catch(Exception e){}
            }
        }).start();
    }
    
    // stops the background music from looping and turns it off
    public static synchronized void stopLoopSound() {
        if (loopClip != null && loopClip.isRunning()) {
            loopClip.stop();
            loopClip.close();
        }
    }
    
    // to show how it works
    public static void main(String[] args){
        playSound("WIN.wav");
    }
}
