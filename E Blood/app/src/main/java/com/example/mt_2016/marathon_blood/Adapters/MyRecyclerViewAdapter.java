package com.example.mt_2016.marathon_blood.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.Activities.GroupProfile;
import com.example.mt_2016.marathon_blood.Activities.Welcome_for_User;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.database.FirebaseDatabase;


import java.util.List;

/**
 * Created by BabarMustafa on 2/28/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {
    CustomViewHolder viewHolder;
    private List<User> feedItemList;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    public MyRecyclerViewAdapter(Context context, List<User> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.signle_group_show, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        User feedItem = feedItemList.get(i);

        //Render image using Picasso library
        if (!TextUtils.isEmpty(feedItem.getProfile_image())) {

            Glide.with(mContext).load(feedItem.getProfile_image())
                    .error(R.drawable.user1)
                    .into(customViewHolder.imageView);

        }

        //Setting text view title
        if (feedItem.getRealAdmin().equals(feedItem.getUID())) {
            customViewHolder.admin.setVisibility(View.VISIBLE);

        }
        customViewHolder.textView.setText(Html.fromHtml(feedItem.getName()));

//        customViewHolder.v
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView imageView;
        protected TextView textView;
        protected TextView admin;
        protected ProgressBar progressBarl;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.profile_view);
            this.textView = (TextView) view.findViewById(R.id.v_username);
            this.admin = (TextView) view.findViewById(R.id.adminShow);
            this.progressBarl = (ProgressBar) view.findViewById(R.id.progress_user);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final int pos = getAdapterPosition();
            final String uid = feedItemList.get(pos).getUID();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Do you want to remove this person ");
            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (Welcome_for_User.login_user.equals(feedItemList.get(pos).getRealAdmin())) {

                        FirebaseDatabase
                                .getInstance()
                                .getReference()
                                .child("My_Groups")
                                .child(uid)
                                .child(feedItemList.get(pos).getGroupName())
                                .removeValue();
                        Toast.makeText(mContext, "" + feedItemList.get(pos).getName() + " reomves Succesfully from group " + feedItemList.get(pos).getGroupName(), Toast.LENGTH_SHORT).show();
                        ((GroupProfile)mContext).finish();
                    } else {

                        Toast.makeText(mContext, "You donot have rights to remove members", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.create().show();
        }
    }
}