package istanbuli.a1c2;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;

import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadClass extends Activity{

    ProgressDialog mProgressDialog;
    private static final int PERMISSION_REQUEST_CODE = 100;

    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;
        private File Out;


        public DownloadTask(Context context,File output) {
            this.context = context;
            this.Out=output;
            mProgressDialog = new ProgressDialog(DownloadClass.this);
            mProgressDialog.setMessage(getString(R.string.download));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setCancelable(true);
        }

        @Override
        protected String doInBackground(String... sUrl) {

            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                output = new FileOutputStream(this.Out.getPath());


//                output = new FileOutputStream(String.valueOf(Uri.fromFile(Out)));
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                return e.toString();
            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            mProgressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setMax(100);
            mProgressDialog.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            mProgressDialog.dismiss();
            if (result != null)
            {
                Toast.makeText(context,getResources().getString(R.string.Download_error),Toast.LENGTH_SHORT).show();
                // DeleteFile(Out);
            }

            else
            {
                Toast.makeText(context,getResources().getString(R.string.Download_Finish),Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void DeleteFile(File file) {
        File fdelete = file;
        if (fdelete.exists()) {
            if (fdelete.delete()) {
//               Toast.makeText(getBaseContext(),"hazf shod",Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(getBaseContext(),"hazf nashod",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startDownload(String Link, final File output) {


        final DownloadTask downloadTask = new DownloadTask(DownloadClass.this,output);
        downloadTask.execute(Link);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(false);
                DeleteFile(output);
            }
        });
    }

    public Boolean getPermission(Activity actv) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionread(actv)) {
                    return  true;

                } else {
                    requestPermissionread(actv);

                }
            }
            else {
                return false;
            }
        }
        return false;

    }
    public boolean checkPermissionread(Activity cntx) {
        int result = ContextCompat.checkSelfPermission(cntx,  Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkPermissionwrite(Activity cntx) {
        int result = ContextCompat.checkSelfPermission(cntx,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    public void requestPermissionread(Activity cntx) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(cntx, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(cntx, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(cntx, new String[]
                    {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    public void requestPermissionwrite(Activity cntx) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(cntx, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(cntx, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(cntx, new String[]
                    {Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public Boolean CheckFile(File Dir,File nameFile) {

        if (nameFile.exists()) {
            return true;
        } else {
            Dir.mkdirs();
            return false;
        }
    }
}
