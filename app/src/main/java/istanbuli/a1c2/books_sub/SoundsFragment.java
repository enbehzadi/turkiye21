package istanbuli.a1c2.books_sub;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import istanbuli.a1c2.DownloadClass;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;


public class SoundsFragment extends Fragment  {

    int last_id_selected=0;

    functions fnc;
    View fragmentView;
    SeekBar song_progressbar;
    DownloadClass downloadclass;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private SearchView sv;
    ImageView previous_btn,play_pause_btn,next_btn;
    SQLiteDatabase db;
    int id_start=0;
    ProgressDialog mProgressDialog;
    myshareprefrence myPrefs;
    String bookName, lng;
    ArrayList<String> Name_Voice = new ArrayList<>();
    ArrayList<String> Page_Voice = new ArrayList<>();
    ArrayList<String> Link_Voice = new ArrayList<>();
    ArrayList<String> Main_Name_Voice = new ArrayList<>();
    ListView lv_list_voices;
    TextView song_info_title,song_progress_current,song_progress_max;
    MediaPlayer mPlayer;
    View vm;
    String root = Environment.getExternalStorageDirectory().toString();
    Handler seekHandler = new Handler();
    int mediaMax;
    int mediaPos = 0;
    LinearLayout b;
    Boolean play_pause=true;
    private adapter valueAdapter;
    File  Play_Music_File_Name;
    String Titel_Music;
    private int seekForwardTime = 5 * 1000; // default 5 second
    private int seekBackwardTime = 5 * 1000; // default 5 second
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


