package hue.com.myapplication.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hue.com.myapplication.fragmentadmin.PaidFragment;
import hue.com.myapplication.fragmentadmin.UnpaidFragment;

public class Billadapter  extends FragmentStatePagerAdapter {
    public Billadapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag=null;
        switch (position){
            case 0:
                frag= new PaidFragment();

                break;
            case 1:
                frag= new UnpaidFragment();
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="Approved";

                break;
            case 1:
                title="Pending";
                break;
        }
        return title;

    }
}
