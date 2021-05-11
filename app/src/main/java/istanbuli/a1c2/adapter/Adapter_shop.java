package istanbuli.a1c2.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.models.shop_item_model;
import istanbuli.a1c2.tabmenu.fragment.ShopFragment;

public class Adapter_shop extends BaseAdapter {

    ArrayList<shop_item_model>shop_item = new  ArrayList<>();
    Context cntx;
    Activity act1;
    private LayoutInflater mInflater;

    public Adapter_shop(Activity act, Context context, ArrayList objects) {
        shop_item = objects;
        cntx=context;
        act1=act;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return shop_item.size();

    }

    @Override
    public Object getItem(int position) {
        return shop_item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = mInflater.inflate(R.layout.row_item_shop, null);
      TextView tv_titel = (TextView) convertView.findViewById(R.id.tv_shop_item_title);
        TextView tv_description = (TextView) convertView.findViewById(R.id.tv_shop_item_description);
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_shop_item_price);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_shop_item);

        Button bt_shop = (Button) convertView.findViewById(R.id.bt_shop_item);
        tv_titel.setText(shop_item.get(position).get_Titel_Item());
        tv_description.setText(shop_item.get(position).get_Description_Item());
        tv_price.setText(shop_item.get(position).get_Price_Item());

bt_shop.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
       // Toast.makeText(cntx,"saeed", Toast.LENGTH_SHORT).show();
        new ShopFragment(act1).ClickBtShop(act1,cntx,view,shop_item.get(position).get_Titel_Item());
    }
});
        int id = cntx.getResources().getIdentifier(shop_item.get(position).Image_Item, "drawable", cntx.getPackageName());
        imageView.setImageResource(id);

        return convertView;

    }

}