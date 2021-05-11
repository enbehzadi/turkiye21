package istanbuli.a1c2.tabmenu.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.books_sub.Books_Sub_Activity;
import istanbuli.a1c2.sentences.Activity_Main_Sentence_Sub;


public class BooksFragment extends Fragment {
    View fragmentView;

    LinearLayout l_a1,l_a2,l_b1,l_b2,l_c1,l_c11;
    Button bt_alphabetl;
    myshareprefrence myPrefs;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView=inflater.inflate(R.layout.fragment_books, null);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myPrefs=new myshareprefrence(getActivity());

        //initialize your view here for use view.findViewById("your view id")

        bt_alphabetl=(Button)fragmentView.findViewById(R.id.bt_alphabet) ;
        l_a1=(LinearLayout)fragmentView.findViewById(R.id.la_a1);

        l_a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("A1");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        l_a2=(LinearLayout)fragmentView.findViewById(R.id.la_a2);

        l_a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("A2");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });


        l_b1=(LinearLayout)fragmentView.findViewById(R.id.la_b1);
        l_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("B1");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        l_b2=(LinearLayout)fragmentView.findViewById(R.id.la_b2);
        l_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("B2");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        l_c1=(LinearLayout)fragmentView.findViewById(R.id.la_c1);
        l_c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("C1");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        l_c11=(LinearLayout)fragmentView.findViewById(R.id.la_c11);
        l_c11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showFragment(new ShopFragment());
                myPrefs.setSelectBook("C1+");

                Intent i = new Intent(getActivity(), Books_Sub_Activity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        bt_alphabetl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myPrefs.setSentence_Titel("Alphabet");

                startActivity(new Intent(getActivity(), Activity_Main_Sentence_Sub.class));
            }
        });

    }

    private void showFragment( Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.view_pager, fragment)
                .commit();
    }


}
