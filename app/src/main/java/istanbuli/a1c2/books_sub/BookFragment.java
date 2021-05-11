package istanbuli.a1c2.books_sub;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.itextpdf.text.pdf.PdfReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import istanbuli.a1c2.Database;
import istanbuli.a1c2.DownloadClass;
import istanbuli.a1c2.R;
import istanbuli.a1c2.behzadi.content.myshareprefrence;



public class BookFragment extends Fragment {
    View fragmentView;

    ProgressDialog mProgressDialog;


    File output_file_download;

    private static final int PERMISSION_REQUEST_CODE = 100;
    Database myDbHelper;
    SQLiteDatabase db;
    ImageView  iv_derskitabi, iv_calisma_kitabi;
    Button bt_derskitabi, bt_calismakitabi;

    myshareprefrence myPrefs;
    String Bookname, BookNameSelected;
    Boolean checkFinishDownload=false;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        fragmentView=inflater.inflate(R.layout.book, null);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myPrefs=new myshareprefrence(getActivity());

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getResources().getString(R.string.download_msg));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        getResources().getString(R.string.saite);
        initUI();
    }

    public void setNameButtonDownloadOrRead()
    {
        if(checkStatusDownloadOrReady(myPrefs.getSelectBook() + "_2"))
        {
            bt_calismakitabi.setText(getResources().getString(R.string.read));
        }
        else
        {
            bt_calismakitabi.setText(getResources().getString(R.string.download));
        }

        if(checkStatusDownloadOrReady(myPrefs.getSelectBook()))
        {
            bt_derskitabi.setText(getResources().getString(R.string.read));
        }
        else
        {
            bt_derskitabi.setText(getResources().getString(R.string.download));
        }
    }

    public Boolean checkStatusDownloadOrReady(String fileName)
    {



        if(CheckFile(fileName))
        {
            if(is_pdf(fileName))
            {
                return true;

            }
            else
            {
                return false;

            }
        }
        else
        {
            return false;

        }
    }
    public  boolean is_pdf(String namePdf) {


// Load the PDF document


        String root = Environment.getExternalStorageDirectory().toString();//get external storage

        String pathFile=root + "/Android/data/" + getActivity().getPackageName().toString() + "/books/" + namePdf+".pdf";


        try {
            String parsedText = "";
            PdfReader reader = new PdfReader(pathFile);
            int n = reader.getNumberOfPages();
//            parsedText=PdfTextExtractor.getTextFromPage(reader,5);
//            System.out.println( parsedText );
            if(n>3)
            {
                return true;

            }
            else
            {
                return false;
            }
            // System.out.println( textFromPdfFilePageOne );
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    public void startDownload(String Link) {

        final BookFragment.DownloadTask downloadTask = new BookFragment.DownloadTask(getActivity());
        downloadTask.execute(Link);

        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(false); //cancel the task
            }
        });
    }
    public void initUI() {

        iv_derskitabi = (ImageView) fragmentView.findViewById(R.id.iv_ders_kitabi);
        iv_calisma_kitabi = (ImageView) fragmentView.findViewById(R.id.iv_calisma_kitabi);
        bt_derskitabi = (Button) fragmentView.findViewById(R.id.bt_ders_kitabi);
        bt_calismakitabi = (Button) fragmentView.findViewById(R.id.bt_calisma_kitabi);

        bt_derskitabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookNameSelected = myPrefs.getSelectBook();
                getPermission(BookNameSelected);
            }
        });
        bt_calismakitabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookNameSelected = myPrefs.getSelectBook() + "_2";
                getPermission(BookNameSelected);
              /*  BookNameSelected = myPrefs.getSelectBook();
                getPermission(BookNameSelected);*/
            }
        });

        myPrefs = new myshareprefrence(getActivity());
        Bookname = myPrefs.getSelectBook();

       //
        // setNameButtonDownloadOrRead();

        switch (Bookname) {
            case "A1":
                iv_derskitabi.setImageResource(R.drawable.a1);
                iv_calisma_kitabi.setImageResource(R.drawable.a12);
                break;
            case "A2":
                iv_derskitabi.setImageResource(R.drawable.a2);
                iv_calisma_kitabi.setImageResource(R.drawable.a22);
                break;
            case "B1":
                iv_derskitabi.setImageResource(R.drawable.b1);
                iv_calisma_kitabi.setImageResource(R.drawable.b12);
                break;
            case "B2":
                iv_derskitabi.setImageResource(R.drawable.b2);
                iv_calisma_kitabi.setImageResource(R.drawable.b22);
                break;
            case "C1":
                iv_derskitabi.setImageResource(R.drawable.c1);
                iv_calisma_kitabi.setImageResource(R.drawable.c12);
                break;
            case "C1+":
                iv_derskitabi.setImageResource(R.drawable.c1);
                iv_calisma_kitabi.setImageResource(R.drawable.c12);
                break;
            default:
                iv_derskitabi.setImageResource(R.drawable.c1);
                iv_calisma_kitabi.setImageResource(R.drawable.c12);
                break;
        }
    }

    public void getPermission(String fileName) {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkPermissionread()) {
                    if (CheckFile(fileName)) {
                        if(is_pdf(fileName))
                        {
                            DisplayPdf(BookNameSelected);

                        }else
                        {

                            startDownload(GetLinkDownload(BookNameSelected));


                        }

                    } else {
                        if (checkPermissionwrite()) {
                            startDownload(GetLinkDownload(BookNameSelected));
                        }
                        else {
                            requestPermissionwrite();
                        }
                        // DownloadPdf(GetLinkDownload(BookNameSelected));
                    }
                } else {
                    requestPermissionread(); // Code for permission
                }
            }
            else {
                if (CheckFile(fileName)) {
                    if(is_pdf(fileName))
                    {
                        DisplayPdf(BookNameSelected);

                    }else
                    {

                        startDownload(GetLinkDownload(BookNameSelected));


                    }

                } else {
                    if (checkPermissionwrite()) {
                        startDownload(GetLinkDownload(BookNameSelected));
                    }
                    else {
                        requestPermissionwrite();
                    }
                    // DownloadPdf(GetLinkDownload(BookNameSelected));
                }
            }
        }
    }
    private boolean checkPermissionread() {
        int result = ContextCompat.checkSelfPermission(getActivity(),  Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private boolean checkPermissionwrite() {
        int result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
    private void requestPermissionread() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }
    private void requestPermissionwrite() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
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

    public Boolean CheckFile(String namePdf) {
        if(namePdf.equalsIgnoreCase("C2"))
        {
            namePdf="C1";
        }
        String root = Environment.getExternalStorageDirectory().toString();//get external storage

        File myDir = new File(root + "/Android/data/" + getActivity().getPackageName().toString()+"/books/");

        File file = null;
        file = new File(root + "/Android/data/" + getActivity().getPackageName().toString() + "/books/" + namePdf+".pdf");
        //  Toast.makeText(getApplicationContext(), file.toString(), Toast.LENGTH_LONG).show();
        output_file_download=file;
        if (file.exists()) {


            return true;
        } else {
            myDir.mkdirs();
            return false;

        }
    }


    public String GetLinkDownload(String nameFile) {
        String link = getResources().getString(R.string.saite);
        Load_Database();
        db = myDbHelper.getReadableDatabase();
        String qr = "SELECT link  FROM Tr_Tbl_Books where name='" + nameFile.trim()+ "'";

        Cursor cr = db.rawQuery(qr, null);
        if (cr.moveToFirst()) {
            link+= cr.getString(cr.getColumnIndex("link"));
        }
        return link;
    }



    private void Load_Database() throws Error {
        myDbHelper = new Database(getActivity());

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
    }

    public void DisplayPdf(String nameFile) {


        Intent intent=new Intent(getActivity(), rebook.class);
        intent.putExtra("selectbook", nameFile);
        startActivity(intent);
        //getActivity().finish();

    }
    private class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
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
                output = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/Android/data/" + getActivity().getPackageName().toString() + "/books/"+BookNameSelected+".pdf");

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
                setNameButtonDownloadOrRead();
                DownloadClass dw=new DownloadClass();
                dw.deleteFile(output_file_download.getPath());
            }

            else
            {
                Toast.makeText(context,getResources().getString(R.string.Download_Finish),Toast.LENGTH_SHORT).show();
                checkFinishDownload=true;
                setNameButtonDownloadOrRead();

                DisplayPdf(BookNameSelected);
            }

        }
    }


}
