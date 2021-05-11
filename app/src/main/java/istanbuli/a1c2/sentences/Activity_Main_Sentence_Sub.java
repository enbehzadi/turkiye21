package istanbuli.a1c2.sentences;
import android.os.Bundle;

import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import istanbuli.a1c2.R;
import istanbuli.a1c2.adapter.ViewPagerAdapter;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;

public class Activity_Main_Sentence_Sub extends AppCompatActivity {


    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    Toolbar toolbar;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    myshareprefrence myPrefs;
    ActionBarDrawerToggle mDrawerToggle;
    functions fnc;
    String[] title;
    ViewPager vp;
    int CountLessonn = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentence_sub_tab_main);
        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tablayout);

        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);


        // add your fragments



        // set adapter on viewpager




        // attach tablayout with viewpager
        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter    (getSupportFragmentManager());

        // add your fragments
        pagerAdapter.addFrag(new Fragment_Sentences_Words(), getResources().getString(R.string.review));

        // set adapter on viewpager
        viewPager.setAdapter(pagerAdapter);

        TextView tv_title=findViewById(R.id.tv_title_sentences_sub);
        tv_title.setText(new myshareprefrence(getBaseContext()).getSentence_Titel());

    }



    public void onBackPressed() {

        finish();
    }
}
