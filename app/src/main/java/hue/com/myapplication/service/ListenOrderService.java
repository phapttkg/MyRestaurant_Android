package hue.com.myapplication.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

import hue.com.myapplication.activityuser.BillActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.model.Bill;

public class ListenOrderService extends Service implements ChildEventListener {
    FirebaseDatabase database;
    DatabaseReference bill;

    @Override
    public void onCreate() {
        super.onCreate();
        database= FirebaseDatabase.getInstance();
        bill= database.getReference("Bill");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bill.addChildEventListener(this);
        return super.onStartCommand(intent, flags, startId);
    }

    public ListenOrderService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Bill b= dataSnapshot.getValue(Bill.class);
        if(b.getState().equals("Unpaid")){
            showNotification(dataSnapshot.getKey(),b);
        }
    }

    private void showNotification(String key, Bill b) {
        Intent i = new Intent(getBaseContext(), BillActivity.class);
        PendingIntent contentIntent= PendingIntent.getActivity(getBaseContext(),0,i,0);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(getBaseContext());
        builder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL).setTicker("My Restaurant")
                .setContentInfo("New Order").setContentText("you have new order"+key)
                .setSmallIcon(R.drawable.logo1);
        NotificationManager manager= (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
       int randomInt= new Random().nextInt(9999-1)+1;
       manager.notify(randomInt,builder.build());
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
