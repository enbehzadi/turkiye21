package istanbuli.a1c2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.books_sub.SentenceModel;

public class Adapter_sentences extends BaseAdapter {

    ArrayList<SentenceModel>Sentence_item = new  ArrayList<>();
    Context cntx;
    private LayoutInflater mInflater;

    public Adapter_sentences(Context context, ArrayList objects) {
        Sentence_item = objects;
        cntx=context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Sentence_item.size();

    }

    @Override
    public Object getItem(int position) {
        return Sentence_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.row_sentence_gridview, null);
        TextView tv_sentence_row_en = (TextView) convertView.findViewById(R.id.tv_sentence_row_en);

        TextView tv_sentence_row_fa = (TextView) convertView.findViewById(R.id.tv_sentence_row_fa);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_sentence_row);
        tv_sentence_row_fa.setText(Sentence_item.get(position).get_Image_Titel_fa());
        tv_sentence_row_en.setText(Sentence_item.get(position).get_Image_Titel_en());

        imageView.setBackgroundResource(R.drawable.bg_icon_cycle);
        int id = cntx.getResources().getIdentifier(Sentence_item.get(position).get_Image_Name(), "drawable", cntx.getPackageName());
        imageView.setImageResource(id);

        return convertView;

    }

}