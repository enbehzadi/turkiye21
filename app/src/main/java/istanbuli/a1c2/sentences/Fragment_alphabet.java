package istanbuli.a1c2.sentences;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;

import android.util.Base64;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
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
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import istanbuli.a1c2.Database;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;
import istanbuli.a1c2.speakTurkce;


public class Fragment_Sentences_Words extends Fragment {

    CheckBox chk_englsih,chk_persian;
    TextToSpeech tts;
    int listViewSize;
    ImageView btplay,bt_Back,bt_next;
    Database myDbHelper;
    SQLiteDatabase db;
    myshareprefrence myPrefs;
    String bookName, lng;
    speakTurkce st1=new speakTurkce();
    speakTurkce st2=new speakTurkce();
    speakTurkce st3=new speakTurkce();
    int id_start=0;
    ArrayList<String> tur_words = new ArrayList<>();
    ArrayList<String> fa_words = new ArrayList<>();
    ArrayList<String> en_words = new ArrayList<>();

    ArrayList<String> de_words = new ArrayList<>();
    ArrayList<String> fr_words = new ArrayList<>();
    ListView lv_list_words;
    private adapter valueAdapter;
    Boolean play_pause=false;

    Boolean hide_show_english=true;
    Boolean hide_show_persian=false;
    View fragmentView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        fragmentView = inflater.inflate(R.layout.fragment_sentence_sub, container, false);


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
                        // resetPlayer();
                        play_pause=false;
                        getActivity().onBackPressed();
                        getActivity().finish();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initUI() {



        lv_list_words =fragmentView.findViewById(R.id.rc_list_words);

        btplay=fragmentView.findViewById(R.id.play_pause_btn);
        bt_Back=fragmentView.findViewById(R.id.previous_btn);
        bt_next=fragmentView.findViewById(R.id.next_btn);
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


                            try {



                     /*           lv_list_words.getAdapter().getView(id_start,vm, (ViewGroup) vm).setAlpha(0.5f);*/
 if(!myPrefs.getSentence_Titel().equals("Alphabet"))
 {
     String[] st=lv_list_words.getAdapter().getItem(id_start).toString().split(":");
     st1.setspeake(getActivity(), st[0],"tr_Tr");
     View aaaa = getViewByPosition(id_start, lv_list_words);
     aaaa.setAlpha(0.8f);
     Thread.sleep(1000);
     st2.setspeake(getActivity(), st[1],"en_En");
     Thread.sleep(2000);

     Animation anim = new AlphaAnimation(0.0f, 1.0f);
     anim.setDuration(50); //You can manage the blinking time with this parameter
     anim.setStartOffset(20);
     anim.setRepeatMode(Animation.INFINITE);
     anim.setRepeatCount(10);
     aaaa.findViewById(R.id.tv_tur_blink).setVisibility(View.VISIBLE);
     aaaa.findViewById(R.id.tv_tur_blink).startAnimation(anim);


     Thread.sleep(4000);
     st3.setspeake(getActivity(), st[0],"tr_Tr");
     aaaa.findViewById(R.id.tv_tur_blink).setVisibility(View.INVISIBLE);
     Thread.sleep(500);

     st1.setspeake(getActivity(), st[1],"en_En");
     Thread.sleep(3000);
     aaaa.setAlpha(1);
}
 else
 {
     String[] st=lv_list_words.getAdapter().getItem(id_start).toString().split(":");

     String[] speak=st[0].split(" ");
     st1.setspeake(getActivity(), speak[0],"tr_Tr");
   //  st2.setspeake(getActivity(),speak[0],"tr_Tr");

     View aaaa = getViewByPosition(id_start, lv_list_words);
     aaaa.setAlpha(0.8f);
     Thread.sleep(2000);

     aaaa.setAlpha(1);
 }






                            }
                            catch (InterruptedException e)
                            {

                            }
                            if(id_start+1==listViewSize)
                            {
                                id_start=-1;
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

        bookName = myPrefs.getSentence_Titel();
        /*lng = myPrefs.getSelectBook();*/
        LoadListLesson();


    }

    public void LoadListLesson() {
        String areaTyp,b ="";
        String lesson = myPrefs.getSentence_CatId();
       // Load_Database();
        Cursor cr;
            functions fnc=new functions(getActivity(),getActivity());
        myshareprefrence myPrefs=new myshareprefrence(getActivity());

        net.sqlcipher.database.SQLiteDatabase db = null;
        try {
            db = functions.Load_Database_Singleton.getInstance(getActivity(),myPrefs.getPasswordDb());
        } catch (IOException e) {
            e.printStackTrace();
        }        try {
            db = functions.Load_Database_Singleton.getInstance(getActivity(),myPrefs.getPasswordDb());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!myPrefs.getSentence_Titel().equals("Alphabet"))
        {
            b = "Tr_Tbl_Sentences_Dictionary";

            areaTyp = "SELECT *  FROM " + b + " where _Id_Lesson=" + lesson.trim();
            cr = db.rawQuery(areaTyp, null);

            tur_words.clear();
            fa_words.clear();
            en_words.clear();
            de_words.clear();
            fr_words.clear();
            if (cr.moveToFirst()) {
                do {
//
//                    byte[] tur_byte=cr.getBlob(cr.getColumnIndex("tur"));
//                    String tur="";
//                    byte[] data = Base64.decode(tur_byte, Base64.DEFAULT);
//                    try {
//                        tur = new String(data, "UTF-8");
//                        tur_words.add(tur);
//
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }


                    tur_words.add(cr.getString(cr.getColumnIndex("tr")));
                    fa_words.add(cr.getString(cr.getColumnIndex("fa")));
                    en_words.add(cr.getString(cr.getColumnIndex("en")));


                } while (cr.moveToNext());
            }
        }
        else
        {
                areaTyp = "SELECT *  FROM Tr_Tbl_Alphabet";
            cr = db.rawQuery(areaTyp, null);
            tur_words.clear();
            fa_words.clear();
            en_words.clear();
            if (cr.moveToFirst()) {
                do {

                    tur_words.add(cr.getString(cr.getColumnIndex("Tr")));
                    fa_words.add(cr.getString(cr.getColumnIndex("Fa")));
                    en_words.add(cr.getString(cr.getColumnIndex("En")));
                } while (cr.moveToNext());
            }
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
        valueAdapter = new adapter(getActivity(),R.layout.row_words_list);
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
//        final LinearLayout vwParentRow = (LinearLayout)v.findViewById(R.id.la_words_list);
//        LinearLayout child = (LinearLayout)vwParentRow.getChildAt(0);
//        TextView child2 = (TextView)child.getChildAt(0);
//        vwParentRow.setAlpha((float) 0.5);
//        int secondsDelayed = 1;
//        speakTurkce st=new speakTurkce();
//        if(!myPrefs.getSentence_Titel().equals("Alphabet"))
//        {
//
//            st.setspeake(getActivity(),child2.getText().toString(),"tr_Tr");
//        }
//        else
//        {
//            String[] speak=child2.getText().toString().split(" ");
//            st.setspeake(getActivity(),speak[3],"tr_Tr");
//        }
//
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                vwParentRow.setAlpha(1);
//
//            }
//        }, secondsDelayed * 100);
//        id_start=tur_words.indexOf(child2.getText().toString());

    }
    class adapter extends ArrayAdapter {


        public adapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=getLayoutInflater().inflate(R.layout.row_words_list,null);

            final LinearLayout la_ir = (LinearLayout) v.findViewById(R.id.la_ir);
            final LinearLayout la_en = (LinearLayout) v.findViewById(R.id.la_en);
            final TextView tv_tur = (TextView) v.findViewById(R.id.tv_tur_word_list);
            final TextView tv_fa = (TextView) v.findViewById(R.id.tv_fa_word_list);
            final TextView tv_en = (TextView) v.findViewById(R.id.tv_en_word_list);

            tv_tur.setText(tur_words.get(position).toString());


            tv_fa.setText(fa_words.get(position).toString());

            final speakTurkce st1=new speakTurkce();


            tv_fa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    st1.setspeake(getActivity(),tv_fa.getText().toString(),"tr_Tr");
                }
            });

            tv_en.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        tv_en.setAlpha(0.5f);
                        Thread.sleep(500);


                        st1.setspeake(getActivity(),tv_en.getText().toString(),"en_En");
                        tv_en.setAlpha(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            tv_tur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    try {
                        tv_tur.setAlpha(0.5f);
                        Thread.sleep(500);


                        st1.setspeake(getActivity(),tv_tur.getText().toString(),"tr_Tr");
                        tv_tur.setAlpha(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            if(!hide_show_english)
            {
                la_en.setVisibility(View.GONE);
            }
            else
            {
                la_en.setVisibility(View.VISIBLE);
                tv_en.setText(en_words.get(position));

            }
            if(!hide_show_persian)
            {
                la_ir.setVisibility(View.GONE);
            }
            else
            {
                la_ir.setVisibility(View.VISIBLE);
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
            return tur_words.get(position)+":"+en_words.get(position);

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return position;
        }

    }



}
