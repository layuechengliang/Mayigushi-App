package com.tzf.libo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tzf.libo.R;
import com.tzf.libo.activity.DetailActivity;
import com.tzf.libo.adapter.SettingListAdapter;
import com.tzf.libo.model.Setting;
import com.tzf.libo.util.DBConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 16/1/10.
 */
public class InputSettingFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.listView)
    ListView listView;

    private SettingListAdapter adapter;

    private List<Setting> data = new ArrayList<>();

    public static InputSettingFragment newInstance() {
        return new InputSettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Setting reason = new Setting();
        reason.setName("送礼事由");
        reason.setType(DBConstants.COMMON_ITEM_REASON);
        data.add(reason);

        Setting subject = new Setting();
        subject.setName("礼簿主题");
        subject.setType(DBConstants.COMMON_ITEM_SUBJECT);
        data.add(subject);

        Setting sendType = new Setting();
        sendType.setName("送礼方式");
        sendType.setType(DBConstants.COMMON_ITEM_SENDTYPE);
        data.add(sendType);

        Setting moneyType = new Setting();
        moneyType.setName("礼金形式");
        moneyType.setType(DBConstants.COMMON_ITEM_MONEYTYPE);
        data.add(moneyType);

        adapter = new SettingListAdapter(getActivity(), data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_setting_fragment, container, false);
        ButterKnife.bind(this, view);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("from", "input");
        intent.putExtra("title", data.get(position).getName());
        intent.putExtra("input_type", data.get(position).getType());
        startActivity(intent);
    }

}