         fragmentView = inflater.inflate(R.layout.list_voices_lesson, container, false);


        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        initUI();
        initData();
        onBackFragment(fragmentView);


    }


    public void onBackFragment(View view)
    {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        resetPlayer();

                        getActivity().onBackPressed();
                        return true;
                    }
                }
                return false;
            }
        });
    }
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString ;

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
    private void initUI() {
        lv_list_voices =fragmentView.findViewById(R.id.rc_list_voices223);


        fnc=new functions(getActivity());

        song_info_title=fragmentView.findViewById(R.id.song_info_title);
        song_progress_max=fragmentView.findViewById(R.id.song_progress_max);
        song_progress_current=fragmentView.findViewById(R.id.song_progress_current);
        song_progressbar=fragmentView.findViewById(R.id.song_progressbar);
        song_progressbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                long duration = song_progressbar.getMax();
                long formattedProgress = song_progressbar.getProgress();
                song_progress_current.setText(milliSecondsToTimer(formattedProgress));
                song_progress_max.setText(milliSecondsToTimer(duration));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                song_progress_current.setVisibility(View.VISIBLE);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mPlayer != null && mPlayer.isPlaying()) {
                    mPlayer.seekTo(seekBar.getProgress());
                }
            }
        });





        play_pause_btn=fragmentView.findViewById(R.id.play_pause_btn);
        next_btn=fragmentView.findViewById(R.id.next_btn);
        previous_btn=fragmentView.findViewById(R.id.previous_btn);
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getContext(),"testb6",Toast.LENGTH_LONG).show();
                forwardSong();
              // next_back(1);
            }
        });
        previous_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //next_back(0);
               // Toast.makeText(getContext(),"test4",Toast.LENGTH_LONG).show();
                rewindSong();
            }
        });
        play_pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(),"test5",Toast.LENGTH_LONG).show();
              next_back(2);

            }
        });


        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.download));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {


            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    private void initData() {
        downloadclass=new DownloadClass();
        myPrefs = new myshareprefrence(getActivity());
        bookName = myPrefs.getSelectBook();
        if(bookName.equalsIgnoreCase("C1"+'+'))
        {
            bookName="C1\'+\'";
        }
        lng = bookName;
        LoadListVoices();
        valueAdapter = new adapter(getActivity(), R.layout.row_voice_list);
        lv_list_voices.setAdapter(valueAdapter);

        //aaaaa.setBackgroundColor(android.R.color.holo_green_light);
        song_info_title.setText(lv_list_voices.getAdapter().getItem(id_start).toString()+"-"+Page_Voice.get(id_start));
    }





    public void LoadListVoices() {
        String []row;
        String b = "Tr_Tbl_Voices";

        if(bookName.equalsIgnoreCase("C1"+'+'))
        {
            bookName="C1\'+\'";
        }
        String Book = myPrefs.getSelectBook();
        db = fnc.Load_Database(getActivity());

        String areaTyp = "SELECT *  FROM Tr_Tbl_Voices where Book='"+Book+"'";
       // String areaTyp = "SELECT *  FROM Tr_Tbl_Voices  where Book="+ Book.trim();

        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {
            do {
                row=cr.getString(cr.getColumnIndex("Name")).split("-");
                Main_Name_Voice.add(cr.getString(cr.getColumnIndex("Name")));
                Name_Voice.add(row[1]);
                Page_Voice.add(getResources().getString(R.string.page)+" "+row[2]);
                Link_Voice.add(cr.getString(cr.getColumnIndex("Link")));
            } while (cr.moveToNext());
        }

    }





    public void  hilight_item_list()
    {
        View new_select = getViewByPosition(id_start);
        new_select.setAlpha(0.8f);
        View old_select = getViewByPosition(last_id_selected);
        old_select.setAlpha(1.0f);
        last_id_selected=id_start;
        song_info_title.setText(lv_list_voices.getAdapter().getItem(id_start).toString()+"-"+Page_Voice.get(id_start));
    }

    public void startDownloadPlay()
    {

        if(downloadclass.getPermission(getActivity()))
        {
            File fileDir = new File(root + "/Android/data/" + getActivity().getPackageName().toString()+"/voices/");

            String []fileName2 = Main_Name_Voice.get(id_start).split("-");
            String fn=fileName2[0]+"-"+fileName2[2]+".mp3";
            Play_Music_File_Name = new File(root + "/Android/data/" + getActivity().getPackageName().toString() + "/voices/" +fn);
            Titel_Music=fileName2[1]+" "+Page_Voice.get(id_start);
            if( downloadclass.CheckFile(fileDir,Play_Music_File_Name))
            {

                playMusic(Play_Music_File_Name,Titel_Music);
            }
            else
            {
                if (downloadclass.checkPermissionwrite(getActivity())) {
                    String link=getResources().getString(R.string.saite)+Link_Voice.get(id_start);
                    startDownload(link,Play_Music_File_Name);
                }
                else {
                    downloadclass.requestPermissionwrite(getActivity());
                }

            }
        }
    }

    public void resetPlayer()
    {
        if(mPlayer!=null)
        {
            if(mPlayer.isPlaying())
            {
                mPlayer.stop();
                mPlayer.reset();
                song_progressbar.setMax(0); // Set the Maximum range of the
                song_progressbar.setProgress(0);// set current progress to song's
                seekHandler.removeCallbacks(moveSeekBarThread);
                seekHandler.postDelayed(moveSeekBarThread, 100);
                play_pause_btn.setImageResource(R.drawable.ic_play_vector);
                play_pause=true;
                mPlayer=null;
            }
        }
    }
    public  void next_back(int action)
    {
        int listViewSize = lv_list_voices.getAdapter().getCount();

        if(action==0)
        {
            if(id_start>0)
            {

                id_start--;
                resetPlayer();
               // hilight_item_list(v);
                startDownloadPlay();
            }
        }
        else if(action==1)
        {
            if(id_start<listViewSize)
            {
                id_start++;
                resetPlayer();
                //hilight_item_list(v);
                startDownloadPlay();
            }
        }
        else
        {

            if(mPlayer!=null)
            {


                if(mPlayer.isPlaying())
                {
                    play_pause_btn.setImageResource(R.drawable.ic_play_vector);
                    mPlayer.pause();
                    play_pause=true;

                }
                else
                {
                    play_pause_btn.setImageResource(R.drawable.ic_pause_vector);
                    mPlayer.start();
                    play_pause=true;
                    mediaPos = mPlayer.getCurrentPosition();
                    mediaMax = mPlayer.getDuration();
                    song_progressbar.setMax(mediaMax); // Set the Maximum range of the
                    song_progressbar.setProgress(mediaPos);// set current progress to song's
                    seekHandler.removeCallbacks(moveSeekBarThread);
                    seekHandler.postDelayed(moveSeekBarThread, 100);


                }
            }
            else
            {

                startDownloadPlay();
            }
        }



        lv_list_voices.getAdapter().getView(id_start,vm, (ViewGroup) vm).setAlpha(0.5f);
        song_info_title.setText(lv_list_voices.getAdapter().getItem(id_start).toString());

        View aaaa = getViewByPosition(id_start);
        aaaa.setAlpha(0.5f);

    }


    public View getViewByPosition(int pos) {
        final int firstListItemPosition = lv_list_voices.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + lv_list_voices.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return lv_list_voices.getAdapter().getView(pos, null, lv_list_voices);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return lv_list_voices.getChildAt(childIndex);
        }
    }
    public void playMusic(File srcFile, String Titel_Music)
    {
        if(mPlayer!=null)
        {
            if(mPlayer.isPlaying())
            {
                mPlayer.stop();
                mPlayer.release();

                mPlayer=new MediaPlayer();


            }
            else
            {
                mPlayer=new MediaPlayer();

            }

        }
        else
        {
            mPlayer=new MediaPlayer();

        }





        Uri myUri = Uri.parse(srcFile.getPath());
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {

            mPlayer.setDataSource(getActivity(), myUri);
            mPlayer.prepare();
            mPlayer.start();


        } catch (IOException e) {
            e.printStackTrace();
        }

        song_info_title.setText(Titel_Music);


        mediaPos = mPlayer.getCurrentPosition();
        mediaMax = mPlayer.getDuration();
        song_progressbar.setMax(mediaMax); // Set the Maximum range of the
        song_progressbar.setProgress(mediaPos);// set current progress to song's
        seekHandler.removeCallbacks(moveSeekBarThread);
        seekHandler.postDelayed(moveSeekBarThread, 100);
        play_pause_btn.setImageResource(R.drawable.ic_pause_vector);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play_pause_btn.setImageResource(R.drawable.ic_play_vector);
                //mPlayer.stop();
                //mPlayer.reset();
                // mPlayer.release();

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
    public void startDownload(String Link, final File output) {


        final DownloadTask downloadTask = new DownloadTask(getActivity(),output);
        downloadTask.execute(Link);


    }


    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private  File Out;

        public DownloadTask(Context context,File output) {
            this.context = context;
            this.Out=output;


        }

        @Override
        protected String doInBackground(String... sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(this.Out.getPath());


//                output = new FileOutputStream(String.valueOf(Uri.fromFile(Out)));
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
            {
                Toast.makeText(context,getResources().getString(R.string.Download_error),Toast.LENGTH_SHORT).show();
                downloadclass.DeleteFile(Out);
            }

            else
            {
                /*Toast.makeText(context,getResources().getString(R.string.Download_Finish),Toast.LENGTH_SHORT).show();*/

                playMusic(Play_Music_File_Name,Titel_Music);
            }

        }
    }
    public void onBackPressed()
    {

        getActivity().finish();
        if(mPlayer!=null)
        {
            mPlayer.stop();
        }

    }

    private Runnable moveSeekBarThread = new Runnable() {
        public void run() {
            if (mPlayer != null) {


                //song_progressbar.setMax(mPlayer.getDuration());
                song_progressbar.setProgress(mPlayer.getCurrentPosition());
                seekHandler.postDelayed(this, 100);

            }
        }

    };

    public void forwardSong() {
        if (mPlayer != null) {
            int currentPosition = mPlayer.getCurrentPosition();
            if (currentPosition + seekForwardTime <= mPlayer.getDuration()) {
                mPlayer.seekTo(currentPosition + seekForwardTime);
            } else {
                mPlayer.seekTo(mPlayer.getDuration());
            }
        }
    }

    public void rewindSong() {
        if (mPlayer != null) {
            int currentPosition = mPlayer.getCurrentPosition();
            if (currentPosition - seekBackwardTime >= 0) {
                mPlayer.seekTo(currentPosition - seekBackwardTime);
            } else {
                mPlayer.seekTo(0);
            }
        }
    }


    class adapter extends ArrayAdapter {


        public adapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v=((Activity)getContext()).getLayoutInflater().inflate(R.layout.row_voice_list,null);

            TextView tv_name_voices = (TextView) v.findViewById(R.id.tv_name_voice_list);
            TextView tv_page_voices = (TextView) v.findViewById(R.id.tv_numberpage_voice_list);
            //tv_name_voices.setTag(position);
            tv_name_voices.setText(Name_Voice.get(position));

            tv_page_voices.setText(Page_Voice.get(position));

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickOnVoicesList(v, position);
                }
            });
            return v;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Name_Voice.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return Name_Voice.get(position);

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return position;
        }

    }


    public void ClickOnVoicesList(View v,int position)
    {


        id_start= position;
        resetPlayer();
        hilight_item_list();
        startDownloadPlay();

    }

    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible())
        {
            if (!isVisibleToUser)   // If we are becoming invisible, then...
            {

                    resetPlayer();
                            }

            if (isVisibleToUser) // If we are becoming visible, then...
            {

            }
        }
    }
}
