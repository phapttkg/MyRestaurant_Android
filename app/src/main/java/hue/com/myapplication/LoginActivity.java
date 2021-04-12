package hue.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hue.com.myapplication.dao.UserDao;
import hue.com.myapplication.database.dphelper;
import hue.com.myapplication.model.User;
import hue.com.myapplication.service.ListenOrderService;

public class LoginActivity extends AppCompatActivity {
    ImageView logo, tron;
    EditText edtEmail, edtPass;
    Button btnLogin, btnSignUp;
    Animation translatey, fadeIn, translatex;
    DatabaseReference myRef;
    FirebaseDatabase database;
    CheckBox cbremember;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inits();

//        Intent intent = getIntent();
//        String us = intent.getStringExtra("tenus");
//        String pass = intent.getStringExtra("pass");
//
//
//        edtEmail.setText(us);
//        edtPass.setText(pass);

        //remember user
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            edtEmail.setText(loginPreferences.getString("username", ""));
            edtPass.setText(loginPreferences.getString("password", ""));
            cbremember.setChecked(true);
        }


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("User");

        dphelper dphelper = new dphelper(this);
        dphelper.open();
        logo.startAnimation(fadeIn);
        tron.startAnimation(translatex);
        edtPass.startAnimation(translatey);
        edtEmail.startAnimation(translatey);
        btnSignUp.startAnimation(translatey);
        btnLogin.startAnimation(translatey);
        cbremember.startAnimation(translatey);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(LoginActivity.this);
                mDialog.setMessage("Please waiting");
                mDialog.show();
                String username = edtEmail.getText().toString();
                String pass = edtPass.getText().toString();
                if ((username.length() == 0) || (pass.length() == 0)) {
                    Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                } else {
                    if (cbremember.isChecked()) {
                        loginPrefsEditor.putBoolean("saveLogin", true);
                        loginPrefsEditor.putString("username", username);
                        loginPrefsEditor.putString("password", pass);
                        loginPrefsEditor.commit();
                    } else {
                        loginPrefsEditor.clear();
                        loginPrefsEditor.commit();
                    }

                    Login(username, pass);
                }

            }
        });
    }

    public void inits() {
        logo = findViewById(R.id.logo);
        tron = findViewById(R.id.tron1);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSign);
        cbremember = findViewById(R.id.cbremember);
        translatex = AnimationUtils.loadAnimation(this, R.anim.translatex);
        translatey = AnimationUtils.loadAnimation(this, R.anim.translatey);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);

    }

    private void Login(final String usname, final String password) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(usname).exists()) {
                    if (!usname.isEmpty() || !password.isEmpty()) {
                        User user = dataSnapshot.child(usname).getValue(User.class);
                        if (user.getPassword().equals(password)) {
                            if (user.getRuler().equals("Admin")) {
                                UserDao.cruser = user;
                                Intent i = new Intent(LoginActivity.this, TrangChu2Activity.class);
                                startActivity(i);

                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            } else {
                                UserDao.cruser = user;
                                Intent i = new Intent(LoginActivity.this, TrangChuActivity.class);
                                i.putExtra("tenus", edtEmail.getText().toString());
                                startActivity(i);
                                Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
