package istanbuli.a1c2.books_sub;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.Gravity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import istanbuli.a1c2.R;

import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;

import istanbuli.a1c2.tabmenu.TopBarActivity;
public class Books_Sub_Activity extends AppCompatActivity {

    Sub_BOoks_Adapter pagerAdapter;
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
    int less=1;
    TextView tv_title_toolbar;
    ImageView iv_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.books_sub_tabbar);


        fnc = new functions(this);
        myPrefs = new myshareprefrence(this);
        CountLessonn = fnc.getCountLesson(myPrefs.getSelectBook());

        tv_title_toolbar=(TextView)findViewById(R.id.tv_title_book_sub);
        iv_menu=(ImageView) findViewById(R.id.iv_toolbar_books_sub);
        //Toast.makeText(getBaseContext(),myPrefs.getLesson(),Toast.LENGTH_LONG).show();

        //mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mDrawerList = findViewById(R.id.left_drawer);

        setupToolbar();
        DataModel[] drawerItem = new DataModel[CountLessonn];
        mNavigationDrawerItemTitles = new String[CountLessonn];

        if(myPrefs.getSelectBook().equalsIgnoreCase("C1"+'+'))
        {
            less=7;
        }
        for (int i = 0; i < CountLessonn; i++) {
            String Lesson=getResources().getString(R.string.lesson);
            mNavigationDrawerItemTitles[i] = Lesson + String.valueOf(i + less);
            drawerItem[i] = new DataModel(R.drawable.camera, mNavigationDrawerItemTitles[i]);
        }




        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row_drwer_sub_books, drawerItem);


        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();


        title = getResources().getStringArray(R.array.arraay_tab_sub_books);

        vp = findViewById(R.id.pager);
        TabLayout tl = findViewById(R.id.tablayout);


        tl.setupWithViewPager(vp);
        pagerAdapter = new Sub_BOoks_Adapter(getSupportFragmentManager());

        selectItem(0,String.valueOf(less));


    }

    public void LoadFragments() {

// refresh
        pagerAdapter.clearFragments();
        pagerAdapter.addFrag(new BookFragment(), title[3]);
        pagerAdapter.addFrag(new SoundsFragment(), title[1]);
        pagerAdapter.addFrag(new GrammerFragment(), title[0]);
        pagerAdapter.addFrag(new WordsFragment(), title[2]);
        vp.setAdapter(pagerAdapter);
        vp.setCurrentItem(3);


    }

    public void onBackPressed() {
        startActivity(new Intent(this,TopBarActivity.class));
        finish();
    }



    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         selectItem(position, String.valueOf(less + 1));

        }

    }

    private void selectItem(int position, String Lesson) {

        mDrawerLayout.closeDrawer(mDrawerList);
        mDrawerList.setSelectionFromTop(0, 0);
        if(position==0)
            {

                mDrawerList.setItemChecked(position, true);

                String Lesson2=String.valueOf(position+1);
                setTitle(myPrefs.getSelectBook() + ">" + mNavigationDrawerItemTitles[position]);
                myPrefs.setLesson(Lesson2);
                LoadFragments();
            }

            else
            {
                if(myPrefs.getUser().equals("Guest"))
                {
                    fnc.dialogRegister(Books_Sub_Activity.this);
                 /*   startActivity( new Intent(getBaseContext(), gmailLogin.class));*/

                }
                else
                {
                    if(fnc.getCheckUpdate(myPrefs.getSelectBook()))
                    {

                        setTitle(myPrefs.getSelectBook() + ">" + mNavigationDrawerItemTitles[position]);
                        String Lesson2=String.valueOf(position+1);
                        myPrefs.setLesson(Lesson2);
                        LoadFragments();
                    }
                    else
                    {

                        fnc.dialog_Sell_product(this
                                ,this.getResources().getString(R.string.name)+":"+myPrefs.getSelectBook(),this.getResources().getString(R.string.price)+":"+myPrefs.getPriceProduct()+" "+this.getResources().getString(R.string.price_vahed));


                    }

                }
            }

    }







    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        tv_title_toolbar.setText(mTitle);
      //  getSupportActionBar().setTitle(mTitle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
     mDrawerToggle.syncState();
    }

    void setupToolbar(){
  /*   toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

    void setupDrawerToggle() {


        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name,R.string.app_name){

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        }; // Drawer Toggle Object Made
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        iv_menu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }




    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                final int RC_SIGN_IN = 9001;
                // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
                if (requestCode == RC_SIGN_IN) {
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = fnc.handleSignInResult(task);
                    if(account !=null)
                    {

                        myPrefs.setUser(account.getEmail());
                        myPrefs.setFullName(account.getDisplayName());

                        Map<String, String> params = new HashMap<>();
                        // params.put("method", mypf.getUser());
                        params.put("method", "Register");
                        params.put("UserName",account.getEmail());
                        params.put("fullname",account.getDisplayName());

                        fnc.getRequestRegister(Books_Sub_Activity.this,params);

                    }
                    else
                    {
                       Toast.makeText(getBaseContext(),"ورود ناموفق بود",Toast.LENGTH_SHORT).show();
                    }

        }
    }


    }
