package istanbuli.a1c2.functions;


import android.app.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.scottyab.aescrypt.AESCrypt;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListenerChangePhoneNumber;
import com.thecode.aestheticdialogs.OnDialogClickListenerRegisterGmail;
import com.thecode.aestheticdialogs.OnDialogClickListenerSell;
import com.thecode.aestheticdialogs.OnDialogClickListenerSendVerifyCode;
import com.thecode.aestheticdialogs.OnDialogClickListenercancel;
import com.thecode.aestheticdialogs.OnDialogClickListenerok;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import istanbuli.a1c2.CustomRequest;
import istanbuli.a1c2.Database;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.intro.Splash_screen;
import istanbuli.a1c2.tabmenu.TopBarActivity;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import static android.content.ContentValues.TAG;


public class functions {
    Context cnt;
    Activity actv;
    public functions(Context cntx) {
        cnt=cntx;
    }
    public void volleyRequest(final VolleyCallBack callBack, String url, final Map<String,String> json) {
        RequestQueue mRequestQueue;
        final JSONObject[] jsn = new JSONObject[1];
        mRequestQueue = Volley.newRequestQueue(cnt);

        final ProgressDialog pDialog;
        pDialog = new ProgressDialog(cnt);
        pDialog.setMessage(cnt.getResources().getString(R.string.get_data_server));
        pDialog.show();
        pDialog.setCancelable(false);

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsn[0] =response;
                    pDialog.hide();
                    callBack.onSuccess(jsn[0]);

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(cnt,cnt.getResources().getString(R.string.error_get_data_server),Toast.LENGTH_SHORT).show();
                    pDialog.cancel();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(cnt,cnt.getResources().getString(R.string.error_get_data_server),Toast.LENGTH_SHORT).show();
                pDialog.cancel();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = json;

                return map;
            }
        };
        mRequestQueue.add(jsObjRequest);
        //return  jsn[0];

    }


public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }

    public Boolean getCheckUpdate(String bookName)
    {
        String b = "Tr_Tbl_Words_" + bookName.trim();
        SQLiteDatabase db = null;
        db = Load_Database(cnt);
        String Count = "";
        String Lesson_Number="2";


        if(bookName.equalsIgnoreCase("C1"+'+'))
        {
            b="'Tr_Tbl_Words_C1+'";
            Lesson_Number="8";

        }
        String areaTyp = "SELECT *  FROM " + b + " where Lesson_Number="+Lesson_Number;

        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {

            do {
                return true;
            }while (cr.moveToNext());

        }
        return false;
    }

