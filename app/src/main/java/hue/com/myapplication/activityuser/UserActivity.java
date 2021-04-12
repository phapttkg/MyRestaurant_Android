package hue.com.myapplication.activityuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hue.com.myapplication.R;
import hue.com.myapplication.model.User;

public class UserActivity extends AppCompatActivity {
    String tenus;
    TextView tvhoten, tvsdt, tvgioitinh;
    ImageView imvback;
    Button btnCapNhat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        AnhXa();
        Intent intent = getIntent();
        tenus = intent.getStringExtra("tenus");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");

        myRef.child(tenus).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    final User us = dataSnapshot.getValue(User.class);
                    us.setUsname(dataSnapshot.getKey());
                    tvhoten.setText(us.getFullname());
                    tvsdt.setText(us.getPhone());
                    tvgioitinh.setText(us.getGender());
                } catch (Exception ex) {
                    Log.e("ERROR", ex.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnCapNhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = tvhoten.getText().toString();
                String phone = tvsdt.getText().toString();
                String gender = tvgioitinh.getText().toString();
                Intent intent1 = new Intent(UserActivity.this,Update_inforUSActivity.class);
                intent1.putExtra("tenus",tenus);
                intent1.putExtra("fullname",fullname);
                intent1.putExtra("phone",phone);
                intent1.putExtra("gender",gender);
                startActivity(intent1);


            }
        });

        imvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    public void AnhXa() {
        tvhoten = findViewById(R.id.tvtenht);
        tvsdt = findViewById(R.id.tvsdtht);
        tvgioitinh = findViewById(R.id.tvgtht);
        imvback = findViewById(R.id.back);
        btnCapNhat = findViewById(R.id.btnCapNhat);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        myRef.child(tenus).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                tvhoten.setText(user.getFullname());
                tvsdt.setText(user.getPhone());
                tvgioitinh.setText(user.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
