package com.example.dashin.LoginModule.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.dashin.LoginModule.fragments.onboarding1;
import com.example.dashin.LoginModule.fragments.onboarding2;
import com.example.dashin.LoginModule.fragments.onboarding3;

public class IntroAdapter extends FragmentPagerAdapter {
    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new onboarding1();
            case 1:
                return new onboarding2();
            case 2:
                return new onboarding3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
