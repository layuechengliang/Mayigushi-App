package com.tzf.junengtie.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzf.junengtie.BuildConfig;
import com.tzf.junengtie.R;

/**
 * @author tangzhifei on 15/11/4.
 */
public class AboutFragment extends Fragment {

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        TextView versionTextView = (TextView) view.findViewById(R.id.versionTextView);
        versionTextView.setText("巨能贴 v" + BuildConfig.VERSION_NAME);
        return view;
    }

}