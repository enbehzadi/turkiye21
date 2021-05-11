package istanbuli.a1c2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import istanbuli.a1c2.R;

public class adapter_fragment_books extends ArrayAdapter {

        ArrayList<books_item>books_item = new  ArrayList<>();

        public adapter_fragment_books(Context context, int textViewResourceId, ArrayList objects) {
            super(context, textViewResourceId, objects);
            books_item = objects;
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.books_item, null);
            TextView textView = (TextView) v.findViewById(R.id.textView);
            ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
            textView.setText(books_item.get(position).getName());
            imageView.setImageResource(books_item.get(position).getImage());
            return v;

        }

    }