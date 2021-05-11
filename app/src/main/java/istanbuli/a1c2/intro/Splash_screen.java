package istanbuli.a1c2.intro;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;
import istanbuli.a1c2.tabmenu.TopBarActivity;
public class Splash_screen extends AppCompatActivity {
    functions fnc;
    Boolean checkUpdate;
    ArrayList<String>BooksSell;
    int UpdateCount=0;
    int UpdateCount2=0;
    myshareprefrence myPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        setContentView(R.layout.activity_splash_screen);
        fnc=new functions(this);

        myPrefs=new myshareprefrence(this);
        checkUpdate=true;



        if(myPrefs.getUser()!="Guest")
        {
            if(myPrefs.get_Check_Update())
            {
                Update();
            }else
            {
                Thread myThread = new Thread()
                {
                    @Override
                    public void run() {
                        try {
                            sleep(1200);
                            startActivity(new Intent(getBaseContext(), TopBarActivity.class));
                                finish();
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                myThread.start();
            }
        }
        else
        {


                    myPrefs = new myshareprefrence(getBaseContext());
                    if(!myPrefs.getStart())
                    {

                        Thread myThread = new Thread()
                        {
                            @Override
                            public void run() {
                                try {
                                    sleep(1200);


                                    startActivity(new Intent(getBaseContext(), TopBarActivity.class));
                                    finish();

                                }
                                catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        myThread.start();





                    }
                    else
                    {

                        startActivity(new Intent(this,MainScreen.class));
                        finish();
                    }


            }


 }
public void Update()
{
    BooksSell=fnc.getListsell();
    for(int i=0;i<BooksSell.size();i++)
    {
        if(!fnc.getCheckUpdate(BooksSell.get(i)))
        {
            checkUpdate=false;
            UpdateCount2++;
            Map<String, String> params = new HashMap<>();
            params.put("method", "Update");
            params.put("UserName",myPrefs.getUser());
            params.put("BookSell",BooksSell.get(i));
            getRequestUpdate(Splash_screen.this,params);

        }
        if(i==BooksSell.size()-1&&checkUpdate)
        {
           startActivity(new Intent(this, TopBarActivity.class));
            finish();
        }




    }


;

}


    public void getRequestUpdate(final Activity activity, Map<String, String> params)
    {
        if (fnc.isNetworkConnected(activity)) {
            fnc.volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(getBaseContext());

                    if (fnc.parsinJson(js)) {
                        UpdateCount++;
                        if(UpdateCount==UpdateCount2)
                        {
                            startActivity(new Intent(activity, TopBarActivity.class));
                            finish();
                            myPrefs.set_Check_Update(false);
                        }


                    } else {
                        Toast.makeText(getBaseContext(), "خطا", Toast.LENGTH_LONG).show();
                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            fnc.dialog_check_internet(activity);
        }
    }
}
