package hue.com.myapplication.activityadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hue.com.myapplication.R;
import hue.com.myapplication.model.User;

public class Showinfor_UsadActivity extends AppCompatActivity {
    TextView tvusname, tvfullname, tvphone, tvgender, tvrule;
    ImageButton btnedit, btncancel;
    ImageView back;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String usn, pass, fullname, phone, gender, rule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showinfor__usad);

        AnhXa();

        Intent intent = getIntent();
        usn = intent.getStringExtra("tenus");
        pass = intent.getStringExtra("pass");
        fullname = intent.getStringExtra("fullname");
        phone = intent.getStringExtra("phone");
        gender = intent.getStringExtra("gender");
        rule = intent.getStringExtra("ruler");

        tvusname.setText(usn);
        tvfullname.setText("Họ và Tên: " + fullname);
        tvphone.setText("SĐT: " + phone);
        tvgender.setText("Giới Tính: " + gender);
        tvrule.setText("Nhóm: " + rule);


        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Showinfor_UsadActivity.this, UpdateRulerActivity.class);
                intent1.putExtra("tenus", usn);
                intent1.putExtra("pass", pass);
                intent1.putExtra("fullname", fullname);
                intent1.putExtra("phone", phone);
                intent1.putExtra("gender", gender);
                intent1.putExtra("ruler", rule);
                startActivity(intent1);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    public void AnhXa() {

        tvusname = findViewById(R.id.tvusname);
        tvfullname = findViewById(R.id.tvfullname1);
        tvphone = findViewById(R.id.tvphone1);
        tvgender = findViewById(R.id.tvgender1);
        tvrule = findViewById(R.id.tvrule1);

        btnedit = findViewById(R.id.btned);
        btncancel = findViewById(R.id.btncen);

        back = findViewById(R.id.back);

    }

    @Override
    protected void onResume() {
        super.onResume();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");

        myRef.child(usn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                tvfullname.setText(user.getFullname());
                tvusname.setText(user.getUsname());
                tvphone.setText(user.getPhone());
                tvgender.setText(user.getGender());
                tvrule.setText(user.getRuler());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
