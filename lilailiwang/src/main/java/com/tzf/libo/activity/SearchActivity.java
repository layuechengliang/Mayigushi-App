package com.tzf.libo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.mayigushi.common.util.ListUtil;
import com.mayigushi.common.util.StringUtil;
import com.mayigushi.common.util.TimeUtil;
import com.tzf.libo.R;
import com.tzf.libo.adapter.SearchListAdapter;
import com.tzf.libo.adapter.SearchResultListAdapter;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.model.Income;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/12/23.
 */
public class SearchActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.searchListView)
    ListView searchListView;
    @Bind(R.id.searchEditText)
    EditText searchEditText;
    @Bind(R.id.listView)
    ListView listView;

    private List<String> nameList = new ArrayList<>();

    private List searchResultList = new ArrayList();

    private SearchListAdapter searchListAdapter;

    private SearchResultListAdapter searchResultListAdapter;

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nameList.clear();
            String name = s.toString().replaceAll("\'", "");
            if (!StringUtil.isEmpty(name)) {
                HashSet<String> tempList = new HashSet<>();
                tempList.addAll(DBTableManager.getInstance(SearchActivity.this).selectExpensesName(name));
                tempList.addAll(DBTableManager.getInstance(SearchActivity.this).selectIncomeName(name));
                nameList.addAll(tempList);
            }
            searchListAdapter.notifyDataSetChanged();
            listView.setVisibility(ListUtil.isEmpty(nameList) ? View.VISIBLE : View.GONE);
            searchListView.setVisibility(ListUtil.isEmpty(nameList) ? View.GONE : View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

        searchEditText.addTextChangedListener(textWatcher);
        searchListAdapter = new SearchListAdapter(this, nameList);
        searchListView.setAdapter(searchListAdapter);
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchListView.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);

                String name = searchListAdapter.getItem(position);
                searchEditText.removeTextChangedListener(textWatcher);
                searchEditText.setText(name);
                searchEditText.setSelection(name.length());
                searchEditText.addTextChangedListener(textWatcher);

                searchResultList.clear();
                searchResultList.addAll(getDBList(name));
                searchResultListAdapter.notifyDataSetChanged();
                hideSoftInput();
            }

        });

        searchResultListAdapter = new SearchResultListAdapter(this, searchResultList);
        listView.setAdapter(searchResultListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = searchResultListAdapter.getItem(position);
                if (obj instanceof Expenses) {
                    Intent intent = new Intent(SearchActivity.this, ExpensesDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("id", ((Expenses) obj).getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SearchActivity.this, IncomeDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("id", ((Income) obj).getId());
                    bundle.putInt("subject", ((Income) obj).getSubject());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }

        });
    }

    private void hideSoftInput() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(searchEditText.getApplicationWindowToken(), 0);
    }

    private List getDBList(String name) {
        List list = new ArrayList();
        list.addAll(DBTableManager.getInstance(this).selectExpensesByName(name));
        list.addAll(DBTableManager.getInstance(this).selectIncomeByName(name));
        sortByTime(list);
        return list;
    }

    private void sortByTime(List list) {
        if (!ListUtil.isEmpty(list)) {
            Collections.sort(list, new Comparator<Object>() {

                public int compare(Object arg0, Object arg1) {
                    long time0 = arg0 instanceof Income ? TimeUtil.getTime(((Income) arg0).getSendtime(), "yyyy-MM-dd") : TimeUtil.getTime(((Expenses) arg0).getReceivetime(), "yyyy-MM-dd");
                    long time1 = arg1 instanceof Income ? TimeUtil.getTime(((Income) arg1).getSendtime(), "yyyy-MM-dd") : TimeUtil.getTime(((Expenses) arg1).getReceivetime(), "yyyy-MM-dd");
                    long result = time0 - time1;
                    if (result > 0) {
                        return -1;
                    } else if (result == 0) {
                        return 0;
                    } else {
                        return 1;
                    }
                }

            });
        }
    }

}
