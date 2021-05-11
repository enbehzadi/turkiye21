package istanbuli.a1c2.tabmenu.fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.adapter.Adapter_dictionary;
import istanbuli.a1c2.books_sub.WordModel;
import istanbuli.a1c2.functions.functions;

import static istanbuli.a1c2.R.id.myRecyclerView;

public class DictionaryFragment extends Fragment {
    View fragmentView;
    private RecyclerView card_recyclerView;
    private Adapter_dictionary mAdapter;
    private ArrayList<WordModel> mArrayList;
    SearchView sv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        fragmentView=inflater.inflate(R.layout.fragment_dictionary, null);
        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        initUI();

       LoadList("a");
    }
    private void initUI()
    {
        card_recyclerView = fragmentView.findViewById(myRecyclerView);
        sv=fragmentView.findViewById(R.id.sv);
        sv.setQueryHint(getResources().getString(R.string.serch_dictionary_hint));
        card_recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        card_recyclerView.setLayoutManager(layoutManager);


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String st) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.equalsIgnoreCase(""))
                {

                    mAdapter.getFilter().filter("a");
                /*    byte[] data = new byte[0];
                    try {
                        data = "a".getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    LoadList(base64);*/
                }
                else
                {
                    mAdapter.getFilter().filter(s);
                  /*  byte[] data = new byte[0];
                    try {
                        data = s.getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                    LoadList(base64);*/

                }

                return false;
            }
        });
    }


    public void LoadList(String st) {

        mArrayList = new ArrayList<>();


        functions fnc=new functions(getContext());
        String row;

        SQLiteDatabase db;
        db = fnc.Load_Database(getActivity());


        //   Toast.makeText(getBaseContext(),b,Toast.LENGTH_LONG).show();




/*        String areaTyp = "SELECT *  FROM Tr_Tbl_dictionary WHERE tur LIKE "+"'"+st+"%'"+ " OR fa LIKE "+"'%"+st+"%'"+"  limit 100";*/

        String areaTyp = "SELECT *  FROM Tr_Tbl_dictionary ";
        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {
            do {

                String fa=cr.getString(cr.getColumnIndex("fa")).toString();

                String en=cr.getString(cr.getColumnIndex("en")).toString();

                byte[] tur_byte=cr.getBlob(cr.getColumnIndex("tur"));
                String tur="";
                byte[] data = Base64.decode(tur_byte, Base64.DEFAULT);
                try {
                    tur = new String(data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                WordModel wmb=new WordModel(tur,fa,en);
                mArrayList.add(wmb);

            } while (cr.moveToNext());
        }
        mAdapter = new Adapter_dictionary (mArrayList);
        card_recyclerView.setAdapter(mAdapter);

    }

}




