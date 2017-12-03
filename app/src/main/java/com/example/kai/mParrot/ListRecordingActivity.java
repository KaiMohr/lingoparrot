package com.example.kai.mParrot;
import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ListRecordingActivity extends Activity implements AbsListView.MultiChoiceModeListener  {
    File fileDirectory = new File(Environment.getExternalStorageDirectory()+File.separator+"parrot-sounds");
    File fileDir = new File(fileDirectory, "");
    String[] ITEMS = fileDir.list();

    private ListView mList;

    protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
        //Register a button for context events
        mList = new ListView(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, ITEMS);
        mList.setAdapter(adapter);
        mList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mList.setMultiChoiceModeListener(this);
        setContentView(mList);
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        //You can do extra work here update the menu if the
        // ActionMode is ever invalidated
        Log.d("parrot list recordings", "onPrepareAction: ");
        return true;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        //This is called when the action mode has ben exited
       // deleteSelectedSoundRecordings();
        Log.d("parrot list recordings", "onDestroyAction: ");
        super.onDestroy();

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.contextmenu, menu);
        Log.d("parrot list recordings", "onCreateActionMode: ");
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        //Switch on the item’s ID to find the action the user selected
        switch(item.getItemId()) {
            case R.id.menu_delete:
                //Perform delete actions
                Log.d("parrot list recordings", "delete clicked");
                deleteSelectedSoundRecordings();

                ListRecordingActivity.this.recreate();
                break;
            case R.id.menu_edit:

                //Perform edit actions
                SparseBooleanArray checked = mList.getCheckedItemPositions();
                String theSelectedItem = (String) mList.getItemAtPosition(checked.keyAt(0));
                
//                Intent addSounds = new Intent (this, addSounds.class);
//                addSounds.putExtra("editfilename", theSelectedItem);
//                startActivity(addSounds);

                String message = theSelectedItem;
                Intent intent=new Intent();
                intent.putExtra("MESSAGE",message);
                setResult(2,intent);
                finish();//finishing activity


                break;
            case R.id.menu_play:
                //Perform edit actions
                Log.d("parrot list recordings", "play clicked");
                playFile();

                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position,
    long id, boolean checked) {
        int count = mList.getCheckedItemCount();
        mode.setTitle(String.format("%d Selected", count));
     //   deleteSelectedSoundRecordings();
        //   Log.d("parrot", "delete items 1: " + mList.getItemAtPosition(checked.keyAt(0)));
        Log.d("parrot list recordings", "onItemCheckedStateChanged");

    }

    @Override
    public void onBackPressed() {
      // This will be called either automatically for you on 2.0
      // or later, or by the code above on earlier versions of the
      // platform.
      // ListRecordingActivity.this.mList.clearFocus();
    Intent  myIntent = new Intent(this, MainActivity.class);
    startActivity(myIntent);
            return;
    }


    private void deleteSelectedSoundRecordings() {

      //  Log.d("parrot", "delete items: ");
        SparseBooleanArray checked = mList.getCheckedItemPositions();

     //   Log.d("parrot", "delete items 1: " + mList.getItemAtPosition(checked.keyAt(0)));


    String theSelectedItems = (String) mList.getItemAtPosition(checked.keyAt(0));
  // String theSelectedItems = (String) mList.getSelectedItem();
   // Log.d("parrot", "delete items inside: " + mList.getItemAtPosition(checked.keyAt(0)));



        File f = new File(fileDirectory + "/" + theSelectedItems);
        Log.d("the file is deleted", ": " + f);
      f.delete();


    }

// get results from other activities

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            String message=data.getStringExtra("MESSAGE");
            // textView1.setText(message);
        }
    }

    public void playFile() {

        SparseBooleanArray checked = mList.getCheckedItemPositions();
        String theSelectedItems = (String) mList.getItemAtPosition(checked.keyAt(0));

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
       // Uri myUri2 = Uri.parse("file://"+path+file[playRandomFile].getName());

        Uri myUri2 = Uri.parse("file://"+path+theSelectedItems);

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
