package com.tzf.libo.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mayigushi.common.util.ListUtil;
import com.tzf.libo.R;
import com.tzf.libo.activity.IncomeDetailActivity;
import com.tzf.libo.adapter.IncomeListAdapter;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Income;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @author tangzhifei on 15/11/17.
 */
public class IncomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.stickyListView)
    StickyListHeadersListView stickyListView;

    private IncomeListAdapter adapter;

    private List<Income> incomeList;

    public static IncomeFragment newInstance() {
        return new IncomeFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_fragment, container, false);
        ButterKnife.bind(this, view);
        stickyListView.setOnItemClickListener(this);
        adapter = new IncomeListAdapter(getActivity());
        stickyListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setData() {
        incomeList = sortList(DBTableManager.getInstance(getActivity()).selectIncome());
        adapter.setData(incomeList);
    }

    private List<Income> sortList(List<Income> list) {
        if (!ListUtil.isEmpty(list)) {
            Collections.sort(list, new Comparator<Income>() {

                public int compare(Income o1, Income o2) {
                    return o2.getSubject() - o1.getSubject();
                }

            });
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), IncomeDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("id", incomeList.get(position).getId());
        bundle.putInt("subject", incomeList.get(position).getSubject());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
