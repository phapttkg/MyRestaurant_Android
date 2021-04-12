package hue.com.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;


import hue.com.myapplication.Fragment.TypefoodFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView ngvtrangchu;
    TextView txtTenNhanVien;
    Toolbar toolbar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        drawerLayout= findViewById(R.id.drwlayout);
        ngvtrangchu=findViewById(R.id.ngvtrangchu);
        View view= ngvtrangchu.getHeaderView(0);

        txtTenNhanVien=(TextView)view.findViewById(R.id.txtQuanlyct);
        Intent intent= getIntent();
        String tendn=intent.getStringExtra("tendn");
        txtTenNhanVien.setText(tendn);
        setSupportActionBar(toolbar);

       // getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.mo, R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.White));
        ngvtrangchu.setItemIconTintList(null);
        ngvtrangchu.setNavigationItemSelectedListener(this);
        fragmentManager=getSupportFragmentManager();
        FragmentTransaction gt= fragmentManager.beginTransaction();
        TypefoodFragment typefoodFragment = new TypefoodFragment();
        gt.replace(R.id.content,typefoodFragment);
        gt.commit();
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.Menu:

//                FragmentTransaction hm= fragmentManager.beginTransaction();
//                LoaiMonadFragment loaiMonadFragment = new LoaiMonadFragment();
//                hm.replace(R.id.content,loaiMonadFragment);
//                hm.commit();
//                menuItem.setChecked(true);
//                setTitle(menuItem.getTitle());
//                drawerLayout.closeDrawers();
//                break;

        }

        return true;
    }
}
