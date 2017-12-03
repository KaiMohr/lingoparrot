package com.example.kai.mParrot;

        import android.app.Activity;
        import android.content.Intent;
        import android.media.MediaPlayer;
        import android.media.MediaRecorder;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;
        import java.io.FileInputStream;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.File;
        import java.nio.channels.FileChannel;
        import android.os.Environment;

public class addSounds extends Activity {
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    private Button start, stop, play;
    private EditText mEdit;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsoundrecord);
        start = (Button) findViewById(R.id.button1);
        stop = (Button) findViewById(R.id.button2);
        play = (Button) findViewById(R.id.button3);
        mEdit = (EditText) findViewById(R.id.recordSoundName);
        stop.setEnabled(false);
        play.setEnabled(false);
     outputFile = Environment.getExternalStorageDirectory().
                getAbsolutePath() + "/parrot-sounds/";
        //      String outputFile = "/storage/extSdCard/ES/mp3/myrecording.3gp";
        Log.i("audiopath", outputFile + "");
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

        final Button redoRecording = (Button) findViewById(R.id.redoRecording);
        redoRecording.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // enable the buttons
                start.setEnabled(true);
                stop.setEnabled(false);
                play.setEnabled(false);
               // delete the old recording
                String txtRecordedFile = mEdit.getText().toString();
              String path = "/storage/emulated/0/parrot-sounds/";
               File f = new File(path + txtRecordedFile);
                Log.d("the file is deleted", ": " + f);
                f.delete();
                // start a recorder for the start button
                myAudioRecorder = new MediaRecorder();
                myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                myAudioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            }
        });

        final Button listRecordings = (Button) findViewById(R.id.listRecordings);
        listRecordings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(addSounds.this,ListRecordingActivity.class);
                startActivityForResult(intent, 2);// Activity is started with requestCode 2
             //  finish();//finishing activity
            }

        });

        final Button navigateHome = (Button) findViewById(R.id.helpButton);
        navigateHome.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent help = new Intent(view.getContext(), helpActivity.class);
                startActivity(help);
            }

        });

    }
    public void start(View view) {

        myAudioRecorder.setOutputFile(outputFile + mEdit.getText().toString());
        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();



        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        start.setEnabled(false);
        stop.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();

    }
    public void stop(View view) {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        stop.setEnabled(false);
        play.setEnabled(true);
       Toast.makeText(getApplicationContext(), "Audio recorded successfully",
               Toast.LENGTH_LONG).show();
    }
    public void play(View view) throws IllegalArgumentException,
    SecurityException, IllegalStateException, IOException {

        MediaPlayer m = new MediaPlayer();
        m.setDataSource(outputFile + mEdit.getText().toString());
        m.prepare();
        m.start();
        Toast.makeText(getApplicationContext(), "Playing audio", Toast.LENGTH_LONG).show();

    }
         /* Checks if external storage is available for read an write */

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public void copyFileUsingApacheCommonsIO(File source, File dest)
    throws IOException {

        FileChannel inputChannel = null;

        FileChannel outputChannel = null;

        try {

            inputChannel = new FileInputStream(source).getChannel();

            outputChannel = new FileOutputStream(dest).getChannel();

            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        } finally {

            inputChannel.close();

            outputChannel.close();

        }

    }
    // Call Back method  to get the Message form another Activity
    @Override
        //code that may throw exception
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
      //  super.onActivityResult(requestCode, resultCode, data);

            // check if the request code is same as what is passed  here it is 2
            if(requestCode==2) {
                String message;
                message = data.getStringExtra("MESSAGE");
                mEdit.setText(message);
                start.setEnabled(false);
                stop.setEnabled(false);
                play.setEnabled(true);
        }
    }
}
