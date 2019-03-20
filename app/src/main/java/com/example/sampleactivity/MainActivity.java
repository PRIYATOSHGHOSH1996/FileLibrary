package com.example.sampleactivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.filelibrary.Callback;
import com.filelibrary.Utils;
import com.filelibrary.exception.ActivityOrFragmentNullException;
import com.notificationlibrary.NotificationHelper;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
Bitmap bitmap;
int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void click(View view) {
       /*final ImageView imageView =findViewById(R.id.image_view);
        final TextView textView =findViewById(R.id.text);
        try {
            Utils.with(this)
                    .getImageFromCamera()
                    .cropEnable(true)
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            imageView.setImageURI(uri);
                            bitmap= BitmapFactory.decodeFile(filepath);
                            textView.setText("image save at: "+filepath);
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();
        } catch (ActivityOrFragmentNullException e) {
            e.printStackTrace();
        }*/
       String CHANNEL_ID ="11";
        int SUMMARY_ID = 0;
        String GROUP_KEY_WORK_EMAIL = "com.android.example.WORK_EMAIL";

        NotificationCompat.Builder newMessageNotification1 =
                new NotificationCompat.Builder(MainActivity.this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("no 1")
                        .setContentText("You will not believe...")
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);



        NotificationCompat.Builder summaryNotification =
                new NotificationCompat.Builder(MainActivity.this)
                        .setContentTitle("no 0")
                        //set content text to support devices running API level < 24
                        .setContentText("Two new messages")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        //build summary info into InboxStyle template
                        .setStyle(new NotificationCompat.InboxStyle()
                                .addLine("Alex Faarborg  Check this out")
                                .addLine("Jeff Chang    Launch Party")
                                .setBigContentTitle("2 new messages")
                                .setSummaryText("janedoe@example.com"))
                        //specify which group this notification belongs to
                        .setGroup(GROUP_KEY_WORK_EMAIL)
                        //set this notification as the summary for the group
                        .setGroupSummary(true)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager =
                null;

            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "my";
            String description = "decr";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = null;
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(description);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
            newMessageNotification1.setChannelId(CHANNEL_ID);
            summaryNotification.setChannelId(CHANNEL_ID);
        }





        notificationManager.notify(id++, newMessageNotification1.build());

        notificationManager.notify(SUMMARY_ID, summaryNotification.build());


        //  Log.e("uri = ",uri+"");
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Utils.Builder.notifyPermissionsChange(requestCode,permissions, grantResults);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
             Utils.Builder.notifyActivityChange(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void click1(View view) {
        final ImageView imageView =findViewById(R.id.image_view1);
        final TextView textView =findViewById(R.id.text1);
        try {
            Utils.with(this)
                    .getImageFile()
                    .compressEnable(true)
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            imageView.setImageURI(uri);
                            bitmap= BitmapFactory.decodeFile(filepath);
                            textView.setText("image save at: "+filepath);
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();
        } catch (ActivityOrFragmentNullException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("ResourceType")
    public void click2(View view) {
        final TextView textView =findViewById(R.id.text2);

            try {
                Intent intent =new Intent(this,MainActivity.class);
                NotificationHelper.with(MainActivity.this)
                        .setNotification(id++,intent)
                        .enableVibrate(false)
                        .setHeader("app")
                        .setBigLargeIcon(bitmap)
                          // .setBigText("atwfygudhsdaiuyfsatyfgdbasfhdsyta\nhh\nhh\nhh\nfdgkas.efn")
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setTitle("new notification")
                        .setContentText("gyhgggggjkfiuegf")
                        .setLargeIcon(bitmap)
                        .build();
            } catch (com.notificationlibrary.exception.ActivityOrFragmentNullException e) {
                e.printStackTrace();
            }

            /*Utils.with(this)
                    .getFile()
                    .getResult(new Callback() {
                        @Override
                        public void onSuccess(Uri uri, String filepath) {
                            Log.e("success = ",uri+"");
                            textView.setText("selected file path is: "+filepath);
                            try {
                                Log.e("copy path",Utils.with(MainActivity.this).copyToAppDirectories(filepath,"temp"));
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ActivityOrFragmentNullException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(String error) {
                            Log.e("error = ",error+"");
                            textView.setText(error);
                        }
                    }).start();*/

    }
}
