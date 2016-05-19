package com.tzf.junengtie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tzf.junengtie.R;
import com.tzf.junengtie.adapter.MsgListAdapter;
import com.tzf.junengtie.db.DataManager;
import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.AppUtil;
import com.tzf.junengtie.wxapi.WXEntryActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzhifei on 15/11/1.
 */
public class MsgFragment extends Fragment implements AdapterView.OnItemClickListener {

    private boolean loaded = false;

    private int status;

    private ListView listView;

    private List<Message> messageList = new ArrayList<>();

    private MsgListAdapter adapter;

    public static MsgFragment newInstance() {
        return new MsgFragment();
    }

    private void resetData() {
        messageList.clear();
        messageList.addAll(DataManager.getMessageByStatus(status));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            status = bundle.getInt("status");
            setRetainInstance(true);
        }

        adapter = new MsgListAdapter(getActivity(), messageList);
    }

    @Override
    public void onResume() {
        super.onResume();
        resetData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != listView) {
            unregisterForContextMenu(listView);
        }

        if (loaded && getUserVisibleHint()) {
            resetData();

            registerForContextMenu(listView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        loaded = true;
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AppUtil.copy(messageList.get(position).getContent());
        Snackbar.make(listView, "复制成功", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(0 == status ? R.menu.home_menu : R.menu.home_favor_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (getUserVisibleHint()) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Message message = messageList.get(info.position);
            switch (item.getItemId()) {
                case R.id.favor:
                    DataManager.addFavorite(message.getUpdateTime());
                    messageList.remove(info.position);
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.share:
                    Snackbar.make(listView, message.getContent(), Snackbar.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), WXEntryActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("message", message);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.delete:
                    DataManager.remove(message.getUpdateTime());
                    messageList.remove(info.position);
                    adapter.notifyDataSetChanged();
                    break;
            }

            return true;
        }

        return super.onContextItemSelected(item);
    }

}
