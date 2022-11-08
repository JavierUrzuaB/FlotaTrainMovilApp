package dispo.train.fcba.cl.flotatrainmovil.views;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import dispo.train.fcba.cl.flotatrainmovil.R;


/**
 * @author ton1n8o - antoniocarlos.dev@gmail.com on 6/13/16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static String TAG = "NOTICIAS";
    public String title;
    public String body;
    public String keyNotif;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String from =remoteMessage.getFrom();
        Log.d(TAG, "Mensaje recibidode:"+ from);

        if(remoteMessage.getData().size() > 0){
            Log.d(TAG,"Data: " + remoteMessage.getData());
            keyNotif = remoteMessage.getData().get("icon");
        }

        //llega la notificacion
        if(remoteMessage.getNotification() != null){
            Log.d(TAG,"Notificación: "+ remoteMessage.getNotification().getTitle() + remoteMessage.getNotification().getBody());
            Log.d(TAG,"Notificación Icon: "+remoteMessage.getNotification().getIcon());
            //construir la notifiacion
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
            if(keyNotif.equals("D")){
                mostrarNotificacionD(title, body);
            }else {
                mostrarNotificacionM(title, body);
            }
        }

    }

    private void mostrarNotificacionM (String title, String body){

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifionBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icon_marca)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notifionBuilder.build());
    }
    private void mostrarNotificacionD (String title, String body){

        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notifionBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.icon_desmarca)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notifionBuilder.build());
    }
}
