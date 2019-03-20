package com.notificationlibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;

import com.notificationlibrary.exception.ActivityOrFragmentNullException;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

public class NotificationHelper {
    private static WeakReference<Activity> activityParent;

    public static ActivityBuilder with(Fragment fragment) throws ActivityOrFragmentNullException {

        if (fragment == null) {
            throw new ActivityOrFragmentNullException("Fragment is null");
        }
        activityParent = new WeakReference<>((Activity) fragment.getActivity());
        return new ActivityBuilder();
    }

    public static ActivityBuilder with(Activity activity) throws ActivityOrFragmentNullException {
        activityParent = new WeakReference<>(activity);
        if (activityParent.get() == null) {
            throw new ActivityOrFragmentNullException("Activity is null");
        }
        return new ActivityBuilder();
    }

    public static class ActivityBuilder {

        public Builder setNotification(Integer notificationChannelId, Intent intent) {
            return new Builder(notificationChannelId, intent);
        }
    }

    public static class Builder {
        NotificationCompat.Builder notificationBuilder;
        RemoteViews notificationLayout;
        RemoteViews notificationExpendLayout;
        private Integer notificationChannelId;
        private Intent intent;
        private boolean enableVibrate = false;
        private boolean enableAutoCancel = true;

        public Builder(Integer notificationChannelId, Intent intent) {
            this.notificationChannelId = notificationChannelId;
            notificationBuilder = new NotificationCompat.Builder(activityParent.get());
            this.intent = intent;
            notificationLayout = new RemoteViews(activityParent.get().getPackageName(), R.layout.notification_small);
            notificationExpendLayout = new RemoteViews(activityParent.get().getPackageName(), R.layout.notification_expand);

        }

        public Builder setSmallIcon(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Drawable drawable = activityParent.get().getResources().getDrawable(R.drawable.iicon);
            Bitmap bitmap1 = ((BitmapDrawable) drawable).getBitmap();
            bitmap1.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream);

            ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 60, byteArrayOutputStream1);
            try {

                byteArrayOutputStream.write(byteArrayOutputStream1.toByteArray());
                byteArrayOutputStream.close();
                byteArrayOutputStream1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return this;
        }

        public Builder setTitle(String a) {
            notificationLayout.setTextViewText(R.id.notification_title, a);
            return this;
        }

        public Builder setHeader(String a) {
           notificationBuilder.setSubText(a);
            return this;
        }

        public Builder setContentText(String a) {
            notificationLayout.setTextViewText(R.id.notification_content, a);
            return this;
        }

        public Builder setBigText(String a) {
            notificationLayout.setViewVisibility(R.id.frame, View.VISIBLE);
            notificationLayout.setTextViewText(R.id.big_text, a);
            return this;
        }

        public Builder setLargeIcon(Bitmap a) {
            if (a != null) {
                notificationLayout.setViewVisibility(R.id.notification_image, View.VISIBLE);
                notificationLayout.setImageViewBitmap(R.id.notification_image, a);
            }
            return this;
        }

        public Builder setBigLargeIcon(Bitmap a) {
            if(a!=null) {
                notificationLayout.setViewVisibility(R.id.frame, View.VISIBLE);
                notificationLayout.setImageViewBitmap(R.id.notification_image1, a);
            }
            return this;
        }

        public Builder setSound(Uri a) {
            notificationBuilder.setSound(a);
            return this;
        }

        public Builder enableVibrate(boolean a) {
            enableVibrate = a;
            return this;
        }

        public Builder enableAutoCancel(boolean a) {
            enableAutoCancel=a;
            return this;
        }



        public void build() {


            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(activityParent.get(), 0, intent, 0);
            NotificationManager notificationManager =(NotificationManager) activityParent.get().getSystemService(Context.NOTIFICATION_SERVICE);
            String NOTIFICATION_CHANNEL_ID = "101";


            NotificationCompat.DecoratedCustomViewStyle n = new NotificationCompat.DecoratedCustomViewStyle();
            notificationBuilder.setSmallIcon(R.drawable.iicon)
                    .setContentIntent(pendingIntent)
                    .setContent(notificationLayout)
                    //.setCustomBigContentView(notificationExpendLayout)
                    .setWhen(System.currentTimeMillis())
                    .setStyle(n)
                    .setAutoCancel(enableAutoCancel)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_MAX);




//                    if (largeIcon!=null)
//                        notificationBuilder.setLargeIcon(largeIcon);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "my";
                String description = "decr";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel mChannel = null;
                mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
                mChannel.setDescription(description);
                mChannel.setShowBadge(false);
                notificationManager.createNotificationChannel(mChannel);
                notificationBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);

            }
            notificationManager.notify(notificationChannelId, notificationBuilder.build());


        }
    }

}
