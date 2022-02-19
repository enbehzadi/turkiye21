package istanbuli.a1c2.behzadi.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImage {

	
	//Satatics
		public static final int SUCC_SAVED=0;
		public static final int ERR_SAVE=1;
		public static final int ERR_EXIST=2;
	//Variables
		String path;
		Context mycontext;
	
	public SaveImage(Context context) {
		mycontext=context;
	}
	
	private int save_to_sd(Bitmap bmp,String path){
		Log.i("Place SavePic", "2");
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        Log.i("Place SavePic", "3");
        //you can create a new file name "test.jpg" in sdcard folder.
        File f = new File(path);
        Log.i("Place SavePic", "4");
        try {
        	File directory = new File(path).getParentFile();
    	    if (!directory.exists() && !directory.mkdirs()) {
    	      throw new IOException("Path to file could not be created.");
    	    }
        	Log.i("Place SavePic", "5"+path);
        	if (f.exists()) return ERR_EXIST;
        	f.createNewFile();
			//write the bytes in file
	        FileOutputStream fo = new FileOutputStream(f);
	        Log.i("Place SavePic", "6");
	        fo.write(bytes.toByteArray());
	        Log.i("Place SavePic", "7");
	     // remember close de FileOutput
	        fo.close();
	        return SUCC_SAVED;
		} catch (IOException e) {
			Log.i("Place SavePic", "Catch");
			e.printStackTrace();
			return ERR_SAVE;
		}
	}
	
	public int Save(int Resource,String FileName){
		Log.i("Place SavePic", "1");
		FileName=Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/"+FileName+".jpg";
		return save_to_sd(new BitmapFactory().decodeResource(mycontext.getResources(),Resource),FileName);
	}
	
	
	
	
}
