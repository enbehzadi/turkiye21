package istanbuli.a1c2.tabmenu.fragment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.adapter.Adapter_sentences;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.books_sub.SentenceModel;
import istanbuli.a1c2.functions.functions;
import istanbuli.a1c2.sentences.Activity_Main_Sentence_Sub;

import static istanbuli.a1c2.R.id.gridview_sentence;


public class SentencesFragment extends Fragment {
    View fragmentView;
    TypedArray listImage ;

    myshareprefrence myPrefs;
    private GridView gridView;
    private Adapter_sentences mAdapter;
    private ArrayList<SentenceModel> mArrayList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView=inflater.inflate(R.layout.fragment_sentences, null);
        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI();
        listImage = getResources().obtainTypedArray(R.array.list);
        initData();
    }

    private void initUI()
    {
        gridView = fragmentView.findViewById(gridview_sentence);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myPrefs.setSentence_CatId(mArrayList.get(position).Cat_Id);
                myPrefs.setSentence_Titel(mArrayList.get(position).get_Image_Titel_en()+"/"+mArrayList.get(position).get_Image_Titel_fa());

                startActivity(new Intent(getActivity(), Activity_Main_Sentence_Sub.class));

            }
        });

    }
    private void initData() {
        mArrayList = new ArrayList<>();
        LoadList();
        mAdapter = new Adapter_sentences (getContext(),mArrayList);
        gridView.setAdapter(mAdapter);
        myPrefs=new myshareprefrence(getActivity());
    }

    public void LoadList() {


        functions fnc=new functions(getContext());
        myPrefs=new myshareprefrence(getActivity());


        SQLiteDatabase db;
        db = fnc.Load_Database(getActivity());

        String areaTyp = "SELECT *  FROM Tr_Tbl_Cat_Sentence";

        Cursor cr = db.rawQuery(areaTyp, null);

        int i=0;
        if (cr.moveToFirst()) {
            do {
                String iv=cr.getString(cr.getColumnIndex("Pic")).toString();
                String[]a = iv.split("\\.");
                String fa=cr.getString(cr.getColumnIndex("Fa")).toString();
                String en=cr.getString(cr.getColumnIndex("En")).toString();
                String cat_id=cr.getString(cr.getColumnIndex("Cat_Id")).toString();
                SentenceModel wmb=new SentenceModel(a[0],fa,en,cat_id);
                mArrayList.add(wmb);
                i++;

            } while (cr.moveToNext());
        }

    }
}
