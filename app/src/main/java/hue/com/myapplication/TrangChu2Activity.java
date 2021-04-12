package hue.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hue.com.myapplication.Fragment.TypefoodFragment;
import hue.com.myapplication.fragmentadmin.BilladFragment;
import hue.com.myapplication.fragmentadmin.LoaiManadFragment;
import hue.com.myapplication.fragmentadmin.MonAnad1Fragment;
import hue.com.myapplication.fragmentadmin.ThongkeadFragment;
import hue.com.myapplication.fragmentadmin.UnpaidFragment;
import hue.com.myapplication.fragmentadmin.UseradFragment;
import hue.com.myapplication.service.ListenOrderService;

public class TrangChu2Activity extends AppCompatActivity {
    FrameLayout frm2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_chu2);
        frm2 = findViewById(R.id.frm2);



        getSupportFragmentManager().beginTransaction().replace(R.id.frm2,new MonAnad1Fragment()).commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomngv2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent service= new Intent(TrangChu2Activity.this, ListenOrderService.class);
        startService(service);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectFragment=null;
            switch (item.getItemId()) {
                case R.id.khachhang:
                    selectFragment= new UseradFragment();

                    break;
                case R.id.itemlm:
                    selectFragment= new LoaiManadFragment();

                    break;
                case R.id.itemma:
                    selectFragment= new MonAnad1Fragment();

                    break;

                case R.id.itemBill:
                    selectFragment= new BilladFragment();

                    break;

                case R.id.itemtk:
                    selectFragment= new ThongkeadFragment();
                    break;


            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frm2,selectFragment).commit();
            return true;
        }


    };
}
