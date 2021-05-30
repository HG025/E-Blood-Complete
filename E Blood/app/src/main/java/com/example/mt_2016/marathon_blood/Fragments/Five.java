package com.example.mt_2016.marathon_blood.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.example.mt_2016.marathon_blood.Adapters.Notification_Adapter;
import com.example.mt_2016.marathon_blood.Adapters.Signup_Adapter;
import com.example.mt_2016.marathon_blood.Models.NotificationMessage;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Five extends Fragment {
    ListView notification_list;
    ArrayList<Post> notifications;
    Notification_Adapter notification_adapterr;
    Post data;
    private FirebaseAuth mAuth;
    View view;
    String login_user;

    public Five() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_five, container, false);
        initializeViews();
        setValuesToAdapter();
        FirebaseDatabase.getInstance().getReference().child("Notification_for_posts")
                .child(login_user)
                .addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                data = dataSnapshot.getValue(Post.class);
                Post notification = new Post(data.getPost_by_id(),data.getRemainings(),data.getPost_by_image(),data.getPush_c(),data.getP_naem(),
                        data.getP_group(),data.getP_units(),data.getP_urgency(),data.getP_country(),data.getP_state(),
                        data.getP_city(),data.getP_hos(),data.getP_rel(),data.getCont(),data.getAdd_i(),data.getTimeInMillis());
                notifications.add(0,notification);

                notification_adapterr.notifyDataSetChanged();
//

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
        return view;
    }

    private void setValuesToAdapter() {
        notification_adapterr = new Notification_Adapter(notifications, getActivity());
        notification_list.setAdapter(notification_adapterr);
    }

    private void initializeViews() {
        notification_list = (ListView) view.findViewById(R.id.notification_list_view);
        notifications = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        login_user= mAuth.getCurrentUser().getUid();
    }

}
