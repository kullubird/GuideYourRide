package android.kevin.guideyourride;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BikeNotificationHandlerService extends Service {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    NotificationManager mNotificationManager;

    public BikeNotificationHandlerService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    int NOTIFICATION_ID = 1;
    private static final String KEY_TEXT_REPLY = "key_text_reply";

    FirebaseDatabase database;
    DatabaseReference myRef;

    final DatabaseHandler db = new DatabaseHandler(this);


    @Override
    public void onCreate() {
        database= FirebaseDatabase.getInstance();



        myRef = database.getReference("b1");


        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                BikeDetails obj = dataSnapshot.getValue(BikeDetails.class);

                if(obj.alcoholStatus==1 & obj.bikeStatus==1)
                    notifyUser("Drunk Riding");
                if(obj.cutCount>=10 & obj.bikeStatus==1)
                    notifyUser("Rash Riding");
                if(obj.speed>=60 & obj.bikeStatus==1)
                    notifyUser("Over Speeding");
                if(obj.hemetStatus==0 & obj.speed>5 & obj.bikeStatus==1)
                    notifyUser("Riding Without Helmet");

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(getApplication(), "Failed to read Value", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notifyUser(String notificationMessage) {

        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        String id = "simple_notification";
        CharSequence name = "GYR Channel";
        String description = "GYR Notification";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        NotificationChannel mChannel = new NotificationChannel(id, name, importance);

        mChannel.setDescription(description);
        mChannel.enableLights(true);

        mChannel.setLightColor(Color.CYAN);
        mChannel.enableVibration(true);

        mNotificationManager.createNotificationChannel(mChannel);



        Intent notificationIntent = new Intent(getApplicationContext(), BikeLog.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                notificationIntent, 0);


        android.app.Notification n = new  android.app.Notification.Builder(getApplicationContext(), id)
                .setContentTitle("Bike Notification")
                .setContentText(notificationMessage)
                .setBadgeIconType(R.mipmap.ic_launcher)
                .setNumber(5)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setAutoCancel(true)
                .setContentIntent(intent)
                .build();




        //n.flags |= Notification.FLAG_AUTO_CANCEL;
        //n.flags |= Notification.FLAG_AUTO_CANCEL;

        Log.e("Notify", "REACHED BUILD");

        mNotificationManager.notify(1, n);

        Log.e("Notify", "BUILD COMPLETED with IMPORTANCE = " + importance);



        //add to db
        String currentDateTime = dateFormat.format(new Date()); // Find todays date

        db.addContact(new Contact(notificationMessage,currentDateTime));


    }


}
