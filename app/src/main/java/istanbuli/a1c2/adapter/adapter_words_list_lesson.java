package istanbuli.a1c2.adapter;
import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import istanbuli.a1c2.R;
import istanbuli.a1c2.books_sub.WordsFragment;

import java.util.ArrayList;

public class adapter_words_list_lesson extends BaseAdapter implements Filterable {
    private LayoutInflater mInflater;

    Activity activity;
    ArrayList<String> tur_words;
    ArrayList<String> en_words;
    ArrayList<String> fa_words;
    private ArrayList<String> mStringList;
    private ArrayList<String> mStringFilterList;
    private ValueFilter valueFilter;
    View v;
    TextView tv_en;
    Boolean hide_show_english;
    public adapter_words_list_lesson(Activity activity, ArrayList<String>
            tur_words,ArrayList<String> en_words, ArrayList<String>fa_words,Boolean hide_show_english ) {
        this.hide_show_english=hide_show_english;
        this.tur_words = tur_words;
        this.fa_words = fa_words;
        this.en_words = en_words;
        this.activity = activity;
        this.mStringList=tur_words;
        this.mStringFilterList=mStringList;
        mInflater=LayoutInflater.from(activity);

        getFilter();
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
         mInflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.row_words_list, null);
        TextView tv_tur = (TextView) v.findViewById(R.id.tv_tur_word_list);
        TextView tv_fa = (TextView) v.findViewById(R.id.tv_fa_word_list);
         tv_en = (TextView) v.findViewById(R.id.tv_en_word_list);

        tv_tur.setText(mStringList.get(position).toString());
        tv_fa.setText(fa_words.get(position).toString());
        if(!hide_show_english)
        {
            tv_en.setVisibility(View.GONE);
        }
        else
        {
            tv_en.setVisibility(View.VISIBLE);
            tv_en.setText(en_words.get(position));

        }
        final WordsFragment sf=new WordsFragment();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sf.ClickOnWordsList4(v);
            }
        });
        return v;
    }


    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
             return mStringList.size();

    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mStringList.get(position);

    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Filter getFilter() {

        if(valueFilter==null) {

            valueFilter=new ValueFilter();
        }

        return valueFilter;
    }

    private class ValueFilter extends Filter {


        //Invoked in a worker thread to filter the data according to the constraint.
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results=new FilterResults();

            if(constraint!=null && constraint.length()>0){

                ArrayList<String> filterList=new ArrayList<String>();

                for(int i=0;i<mStringFilterList.size();i++){

                    if(mStringFilterList.get(i).contains(constraint)) {

                        filterList.add(mStringFilterList.get(i));

                    }
                }


                results.count=filterList.size();

                results.values=filterList;

            }else{

                results.count=mStringFilterList.size();

                results.values=mStringFilterList;


            }

            return results;
        }


        //Invoked in the UI thread to publish the filtering results in the user interface.
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            mStringList=(ArrayList<String>) results.values;

            notifyDataSetChanged();



        }

    }

}
