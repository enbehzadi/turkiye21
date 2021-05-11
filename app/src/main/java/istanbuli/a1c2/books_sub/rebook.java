package istanbuli.a1c2.books_sub;

import android.app.Activity;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;
import androidx.viewpager.widget.ViewPager;


import com.pdfview.PDFView;
import com.pdfview.subsamplincscaleimageview.SubsamplingScaleImageView;
import istanbuli.a1c2.R;
import java.io.File;



final public class rebook extends Activity  {



    Integer pageNumber = 1;
    String pdfFileName;
    String FilePdf;
    PDFView pdfview;
    EditText et_pg;
    TextView tv_pg;
    Button bt_pg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readbook);
        findviewbyid();
        load_pdf();

   }
    public void findviewbyid () {

        pdfview=findViewById(R.id.activity_main_pdf_view);
        tv_pg=findViewById(R.id.tv_pg);
        pdfview.setPanLimit(SubsamplingScaleImageView.PAN_LIMIT_INSIDE);
    }
    public  void load_pdf()
    {

        Bundle b = new Bundle();
        b = getIntent().getExtras();
        pdfFileName = b.getString("selectbook");

        tv_pg.setText(pdfFileName);
        FilePdf=pdfFileName;
        if(pdfFileName.equalsIgnoreCase("C2"))
        {
            pageNumber=114;
            pdfFileName="C1(7-12)";
            FilePdf="C1";
        }
        String root = Environment.getExternalStorageDirectory().toString();
        String fpath=root+ "/Android/data/" + getPackageName() + "/books/" + FilePdf.toString()+".pdf";


        pdfview.fromFile(fpath);
        pdfview.show();


    }



}