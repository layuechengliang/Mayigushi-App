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
import com.tzf.libo.model.Income;
import com.tzf.libo.util.DBConstants;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by tangzhifei on 15/11/21.
 */
public class IncomeListAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Income> data = new ArrayList<>();

    private LayoutInflater inflater;

    public IncomeListAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<Income> data) {
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
            convertView = inflater.inflate(R.layout.income_list_item_header, parent, false);
            holder.moneyTextView = (TextView) convertView.findViewById(R.id.moneyTextView);
            holder.subjectTextView = (TextView) convertView.findViewById(R.id.subjectTextView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        Income income = data.get(position);
        if (null != income) {
            holder.subjectTextView.setText(DataManager.getBaseKey(income.getSubject(), DBConstants.COMMON_ITEM_SUBJECT));
            holder.moneyTextView.setText("合 : " + getMoneyBySubject(income.getSubject()));
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        return !ListUtil.isEmpty(data) ? data.get(position).getSubject() : 0;
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
            convertView = inflater.inflate(R.layout.income_list_item, parent, false);
            holder.senderTextView = (TextView) convertView.findViewById(R.id.senderTextView);
            holder.moneyTextView = (TextView) convertView.findViewById(R.id.moneyTextView);
            holder.moneyTypeTextView = (TextView) convertView.findViewById(R.id.moneyTypeTextView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Income income = data.get(position);
        if (null != income) {
            holder.senderTextView.setText(income.getSender());
            holder.moneyTypeTextView.setText(DataManager.getBaseKey(income.getMoneyType(), DBConstants.COMMON_ITEM_MONEYTYPE));
            holder.moneyTextView.setText(String.valueOf(income.getMoney()));
        }

        return convertView;
    }

    class HeaderViewHolder {

        TextView subjectTextView;

        TextView moneyTextView;

    }

    class ViewHolder {

        TextView senderTextView;

        TextView moneyTypeTextView;

        TextView moneyTextView;

    }

    private float getMoneyBySubject(int subject) {
        if (ListUtil.isEmpty(data)) {
            return 0;
        }

        float money = 0;
        for (Income income : data) {
            if (subject == income.getSubject()) {
                money += income.getMoney();
            }
        }
        return money;
    }

}