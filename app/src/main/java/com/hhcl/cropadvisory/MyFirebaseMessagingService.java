package com.hhcl.cropadvisory;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Karthik Kumar K on 21-11-2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    String message, title;
    private NotificationUtils notificationUtils;
    DBHelper db = new DBHelper(this);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        if (remoteMessage.getNotification() != null) {
            Log.d("Notification Body: ", remoteMessage.getNotification().getBody());
            //  handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());

                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    private void createNotification(String body) {
        Intent intent = new Intent(this, Mainactivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Crop Advisory")
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
        // db.addContact(new DBModel("karthik", body,"fsfsdf","ccsc","sasdsaa","dsfcsf","30 dec"));

        // broadcastIntent();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("msgrec", body);
        editor.commit();
    }


    private void handleNotification(String message) {
        Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification1 = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils1 = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {

            JSONObject data = json.getJSONObject("data");

            title = data.getString("azistaCSR_title");
            message = data.getString("azistaCSR_message");
            String imagetype = data.getString("azistaCSR_imageType");
            String imageUrl = data.getString("azistaCSR_image");
            String timestamp = data.getString("azistaCSR_timestamp");
            String notifyid = data.getString("azistaCSR_notificationId");
//            JSONObject payload = data.getJSONObject("payload");

//            Log.e(TAG, "imagetype: " + imagetype);
//            Log.d(TAG, "title: " + message);
            db.addContact(new DBModel("i", title, message, imageUrl, imagetype, notifyid, "dsfcsf", timestamp, "0", "0"));
             storenotmsgPref(message);
            SimpleDateFormat curformate = new SimpleDateFormat("dd-mm-yyyy");
            Date dateobj = curformate.parse(timestamp);
            String newtimestamp = curformate.format(dateobj);
            Log.d("newtimestamp", newtimestamp);
            Calendar currentdate = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
            String formatedate = df.format(currentdate.getTime());
            Log.d("newtimestamp1", formatedate);
            // Reading all contacts
//            Log.d("Reading: ", "Reading all contacts..");
//            List<DBModel> contacts = db.getAllContacts();
//
//            for (DBModel cn : contacts) {
//                String log = "Id: "+cn.getNo_id()+" ,Name: " + cn.getTt_name() + " ,noti: " + cn.getMsg()+ cn.getNo_id()+ " ,noti: " + cn.getMsg()+ cn.getImgfull()+cn.getStar();
//                // Writing Contacts to log
//                Log.d("Name: ", log);
//            }
            URL iurl = new URL(imageUrl);
            URLConnection ucon = iurl.openConnection();

            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);

            ByteArrayBuffer baf = new ByteArrayBuffer(500);
            int current = 0;
            while ((current = bis.read()) != -1) {
     /* This approach slowdown the process*/
                baf.append((byte) current);
            }

            byte[] img_ary = baf.toByteArray();

            ByteArrayInputStream imageStream = new ByteArrayInputStream(
                    img_ary);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);

            //  if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            //storenotmsgPref(message);
           // Log.d("foreground", message);
if (title.equalsIgnoreCase("") && message.equalsIgnoreCase("")){
    storenotmsgPref(message);
}else {
    Intent intent = new Intent(this, Mainactivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent resultIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT);

    Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.nicon)
            .setContentTitle(title)
            // .setLargeIcon(theImage)
            .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(theImage)

            )
            .setAutoCancel(true)
            .setSound(notificationSoundURI)
            .setContentIntent(resultIntent);
    int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
    NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    notificationManager.notify(m, mNotificationBuilder.build());
    // app is in foreground, broadcast the push message
    Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
    pushNotification.putExtra("message", message);
    LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

    // play notification sound
    NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
    notificationUtils.playNotificationSound();
    int badgeCount = 1;
    ShortcutBadger.applyCount(this, badgeCount); //for 1.1.4+
    //ShortcutBadger.with(getApplicationContext()).count(badgeCount);
}
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    private void storenotmsgPref(String msg) {
        Intent intent = new Intent("YourAction");
        Bundle bundle = new Bundle();
        //bundle.put... // put extras you want to pass with broadcast. This is optional
        bundle.putString("valueName", msg);
// bundle.putDouble("doubleName", speed);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }

}
