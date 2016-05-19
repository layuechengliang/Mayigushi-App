package com.tzf.libo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayigushi.common.util.ListUtil;
import com.tzf.libo.R;

import java.util.List;

/**
 * @author tangzhifei on 15/11/21.
 */
public class SearchListAdapter extends BaseAdapter {

    private Context context;

    private List<String> data;

    public SearchListAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return ListUtil.getSize(data);
    }

    @Override
    public String getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.input_list_item, null);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nameTextView.setText(data.get(position));
        return convertView;
    }

    private final class ViewHolder {

        private TextView nameTextView;

    }

}
