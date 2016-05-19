package com.tzf.libo.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayigushi.common.util.SharedPreferencesUtil;
import com.mayigushi.common.util.StringUtil;
import com.meiqia.meiqiasdk.activity.MQConversationActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tzf.libo.R;
import com.tzf.libo.activity.HomeActivity;
import com.tzf.libo.util.C;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 16/1/10.
 */
public class LoginFragment extends Fragment {

    @Bind(R.id.passwordEditText)
    MaterialEditText passwordEditText;
    @Bind(R.id.helpButton)
    FloatingActionButton helpButton;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);
        ButterKnife.bind(this, view);

        helpButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MQConversationActivity.class);
                startActivity(intent);
            }

        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void login() {
        SharedPreferences sp = SharedPreferencesUtil.getPreferences(getActivity(), C.SP_FILE_NAME);
        String localPassword = sp.getString(C.LOCAL_PASSWORD, StringUtil.EMPTY_STRING);

        String password = passwordEditText.getText().toString();
        if (!StringUtil.isEquals(password, localPassword)) {
            Snackbar.make(passwordEditText, "密码错误", Snackbar.LENGTH_LONG).show();
            return;
        }

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}
