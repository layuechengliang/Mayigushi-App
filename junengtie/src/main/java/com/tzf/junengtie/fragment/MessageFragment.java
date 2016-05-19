package com.tzf.junengtie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tzf.junengtie.R;
import com.tzf.junengtie.adapter.MessageListAdapter;
import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.AppUtil;
import com.tzf.junengtie.view.RecyclerViewItemDecoration;
import com.tzf.junengtie.wxapi.WXEntryActivity;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author tangzhifei on 15/11/1.
 */
public class MessageFragment extends Fragment implements MessageListAdapter.ItemOnClickListener, MessageListAdapter.ItemOnLongClickListener {

    private int status;

    private Realm realm;

    private MessageListAdapter adapter;

    private RecyclerView messageRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private List<Message> messageList = new ArrayList<>();

    public static MessageFragment newInstance() {
        return new MessageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            status = bundle.getInt("status");
            setRetainInstance(true);
        }
        realm = Realm.getDefaultInstance();
    }

    @Override
    public void onDestroy() {
        if (null != realm) {
            realm.close();
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.message_fragment, container, false);

        RealmResults<Message> realmResults = realm.where(Message.class).equalTo("status", status).findAll();
        realmResults.sort("updateTime", RealmResults.SORT_ORDER_DESCENDING);
        for (Message message : realmResults) {
            messageList.add(message);
        }

        messageRecyclerView = (RecyclerView) view.findViewById(R.id.messageRecyclerView);
        messageRecyclerView.setHasFixedSize(true);
        adapter = new MessageListAdapter(getActivity(), messageList, this, this);
        layoutManager = new LinearLayoutManager(getActivity());
        messageRecyclerView.setLayoutManager(layoutManager);
        messageRecyclerView.addItemDecoration(new RecyclerViewItemDecoration(getActivity()));
        messageRecyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(View view, int postion) {
        AppUtil.copy(messageList.get(postion).getContent());
        Snackbar.make(messageRecyclerView, "复制成功", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemLongClick(View view, int postion) {
        Intent intent = new Intent(getContext(), WXEntryActivity.class);
        startActivity(intent);
    }

}
