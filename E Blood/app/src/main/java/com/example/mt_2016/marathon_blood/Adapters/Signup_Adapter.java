package com.example.mt_2016.marathon_blood.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;


import java.util.ArrayList;

/**
 * Created by BabarMustafa on 10/20/2016.
 */

public class Signup_Adapter extends BaseAdapter implements Filterable {


    private ArrayList<User> dataList,listRawData, searchedItems;
    private ArrayList<User>  backup_list;
    public  static Signup_Adapter signup_adapter;
    private Context context;
    private int mCurrentFilterLength;
    private ArrayList<User> mMemberDataListBackup;
    LayoutInflater inflator;



    public Signup_Adapter(ArrayList<User> listMain,ArrayList<User> listRawData, Context context) {
        this.dataList = listMain;
        this.context = context;
        this.listRawData = listRawData;
        inflator = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflator.inflate(R.layout.signle_user_show, null);


        TextView forname = (TextView) view.findViewById(R.id.v_username);
        TextView forbgr = (TextView) view.findViewById(R.id.bld);
      CircularImageView pcircularImageView = (CircularImageView) view.findViewById(R.id.profile_view);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_user);


        final User data = dataList.get(position);

        String username = data.getName();



        //to still the condition after changes
        final User todoChekd = (User) getItem(position);
        forname.setText(dataList.get(position).getName());
        forbgr.setText(dataList.get(position).getBlood_Group());

//
        Glide.with(context)
                .load(data.getProfile_image())
                .error(R.drawable.user1)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
//                        pcircularImageView.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/chatapplication-f99c2.appspot.com/o/grou_ico.png?alt=media&token=b664fb78-2b74-49b5-8297-6bc0e9e3c6c9"));

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(pcircularImageView);


        return view;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                ArrayList<User> bookingsResultFilter = new ArrayList<>();
                if (searchedItems == null) {
                    searchedItems = listRawData;
                }

                if (constraint.length() == 0) {
                    oReturn.values = listRawData;
//                    Utils.log("Return Complete Data " + bookingsRawData.size());
                    return oReturn;
                }
                if (searchedItems != null && searchedItems.size() > 0) {
                    for (final User g : searchedItems) {
                        if (g.getName().contains(constraint.toString())
                                || g.getBlood_Group().toLowerCase().contains(constraint.toString().toLowerCase())
//                                || g.getStatus().toLowerCase().contains(constraint.toString().toLowerCase())
//                                || g.getName().toLowerCase().contains(constraint.toString().toLowerCase())
//                                || g.getNumber().contains(constraint.toString())
//                                || g.getEmail().toLowerCase().contains(constraint.toString().toLowerCase())
//                                || g.getId().contains(constraint.toString())
//                                || g.getType().toLowerCase().contains(constraint.toString().toLowerCase())

                                ) {

                            bookingsResultFilter.add(g);
                        }
                    }

                }
                oReturn.values = bookingsResultFilter;
                return oReturn;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                dataList.clear();
                ArrayList<User> usersFilter = (ArrayList<User>) results.values;
                if (usersFilter != null) {
                    dataList.addAll(usersFilter);
                }
                notifyDataSetChanged();

            }
        };
    }
}
