package com.example.mt_2016.marathon_blood.Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BabarMustafa on 5/15/2017.
 */

public class UserMemberAdapter extends ArrayAdapter<User> {
    private ArrayList<User> dataList = new ArrayList<>();
    private ArrayList<User>  backup_list;
    public  static UserMemberAdapter signup_adapter;
    private Context context;
    private int mCurrentFilterLength;
    public LayoutInflater inflater;


    public UserMemberAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<User> objects) {
        super(context, resource, objects);
        this.dataList = objects;
        this.context = context;
        this.backup_list =new ArrayList<>(objects);
        signup_adapter = this;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void add(@Nullable User object) {
        super.add(object);
        backup_list.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflater.inflate(R.layout.signle_user_show, null);


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
    public void filterPolicy(String s){
        int filterLength = s.length();
        if (filterLength == 0 || filterLength < mCurrentFilterLength) {
            mCurrentFilterLength = filterLength;
            dataList.clear();
            dataList.addAll(backup_list);
            if (filterLength == 0) {
                notifyDataSetChanged();
                return;
            }
        }

        int i = 0;
        while (i < dataList.size()) {
            if (!dataList.get(i).getName().toLowerCase().contains(s)) {
                dataList.remove(i);
            } else {
                i++;
            }
        }
        mCurrentFilterLength = filterLength;
        notifyDataSetChanged();

    }

}
