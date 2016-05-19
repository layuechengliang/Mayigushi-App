package com.tzf.libo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzf.libo.R;

/**
 * @author tangzhifei on 15/11/20.
 */
public class ImportExportFragment extends Fragment {

    public static ImportExportFragment newInstance() {
        return new ImportExportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        return view;
    }

}