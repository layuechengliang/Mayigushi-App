package com.tzf.junengtie.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tzf.junengtie.R;
import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.ListUtil;
import com.tzf.junengtie.util.TimeUtil;

import java.util.List;

/**
 * Created by tangzhifei on 15/11/6.
 */
public class MsgListAdapter extends BaseAdapter {

    private Context context;

    private List<Message> data;

    public MsgListAdapter(Context context, List<Message> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return ListUtil.getSize(data);
    }

    @Override
    public Object getItem(int position) {
        return ListUtil.isEmpty(data) ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.msg_list_item, null);
            viewHolder.contentTextView = (TextView) convertView.findViewById(R.id.contentTextView);
            viewHolder.updateTimeTextView = (TextView) convertView.findViewById(R.id.updateTimeTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Message message = data.get(position);
        if (null != message) {
            viewHolder.contentTextView.setText(message.getContent());
            viewHolder.updateTimeTextView.setText(TimeUtil.getTime(message.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
        }
        return convertView;
    }

    private final class ViewHolder {

        private TextView contentTextView;

        private TextView updateTimeTextView;

    }

}