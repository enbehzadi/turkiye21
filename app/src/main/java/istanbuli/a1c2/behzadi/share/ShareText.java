package istanbuli.a1c2.behzadi.share;


import android.content.Context;
import android.content.Intent;

import istanbuli.a1c2.R;


public class ShareText {

	
	public static void ShareText(Context context,String text){
	    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
	    sharingIntent.setType("text/plain");
	    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
	    sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
	    context.startActivity(Intent.createChooser(sharingIntent, ""));
	}
}
