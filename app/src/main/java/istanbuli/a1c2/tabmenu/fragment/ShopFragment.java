package istanbuli.a1c2.tabmenu.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zarinpal.ewallets.purchase.ZarinPal;

import java.util.ArrayList;

import istanbuli.a1c2.R;
import istanbuli.a1c2.adapter.Adapter_shop;
import istanbuli.a1c2.behzadi.content.myshareprefrence;
import istanbuli.a1c2.functions.functions;
import istanbuli.a1c2.models.shop_item_model;
import istanbuli.a1c2.tabmenu.TopBarActivity;


import static istanbuli.a1c2.R.id.TOP_END;
import static istanbuli.a1c2.R.id.listview_shop;


public class ShopFragment extends Fragment  {
    View fragmentView;

    myshareprefrence myPrefs;
    private ListView listView;
    private Adapter_shop mAdapter;
    private ArrayList<shop_item_model> mArrayList;
    functions fnc;
    Activity act;



    public ShopFragment(Activity act) {
        act=act;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        fragmentView=inflater.inflate(R.layout.fragment_shop, null);
        return fragmentView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fnc=new functions(getContext());
        initUI();
        initData();


    }


    private void initUI()
    {
        listView = fragmentView.findViewById(listview_shop);
        getActivity().getSupportFragmentManager();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(new Intent(getActivity(), TopBarActivity.class));

            }
        });

    }
    private void initData() {
        mArrayList = new ArrayList<>();
        LoadList();
        mAdapter = new Adapter_shop(act,getContext(),mArrayList);
        listView.setAdapter(mAdapter);
        myPrefs=new myshareprefrence(getActivity());

        if(!myPrefs.getshow_shop()&&fnc.getSystemLocale().equalsIgnoreCase("en"))
        {
            /*fnc.dialog_Alert(getActivity(),getContext().getResources().getString(R.string.shop),getContext().getResources().getString(R.string.persian_active_shop));*/
            myPrefs.setshow_shop(true);
        }
    }

    public void LoadList() {



        myPrefs=new myshareprefrence(getActivity());


        SQLiteDatabase db;
        db = fnc.Load_Database(getActivity());

        String areaTyp = "SELECT *  FROM Tr_Tbl_Shop";

        Cursor cr = db.rawQuery(areaTyp, null);
        int i=0;
        if (cr.moveToFirst()) {
            do {

                String id_item=cr.getString(cr.getColumnIndex("Code")).toString();
                String title=cr.getString(cr.getColumnIndex("Title")).toString();
                String description=getResources().getString(R.string.details_min);

                String image=cr.getString(cr.getColumnIndex("Image")).toString();

                //String price=cr.getString(cr.getColumnIndex("Price")).toString();
                String price=myPrefs.getPriceProduct() +" "+ getResources().getString(R.string.price_vahed);

                shop_item_model wmb=new shop_item_model(title,description,image,id_item,price);
                mArrayList.add(wmb);


            } while (cr.moveToNext());
        }

    }
    public void ClickBtShop(Activity act1,Context cntx, View view,String st) {
        functions fnc=new functions(cntx);
        myshareprefrence mypfc=new myshareprefrence(cntx);
            if(new myshareprefrence(cntx).getUser()!="Guest")
            {
                String[] Prd=st.split(" ");
                if(Prd[1].length()>2)
                {
                    String[] Prd2=st.split("\\(");
                    String[] a=Prd2[0].split(" ");
                    if(fnc.CheckUpdate(a[1]))
                    {
                        Toast.makeText(cntx,cntx.getResources().getString(R.string.You_bought_this_product),Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        fnc.dialog_Sell_product((Activity) cntx
                                ,getResources().getString(R.string.name)+":"+a[1],getResources().getString(R.string.price)+":"+mypfc.getPriceProduct()+" "+cntx.getResources().getString(R.string.price_vahed));
                    }
                }
                else
                {
                    if(fnc.CheckUpdate(Prd[1]))
                    {
                       // Toast.makeText(cntx,"hi",Toast.LENGTH_SHORT).show();
                       Toast.makeText(cntx,cntx.getResources().getString(R.string.You_bought_this_product),Toast.LENGTH_SHORT).show();
                      /*  fnc.Snackbar(getView(),"ovdn",getResources().getString(R.string.You_bought_this_product));

                        fnc.dialog_((Activity) cntx
                                ,getResources().getString(R.string.name)+":"+Prd[1],getResources().getString(R.string.price)+":"+fnc.getPrice(Prd[1]));*/

                    }
                    else
                    {


                        fnc.dialog_Sell_product((Activity) cntx
                                ,cntx.getResources().getString(R.string.name)+":"+Prd[1],cntx.getResources().getString(R.string.price)+":"+mypfc.getPriceProduct()+" "+cntx.getResources().getString(R.string.price_vahed));
                    }
                }

            }
            else
            {
                fnc.dialogRegister((Activity) cntx);
            }



    }



}
