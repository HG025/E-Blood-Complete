package com.example.mt_2016.marathon_blood.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Models.groups_create_info;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

/**
 * Created by BabarMustafa on 2/8/2017.
 */

//public class Groups_show_Adapter {
//}

public class Groups_show_Adapter extends BaseAdapter {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private ArrayList<groups_create_info> dataList;
    private Context context;

    public Groups_show_Adapter(ArrayList< groups_create_info> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public  groups_create_info getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflater.inflate(R.layout.single_group_show, null);


        TextView forname = (TextView) view.findViewById(R.id.g_v_username);
        final CircularImageView pcircularImageView = (CircularImageView) view.findViewById(R.id.g_profile_view);

        final  groups_create_info data = dataList.get(position);

        String username = data.getGroup_name();
//        String iml = data.getProfile_image();
//        Toast.makeText(context, ""+iml, Toast.LENGTH_SHORT).show();



//mAuth.getCurrentUser().toString();

        //to still the condition after changes
        final  groups_create_info todoChekd = ( groups_create_info) getItem(position);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
        forname.setText(dataList.get(position).getGroup_name());
//        System.out.print(""+data.getProfile_image());
        Glide.with(context)
                .load(data.getG_i_url())
                .error(R.drawable.grou_ico)
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
}
