package com.mayigushi.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.mayigushi.common.R;
import com.mayigushi.common.model.Recommend;
import com.mayigushi.common.util.ListUtil;

import java.util.List;

/**
 * Created by tangzhifei on 15/11/21.
 */
public class RecommendListAdapter extends BaseAdapter {

    private Context context;

    private List<Recommend> data;

    public RecommendListAdapter(Context context, List<Recommend> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return ListUtil.getSize(data);
    }

    @Override
    public Recommend getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.recommend_list_item, null);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.titleTextView);
            viewHolder.iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Recommend recommend = data.get(position);
        if (null != recommend) {
            viewHolder.titleTextView.setText(recommend.getTitle());
            viewHolder.iconImageView.setImageResource(recommend.getIconRes());
            viewHolder.descriptionTextView.setText(recommend.getDescription());
        }
        return convertView;
    }

    private final class ViewHolder {

        private ImageView iconImageView;

        private TextView titleTextView;

        private TextView descriptionTextView;

    }

}
