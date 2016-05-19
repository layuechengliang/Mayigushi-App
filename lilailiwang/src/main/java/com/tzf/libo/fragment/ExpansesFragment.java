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
import com.tzf.libo.activity.ExpensesDetailActivity;
import com.tzf.libo.adapter.ExpensesListAdapter;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Expenses;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * @author tangzhifei on 15/11/17.
 */
public class ExpansesFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Bind(R.id.stickyListView)
    StickyListHeadersListView stickyListView;

    private ExpensesListAdapter adapter;

    private List<Expenses> expensesList;

    public static ExpansesFragment newInstance() {
        return new ExpansesFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.expenses_fragment, container, false);
        ButterKnife.bind(this, view);
        stickyListView.setOnItemClickListener(this);
        adapter = new ExpensesListAdapter(getActivity());
        stickyListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setData() {
        expensesList = sortList(DBTableManager.getInstance(getActivity()).selectExpenses());
        adapter.setData(expensesList);
    }

    private List<Expenses> sortList(List<Expenses> list) {
        if (!ListUtil.isEmpty(list)) {
            Collections.sort(list, new Comparator<Expenses>() {

                public int compare(Expenses o1, Expenses o2) {
                    return o2.getReceivetime().compareTo(o1.getReceivetime());
                }

            });
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ExpensesDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", expensesList.get(position).getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
