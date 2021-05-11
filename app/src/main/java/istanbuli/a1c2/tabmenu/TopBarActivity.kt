package istanbuli.a1c2.tabmenu

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.yarolegovich.slidingrootnav.SlideGravity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.zarinpal.ewallets.purchase.ZarinPal
import istanbuli.a1c2.R
import istanbuli.a1c2.adapter.ViewPagerAdapter
import istanbuli.a1c2.behzadi.content.myshareprefrence
import istanbuli.a1c2.functions.functions
import istanbuli.a1c2.menu.*
import istanbuli.a1c2.tabmenu.fragment.BooksFragment
import istanbuli.a1c2.tabmenu.fragment.DictionaryFragment
import istanbuli.a1c2.tabmenu.fragment.SentencesFragment
import istanbuli.a1c2.tabmenu.fragment.ShopFragment
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.activity_top_bar.*
import java.util.*


class TopBarActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener {

  private val POS_REGISTER = 0
    private val POS_UPDATE = 1
    private val POS_SHARE = 2
    private val POS_MESSAGE = 3
    private val POS_RESOURCE = 4
    private val POS_ABOUTEME = 5
    private val POS_PRIVACY = 6
    private val POS_LOGOUT =7

    protected var content: View? = null

    var fnc = functions(baseContext)
    private lateinit var screenTitles: Array<String>
    private lateinit var screenIcons: Array<Drawable?>
    private var slidingRootNav: SlidingRootNav? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_bar)



        /*var fnc3 = functions(baseContext);
        fnc3.backUpDataBase(baseContext);*/
        val toolbar = findViewById<ImageView>(R.id.iv_toolbar)

        toolbar.setOnClickListener(View.OnClickListener {
            slidingRootNav!!.openMenu()

        })
        slidingRootNav = SlidingRootNavBuilder(this)

            .withMenuOpened(false)
            .withGravity(SlideGravity.RIGHT)
            .withContentClickableWhenMenuOpened(false)
            .withSavedState(savedInstanceState)
            .withMenuLayout(R.layout.menu_left_drawer)
            .inject()
        screenIcons = loadScreenIcons()
        screenTitles = loadScreenTitles()
        val adapter = DrawerAdapter(
            Arrays.asList(
                createItemFor(POS_REGISTER),
                createItemFor(POS_UPDATE),
                createItemFor(POS_SHARE),
                createItemFor(POS_MESSAGE),
                createItemFor(POS_RESOURCE),
                createItemFor(POS_ABOUTEME),
                createItemFor(POS_PRIVACY),
                SpaceItem(48),
                createItemFor(POS_LOGOUT)
            )
        )

        adapter.setListener(this)
        val list = findViewById<RecyclerView>(R.id.list)
        list.isNestedScrollingEnabled = false
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter



        var vp=findViewById<ViewPager>(R.id.view_pager)
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager).also {


            it.addFrag(ShopFragment(this@TopBarActivity))
            it.addFrag(SentencesFragment())
            it.addFrag(DictionaryFragment())
            it.addFrag(BooksFragment())
        }

        vp.adapter = pagerAdapter
        vp.currentItem = 3
        vp.addOnPageChangeListener(object :
            androidx.viewpager.widget.ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(p0: Int) {

            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

            }

            override fun onPageSelected(p0: Int) {
                top_navigation_constraint.setCurrentActiveItem(p0)
                // top_navigation_constraint.setBackgroundColor(color())
                //  Toast.makeText(baseContext, p0.toString(),Toast.LENGTH_LONG).show()
            }

        })

        top_navigation_constraint.setNavigationChangeListener { _, position ->
            vp.setCurrentItem(position, true)
        }
        vp.currentItem = 3;
        top_navigation_constraint.setCurrentActiveItem(3)


        var menu_righte=findViewById<com.gauravk.bubblenavigation.BubbleToggleView>(R.id.c_item_other)

        menu_righte.setOnClickListener(View.OnClickListener {
            // getactivi.slidingRootNav.openMenu()

        })
        val  mysh = myshareprefrence(this);


        val tv_user_name = findViewById<View>(R.id.tv_user_name) as TextView
        tv_user_name.setText(mysh.getUser())

        if (mysh._Check_welcome&&!mysh.user.equals("Guest"))
        {
            var fnc = functions(this);
            fnc.dialog_YourWelcome(this, mysh.fullName)
            mysh.set_Check_welcome(false);
        }


        if (intent.data != null) {
            ZarinPal.getPurchase(this).verificationPayment(
                intent.data
            ) { isPaymentSuccess, refID, paymentRequest ->

                if(isPaymentSuccess)
                {

                    var fnc = functions(this);
                    //Toast.makeText(baseContext, baseContext.resources.getString(R.string.pay_successfully), Toast.LENGTH_LONG).show()

                    val params: MutableMap<String, String> = HashMap()
                    params["method"] = "buy"
                    params["UserName"] = mysh.getUser()
                    params["Pruduct"] =mysh.for_Buy
                    params["Price"] ="100"
                    params["Id_Price"] =refID
                    fnc.getRequestUpdate_shop(this@TopBarActivity, params)

                }
                else
                {
                  //  Toast.makeText(baseContext, baseContext.resources.getString(R.string.pay_error), Toast.LENGTH_LONG).show()
                }
            }
        }
       if(!mysh.getshow_privacy())
        {
            fnc.dialog_Alert(
                this@TopBarActivity,
                resources.getString(R.string.privacy_title),
                resources.getString(
                    R.string.privacy_message
                )
            );
            mysh.setshow_privacy(true)
        }



    }
    override fun onItemSelected(position: Int) {
        if (position == POS_LOGOUT) {
            finish()
        }

        var fnc = functions(this);
        var mypfc = myshareprefrence(this);

        slidingRootNav!!.closeMenu()
        when (position) {
            POS_REGISTER -> {
                if (mypfc.user.equals("Guest")) {
                    fnc.dialogRegister(this@TopBarActivity)

                } else {

                    content = findViewById(android.R.id.content);
                    fnc.Snackbar(content, resources.getString(R.string.register), resources.getString(R.string.register_alarm_last))
                }


            }
            POS_UPDATE -> {
                if (mypfc.user.equals("Guest")) {
                    fnc.dialogRegister(this@TopBarActivity)

                } else {


                    val params: MutableMap<String, String> = HashMap()

                    params["method"] = "Register"
                    params["UserName"] = mypfc.user
                    params["fullname"] = mypfc.fullName

                    fnc.getRequestRegister(this@TopBarActivity, params)


                }
            }
            POS_SHARE -> {
                fnc.shareApp_google_play(this@TopBarActivity)
            }
            POS_MESSAGE -> {
                fnc.commentApp_google_play(this@TopBarActivity)
            }
            POS_RESOURCE -> {
                fnc.dialog_Alert(
                    this@TopBarActivity,
                    resources.getString(R.string.resource_title),
                    resources.getString(
                        R.string.resource_message
                    )
                )
            }
            POS_ABOUTEME -> {
                fnc.dialog_Alert(
                    this@TopBarActivity,
                    resources.getString(R.string.aboutme_title),
                    resources.getString(
                        R.string.aboutme_message
                    )
                )
            }
            POS_PRIVACY -> {
                fnc.dialog_Alert(
                    this@TopBarActivity,
                    resources.getString(R.string.privacy_title),
                    resources.getString(
                        R.string.privacy_message
                    )
                )
            }
            POS_LOGOUT -> {

                this.finish()
            }

            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
        slidingRootNav!!.closeMenu()
        val selectedScreen: Fragment = CenteredTextFragment.createFor(screenTitles[position])
       //showFragment(selectedScreen)
    }



    private fun createItemFor(position: Int): DrawerItem<*> {
        return SimpleItem(screenIcons[position], screenTitles[position])
            .withIconTint(color(R.color.textColorSecondary))
            .withTextTint(color(R.color.textColorPrimary))
            .withSelectedIconTint(color(R.color.colorAccent))
            .withSelectedTextTint(color(R.color.colorAccent))
    }

    private fun loadScreenTitles(): Array<String> {
        return resources.getStringArray(R.array.ld_activityScreenTitles)
    }

    private fun loadScreenIcons(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.ld_activityScreenIcons)
        val icons = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id)
            }
        }
        ta.recycle()
        return icons
    }

    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

    @Override
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val RC_SIGN_IN = 9001
        val  myPrefs = myshareprefrence(this);
        val  fnc = functions(this);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.

            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account: GoogleSignInAccount? =null
            account = fnc.handleSignInResult(task)
            if (account != null) {


                myPrefs.user = account.email
                myPrefs.fullName = account.displayName

                val params: MutableMap<String, String> = HashMap()
                // params.put("method", mypf.getUser());
                params["method"] = "Register"
                params["UserName"] = account.getEmail().toString()
                params["fullname"] = account.getDisplayName().toString()
                fnc.getRequestRegister(this@TopBarActivity, params)
            } else {
                Toast.makeText(getBaseContext(), resources.getString(R.string.Error), Toast.LENGTH_SHORT).show()
            }
        }
    }
}

