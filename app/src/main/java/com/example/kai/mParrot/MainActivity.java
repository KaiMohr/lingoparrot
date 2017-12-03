package com.example.kai.mParrot;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.media.AudioManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.net.Uri;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Environment;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private AudioManager mAudioManager;
    private boolean mPhoneIsSilent;
    private boolean onHomeScreen;
    private static final String TAG = "Parrot";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d("MyApp", "No SDCARD");
        }
        else {
            File directory = new File(Environment.getExternalStorageDirectory()+File.separator+"parrot-sounds");
            directory.mkdirs();
            Log.d("MyApp", "created parrot-sounds folder");
        }
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        onHomeScreen = true;
        // dismiss the keypad lock
        //Get the window from the context
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        // checkIfPhoneIsSilent();
        setButtonClickListenerToggleOnOff();
        setButtonClickListenerAddSound();

        // Log.v("is external writable:  ", isExternalStorageWritable() + " ");
        // play buip sound
        // MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.buip);
        // mediaPlayer.start();
        mPhoneIsSilent = false;
        Toast.makeText(MainActivity.this, "timer started", Toast.LENGTH_LONG).show();
        if (onHomeScreen & mPhoneIsSilent) {
            // do nothing
            Log.i("home screen", onHomeScreen + "");
            Log.i("phone silent", mPhoneIsSilent + "  ");
            // Log.i("timer", "cancel");
        } else {
            Log.i("home screen", onHomeScreen + "");
            Log.i("phone silent", mPhoneIsSilent + "  ");
            Log.i("timer", "is running");
            // create a timer
            MyTimerTask myTask = new MyTimerTask();
            Timer myTimer = new Timer();
            myTimer.schedule(myTask, 6000, 30000);
            // Parameters
            // task  the task to schedule.
            // delay  amount of time in milliseconds before first execution.
            // period  amount of time in milliseconds between subsequent executions.

            Log.i("home screen", onHomeScreen + "");
            Log.i("phone silent", mPhoneIsSilent + "  ");
            Log.i("timer", "created");
        }

    }
  // lets define a timer
  class MyTimerTask extends TimerTask {
            public void run() {

                if (mPhoneIsSilent) {
                    // do nothing
                    Log.i("the timer", "is not running");
                    Log.i("home screen", onHomeScreen + "is not started first time");
                    Log.i("phone silent", mPhoneIsSilent + "  ");
                    Log.i("timer", "cancel");
                    this.cancel();


                } else {
                    playRandomFile();
                    Log.i("timer", "is running");
                }



            }
        }
    private void setButtonClickListenerAddSound() {
        ImageButton addSoundButton = (ImageButton) findViewById(R.id.addSoundButton);
        addSoundButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mPhoneIsSilent = true;
                Intent addSound = new Intent(v.getContext(),addSounds.class);
                startActivity(addSound);
                Log.i("button", "add sound clicked");
            }

        });
    }
    private void setButtonClickListenerToggleOnOff() {
        ImageButton toggleButton = (ImageButton) findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
         // write into log if button is clicked
         Log.i("button:","toggle clicked");
         // File sdcard = Environment.getExternalStorageDirectory();
         // File audioFile = new File(sdcard.getPath() + "/ES/mp3/wandern2.mp3");
         // File audioFile;
         // audioFile = new File(sdcard.getPath() + "/ES/mp3/");
         // Log.i("is external writable:  ", isExternalStorageWritable() + " ");
         // Log.i("sd card name", audioFile.getName() + " can read " + sdcard.canRead());
         // Log.i("the uri", Uri.fromFile(audioFile) + "  ");
         // list all resource files in raw folder
    listRaw();
    // playRandomFile();
      if (mPhoneIsSilent) {
          // change back to normal mode
          // mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
       mPhoneIsSilent = false;
       playRandomFile();
       // create a timer
       Toast.makeText(MainActivity.this, "timer started", Toast.LENGTH_LONG).show();
       MyTimerTask myTask = new MyTimerTask();
       if (myTask == null) {
       Timer myTimer = new Timer();
       myTimer.schedule(myTask, 6000, 30000);
       //        Parameters
       //        task  the task to schedule.
       //        delay  amount of time in milliseconds before first execution.
       //        period  amount of time in milliseconds between subsequent executions.
       Log.i("home screen", onHomeScreen + "");
       Log.i("phone silent", mPhoneIsSilent + "  ");
       Log.i("timer", "created");
       }

       } else {
       // change silent mode
       // mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
          Toast.makeText(MainActivity.this, "timer stopped", Toast.LENGTH_LONG).show();
       mPhoneIsSilent = true;
       }

                     // Now toggle the UI again
                    toggleUi();
            }
            // todo auto generated method stub
            });
    }
    /**
     * Checks to see if phone is currently im silent mode:
     */
    private void checkIfPhoneIsSilent() {
        mPhoneIsSilent = mPhoneIsSilent;
//        int ringerMode = mAudioManager.getRingerMode();
//        if (ringerMode == AudioManager.RINGER_MODE_SILENT) {
//            mPhoneIsSilent = true;
//        } else {
//            mPhoneIsSilent = false;
//        }
    }
    /**
     * Toggle the UI images from silent to normal and vice versa
     */
    private void toggleUi() {
        ImageView imageView = (ImageView) findViewById(R.id.phone_icon);
        Drawable newPhoneImage;

        if (mPhoneIsSilent) {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_off);

        } else {
            newPhoneImage = getResources().getDrawable(R.drawable.phone_on);
        }

        imageView.setImageDrawable(newPhoneImage);
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkIfPhoneIsSilent();
        toggleUi();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /* Checks if external storage is available for read an write */
    public boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    // list all resource files to log
    public void listRaw(){
        Field[] fields=R.raw.class.getFields();
        for(int count=0; count < fields.length; count++){
            Log.i("Raw Asset: ", fields[count].getName());
        }
    }
    public void playRandomFile() {

        MediaPlayer mPlayer;
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        // String path = "/storage/extSdCard/ES/mp3/";
        String path = "/storage/emulated/0/parrot-sounds/";
        Log.d("Files", "Path: " + path);
        File f = new File(path);
        File file[] = f.listFiles();
        Log.d("Files", "Size: "+ file.length);
        // set random sound file
        int min = 1;
        int max = file.length;
        Random r = new Random();
        int playRandomFile = r.nextInt(max - min) + min;
        Log.d("the random", "Number:" + playRandomFile);
        // set the uri to play random sound file
        Uri myUri2 = Uri.parse("file://"+path+file[playRandomFile].getName());

        try {
            mPlayer.setDataSource(getApplicationContext(), myUri2);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // prepare the player other wise it will through an error
            mPlayer.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }

        // play the sound file
        mPlayer.start();

        // release the player after playing the sound file to prevent out of memory error
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mPlayer) {
                mPlayer.release();
                Log.i("player", "released");

            }
        });


    }

}