package istanbuli.a1c2.behzadi;
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
    public void setSpeaker(String speaker) {
        editor.putString("Speaker",speaker);
        editor.apply();
    }
    public String getspeaker()
    {
        return mPref.getString("Speaker","on");
    }
}



