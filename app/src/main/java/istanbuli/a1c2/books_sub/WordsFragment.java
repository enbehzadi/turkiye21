package istanbuli.a1c2.books_sub;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.util.ArrayList;

import istanbuli.a1c2.Database;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.speakTurkce;


public class WordsFragment extends Fragment {
    View fragmentView;
    CheckBox chk_englsih,chk_persian;
    TextToSpeech tts;
    int listViewSize;



    ImageView btplay,bt_Back,bt_next;
    Database myDbHelper;
    SQLiteDatabase db;

    myshareprefrence myPrefs;
    String bookName, lng;
    speakTurkce st=new speakTurkce();
    int id_start=0;
    ArrayList<String> tur_words = new ArrayList<>();
    ArrayList<String> fa_words = new ArrayList<>();
    ArrayList<String> en_words = new ArrayList<>();
    ListView lv_list_words;
    private adapter valueAdapter;
    Boolean play_pause=false;

    Boolean hide_show_english=true;
    Boolean hide_show_persian=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView=inflater.inflate(R.layout.list_words_lesson, null);
        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                       // resetPlayer();
                        play_pause=false;
                        getActivity().onBackPressed();
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private void initUI() {



        lv_list_words = (ListView) fragmentView.findViewById(R.id.rc_list_words);

        btplay=(ImageView)fragmentView.findViewById(R.id.play_pause_btn);
        bt_Back=(ImageView)fragmentView.findViewById(R.id.previous_btn);
        bt_next=(ImageView)fragmentView.findViewById(R.id.next_btn);
        btplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play_pause)
                {
                    play_pause=false;
                    btplay.setImageResource(R.drawable.ic_play_vector);
                    autoscrool();
                }
                else
                {
                    play_pause=true;
                    btplay.setImageResource(R.drawable.ic_pause_vector);
                    autoscrool();
                }

            }
        });
        bt_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_start>0)
                {
                    id_start--;
                }    }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id_start<listViewSize)
                {
                    id_start++;
                }    }
        });

        chk_englsih=fragmentView.findViewById(R.id.checkBox_english);
        chk_persian=fragmentView.findViewById(R.id.checkBox_farsi);
        chk_englsih.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    hide_show_list(true);
                }
                else
                {
                    hide_show_list(false);
                }
            }
        });
        chk_persian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked)
                {
                    hide_show_list_persian(true);
                }
                else
                {
                    hide_show_list_persian(false);
                }
            }
        });
    }
    public  void autoscrool()
    {
        new Thread(new Runnable() {

            @Override
            public void run() {
                listViewSize = lv_list_words.getAdapter().getCount();
                if (play_pause) {
                    for (; id_start < listViewSize; id_start++) {
                        lv_list_words.smoothScrollToPositionFromTop(id_start, 420,1000);
                        if(play_pause)
                        {



                            /*           lv_list_words.getAdapter().getView(id_start,vm, (ViewGroup) vm).setAlpha(0.5f);*/
                            try {
                            st.setspeake(getActivity(), lv_list_words.getAdapter().getItem(id_start).toString());
                            View aaaa = getViewByPosition(id_start, lv_list_words);

                                aaaa.setBackgroundResource(R.color.colorBackgroundPdf);
                                Thread.sleep(3000);
                                aaaa.setBackgroundResource(R.color.colorRowWords);
                            }catch (Exception e)
                            {

                            }

                        }
                        else
                        {
                            break;
                        }
                    }
                }
                else
                {

                }
            }
        }).start();

    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    private void initData() {
        myPrefs = new myshareprefrence(getActivity());

        bookName = myPrefs.getSelectBook();
        lng = myPrefs.getSelectBook();
        LoadListLesson();


    }

    public void LoadListLesson() {

       // myPrefs.setLesson("?????? 1");

        String  TableName;
        String lesson;
        if(bookName.equalsIgnoreCase("C1+"))
        {
            TableName="'Tr_Tbl_Words_C1+'";
            Integer  lesson2 = Integer.valueOf(myPrefs.getLesson())+6;
            lesson = String.valueOf(lesson2);

        }
        else
        {
            TableName = "Tr_Tbl_Words_" + bookName.trim();
            lesson = myPrefs.getLesson();

        }
        Load_Database();
        db = myDbHelper.getReadableDatabase();
        String areaTyp = "SELECT *  FROM " + TableName + " where Lesson_Number=" + lesson.trim();
        Cursor cr = db.rawQuery(areaTyp, null);
        tur_words.clear();
        fa_words.clear();
        en_words.clear();
        if (cr.moveToFirst()) {
            do {

                tur_words.add(cr.getString(cr.getColumnIndex("Tur")));
                fa_words.add(cr.getString(cr.getColumnIndex("Fa")));
                en_words.add(cr.getString(cr.getColumnIndex("En")));
            } while (cr.moveToNext());
        }
        hide_show_english=myPrefs.get_hide_show_english();
        hide_show_persian=myPrefs.get_hide_show_persian();
        if(hide_show_english)
        {
            chk_englsih.setChecked(true);
        }
        if(hide_show_persian)
        {
            chk_persian.setChecked(true);
        }
        lng = myPrefs.getSelectBook();
        valueAdapter = new adapter(getContext(),R.layout.row_words_list);
        lv_list_words.setAdapter(valueAdapter);
        lv_list_words.setTextFilterEnabled(true);

    }

    private void Load_Database() throws Error {
        myDbHelper = new Database(getActivity());

        try {
            myDbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            myDbHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;

        }
    }

    public void hide_show_list(Boolean t) {
        if (t)
        {
            myPrefs.set_hide_show_english(true);
            LoadListLesson();
        }
        else
        {
            myPrefs.set_hide_show_english(false);
            LoadListLesson();
        }


    }

    public void hide_show_list_persian(Boolean t) {
        if (t)
        {
            myPrefs.set_hide_show_persian(true);
            LoadListLesson();
        }
        else
        {
            myPrefs.set_hide_show_persian(false);
            LoadListLesson();
        }


    }

    public void ClickOnWordsList4(View v)
    {
        final LinearLayout vwParentRow = (LinearLayout)v.findViewById(R.id.la_words_list);
        TextView child = (TextView)vwParentRow.getChildAt(0);


        vwParentRow.setBackgroundResource(R.color.colorBackgroundPdf);
        int secondsDelayed = 1;
        try {
            speakTurkce st = new speakTurkce();
            st.setspeake(getActivity(), child.getText().toString());
            new Handler().postDelayed(new Runnable() {
                public void run() {

                    vwParentRow.setBackgroundResource(R.color.colorRowWords);
                }
            }, secondsDelayed * 100);
            id_start = tur_words.indexOf(child.getText().toString());
        }
        catch (Exception e)
        {

        }
    }
    class adapter extends ArrayAdapter {


        public adapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=((Activity)getContext()).getLayoutInflater().inflate(R.layout.row_words_list,null);

            TextView tv_tur = (TextView) v.findViewById(R.id.tv_tur_word_list);
            TextView tv_fa = (TextView) v.findViewById(R.id.tv_fa_word_list);
            TextView tv_en = (TextView) v.findViewById(R.id.tv_en_word_list);

            tv_tur.setText(tur_words.get(position).toString());
            tv_fa.setText(fa_words.get(position).toString());
            if(!hide_show_english)
            {
                tv_en.setVisibility(View.GONE);
            }
            else
            {
                tv_en.setVisibility(View.VISIBLE);
                tv_en.setText(en_words.get(position));

            }
            if(!hide_show_persian)
            {
                tv_fa.setVisibility(View.GONE);
            }
            else
            {
                tv_fa.setVisibility(View.VISIBLE);
                tv_fa.setText(fa_words.get(position));

            }
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickOnWordsList4(v);
                }
            });

            return v;
        }


        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return tur_words.size();

        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return tur_words.get(position);

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return position;
        }

    }



    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible())
        {
            if (!isVisibleToUser)   // If we are becoming invisible, then...
            {

                play_pause=false;
                btplay.setImageResource(R.drawable.ic_play_vector);
                autoscrool();
            }

            if (isVisibleToUser) // If we are becoming visible, then...
            {

            }
        }
    }

}
