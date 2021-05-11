package istanbuli.a1c2.adapter;
import android.app.Activity;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import istanbuli.a1c2.R;
import istanbuli.a1c2.books_sub.SoundsFragment;

import java.util.ArrayList;

public class adapter_voices_list_lesson extends BaseAdapter  {
    LayoutInflater mInflater;

    Activity activity;
    ArrayList<String> name_Voices;
    ArrayList<String> page_Voices;
    private ArrayList<String> mStringList;
    private ArrayList<String> mStringFilterList;
    SoundsFragment sf;
    View v;
    LinearLayout la;
    public adapter_voices_list_lesson(Activity activity, ArrayList<String>
            Name_Voices,ArrayList<String> Page_Voices) {
        this.name_Voices = Name_Voices;
        sf=new SoundsFragment();
        this.page_Voices = Page_Voices;
        this.activity = activity;
        this.mStringList=Name_Voices;
        this.mStringFilterList=mStringList;
        mInflater=LayoutInflater.from(activity);

    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        mInflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        v = mInflater.inflate(R.layout.row_voice_list, null);

        la=(LinearLayout) v.findViewById(R.id.la_voices_list);
        TextView tv_name_voices = (TextView) v.findViewById(R.id.tv_name_voice_list);
        TextView tv_page_voices = (TextView) v.findViewById(R.id.tv_numberpage_voice_list);
        tv_name_voices.setTag(position);
        tv_name_voices.setText(name_Voices.get(position));

       tv_page_voices.setText(page_Voices.get(position));

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
