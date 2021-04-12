package hue.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hue.com.myapplication.Fragment.CartFragment;
import hue.com.myapplication.Fragment.SettingFragment;
import hue.com.myapplication.Fragment.TypefoodFragment;
import hue.com.myapplication.service.ListenOrderService;

public class TrangChuActivity extends AppCompatActivity {
 Toolbar toolbar1;
SettingFragment settingFragment;
TypefoodFragment typefoodFragment;
CartFragment cartFragment;
FrameLayout frm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu);

//        toolbar1= (Toolbar) findViewById(R.id.toolbar1);
        frm = findViewById(R.id.frm);
        getSupportFragmentManager().beginTransaction().replace(R.id.frm,new TypefoodFragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomngv);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       // toolbar1.setTitle("Menu");
    }



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment=null;
            switch (item.getItemId()) {
                case R.id.itemmenu:
                    selectFragment= new TypefoodFragment();
                    //toolbar1.setTitle("Menu");
                    break;
                case R.id.cart:
                    selectFragment= new CartFragment();
                    //toolbar1.setTitle("Cart");
                    break;
                case R.id.setting:
                    selectFragment= new SettingFragment();
                    //toolbar1.setTitle("Setting");
                   break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frm,selectFragment).commit();
            return true;
        }


    };
}