package hue.com.myapplication.fragmentadmin;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import hue.com.myapplication.R;
import hue.com.myapplication.adapter.Billadapter;


public class BilladFragment extends Fragment {

    TabLayout tab;
    ViewPager pager;
    UnpaidFragment unpaidFragment;
    PaidFragment paidFragment;
    FragmentManager fragmentManager;
    Billadapter billadapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_billad, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tab= view.findViewById(R.id.tabbill);
        pager= view.findViewById(R.id.paerbill);
        unpaidFragment= new UnpaidFragment();
        paidFragment= new PaidFragment();
        fragmentManager= getFragmentManager();
        billadapter= new Billadapter(fragmentManager);
        pager.setAdapter(billadapter);
        tab.setupWithViewPager(pager);
        //trươt viewpaer
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
