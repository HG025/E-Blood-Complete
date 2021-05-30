package com.example.mt_2016.marathon_blood.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.mt_2016.marathon_blood.Activities.ConversationActivity;
import com.example.mt_2016.marathon_blood.Adapters.Signup_Adapter;
import com.example.mt_2016.marathon_blood.MainActivity;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Members extends Fragment {
    private ListView emailList;
    private ArrayList<User> messages, listRawData;
    private Signup_Adapter listAdapter;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private User uSer;
    ArrayList<String> list;
    User data;
    DatabaseReference database;
    String pId;
    private Context context;
    String push1;
    String push2;
    String push3;
    public HashMap<String, String> hashObj = new HashMap<>();
    public HashMap<String, String> hashObj2 = new HashMap<>();
    String friend_uid_on_clicked;
    String friend_pic;
    String friend_name;
    String friend_email;
    String friend_gender;
    String friend_bg;
    SearchView mSearchView;
    View view;


    public Members() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_hird, container, false);
        initializeViews();
        setDatatoAdpater();
        setupSearchView();
        goToConversation();
        FirebaseDatabase.getInstance().getReference().child("User_info").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // This method is called once with the initial value and again
                // whenever Data at this location is updated.
                data = dataSnapshot.getValue(User.class);
                list.add(data.getUID());
                if (data.getUID().equals(mAuth.getCurrentUser().getUid())){

                }
                else {
                    User email = new User(data.getUID(), data.getName(), data.getEmail(), data.getCountry()
                            , data.getCity(), data.getPassword(), data.getGEnder(), data.getBlood_Group(), data.getProfile_image());
                    messages.add(email);
                    listRawData.add(email);
                    listAdapter.notifyDataSetChanged();
                }


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

    private void goToConversation() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent conwindow = new Intent(getActivity(), ConversationActivity.class);
                friend_uid_on_clicked = list.get(position);
                friend_pic = messages.get(position).getProfile_image();
                friend_name = messages.get(position).getName();
                friend_gender = messages.get(position).getGEnder();
                friend_email = messages.get(position).getEmail();
                friend_bg = messages.get(position).getBlood_Group();
                Toast.makeText(getActivity(), "uid"+friend_uid_on_clicked, Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), "NAme"+messages.get(position).getName(), Toast.LENGTH_SHORT).show();

                conwindow.putExtra("friend_uid", friend_uid_on_clicked);
                conwindow.putExtra("friend_image", friend_pic);
                conwindow.putExtra("friend_name", friend_name);
                conwindow.putExtra("friend_email", friend_email);
                conwindow.putExtra("friend_gender", friend_gender);
                conwindow.putExtra("friend_bG", friend_bg);
                conwindow.putExtra("imp", friend_uid_on_clicked);
                startActivity(conwindow);
            }
        });
    }

    private void setDatatoAdpater() {
        listAdapter = new Signup_Adapter(messages, listRawData, getActivity());
        emailList.setAdapter(listAdapter);
    }

    private void initializeViews() {
        list = new ArrayList<>();
        mSearchView = (SearchView) view.findViewById(R.id.blocksearch);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser().getUid() != null) {
            final String login_user = mAuth.getCurrentUser().getUid();
        }
        database = FirebaseDatabase.getInstance().getReference();


        emailList = (ListView) view.findViewById(R.id.list_view);
        emailList.setTextFilterEnabled(true);
        messages = new ArrayList<>();
        listRawData = new ArrayList<>();
    }

    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    emailList.clearTextFilter();
                    listAdapter.getFilter().filter("");
                    emailList.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();
                } else {
//            listview.setFilterText(newText);
                    listAdapter.getFilter().filter(newText);
                }

                return false;
            }
        });
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("Search with Name,BloodGRoup");
    }


}
