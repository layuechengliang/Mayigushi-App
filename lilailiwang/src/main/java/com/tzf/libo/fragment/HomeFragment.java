package com.tzf.libo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzf.libo.R;
import com.tzf.libo.activity.ExpensesEditActivity;
import com.tzf.libo.activity.IncomeEditActivity;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    public void add() {
        Intent intent = new Intent();
        if (0 == viewPager.getCurrentItem()) {
            intent.setClass(getActivity(), IncomeEditActivity.class);
        } else {
            intent.setClass(getActivity(), ExpensesEditActivity.class);
        }
        startActivity(intent);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return i == 0 ? IncomeFragment.newInstance() : ExpansesFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return 0 == position ? "我的礼簿" : "支出";
        }

    }

}
