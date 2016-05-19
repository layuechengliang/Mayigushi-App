package com.tzf.libo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayigushi.common.util.ListUtil;
import com.tzf.libo.R;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.model.Income;
import com.tzf.libo.util.DBConstants;

import java.util.List;

/**
 * @author tangzhifei on 15/11/21.
 */
public class SearchResultListAdapter extends BaseAdapter {

    private Context context;

    private List data;

    public SearchResultListAdapter(Context context, List data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.result_list_item, null);
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.typeTextView);
            viewHolder.reasonTextView = (TextView) convertView.findViewById(R.id.reasonTextView);
            viewHolder.moneyTextView = (TextView) convertView.findViewById(R.id.moneyTextView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Object obj = data.get(position);
        if (null != obj) {
            if (obj instanceof Expenses) {
                Expenses expenses = (Expenses) obj;
                viewHolder.nameTextView.setText(expenses.getReceiver());
                viewHolder.moneyTextView.setText(String.valueOf(expenses.getMoney()));
                viewHolder.typeTextView.setText("支");
                viewHolder.timeTextView.setText(expenses.getReceivetime());
                viewHolder.reasonTextView.setText(DataManager.getBaseKey(expenses.getReason(), DBConstants.COMMON_ITEM_REASON));
            } else {
                Income income = (Income) obj;
                viewHolder.nameTextView.setText(income.getSender());
                viewHolder.moneyTextView.setText(String.valueOf(income.getMoney()));
                viewHolder.typeTextView.setText("收");
                viewHolder.timeTextView.setText(income.getSendtime());
                viewHolder.reasonTextView.setText(DataManager.getBaseKey(income.getSubject(), DBConstants.COMMON_ITEM_SUBJECT));
            }
        }

        return convertView;
    }

    private final class ViewHolder {

        private TextView typeTextView;

        private TextView nameTextView;

        private TextView timeTextView;

        private TextView reasonTextView;

        private TextView moneyTextView;

    }

}
