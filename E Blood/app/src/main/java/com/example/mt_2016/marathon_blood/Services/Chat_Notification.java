package com.example.mt_2016.marathon_blood.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.mt_2016.marathon_blood.Activities.Welcome_for_User;
import com.example.mt_2016.marathon_blood.Models.NotificationMessage;
import com.example.mt_2016.marathon_blood.Models.Post;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.Random;

/**
 * Created by BabarMustafa on 6/13/2017.
 */

//public class Chat_Notification {
//}

public class Chat_Notification extends Service {

    private FirebaseUser currentUser;
    String whoMessage;
    NotificationMessage data;

    public Chat_Notification() {
    }

    @Override
    public void onCreate() {
        if(FirebaseAuth.getInstance().getCurrentUser().getUid() != null){

            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String id_of_login_user = currentUser.getUid();

            FirebaseDatabase.getInstance().getReference().child("NotificationsForChatOfUsers")
                    .child(id_of_login_user)

                    .addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                            currentUser = FirebaseAuth.getInstance().getCurrentUser();


                            Log.v("USER", "" + currentUser.getUid());


                            data = dataSnapshot.getValue(NotificationMessage.class);

                            notif(data.getmTimestamp(),data.getMessage(),data.getNotfpush(),data.getUUID(),data.getWhoMessage());

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
                        public void onCancelled(DatabaseError databaseError) {

                        }


                    });
        }
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public IBinder onBind(Intent intent) {
        return null;
    }

    public void notif(long timestamp,String msg,String notpush,String uid,String messnger) {
        Intent intent = new Intent(this,Welcome_for_User.class);

        //view of notification
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notification = new Notification.Builder(this)
                .setTicker("Chat Notification")
                .setContentTitle("Message from "+messnger)
                .setStyle(new Notification.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setTicker("Notification From E-Blood Provider")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setVibrate(new long[]{500, 500})
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int randomNumber = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(randomNumber, notification.build());
//after delievery of notification removed from notification
        FirebaseDatabase.getInstance().getReference()
                .child("NotificationsForChatOfUsers")
                .child(currentUser.getUid())
                .child(notpush)
                .removeValue();


    }
}
