package com.tzf.junengtie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzf.junengtie.R;
import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.TimeUtil;

import java.util.List;

/**
 * Created by tangqi on 8/20/15.
 */
public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Message> mNewsList;

    private LayoutInflater mLayoutInflater;

    private ItemOnClickListener itemOnClickListener;

    private ItemOnLongClickListener itemOnLongClickListener;

    public MessageListAdapter(Context mContext, List<Message> mNewsList, ItemOnClickListener onClickListener, ItemOnLongClickListener onLongClickListener) {
        this.mNewsList = mNewsList;
        this.itemOnClickListener = onClickListener;
        this.itemOnLongClickListener = onLongClickListener;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeViewHolder(mLayoutInflater.inflate(R.layout.message_list_item, parent, false), itemOnClickListener, itemOnLongClickListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message news = mNewsList.get(position);
        ((ThemeViewHolder) holder).contentTextView.setText(news.getContent());
        ((ThemeViewHolder) holder).updateTimeTextView.setText(TimeUtil.getTime(news.getUpdateTime(), "yyyy-MM-dd hh:mm:ss"));
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    public class ThemeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView contentTextView;
        TextView updateTimeTextView;
        ItemOnClickListener itemOnClickListener;
        ItemOnLongClickListener itemOnLongClickListener;

        public ThemeViewHolder(View v, ItemOnClickListener onClickListener, ItemOnLongClickListener onLongClickListener) {
            super(v);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
            this.itemOnClickListener = onClickListener;
            this.itemOnLongClickListener = onLongClickListener;
            contentTextView = (TextView) v.findViewById(R.id.contentTextView);
            updateTimeTextView = (TextView) v.findViewById(R.id.updateTimeTextView);
        }

        @Override
        public void onClick(View v) {
            if (null != itemOnClickListener) {
                itemOnClickListener.onItemClick(v, getLayoutPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (null != itemOnLongClickListener) {
                itemOnLongClickListener.onItemLongClick(v, getLayoutPosition());
            }
            return true;
        }

    }

    public interface ItemOnClickListener {
        void onItemClick(View view, int postion);
    }

    public interface ItemOnLongClickListener {
        void onItemLongClick(View view, int postion);
    }

}
