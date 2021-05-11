package istanbuli.a1c2.behzadi.content;

import android.content.Context;
import android.content.SharedPreferences;

public class myshareprefrence {
    SharedPreferences mPref;
    SharedPreferences.Editor editor;
    Context cnt;
    public static final String PREFERENCES_FILE_NAME ="MyAppPreferences";

    public myshareprefrence(Context mContext) {
        cnt=mContext;
        mPref = mContext.getSharedPreferences(PREFERENCES_FILE_NAME,0);
        editor=mPref.edit();
        SharedPreferences.Editor editor = mPref.edit();
    }
    public void setSelectBook(String NameBook) {
        editor.putString("NameBook",NameBook);
        editor.apply();
    }
    public String getSelectBook()
    {
        return mPref.getString("NameBook","a1");
    }

    public void setLanguage(String Language) {
        editor.putString("Language",Language);
        editor.apply();
    }
    public String getLanguage()
    {
        return mPref.getString("Language","ENGLISH");
    }

    public String getEvent()
    {
        return mPref.getString("Event","1");
    }


    public void setEvent(String Event) {
        editor.putString("Event",Event);
        editor.apply();
    }
    public String getLesson()
    {
        return mPref.getString("Lesson","1");
    }
    public void setLesson(String Lesson) {
        editor.putString("Lesson",Lesson);
        editor.apply();
    }

    public Boolean get_hide_show_english()
    {
        return mPref.getBoolean("status",true);
    }
    public void set_hide_show_english(Boolean st) {
        editor.putBoolean("status",st);
        editor.apply();
    }

    public Boolean get_hide_show_persian()
    {
        return mPref.getBoolean("fa",true);
    }
    public void set_hide_show_persian(Boolean st) {
        editor.putBoolean("fa",st);
        editor.apply();
    }


    public Boolean get_Check_welcome()
    {
        return mPref.getBoolean("welcome",true);
    }
    public void set_Check_welcome(Boolean welcome) {
        editor.putBoolean("welcome",welcome);
        editor.apply();
    }

    public Boolean get_Check_Update()
    {
        return mPref.getBoolean("update",false);
    }
    public void set_Check_Update(Boolean st) {
        editor.putBoolean("update",st);
        editor.apply();
    }

//    public String getTYpe()
//    {
//        return mPref.getString("Event","1");
//    }
//
//    public void setConfig(String []Lesson) {
//        editor.putStringSet("Lesson",Lesson[]);
//        editor.apply();
//    }
    public String getTYpe()
    {
        return mPref.getString("Event","1");
    }

    public String getjson()
    {
        return mPref.getString("json","1");
    }
    public void setjson(String json) {
        editor.putString("json",json);
        editor.apply();
    }
    public String getUser()
    {
        return mPref.getString("user","Guest");
    }
    public void setFullName(String name) {
        editor.putString("fullname",name);
        editor.apply();
    }

    public String getFullName()
    {
        return mPref.getString("fullname","Guest");
    }
    public void setUser(String name) {
        editor.putString("user",name);
        editor.apply();
    }
    public Boolean getStart()
    {
        return mPref.getBoolean("Start",true);
    }
    public void setStart(Boolean start) {
        editor.putBoolean("Start",start);
        editor.apply();
    }

    public String getSentence_CatId()
    {
        return mPref.getString("CatId","1");
    }
    public void setSentence_CatId(String CatId) {
        editor.putString("CatId",CatId);
        editor.apply();
    }

    public String getSentence_Titel()
    {
        return mPref.getString("Titel","");
    }
    public void setSentence_Titel(String Titel) {
        editor.putString("Titel",Titel);
        editor.apply();
    }

    public String getFor_Buy()
    {
        return mPref.getString("product_name","");
    }
    public void setFor_Buy(String product_name) {
        editor.putString("product_name",product_name);
        editor.apply();
    }
    public String getMerchantID()
    {
        return mPref.getString("MerchantID","");
    }
    public void setMerchantID(String MerchantID) {
        editor.putString("MerchantID",MerchantID);
        editor.apply();
    }

    public String getPriceProduct()
    {
        return mPref.getString("PriceProduct","");
    }
    public void setPriceProduct(String PriceProduct) {
        editor.putString("PriceProduct",PriceProduct);
        editor.apply();
    }
    public Boolean getshow_privacy()
    {
        return mPref.getBoolean("privacy",false);
    }
    public void setshow_privacy(Boolean status) {
        editor.putBoolean("privacy",status);
        editor.apply();
    }

    public Boolean getshow_grammer()
    {
        return mPref.getBoolean("grammer",false);
    }
    public void setshow_grammer(Boolean status) {
        editor.putBoolean("grammer",status);
        editor.apply();
    }

    public Boolean getshow_shop()
    {
        return mPref.getBoolean("shop",false);
    }
    public void setshow_shop(Boolean status) {
        editor.putBoolean("shop",status);
        editor.apply();
    }
}



