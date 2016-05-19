package com.tzf.libo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mayigushi.common.util.ListUtil;
import com.mayigushi.common.util.StringUtil;
import com.mayigushi.common.util.TimeUtil;
import com.tzf.libo.R;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.util.DBConstants;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by tangzhifei on 15/11/21.
 */
public class ExpensesListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Expenses> data = new ArrayList<>();

    private LayoutInflater inflater;

    public ExpensesListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Expenses> data) {
        this.data.clear();
        if (!ListUtil.isEmpty(data)) {
            this.data.addAll(data);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.expenses_list_item_header, parent, false);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            holder.moneyTextView = (TextView) convertView.findViewById(R.id.moneyTextView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Expenses expenses = data.get(position);
        if (null != expenses) {
            holder.timeTextView.setText(expenses.getReceivetime().substring(0, 4) + "年");
            holder.moneyTextView.setText("合 : " + getMoneyByTime(expenses.getReceivetime().substring(0, 4)));
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return !ListUtil.isEmpty(data) ? TimeUtil.getTime(data.get(position).getReceivetime(), "yyyy") : 0;
    }

    @Override
    public int getCount() {
        return !ListUtil.isEmpty(data) ? data.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return !ListUtil.isEmpty(data) ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.expenses_list_item, parent, false);
            holder.receiverTextView = (TextView) convertView.findViewById(R.id.receiverTextView);
            holder.reasonTextView = (TextView) convertView.findViewById(R.id.reasonTextView);
            holder.moneyTextView = (TextView) convertView.findViewById(R.id.moneyTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Expenses expenses = data.get(position);
        if (null != expenses) {
            holder.receiverTextView.setText(expenses.getReceiver());
            holder.reasonTextView.setText(DataManager.getBaseKey(expenses.getReason(), DBConstants.COMMON_ITEM_REASON));
            holder.moneyTextView.setText(String.valueOf(expenses.getMoney()));
        }

        return convertView;
    }

    class HeaderViewHolder {

        TextView timeTextView;

        TextView moneyTextView;

    }

    class ViewHolder {

        TextView receiverTextView;

        TextView reasonTextView;

        TextView moneyTextView;

    }

    private float getMoneyByTime(String time) {
        if (ListUtil.isEmpty(data)) {
            return 0;
        }

        float money = 0;
        for (Expenses expenses : data) {
            if (StringUtil.isEquals(time, expenses.getReceivetime().substring(0, 4))) {
                money += expenses.getMoney();
            }
        }
        return money;
    }

}
