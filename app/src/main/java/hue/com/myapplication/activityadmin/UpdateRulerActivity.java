package hue.com.myapplication.activityadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hue.com.myapplication.R;

public class UpdateRulerActivity extends AppCompatActivity {
    ImageView back;
    TextView tvuname, tvpass, tvfullname, tvphone, tvgender;
    Spinner spRuler;
    Button btnOk, btnCancel;

    String newrule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ruler);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("User");
        AnhXa();
        Intent intent = getIntent();
        final String usn = intent.getStringExtra("tenus");
        String pass = intent.getStringExtra("pass");
        String fullname = intent.getStringExtra("fullname");
        String phone = intent.getStringExtra("phone");
        String gender = intent.getStringExtra("gender");
        String ruler = intent.getStringExtra("ruler");

        tvuname.setText(usn);
        tvpass.setText(pass);
        tvfullname.setText(fullname);
        tvphone.setText(phone);
        tvgender.setText(gender);


        String[] rul = {"Admin", "User", "Employees"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdateRulerActivity.this,
                android.R.layout.simple_list_item_1, rul);
        spRuler.setAdapter(arrayAdapter);
        for (int i = 0; i < spRuler.getCount(); i++) {
            if (spRuler.getItemAtPosition(i).toString().equals(ruler)) {
                spRuler.setSelection(i);
            }
        }

        spRuler.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                newrule = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("User");

                try {
                    myRef.child(usn).child("ruler").setValue(newrule);
                    finish();
                } catch (Exception ex) {
                    Log.e("ERROR", ex.toString());
                }


            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(UpdateRulerActivity.this, "Đã Hủy", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void AnhXa() {
        back = findViewById(R.id.back);
        tvuname = findViewById(R.id.edtEmail);
        tvpass = findViewById(R.id.edtPass);
        tvfullname = findViewById(R.id.edtFullname);
        tvphone = findViewById(R.id.edtPhone);
        tvgender = findViewById(R.id.edtGender);
        spRuler = findViewById(R.id.spRuler);
        btnOk = findViewById(R.id.btnOK);
        btnCancel = findViewById(R.id.btnCancel);

    }
    }

