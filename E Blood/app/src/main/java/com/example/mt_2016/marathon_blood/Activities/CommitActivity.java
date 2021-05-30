package com.example.mt_2016.marathon_blood.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Adapters.ToBoAdapter;
import com.example.mt_2016.marathon_blood.Adapters.VoluntersAdapter;
import com.example.mt_2016.marathon_blood.Models.comit_for;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommitActivity extends AppCompatActivity {

    String c_postid;
    String c_poster_name;
    String c_blood_grou;
    String c_country;
    String c_hospital;
    String c_urgency;
    String c_relate;
    String c_contac;
    String c_hidayat;
    String c_units;
    String c_postby;
    String c_remaings;
    String c_image;
    String c_city;
    String c_state;
    String c_timestamp;
    Button id_btn;
    EditText com_text;
    comit_for dta;
    DatabaseReference databse;
    public HashMap<String, Object> hashObj = new HashMap<>();
    private ListView emailList;
    private List<comit_for> messages;
    private List<comit_for> volunters;
    private ToBoAdapter listAdapter;
    private VoluntersAdapter volunters_listAdapter;
    private ListView volunters_list;
    ActionBar actionBar;

    /*
    *converation acivity main thread issue solved
    *group remmove member logic done
    *post from here text size issue solved
    *image border remove from all post fragment and my post
    *border also removed fromm post from here
    *group creta main thread to remove load done
    *conversation activity remove load frommain thread done
    *Commit activity remove load frommain thread done
    *Group conversation activity remove load frommain thread done
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        IntializeViews();
        getDataFromINtent();
        setDatatoAdapter();
        AdjustBothListViewOnScreen();
        declearAndInitalizingViewsTOSEtData();
        toCommit();
        databse
                .child("Commit_for_posts")
                .child(c_postid)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.
                        comit_for data = dataSnapshot.getValue(comit_for.class);

                        // list.add(data.getP_id());
//                list.add(data.getP_city());
                        messages.add(new comit_for(data.getComit_nmae(), data.getCommit_text(), data.getTimeInMillis()));
                        listAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });
        databse
                .child("Volunters_for_posts")
                .child(c_postid)
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.
                        comit_for data = dataSnapshot.getValue(comit_for.class);

                        // list.add(data.getP_id());
//                list.add(data.getP_city());
                        volunters.add(new comit_for(data.getComit_nmae(),
                                data.getCommit_text(), c_postby, data.getConfirm_donation(), c_postid
                                , data.getPush_of(), data.getTimeInMillis()));
                        volunters_listAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }


                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("TAG", "Failed to read value.", error.toException());
                    }
                });

    }

    private void toCommit() {
        id_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String com = com_text.getText().toString();
                String bidn = Welcome_for_User.name;

                hashObj.put("comit_nmae", bidn);
                hashObj.put("commit_text", com);
                hashObj.put("mTimestamp", ServerValue.TIMESTAMP);


                databse
                        .child("Commit_for_posts")
                        .child(c_postid)
                        .push()
                        .setValue(hashObj);
                com_text.setText("");
            }
        });
    }

    private void declearAndInitalizingViewsTOSEtData() {
        TextView forname = (TextView) findViewById(R.id.c_name);
        TextView units = (TextView) findViewById(R.id.c_units);
        TextView location = (TextView) findViewById(R.id.c_location);
        TextView city = (TextView) findViewById(R.id.c_city);
        TextView state = (TextView) findViewById(R.id.c_state);

        TextView hos = (TextView) findViewById(R.id.c_hospi);
        TextView urgenc = (TextView) findViewById(R.id.c_urgency);
        TextView contact = (TextView) findViewById(R.id.c_numb);
        TextView falt = (TextView) findViewById(R.id.c_instru);
        TextView remaings = (TextView) findViewById(R.id.c_remainigs);
        TextView time_stamp = (TextView) findViewById(R.id.c_timestamp);
        CircularImageView post_by_imageview = (CircularImageView) findViewById(R.id.c_user_image);


        forname.setText(c_poster_name);
        units.setText(c_units + " Units of " + c_blood_grou + " Required");
        location.setText("Country" + c_country);
        hos.setText("At " + c_hospital + " For My " + c_relate);
        urgenc.setText("Urgency: " + c_urgency);
        city.setText("City: " + c_city);
        state.setText("State: " + c_state);
        contact.setText("Contact at: " + c_contac);
        falt.setText("Instructions " + c_hidayat);
        remaings.setText("Remainings: " + c_remaings);
        time_stamp.setText(c_timestamp);
        Glide.with(CommitActivity.this)
                .load(c_image)
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
    }

    private void AdjustBothListViewOnScreen() {
        setListViewHeightBasedOnChildren(emailList);
        setListViewHeightBasedOnChildren(volunters_list);
    }

    private void setDatatoAdapter() {
        listAdapter = new ToBoAdapter(messages, this);
        volunters_listAdapter = new VoluntersAdapter(volunters, this);
        emailList.setAdapter(listAdapter);
        volunters_list.setAdapter(volunters_listAdapter);
    }

    private void IntializeViews() {
        actionBar = this.getSupportActionBar();

        // Set the action bar back button to look like an up button
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        databse = FirebaseDatabase.getInstance().getReference();
        emailList = (ListView) findViewById(R.id.email_Lis);
        volunters_list = (ListView) findViewById(R.id.volunters_list);
        messages = new ArrayList<>();
        volunters = new ArrayList<>();
        id_btn = (Button) findViewById(R.id.bid_button_add);
        com_text = (EditText) findViewById(R.id.com_user);
    }

    private void getDataFromINtent() {
        c_postid = getIntent().getStringExtra("postid");
        c_poster_name = getIntent().getStringExtra("posternbame");
        c_blood_grou = getIntent().getStringExtra("bloudgro");
        c_country = getIntent().getStringExtra("country");
        c_hospital = getIntent().getStringExtra("hospital");
        c_urgency = getIntent().getStringExtra("urgency");
        c_relate = getIntent().getStringExtra("Realation");
        c_contac = getIntent().getStringExtra("contact_nm");
        c_hidayat = getIntent().getStringExtra("hiday");
        c_units = getIntent().getStringExtra("units");
        c_city = getIntent().getStringExtra("city");
        c_state = getIntent().getStringExtra("state");
        c_postby = getIntent().getStringExtra("post_by");
        c_remaings = getIntent().getStringExtra("remaings");
        c_image = getIntent().getStringExtra("post_image");
        c_timestamp = getIntent().getStringExtra("timestamp");
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
