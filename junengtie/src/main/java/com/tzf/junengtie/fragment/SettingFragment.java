package com.tzf.junengtie.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tzf.junengtie.R;
import com.tzf.junengtie.db.DataManager;
import com.tzf.junengtie.util.SpManager;

/**
 * @author tangzhifei on 15/11/4.
 */
public class SettingFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private SwitchCompat switchCompat;

    private TextView clearTextView;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);

        boolean checked = SpManager.getInstance().getGlobalBoolean(SpManager.FLOAT_ICON, true);
        switchCompat = (SwitchCompat) view.findViewById(R.id.switchCompat);
        switchCompat.setChecked(checked);
        switchCompat.setOnCheckedChangeListener(this);

        clearTextView = (TextView) view.findViewById(R.id.clearTextView);
        clearTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DataManager.removeHistory();
                Snackbar.make(clearTextView, "成功清除.", Snackbar.LENGTH_LONG).show();
            }

        });

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SpManager.getInstance().setGlobalBoolean(SpManager.FLOAT_ICON, isChecked);
    }

}
