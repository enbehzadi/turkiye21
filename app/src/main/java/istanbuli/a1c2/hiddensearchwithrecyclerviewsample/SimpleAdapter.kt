package istanbuli.a1c2.hiddensearchwithrecyclerviewsample

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import istanbuli.a1c2.R

/**
 * Created by Luca Nicoletti
 * © 28/07/2018
 * All rights reserved.
 */

class SimpleAdapter(private val arrayOfStrings: List<String>) :
    RecyclerView.Adapter<SimpleAdapter.ViewHolder>(), Filterable {

    private var copyOfStrings: List<String> = arrayOfStrings.toList()

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                val results = FilterResults()
                if (value.isNullOrEmpty()) {
                    results.values = arrayOfStrings
                } else {
                    copyOfStrings = arrayOfStrings.filter {
                        it.contains(value, true)
                    }
                    results.values = copyOfStrings
                }
                return results
            }

            override fun publishResults(value: CharSequence?, results: FilterResults?) {
                copyOfStrings = (results?.values as? List<String>).orEmpty()
                notifyDataSetChanged()
            }

        }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.simple_adapter, null))
    }

    override fun getItemCount(): Int {
        return copyOfStrings.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfStrings[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.simple_text).text = value
            this.itemView.setOnClickListener {
                Log.e("CLCK", "Clicked item $value at $position")
            }
        }
    }
}