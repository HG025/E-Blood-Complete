package com.example.mt_2016.marathon_blood.Adapters;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mt_2016.marathon_blood.Models.User;
import com.example.mt_2016.marathon_blood.Models.grouop_convo;
import com.example.mt_2016.marathon_blood.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * Created by BabarMustafa on 2/8/2017.
 */
//
//public class G_MeesageAdapter {
//}

public class G_MeesageAdapter extends BaseAdapter {

    private ArrayList<grouop_convo> dataList;
    private Context context;
    private User uid;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String extention;
    DownloadManager downloadManager;
    String d;

    public G_MeesageAdapter(ArrayList<grouop_convo> dataList, Context context, User uid) {
        this.dataList = dataList;
        this.context = context;
        this.uid = uid;
    }

    public G_MeesageAdapter(ArrayList<grouop_convo> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
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
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view;


            view = inflater.inflate(R.layout.group_message_s_view, null);

        // final View inflater = LayoutInflater.from(MainView.this).inflate(R.layout.for_messages,null);
//        View view = inflater.inflate(R.layout.right_view, null);
//
        TextView for_messages = (TextView) view.findViewById(R.id.g_mesages_view);
        ImageView for_s = (ImageView) view.findViewById(R.id.g_for_file_view);
        TextView for_time = (TextView) view.findViewById(R.id.time_g_message);
        TextView for_name = (TextView) view.findViewById(R.id.g_sendername);





        downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);


        final grouop_convo data = dataList.get(position);

        String mssgs = data.getMsg();
        for_name.setText(dataList.get(position).getSendername());

        final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        final Matcher muy = p.matcher(mssgs);//replace with string to compare
        if(muy.find()) {

            String fileExtenstion = MimeTypeMap.getFileExtensionFromUrl(mssgs);
            String finale_extention_of_file = "." +fileExtenstion;

            for_s.setVisibility(View.VISIBLE);
            // Picasso.with(context).load(mssgs).into(for_s);
            if(finale_extention_of_file.equals(".pdf") || finale_extention_of_file.equals(".3GP")
                    || finale_extention_of_file.equals(".MPG") || finale_extention_of_file.equals(".MPG2")
                    || finale_extention_of_file.equals(".MPG4") ) {
                Glide.with(context)
                        .load(R.drawable.pdf_for)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            if(finale_extention_of_file.equals(".mp4")) {
                Glide.with(context)
                        .load(R.drawable.mob)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".doc") || finale_extention_of_file.equals(".docx") ) {
                Glide.with(context)
                        .load(R.drawable.word_for)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".ppt") || finale_extention_of_file.equals(".pptx") ) {
                Glide.with(context)
                        .load(R.drawable.ppt_for)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".xls") || finale_extention_of_file.equals(".xlsx") ) {
                Glide.with(context)
                        .load(R.drawable.excel_for)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".rar") ) {
                Glide.with(context)
                        .load(R.drawable.rar)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".zip") ) {
                Glide.with(context)
                        .load(R.drawable.fpr_zip)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".txt") ) {
                Glide.with(context)
                        .load(R.drawable.re_text)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".psd") ) {
                Glide.with(context)
                        .load(R.drawable.psd)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equals(".apk") ) {
                Glide.with(context)
                        .load(R.drawable.ap)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }


            else if(finale_extention_of_file.equalsIgnoreCase(".jpg") ||finale_extention_of_file.equalsIgnoreCase(".png")
                    ||finale_extention_of_file.equalsIgnoreCase(".gif") ||
                    finale_extention_of_file.equalsIgnoreCase(".jpeg")) {
                Glide.with(context)
                        .load(mssgs)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else if(finale_extention_of_file.equalsIgnoreCase(".mp3") ||finale_extention_of_file.equalsIgnoreCase(".3ga")
                    ||finale_extention_of_file.equalsIgnoreCase(".amr") || finale_extention_of_file.equalsIgnoreCase(".amr") ||
                    finale_extention_of_file.equalsIgnoreCase(".m4a") || finale_extention_of_file.equalsIgnoreCase(".wav") ||
                    finale_extention_of_file.equalsIgnoreCase(".wma") )  {
                Glide.with(context)
                        .load(R.drawable.au)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
            else{
                Glide.with(context)
                        .load(R.drawable.aty)
                        .into(for_s);
                PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
                String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
                for_time.setText(ago);
            }
        }
        else {

            for_messages.setText(mssgs);
            PrettyTime prettyTime = new PrettyTime(Locale.getDefault());
           String  ago = prettyTime.format(new Date(dataList.get(position).getTimeInMillis()));
            for_time.setText(ago);

        }


        for_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = dataList.get(position).getMsg();

                downloadFileNow("pdf", String.valueOf(d), "file");
            }
        });

        //to still the condition after changes
//        final NotificationMessage todoChekd = (NotificationMessage) getItem(position);



        return view;
    }
    public void downloadFileNow(String fileType, String d, String fileName) {
        Uri uri = Uri.parse(d);
        DownloadManager.Request request = new DownloadManager.Request(uri);
//                set the notification
        request.setDescription("Downloading " + fileName).setTitle("Demo App");



        extention = String.valueOf(d);
        extention = extention.substring(extention.lastIndexOf(".")).substring(0,4);




        request.setDestinationInExternalPublicDir("/bbbbbb/Files", fileName + extention  );



//                make file visible by and manageable by system's download app
        request.setVisibleInDownloadsUi(true);

//                select which network, etc
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE);
        //after download notification will show complete
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

//                queue the download
        downloadManager.enqueue(request);

    }

}
