package com.tzf.libo.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzf.libo.BuildConfig;
import com.tzf.libo.R;

/**
 * @author tangzhifei on 15/11/15.
 */
public class AboutFragment extends Fragment {

    private TextView versionTextView;

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        versionTextView = (TextView) view.findViewById(R.id.versionTextView);
        versionTextView.setText("礼来礼往 v" + BuildConfig.VERSION_NAME);
        return view;
    }

}
