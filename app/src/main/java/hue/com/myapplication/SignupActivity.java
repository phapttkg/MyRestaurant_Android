package hue.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {
    ImageView logo, tron;
    EditText edtEmail, edtPass, edtConfirm, edtFullname, edtPhone;
    Spinner edtGender;
    Button btnSignUp;
    Animation translatex, translatey, fadeIn;
    DatabaseReference myRef;
    FirebaseDatabase database;
    String gioitinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");
        inits();
        logo.startAnimation(fadeIn);
        tron.startAnimation(translatex);
        edtEmail.startAnimation(translatey);
        edtPass.startAnimation(translatey);
        edtConfirm.startAnimation(translatey);
        edtFullname.startAnimation(translatey);
        edtGender.startAnimation(translatey);
        edtPhone.startAnimation(translatey);
        btnSignUp.startAnimation(translatey);


        String[] gd = {"Nam", "Nữ", "Khác"};
        ArrayAdapter arrayAdapter = new ArrayAdapter(SignupActivity.this, R.layout.support_simple_spinner_dropdown_item, gd);
        edtGender.setAdapter(arrayAdapter);
        edtGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gioitinh = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void inits() {
        logo = findViewById(R.id.logo);
        tron = findViewById(R.id.tron1);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtConfirm = findViewById(R.id.edtConfirm);
        edtFullname = findViewById(R.id.edtFullname);
        edtPhone = findViewById(R.id.edtPhone);
        edtGender = findViewById(R.id.edtGender);
        btnSignUp = findViewById(R.id.btnSignUp);
        translatex = AnimationUtils.loadAnimation(this, R.anim.translatex);
        translatey = AnimationUtils.loadAnimation(this, R.anim.translatey);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
    }
    public void Signup(View view) {

        myRef = database.getReference("User");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String usname = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                String confirm = edtConfirm.getText().toString();
                String fullname = edtFullname.getText().toString();
                String phone = edtPhone.getText().toString();
                String ruler = "User";
                if (edtEmail.getText().length() <3) {
                    Toast.makeText(SignupActivity.this, "Username có 3 ky tự trở lên", Toast.LENGTH_SHORT).show();
                } else if (edtPass.getText().length() <3) {
                    Toast.makeText(SignupActivity.this, "Password có 3 ky tự trở lên", Toast.LENGTH_SHORT).show();
                } else if (edtFullname.getText().length() == 0) {
                    Toast.makeText(SignupActivity.this, "Fullname không được để trống", Toast.LENGTH_SHORT).show();
                } else if (edtConfirm.getText().length() == 0) {
                    Toast.makeText(SignupActivity.this, "Mời Confirm lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (edtPhone.getText().length() == 0) {
                    Toast.makeText(SignupActivity.this, "Phone không được để trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (dataSnapshot.child(usname).child(usname).exists()) {
                        Toast.makeText(SignupActivity.this, "USERNAME ĐÃ TỒN TẠI", Toast.LENGTH_SHORT).show();
                    } else if (pass.equals(confirm)) {
                        try {

                            myRef.child(usname).child("usname").setValue(usname);
                            myRef.child(usname).child("password").setValue(pass);
                            myRef.child(usname).child("fullname").setValue(fullname);
                            myRef.child(usname).child("phone").setValue(phone);
                            myRef.child(usname).child("gender").setValue(gioitinh);
                            myRef.child(usname).child("ruler").setValue(ruler);
                            Toast.makeText(SignupActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();



                            Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                            intent.putExtra("tenus",usname);
                            intent.putExtra("pass",pass);
                            startActivity(intent);



                        } catch (Exception ex) {
                            Toast.makeText(SignupActivity.this, "Error:" + ex.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Please Re-enter password", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