public int getCountLesson(String bookName)
{
    SQLiteDatabase db = null;
    db = Load_Database(cnt);
    String Count = "";
    String areaTyp = "SELECT *  FROM Tr_Tbl_Books where name='"+bookName+"'";

    Cursor cr = db.rawQuery(areaTyp, null);

    if (cr.moveToFirst()) {

        do {
            Count = cr.getString(cr.getColumnIndex("count_lesson"));
        }while (cr.moveToNext());

    }
   return Integer.valueOf(Count);
}
    public ArrayList<String> getListsell()
    {
        ArrayList<String>ListSell;
        ListSell=new ArrayList<>();
        SQLiteDatabase db = null;
        db = Load_Database(cnt);
        String areaTyp = "SELECT *  FROM Tr_Tbl_Sells";

        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {

            do {
                ListSell.add(cr.getString(cr.getColumnIndex("Product")));

            }while (cr.moveToNext());

        }
        return ListSell;
    }
    public String  getPrice(String Bookname)
    {

        if(Bookname.length()>2)
        {
            Bookname="C1\'+\'";
        }
        String b = "Tr_Tbl_Words_" + Bookname.trim();



        String Price="0";

        SQLiteDatabase db = Load_Database(cnt);
        String areaTyp = "SELECT *  FROM Tr_Tbl_Shop" ;

        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {

            do {
                return   cr.getString(cr.getColumnIndex("Price"));

            }while (cr.moveToNext());

        }
        return Price;
    }
    public Boolean CheckUpdate(String Bookname)
    {
        String b;
        String Lesson_number="2";
        if(Bookname.length()>2)
        {


            b="'Tr_Tbl_Words_C1+'";

            Lesson_number="7";
        }
        else
        {
             b = "Tr_Tbl_Words_" + Bookname.trim();
        }





        SQLiteDatabase db = Load_Database(cnt);
        String areaTyp = "SELECT *  FROM " + b + " where Lesson_Number="+Lesson_number;

        Cursor cr = db.rawQuery(areaTyp, null);

        if (cr.moveToFirst()) {

            do {
                return true;
            }while (cr.moveToNext());

        }
        return false;
    }
    public SQLiteDatabase Load_Database(Context cntx) throws Error {
        SQLiteDatabase db;
        Database myDbHelper;

        myDbHelper = new Database(cntx);

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
        db = myDbHelper.getReadableDatabase();
        return  db;

    }

    public void Query_Run(List<List<String>> qri) {
        SQLiteDatabase db = Load_Database(cnt);
        for(int i=0;i<qri.size();i++)
        {
            for(int j=0;j<qri.get(i).size();j++)
            {

                db.execSQL(qri.get(i).get(j));

            }

        }
    }

    public void UpdateRecord(String Tbl_Name,String Name_Record,String New_Value,Integer Id_Record)
    {
        String my_query="UPDATE" +  Tbl_Name +" "+"SET"+ " "+Name_Record +"="+ New_Value +" "+ "WHERE _Id ="+Id_Record ;

        SQLiteDatabase db = Load_Database(cnt);
        db.execSQL(my_query);

    }
    public Boolean parsinJson(JSONObject response) {
        ArrayList<String> Record_insert = new ArrayList<>();
            ArrayList<String> Tables_Create_query = new ArrayList<>();
            List<List<String>> listOfLists = new ArrayList<>();

            try {

                JSONArray names = response.names();
                ArrayList<JSONObject> keys = new ArrayList<>();

                ArrayList<String> Tabele_Names = new ArrayList<>();
                ;
                ArrayList<JSONArray> Tabele_Keys = new ArrayList<>();
                for (int i = 0; i < names.length(); i++) {
                    keys.add(response.getJSONObject(names.get(i).toString()));

                    Iterator<String> iter = keys.get(i).keys();


                    while (iter.hasNext()) {

                        String tb_name = iter.next();

                        Tabele_Names.add(tb_name);
                        try {
                           /* if(tb_name.equalsIgnoreCase("Tr_Tbl_Grammer_C1+"))
                            {
                                tb_name="Tr_Tbl_Grammer_C1'+'";
                            }
                            if(tb_name.equalsIgnoreCase("Tr_Tbl_Words_C1+"))
                            {
                                tb_name="Tr_Tbl_Words_C1'+'";
                            }*/
                            Tabele_Keys.add(keys.get(i).getJSONArray(tb_name));


                        } catch (JSONException e) {
                            // Something went wrong!
                        }
                    }

                }

                for (int i = 0; i < Tabele_Keys.size(); i++) {
                    String tb_qr = "CREATE TABLE IF NOT EXISTS ";
                    tb_qr += Tabele_Names.get(i) + " " + "(";
                    Boolean addRecord = true;

                    for (int j = 0; j < Tabele_Keys.get(i).length(); j++) {
                        JSONObject js = Tabele_Keys.get(i).getJSONObject(j);
                        // addAllFacts(js);
                        Iterator<String> iter = js.keys();

                        JSONObject val2;

                        Iterator<String> bb;
                        while (iter.hasNext()) {
                            String key = iter.next();
                            val2 = js.getJSONObject(key);

                            bb = val2.keys();

                            String query = "INSERT INTO " + " " + Tabele_Names.get(i) + " " + "VALUES " + "(";

                            while (bb.hasNext()) {

                                String Id = bb.next();
                                if (addRecord) {
                                    if (Id.equalsIgnoreCase("_Id")) {
                                        tb_qr += "`" + Id + "`" + " INT(11) NOT NULL,";
                                    } else {
                                        tb_qr += "`" + Id + "`" + " TEXT,";
                                    }


                                }


                                JSONObject obj = js.getJSONObject(key);

                                String remove_char = obj.optString(Id).replaceAll("'", "");
                                String remove_char2 = remove_char.replaceAll(",", "__");

                                String val4 = remove_char2;
                                if (val4.equalsIgnoreCase("null")) {
                                    query += "Null,";
                                }
                               /* else if(m==0)
                                {
                                    query+="null,";
                                }*/
                                else {
                                    query += "'" + val4 + "' " + ",";

                                }


                            }
                            String gg = method(query);
                            query = gg;

                            query += ");";
                            Record_insert.add(query);

                        }
                        addRecord = false;


                    }
                    String gg = method(tb_qr);
                    Tables_Create_query.add(gg + " );");
                }


                listOfLists.add(0, Tables_Create_query);
                listOfLists.add(1, Record_insert);
                Query_Run(listOfLists);

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
}
public Boolean start_Quick (JSONObject response){




        try {
            String js = response.getJSONObject("start_quick").getJSONArray("Tr_Tbl_Config").getJSONObject(0).getJSONObject("1").getString("query");

            SQLiteDatabase db = Load_Database(cnt);

            String[] splite1 = js.split(";");
            ArrayList<String> InsertsMain = new ArrayList<String>();
            ArrayList<String> Create_Table = new ArrayList<String>();
            ArrayList<String> InsertsRecords = new ArrayList<String>();
            for (int i = 0; i < splite1.length; i++) {
                if (splite1[i].contains("CREATE")) {
                    Create_Table.add(splite1[i]);
                } else if (splite1[i].contains("INSERT")) {
                    InsertsMain.add(splite1[i]);
                } else {

                }
            }
            for (int j = 0; j < InsertsMain.size(); j++) {
                String[] a = InsertsMain.get(j).split("VALUES");
                String b = a[1];
                String[] c = b.split("\\),");
                for (int l = 0; l < c.length; l++) {
                    String d = a[0] + " " + c[l] + ");";
                    InsertsRecords.add(d);
                }


            }
            for (int i = 0; i < Create_Table.size(); i++) {
                db.execSQL(Create_Table.get(i));
            }
            for (int i = 0; i < InsertsMain.size(); i++) {
                db.execSQL(InsertsMain.get(i));
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean start_Quick3(String response){
        String js = response;
            SQLiteDatabase db = Load_Database(cnt);

            String[] splite1 = js.split(";");
            ArrayList<String> InsertsMain = new ArrayList<String>();
            ArrayList<String> Create_Table = new ArrayList<String>();
            ArrayList<String> InsertsRecords = new ArrayList<String>();
            for (int i = 0; i < splite1.length; i++) {
                if (splite1[i].contains("CREATE")) {
                    Create_Table.add(splite1[i]);
                } else if (splite1[i].contains("INSERT")) {
                    InsertsMain.add(splite1[i]);
                } else {

                }
            }


        if(Build.VERSION.SDK_INT>22)
        {
            for (int i = 0; i < Create_Table.size(); i++) {
                db.execSQL(Create_Table.get(i));
            }
            for (int i = 0; i < InsertsMain.size(); i++) {
                if(InsertsMain.get(i).contains("Tr_Tbl_Dictionary")) {

                }
                else if (InsertsMain.get(i).contains("Tr_Tbl_Sentence_Dictionary"))
                {

                }
                else
                {
                    db.execSQL(InsertsMain.get(i));
                }

            }
        }
        else
        {
            for (int i = 0; i < Create_Table.size(); i++) {
               String b= Create_Table.get(i).replace("NOT NULL PRIMARY KEY AUTOINCREMENT"," ");
                db.execSQL(b);
            }
            for (int i = 0; i < InsertsMain.size(); i++) {
                if(InsertsMain.get(i).contains("Tr_Tbl_Dictionary"))
                {

                        /*String[] a = InsertsMain.get(i).split("VALUES");
                        String b = a[1];
                        String[] c = b.split("'\\),");
                        for (int l = 0; l < c.length; l++) {
                            String d = a[0] + "  VALUES ";









                            if(l==c.length-1)
                            {
                                d+=c[l] + ";";
                            }else
                            {
                                d+=c[l] + "');";
                                db.execSQL(d);
                            }


                        }*/



                }
                else if(InsertsMain.get(i).contains("Tr_Tbl_Sentence_Dictionary"))
                {
                    /*String[] a = InsertsMain.get(i).split("VALUES");
                    String b = a[1];
                    String[] c = b.split("\\),");
                    for (int l = 0; l < c.length; l++) {
                        String d = a[0] + "  VALUES ";


                        if(!c[l].substring(c[l].length() - 1).contains(")")) {
                            d += c[l] + ");";
                        }
                        else {
                            d += c[l] + ";";
                        }


                        db.execSQL(d);*/
                    //}
                }
                else
                {
                    db.execSQL(InsertsMain.get(i));
                }

            }
        }

           /* for (int j = 0; j < InsertsMain.size(); j++) {
                String[] a = InsertsMain.get(j).split("VALUES");
                String b = a[1];
                String[] c = b.split("\\),");
                String d = "";
                for (int l = 0; l < c.length; l++) {

                    if(!c[l].substring(c[l].length() - 1).contains(")"))
                    {
                        if(c[l].contains("(AD 354-430"))
                        {
                            d = a[0] + " VALUES " + c[l] + "));";
                        }
                        else
                        {
                            d = a[0] + " VALUES " + c[l] + ");";
                            db.execSQL(d);

                        }


                    }
                    else {
                        d = a[0] + " VALUES " + c[l] + ";";
                        db.execSQL(d);
                    }
                   
                  

                  



                }


            }*/


            return true;

    }
    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == ',') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }

    public interface VolleyCallBack {
         void onSuccess(JSONObject js);

    }

    public void goActivity(Context cntx, Class cls) {
        Intent i = new Intent(cntx, cls);
        cntx.startActivity(i);

    }


    public void Snackbar(View View, String titel, String msg) {

        // make snackbar
        Snackbar mSnackbar = Snackbar.make(View, msg, Snackbar.LENGTH_SHORT);
// get snackbar view
        View mView = mSnackbar.getView();
// get textview inside snackbar view
        TextView mTextView = (TextView) mView.findViewById(com.google.android.material.R.id.snackbar_text);
// set text to center
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        else
            mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
// show the snackbar
        mSnackbar.show();

    }


    public GoogleSignInAccount handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            return account;
            // Signed in successfully, show authenticated UI.
            // updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.

            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            // updateUI(null);
            return  null;
        }
    }
    public void loginGmail(Activity actv)
    {

        final int RC_SIGN_IN = 9001;
        GoogleSignInClient mGoogleSignInClient = null;
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(cnt, gso);

        Intent signInIntent = mGoogleSignInClient.getSignInIntent();

        actv.startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    public void dialog_check_internet(Activity activity)
    {
        new AestheticDialog.Builder(activity, DialogStyle.CONNECTIFY, DialogType.ERROR)
                .setTitle("عدم دسترسی به اینترنت")
                .setMessage("اتصالات به اینترنت را بررسی کنید")
                .setDuration(2000)
                .setOnClickListenercancel(new OnDialogClickListenercancel() {
                    @Override
                    public void onClick(@NotNull AestheticDialog.Builder dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void dialogRegister(final Activity mn)
    {
        new AestheticDialog.Builder(mn, DialogStyle.REGISTER_GMAIL, DialogType.REGISTER).setTitle(mn.getResources().getString(R.string.register)).setMessage(mn.getResources().getString(R.string.register_alarm)).setAnimation(DialogAnimation.SLIDE_DOWN)  .setCancelable(false)       .setonClickListenerRegisterGmail(new OnDialogClickListenerRegisterGmail() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {


                loginGmail(mn);

                dialog.dismiss();
            }
        }).setOnClickListenercancel(new OnDialogClickListenercancel() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {
                dialog.dismiss();


            }
        }).show();
    }





    public void dialog_erorr_phoneNumber(final Activity mn)
    {
        new AestheticDialog.Builder(mn, DialogStyle.TOASTER, DialogType.ERROR)
                .setTitle("خطا")
                .setMessage("شماره وارد شده اشتباه است")
                .setDuration(2000)
                .setOnClickListenercancel(new OnDialogClickListenercancel() {
                    @Override
                    public void onClick(@NotNull AestheticDialog.Builder dialog) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public void dialog_Sell_product(final Activity mn, final String Titele, final String price)
    {
        new AestheticDialog.Builder(mn, DialogStyle.SELL, DialogType.SELL)
                .setTitle(Titele)
                .setMessage(price)

                .setOnClickListenercancel(new OnDialogClickListenercancel() {
                    @Override
                    public void onClick(@NotNull AestheticDialog.Builder dialog) {
                        dialog.dismiss();
                    }
                }).setonClickListenerSell(new OnDialogClickListenerSell() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {
                dialog.dismiss();
                String []mount=price.split(":");
                String []mount2=mount[1].split(" ");
                buy_product(Titele,Long.valueOf(mount2[0]));

            }
        }).show();
    }
    public void dialog_Success(Activity mn,String Titele,String price)
    {
        new AestheticDialog.Builder(mn, DialogStyle.TOASTER, DialogType.SUCCESS)
                .setTitle(Titele)
                .setMessage(price)

                .setOnClickListenercancel(new OnDialogClickListenercancel() {
                    @Override
                    public void onClick(@NotNull AestheticDialog.Builder dialog) {
                        dialog.dismiss();
                    }
                }).setonClickListenerSell(new OnDialogClickListenerSell() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {


            }
        }).show();
    }
    public void dialog_YourWelcome(Activity acv,String FullNmae)
    {

            dialog_Success(acv,acv.getResources().getString(R.string.register_succsefull),acv.getResources().getString(R.string.hi)+" "+FullNmae+" " +acv.getResources().getString(R.string.dear));

    }


    public String getSystemLocale()
    {
        String lng;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            lng=LocaleList.getDefault().get(0).getLanguage();
        }
        else
            {
            lng=Locale.getDefault().getLanguage();
        }
        return lng;

    }
    public void dialog_Alert(Activity acv,String Title,String Message)
    {
        new AestheticDialog.Builder(acv, DialogStyle.ALERT, DialogType.ALERT)
                .setTitle(Title)
                .setMessage(Message).show();

    }
    public void dialogVerifySms(final Activity mn)
    {
        new AestheticDialog.Builder(mn, DialogStyle.REGISTER_USER, DialogType.REGISTER).setAnimation(DialogAnimation.SPLIT)  .setCancelable(false).setOnClickListenerok(new OnDialogClickListenerok() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {
                if(isNetworkConnected(cnt))
                {
                    if(dialog.validCellPhone())
                    {
                        dialog.playProgressbar();

                    }
                    else
                    {
                        dialog_erorr_phoneNumber(mn);
                    }
                }
                else
                {
                    dialog_check_internet(mn);
                }

            }
        }).setonClickListenerChangePhoneNumber(new OnDialogClickListenerChangePhoneNumber() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {

                dialog.enablePhonenUMber();
                dialog.showBtnsend();

            }
        }).setOnClickListenercancel(new OnDialogClickListenercancel() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {

                dialog.dismiss();

            }
        }).setonClickListenerSendVerifyCode(new OnDialogClickListenerSendVerifyCode() {
            @Override
            public void onClick(@NotNull AestheticDialog.Builder dialog) {


                //dialog.dismiss();

            }
        })

                .show();
    }


    public void getRequestRegister(final Activity activity, Map<String, String> params)
    {
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(cnt);

                    if (parsinJson(js)) {

                        myPrefs.setStart(false);
                        myPrefs.set_Check_Update(true);
                        activity.startActivity(new Intent(cnt, Splash_screen.class));
                        activity.finish();
                    } else {
                        Toast.makeText(cnt, "خطا", Toast.LENGTH_LONG).show();
                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }
    public void getRequestUpdate(final Activity activity, Map<String, String> params)
    {
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(cnt);

                    if (parsinJson(js)) {

                        myPrefs.set_Check_Update(false);

                    } else {
                        Toast.makeText(cnt, cnt.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }
    public void getRequestStart(final Activity activity, Map<String, String> params)
    {
        actv=activity;
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {

                    try {
                        String code = js.getJSONObject("start_quick").getJSONArray("Tr_Tbl_Config").getJSONObject(0).getJSONObject("1").getString("key_db");
                        String MerchantID = js.getJSONObject("start_quick").getJSONArray("Tr_Tbl_Config").getJSONObject(0).getJSONObject("1").getString("MerchantID");

                        String PriceProduct = js.getJSONObject("start_quick").getJSONArray("Tr_Tbl_Config").getJSONObject(0).getJSONObject("1").getString("Price_Product");
                     myshareprefrence mypfc=new myshareprefrence(cnt);

                     mypfc.setMerchantID(MerchantID);
                        mypfc.setPriceProduct(PriceProduct);
                       String encryptedMsg = AESCrypt.decrypt(code, loadAssetFile(activity,"encript.txt"));


                        if(start_Quick3( encryptedMsg))
                        {
                            final myshareprefrence myPrefs;
                            myPrefs = new myshareprefrence(cnt);

                            myPrefs.setStart(false);

                            activity.startActivity(new Intent(cnt, TopBarActivity.class));
                            activity.finish();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (GeneralSecurityException e) {
                        e.printStackTrace();
                    }



                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }
    public void getRequestUpdate_shop(final Activity activity, Map<String, String> params)
    {
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(cnt);

                    if (parsinJson(js)) {

                        myPrefs.setStart(false);
                        myPrefs.set_Check_Update(true);
                        activity.startActivity(new Intent(cnt, Splash_screen.class));
                        activity.finish();
                    } else {
                        Toast.makeText(cnt, cnt.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }

    public void getstart_quick_normal(final Activity activity, Map<String, String> params)
    {
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(cnt);

                    if (parsinJson(js)) {

                        myPrefs.setStart(false);

                        activity.startActivity(new Intent(cnt, TopBarActivity.class));
                        activity.finish();
                        myPrefs.set_Check_Update(true);
                    } else {
                        Toast.makeText(cnt, cnt.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }
    public static String loadAssetFile(Context context, String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName)));
            StringBuilder out= new StringBuilder();
            String eachline = bufferedReader.readLine();
            while (eachline != null) {
                out.append(eachline);
                eachline = bufferedReader.readLine();
            }
            return out.toString();
        } catch (IOException e) {
            Log.e("Load Asset File",e.toString());
        }
        return null;
    }
    public void getstart_quick(final Activity activity, Map<String, String> params)
    {
        if (isNetworkConnected(cnt)) {
            volleyRequest(new functions.VolleyCallBack() {
                @Override
                public void onSuccess(JSONObject js) {
                    final myshareprefrence myPrefs;
                    myPrefs = new myshareprefrence(cnt);

                    if (start_Quick(js)) {



                        activity.startActivity(new Intent(cnt, TopBarActivity.class));
                        activity.finish();
                        myPrefs.set_Check_Update(true);
                        myPrefs.setStart(false);
                    } else {
                        Toast.makeText(cnt, cnt.getResources().getString(R.string.error), Toast.LENGTH_LONG).show();                    }


                }
            }, "http://istanbulibedoon.ir/getdata.php", params);

        } else {
            dialog_check_internet(activity);
        }
    }






    public void buy_product(String product,Long mount)
    {
        myshareprefrence mypfc=new myshareprefrence(cnt);
        PaymentRequest payment = ZarinPal.getPaymentRequest();


        payment.setMerchantID(mypfc.getMerchantID());
        payment.setAmount(mount);
        payment.setDescription(product);
        payment.setCallbackURL("app://app");
       // payment.setMobile("09355106005");
        payment.setEmail(mypfc.getUser());
        payment.isZarinGateEnable(false);


        ZarinPal.getPurchase(cnt).startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {

                cnt.startActivity(intent);
            }
        });
    }


    public void shareApp_google_play(Activity actv)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =cnt.getResources().getString(R.string.link_download_google_play)+cnt.getPackageName() ;
        String shareSub =cnt.getResources().getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        actv.startActivity(Intent.createChooser(sharingIntent, cnt.getResources().getString(R.string.download_app)));
    }
    public void shareApp_cafebazaar(Activity actv)
    {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =cnt.getResources().getString(R.string.link_download_caffe_bazaar)+cnt.getPackageName() ;
        String shareSub =cnt.getResources().getString(R.string.app_name);
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        actv.startActivity(Intent.createChooser(sharingIntent, cnt.getResources().getString(R.string.download_app)));




    }
    public void commentApp_google_play(Activity actv) {

        Uri uri = Uri.parse("market://details?id=" + actv.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            actv.startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(actv, " unable to find market app", Toast.LENGTH_LONG).show();
        }

    }
    public void commentApp_coffe_bazaar(Activity actv) {

        String pName=actv.getApplicationContext().getPackageName();
        Intent intent =new Intent(Intent.ACTION_EDIT);

        intent.setData(Uri.parse("http://cafebazaar.ir/app/"+pName+"/?l=fa"));
        actv.startActivity(intent);
        actv.finish();

    }
}



