package hue.com.myapplication.Fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import hue.com.myapplication.activityuser.DoiMK_Activity;
import hue.com.myapplication.LoginActivity;
import hue.com.myapplication.R;
import hue.com.myapplication.TrangChuActivity;
import hue.com.myapplication.activityuser.UserActivity;
import hue.com.myapplication.activityuser.BillUserActivity;
import hue.com.myapplication.activityuser.GioiThieuActivity;
import hue.com.myapplication.dao.UserDao;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    Button btnthongtin, btndoimatkhau, btnhoadon, btngioithieu, btnthoat;
    ImageView back;
    String a;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        btnthongtin = view.findViewById(R.id.btnthongtin);
        btndoimatkhau = view.findViewById(R.id.btndoimk);
        btnhoadon = view.findViewById(R.id.btnhoadon);
        btngioithieu = view.findViewById(R.id.btnAbout);
        btnthoat = view.findViewById(R.id.btnLogout);
        back = view.findViewById(R.id.back);
        a = UserDao.cruser.getUsname();
        btnthongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.putExtra("tenus", a);
                startActivity(intent);
            }
        });
        btndoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DoiMK_Activity.class);
                startActivity(intent);
            }
        });


        btngioithieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), GioiThieuActivity.class);
                startActivity(intent);
            }
        });


        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        btnhoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BillUserActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



}
