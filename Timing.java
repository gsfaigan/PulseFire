/**
 * timing for in game timer
 * 
 * @Gabriel Faigan
 * @Dec 2024
 */
public class Timing{
    private int seconds = 0;
    private int minutes = 0;
    private int centiseconds = 0;
    private boolean running = false;
    private Thread timerThread; // Thread for managing time updates
    public void start(){
        if(!running){ // so that every time a level advances a new thread doesn't add to the timer, making it speed up
                      // prevents starting multiple threads
            running = true;
            
            // i used a lambda expression here, to clean up the code
            // it's an anonymous function (code block without a name)
            // that can still be passed around and excecuted
            // syntax: (parameters) -> {code}
            timerThread = new Thread(() ->{
                    while (running) {
                        try {
                            Thread.sleep(10);  // wait for 10 milliseconds
                           synchronized (this){ // prevent concurrent time modifications
                                centiseconds++;
                                if (centiseconds == 100){
                                    centiseconds = 0;
                                    seconds++;
                                }
                                if (seconds == 60){
                                    seconds = 0;
                                    minutes++;
                                }
                            }
                        } catch (Exception e) {}
                    }
                });
            timerThread.start(); // start the thread
        }
    }

    public void stop(){
        if(running){
            running = false;
            try{ // what does this even do?
                timerThread.join(); // wait for the thread to finish excecution
            }catch(Exception e){}
        }
    }

    public String getTime() {
        //MM:SS:CC
        synchronized(this){
            return String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
        }
    }
    
    // to show how it works
    public static void main(String[] args) {
        Timing timer = new Timing();
        timer.start();
        while(true){
            System.out.println("Time elapsed: " + timer.getTime());
        }
    }
}