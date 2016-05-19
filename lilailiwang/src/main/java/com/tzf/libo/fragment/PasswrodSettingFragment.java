package com.tzf.libo.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayigushi.common.util.SharedPreferencesUtil;
import com.mayigushi.common.util.StringUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tzf.libo.R;
import com.tzf.libo.util.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 16/1/10.
 */
public class PasswrodSettingFragment extends Fragment {

    @Bind(R.id.passwordEditText)
    MaterialEditText passwordEditText;
    @Bind(R.id.rePasswordEditText)
    MaterialEditText rePasswordEditText;

    public static PasswrodSettingFragment newInstance() {
        return new PasswrodSettingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_setting_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void updatePassword() {
        String password = passwordEditText.getText().toString();
        if (password.length() < 6) {
            Snackbar.make(passwordEditText, "密码不能少于6位", Snackbar.LENGTH_LONG).show();
            return;
        }

        String rePassword = rePasswordEditText.getText().toString();
        if (rePassword.length() < 6) {
            Snackbar.make(rePasswordEditText, "确认密码不能少于6位", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!StringUtil.isEquals(password, rePassword)) {
            Snackbar.make(rePasswordEditText, "两次输入密码不一致", Snackbar.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sp = SharedPreferencesUtil.getPreferences(getContext(), C.SP_FILE_NAME);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(C.LOCAL_PASSWORD, password);
        editor.commit();

        Snackbar.make(rePasswordEditText, "密码设置成功", Snackbar.LENGTH_LONG).show();
        getActivity().finish();
    }

}
