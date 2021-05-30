package com.example.mt_2016.marathon_blood.Fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup.LayoutParams;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.mt_2016.marathon_blood.Activities.CommitActivity;
import com.example.mt_2016.marathon_blood.Activities.PostActivity;
import com.example.mt_2016.marathon_blood.Activities.Welcome_for_User;
import com.example.mt_2016.marathon_blood.Adapters.PostAdapter;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.Models.comit_for;
import com.example.mt_2016.marathon_blood.NetWorkClasses.ConnectivityStatus;
import com.example.mt_2016.marathon_blood.R;
import com.example.mt_2016.marathon_blood.Services.PostBloodService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.mikhaellopez.circularimageview.CircularImageView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static com.example.mt_2016.marathon_blood.Fragments.Login.showDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class One extends Fragment {
    private ListView emailList;
    private ArrayList<Post> messages;
    private PostAdapter listAdapter;
    Post data;
    ArrayList<String> list;
    ArrayList<String> lisft;
    String postid;
    String poster_name;
    String blood_grou;
    String country;
    String city;
    String state;
    String hospital;
    String urgency;
    String relate;
    String contac;
    String hidayat;
    String un;
    private PopupWindow mPopupWindow;
    DatabaseReference databse;
    String current;
    private FirebaseAuth mAuth;
    boolean isLongClick = false;
    comit_for daty;
    public HashMap<String, Object> hashObj = new HashMap<>();
    CircularImageView imageView;
    String f_vas;
    private Context mContext;
    String Postby;
    String remaings;
    String post_imsge;
    View view;
    TextView go_to_post;

    public One() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_one, container, false);
        initalizeViews();
        setDataToAdapter();
        Loadimage();
        PostDetailsView();
        toDonateBlood();
        RequestaBlood();

        FirebaseDatabase.getInstance()
                .getReference()
                .child("Post_By-User")
                .addChildEventListener(new ChildEventListener() {

                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // This method is called once with the initial value and again
                        // whenever Data at this location is updated.
                        data = dataSnapshot.getValue(Post.class);
                        Log.d("values", dataSnapshot.getValue().toString());
                        list.add(data.getPush_c());
                        lisft.add(data.getRemainings());
                        // Log.v("DATA", "" + data.getId() + data.getName() + data.getCity());
                        Post email = new Post(data.getPost_by_id(), data.getRemainings(), data.getPost_by_image(), data.getPush_c(), data.getP_naem(), data.getP_group(), data.getP_units(),
                                data.getP_urgency(), data.getP_country(), data.getP_state(), data.getP_city(), data.getP_hos(),
                                data.getP_rel(), data.getCont(), data.getAdd_i(), data.getTimeInMillis());
                        messages.add(email);
                        listAdapter.notifyDataSetChanged();
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

    private void RequestaBlood() {
        go_to_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ConnectivityStatus.isConnected(getActivity())){
                    Login.showDialog(getActivity(),getResources().getString(R.string.no_internet));


                }else {
                    Intent i = new Intent(getActivity(), PostActivity.class);
                    startActivity(i);
                }

            }
        });
    }

    private void toDonateBlood() {
        emailList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int pos, long l) {
                isLongClick = true;
                if(!ConnectivityStatus.isConnected(getActivity())){
                    Login.showDialog(getActivity(),"You Cannot Donate Blood Beacause You have No Internet Connection Kindly Checck Your Internet Connecction.");


                }else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Volunteer");
                    builder.setMessage("You want to Donate or Note ?");
                    builder.setPositiveButton("Donate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int gyt = Integer.parseInt(lisft.get(pos));
                            if (mAuth.getCurrentUser().getUid().equals(messages.get(pos).getPost_by_id())) {
                                final AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setTitle("Warninigs");
                                builder1.setMessage("You are not allowed to donate blood on our own Post");
                                builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                builder1.create().show();
                            } else {
                                if (gyt == 0) {
                                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                                    // Inflate the custom layout/view
                                    View customView = inflater.inflate(R.layout.requirements_popup, null);

                                    mPopupWindow = new PopupWindow(
                                            customView,
                                            LayoutParams.WRAP_CONTENT,
                                            LayoutParams.WRAP_CONTENT
                                    );

                                    // Set an elevation value for popup window
                                    // Call requires API level 21
                                    if (Build.VERSION.SDK_INT >= 21) {
                                        mPopupWindow.setElevation(5.0f);
                                    }

                                    // Get a reference for the custom view close button
                                    ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);

                                    // Set a click listener for the popup window close button
                                    closeButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            // Dismiss the popup window
                                            mPopupWindow.dismiss();
                                        }
                                    });

                                    mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                                } else {
                                    int yu = gyt - 1;
                                    f_vas = String.valueOf(yu);
                                    postid = list.get(pos);
                                    databse
                                            .child("Post_By-User")
                                            .child(postid)
                                            .child("remainings")
                                            .setValue(f_vas);
                                    databse
                                            .child("users_post")
                                            .child(messages.get(pos).getPost_by_id())
                                            .child("My_post")
                                            .child(postid)
                                            .child("remainings")
                                            .setValue(f_vas);
                                    listAdapter.notifyDataSetChanged();
                                    String p = databse.push().getKey();
                                    String com = "Donated";
                                    String bidn = Welcome_for_User.name;
                                    hashObj.put("comit_nmae", bidn);
                                    hashObj.put("commit_text", com);
                                    hashObj.put("confirm_donation", "not yet");
                                    hashObj.put("push_of", p);
                                    hashObj.put("mTimestamp", ServerValue.TIMESTAMP);


                                    databse
                                            .child("Volunters_for_posts")
                                            .child(postid)
                                            .child(p)
                                            .setValue(hashObj);
                                    Toast.makeText(getActivity(), "You Succesfully Announced the Blood Donation", Toast.LENGTH_SHORT).show();
                                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                                    ft.detach(One.this).attach(One.this).commit();
                                }
                            }


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    builder.create().show();
                }


                return false;
            }
        });
    }

    private void PostDetailsView() {
        emailList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if (isLongClick == false) {
                    Intent conwindow = new Intent(getActivity(), CommitActivity.class);
                    postid = list.get(position);
                    poster_name = messages.get(position).getP_naem();
                    blood_grou = messages.get(position).getP_group();
                    relate = messages.get(position).getP_rel();
                    country = messages.get(position).getP_country();
                    hospital = messages.get(position).getP_hos();
                    city = messages.get(position).getP_city();
                    state = messages.get(position).getP_state();
                    urgency = messages.get(position).getP_urgency();
                    contac = String.valueOf(messages.get(position).getCont());
                    hidayat = messages.get(position).getAdd_i();
                    un = messages.get(position).getP_units();
                    Postby = messages.get(position).getPost_by_id();
                    remaings = messages.get(position).getRemainings();
                    post_imsge = messages.get(position).getPost_by_image();
                    PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                    String ago = prettyTime.format(new Date(messages.get(position).getTimeInMillis()));

                    conwindow.putExtra("postid", postid);
                    conwindow.putExtra("posternbame", poster_name);
                    conwindow.putExtra("bloudgro", blood_grou);
                    conwindow.putExtra("Realation", relate);
                    conwindow.putExtra("country", country);
                    conwindow.putExtra("city", city);
                    conwindow.putExtra("state", state);
                    conwindow.putExtra("hospital", hospital);
                    conwindow.putExtra("urgency", urgency);
                    conwindow.putExtra("contact_nm", contac);
                    conwindow.putExtra("hiday", hidayat);
                    conwindow.putExtra("units", un);
                    conwindow.putExtra("post_by", Postby);
                    conwindow.putExtra("remaings", remaings);
                    conwindow.putExtra("post_image", post_imsge);
                    conwindow.putExtra("timestamp", ago);
                    startActivity(conwindow);
                }
                isLongClick = false;
            }
        });
    }

    private void Loadimage() {


        Glide.with(getActivity())
                .load(Welcome_for_User.photoUrl)
                .error(R.drawable.user1)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imageView);
    }

    private void setDataToAdapter() {
        listAdapter = new PostAdapter(messages, getActivity());
        emailList.setAdapter(listAdapter);
    }

    private void initalizeViews() {
        list = new ArrayList<>();
        lisft = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        current = mAuth.getCurrentUser().getUid();
        go_to_post = (TextView) view.findViewById(R.id.to_post);
        emailList = (ListView) view.findViewById(R.id.listview);
        imageView = (CircularImageView) view.findViewById(R.id.user_image);
        databse = FirebaseDatabase.getInstance().getReference();
        messages = new ArrayList<>();
        mContext = getContext();
    }

}
