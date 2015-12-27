import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
import java.io.Serializable;

public class AudioPlayer implements Serializable 
{

  AudioFormat audioFormat;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;
  boolean stopPlayback = false;
  
  //-------------------------------------------//

  public AudioPlayer(){//constructor

   
  }//end constructor
  //-------------------------------------------//

  public void setStopPlayback(boolean newPlayback)
  {
    stopPlayback = newPlayback;
  }

  //This method plays back audio data from an
  // audio file whose name is specified in the
  // text field.
public void playAudio(String soundName) {
    try{
      File soundFile =
                   new File(soundName);
      audioInputStream = AudioSystem.
                  getAudioInputStream(getClass().getResource(soundName));
      audioFormat = audioInputStream.getFormat();
      //System.out.println(audioFormat);

      DataLine.Info dataLineInfo =
                          new DataLine.Info(
                            SourceDataLine.class,
                                    audioFormat);

      sourceDataLine =
             (SourceDataLine)AudioSystem.getLine(
                                   dataLineInfo);

      //Create a thread to play back the data and
      // start it running.  It will run until the
      // end of file, or the Stop button is
      // clicked, whichever occurs first.
      // Because of the data buffers involved,
      // there will normally be a delay between
      // the click on the Stop button and the
      // actual termination of playback.
      new PlayThread().start();
    }catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }//end catch
  }//end playAudio


//=============================================//
//Inner class to play back the data from the
// audio file.
class PlayThread extends Thread{
  byte tempBuffer[] = new byte[100000];

  public void run(){
    try{
      sourceDataLine.open(audioFormat);
      sourceDataLine.start();

      int cnt;
      //Keep looping until the input read method
      // returns -1 for empty stream or the
      // user clicks the Stop button causing
      // stopPlayback to switch from false to
      // true.
      while((cnt = audioInputStream.read(
           tempBuffer,0,tempBuffer.length)) != -1
                       && stopPlayback == false){
        if(cnt > 0){
          //Write data to the internal buffer of
          // the data line where it will be
          // delivered to the speaker.
          sourceDataLine.write(
                             tempBuffer, 0, cnt);
        }//end if
      }//end while
      //Block and wait for internal buffer of the
      // data line to empty.
      sourceDataLine.drain();
      sourceDataLine.close();

      //Prepare to playback another file
      
      stopPlayback = false;
    }catch (Exception e) {
      e.printStackTrace();
    }//end catch
  }//end run
}//end inner class PlayThread
//===================================//

}//end outer class AudioPlayer02.java