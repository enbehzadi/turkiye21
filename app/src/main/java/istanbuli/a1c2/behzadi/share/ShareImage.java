package istanbuli.a1c2.behzadi.share;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class ShareImage {
	//Statics
	public static final int SUCC_SAVED=0;
		public static final int ERR_SAVE=1;
	//Variables
		String str_file;
		String f_path_share="file:///sdcard/temp/";
		String Full_path="";
		Context	mycontext;
	public ShareImage(Context context) {
		mycontext=context;
	}
	
	
	private int save_to_sd(Bitmap bmp){
		
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 40, bytes);

        //you can create a new file name "test.jpg" in sdcard folder.
        Calendar cl=Calendar.getInstance();
        str_file=Long.toString(cl.getTimeInMillis())+".jpg";
        Full_path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/temp/"+str_file;
        String path=Full_path;
        File f = new File(path);
        try {
        	File directory = new File(Full_path).getParentFile();
    	    if (!directory.exists() && !directory.mkdirs()) {
    	      throw new IOException("Path to file could not be created.");
    	    }
			f.createNewFile();
			//write the bytes in file
	        FileOutputStream fo = new FileOutputStream(f);
	        fo.write(bytes.toByteArray());
	     // remember close de FileOutput
	        fo.close();
	        return SUCC_SAVED;
		} catch (IOException e) {
			e.printStackTrace();
			return ERR_SAVE;
		}
	}
	
	 private void send_pic(){
	    	// Now send it out to share
	        Intent share = new Intent(Intent.ACTION_SEND);
	        share.setType("image/*");
	        share.putExtra(Intent.EXTRA_STREAM, Uri.parse(f_path_share+"/"+str_file));
	        mycontext.startActivity(Intent.createChooser(share, "Share photo"));
	 }
	 
	 public int Send(Bitmap bmp){
		 int a=save_to_sd(bmp);
		 if (a==SUCC_SAVED)
			 send_pic();
		 return a;
	 }
	 
	 public int Send(int Res){
		 Bitmap bmp=new BitmapFactory().decodeResource(mycontext.getResources(), Res);
		 int a=save_to_sd(bmp);
		 if (a==SUCC_SAVED)
			 send_pic();
		 return a;
	 }

}
