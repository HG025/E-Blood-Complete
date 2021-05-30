package com.example.mt_2016.marathon_blood.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Activities.CommitActivity;
import com.example.mt_2016.marathon_blood.Fragments.Login;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;


import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by BabarMustafa on 10/20/2016.
 */

public class PostAdapter extends BaseAdapter {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private ArrayList<Post> dataList;
    private Context context;
    String ago;
    public PostAdapter(ArrayList<Post> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Post getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
        View view = inflater.inflate(R.layout.single_show, null);


        TextView forname = (TextView) view.findViewById(R.id.a_name);
        TextView units_group = (TextView) view.findViewById(R.id.a_units_groups);
        TextView hos_rela = (TextView) view.findViewById(R.id.a_hos_relation);
        TextView bld_urgency = (TextView) view.findViewById(R.id.a_urgency);
        TextView contact = (TextView) view.findViewById(R.id.a_contact);
        TextView additionale = (TextView) view.findViewById(R.id.a_voln);
        TextView city = (TextView) view.findViewById(R.id.a_city);
        TextView state = (TextView) view.findViewById(R.id.a_state);
        TextView country = (TextView) view.findViewById(R.id.a_country);

        TextView vol_requi = (TextView) view.findViewById(R.id.a_current_timestamp);
        Button b_tn = (Button) view.findViewById(R.id.for_commits);
        Button btn_volunt = (Button) view.findViewById(R.id.for_volunt);
        TextView remainig = (TextView) view.findViewById(R.id.to_remain);
        CircularImageView post_by_imageview = (CircularImageView) view.findViewById(R.id.a_user_image);



        final Post data = dataList.get(position);

//        String username = data.getName();
//        String iml = data.getProfile_image();
//        Toast.makeText(context, ""+iml, Toast.LENGTH_SHORT).show();



//mAuth.getCurrentUser().toString();

        //to still the condition after changes
//        final User todoChekd = (User) getItem(position);
        Glide.with(context)
                .load(dataList.get(position).getPost_by_image())
                .error(R.drawable.user1)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
                        return false;
//                        pcircularImageView.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/chatapplication-f99c2.appspot.com/o/grou_ico.png?alt=media&token=b664fb78-2b74-49b5-8297-6bc0e9e3c6c9"));

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(post_by_imageview);
        forname.setText(dataList.get(position).getP_naem());
        units_group.setText(dataList.get(position).getP_units() + " Units of "+dataList.get(position).getP_group() + " Blood Required." );
        hos_rela.setText("At " +dataList.get(position).getP_hos() + " For my "+dataList.get(position).getP_rel() );
        city.setText("City: "+dataList.get(position).getP_city());
        country.setText("Country: "+dataList.get(position).getP_country());
        state.setText("State: "+dataList.get(position).getP_state());
        bld_urgency.setText("Urgency: " +dataList.get(position).getP_urgency());
        contact.setText("Contact at:" +dataList.get(position).getCont() );
        additionale.setText("Instructions:"+dataList.get(position).getAdd_i());
        PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
        ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
        vol_requi.setText(ago);
        remainig.setText("Remainings:"+dataList.get(position).getRemainings());


////        System.out.print(""+data.getProfile_image());
//        Picasso.with(context).load(data.getProfile_image()).into(pcircularImageView);


        b_tn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commit_call = new Intent(context, CommitActivity.class);
                commit_call.putExtra("postid", dataList.get(position).getPush_c());
                commit_call.putExtra("posternbame",dataList.get(position).getP_naem());
                commit_call.putExtra("bloudgro",dataList.get(position).getP_group());
                commit_call.putExtra("Realation",dataList.get(position).getP_rel());
                commit_call.putExtra("country",dataList.get(position).getP_country());
                commit_call.putExtra("city",dataList.get(position).getP_city());
                commit_call.putExtra("state",dataList.get(position).getP_state());
                commit_call.putExtra("hospital",dataList.get(position).getP_hos());
                commit_call.putExtra("urgency",dataList.get(position).getP_urgency());
                commit_call.putExtra("contact_nm",dataList.get(position).getCont());
                commit_call.putExtra("hiday",dataList.get(position).getAdd_i());
                commit_call.putExtra("units",dataList.get(position).getP_units());
                commit_call.putExtra("post_by",dataList.get(position).getPost_by_id());
                commit_call.putExtra("remaings",dataList.get(position).getRemainings());
                commit_call.putExtra("post_image",dataList.get(position).getPost_by_image());
                commit_call.putExtra("timestamp",ago);




                context.startActivity(commit_call);
            }
        });
        btn_volunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent commit_call = new Intent(context, CommitActivity.class);
                commit_call.putExtra("postid", dataList.get(position).getPush_c());
                commit_call.putExtra("posternbame",dataList.get(position).getP_naem());
                commit_call.putExtra("bloudgro",dataList.get(position).getP_group());
                commit_call.putExtra("Realation",dataList.get(position).getP_rel());
                commit_call.putExtra("country",dataList.get(position).getP_country());
                commit_call.putExtra("city",dataList.get(position).getP_city());
                commit_call.putExtra("state",dataList.get(position).getP_state());
                commit_call.putExtra("hospital",dataList.get(position).getP_hos());
                commit_call.putExtra("urgency",dataList.get(position).getP_urgency());
                commit_call.putExtra("contact_nm",dataList.get(position).getCont());
                commit_call.putExtra("hiday",dataList.get(position).getAdd_i());
                commit_call.putExtra("units",dataList.get(position).getP_units());
                commit_call.putExtra("post_by",dataList.get(position).getPost_by_id());
                commit_call.putExtra("remaings",dataList.get(position).getRemainings());
                commit_call.putExtra("post_image",dataList.get(position).getPost_by_image());
                commit_call.putExtra("timestamp",ago);




                context.startActivity(commit_call);
            }
        });
        return view;
    }
}