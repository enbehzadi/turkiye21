package istanbuli.a1c2.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.books_sub.WordModel;

public class Adapter_dictionary extends RecyclerView.Adapter<Adapter_dictionary.ViewHolder> implements Filterable {
    private ArrayList<WordModel> mArrayList;
    private ArrayList<WordModel> mFilteredList;

    public Adapter_dictionary(ArrayList<WordModel> mArrayList) {
        this.mArrayList = mArrayList;
        this.mFilteredList = mArrayList;
    }


    @Override
    public Adapter_dictionary.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_estelahat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Adapter_dictionary.ViewHolder holder, int position) {
        holder.tv_Tr.setText(mFilteredList.get(position).get_Tr());
        holder.tv_Fa.setText(mFilteredList.get(position).get_Fa());
        holder.tv_En.setText(mFilteredList.get(position).get_En());
    }

    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    mFilteredList = mArrayList;
                } else {
                    ArrayList<WordModel> filterList = new ArrayList<>();
                    for (WordModel androidVersion : mArrayList) {
                          if (androidVersion.get_Tr().toLowerCase().startsWith(charString) || androidVersion.get_Fa().toLowerCase().contains(charString) ) {
                            filterList.add(androidVersion);
                        }
                    }
                    mFilteredList = filterList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                notifyDataSetChanged();

            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_Tr, tv_Fa, tv_En;

        ViewHolder(View view) {
            super(view);
            tv_Tr = (TextView) view.findViewById(R.id.tv_tr);
            tv_Fa = (TextView) view.findViewById(R.id.tv_fa);
            tv_En = (TextView) view.findViewById(R.id.tv_en);
        }
    }
}
