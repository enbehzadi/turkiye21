package istanbuli.a1c2.books_sub;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import istanbuli.a1c2.R;
import istanbuli.a1c2.adapter.adapter_grammer_list_lesson;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;
public class GrammerFragment extends Fragment {
    View fragmentView;


    SQLiteDatabase db;

    myshareprefrence myPrefs;
    String bookName, lng;
    ArrayList<String> description_grammer = new ArrayList<>();

    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView=inflater.inflate(R.layout.list_grammer_lesson, null);
        return fragmentView;

    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI();
       initData();
    }

    private void initUI() {
        expListView = fragmentView.findViewById(R.id.ex_list_grammer);
    }

    private void initData() {
        myPrefs = new myshareprefrence(getActivity());

        bookName = myPrefs.getSelectBook();
        lng = myPrefs.getSelectBook();
        LoadListLesson();
        adapter_grammer_list_lesson valueAdapter = new adapter_grammer_list_lesson(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(valueAdapter);

        functions fnc=new functions(getContext());
        if(!myPrefs.getshow_grammer()&&fnc.getSystemLocale().equalsIgnoreCase("en"))
        {

            /*fnc.dialog_Alert(getActivity(),getContext().getResources().getString(R.string.alarm),getContext().getResources().getString(R.string.persian_active_grammer));*/
            myPrefs.setshow_grammer(true);
        }
    }


    public void LoadListLesson() {

        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();
        String  TableName;
        String lesson;
        if(bookName.equalsIgnoreCase("C1+"))
        {
            TableName="'Tr_Tbl_Grammer_C1+'";
            Integer  lesson2 = Integer.valueOf(myPrefs.getLesson())+6;
            lesson = String.valueOf(lesson2);
        }
        else
        {
            TableName = "Tr_Tbl_Grammer_" + bookName.trim();
            lesson = myPrefs.getLesson();
        }

        functions fn=new functions(getActivity());
        db=fn.Load_Database(getActivity());

        String areaTyp = "SELECT *  FROM "
                + TableName + " where Lesson_Number="
                + lesson.trim();
        Cursor cr1 = db.rawQuery(areaTyp, null);

        if (cr1.moveToFirst()) {
            do {

                listDataHeader.add(cr1.getString(cr1.getColumnIndex("TgFa")));
                description_grammer.add(cr1.getString(cr1.getColumnIndex("Fa")));
            } while (cr1.moveToNext());
        }

        cr1.close();

        for(int i=0;i<listDataHeader.size();i++)
        {
            List<String> comingSoon2 = new ArrayList<>();

            comingSoon2.add(description_grammer.get(i));
            listDataChild.put(listDataHeader.get(i),comingSoon2);
        }
    }



}
