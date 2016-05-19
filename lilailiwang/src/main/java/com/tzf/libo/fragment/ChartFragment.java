package com.tzf.libo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkfuns.logutils.LogUtils;
import com.mayigushi.common.util.ListUtil;
import com.tzf.libo.R;
import com.tzf.libo.db.DBTableManager;

import java.util.List;

/**
 * @author tangzhifei on 15/12/25.
 */
public class ChartFragment extends Fragment {

    public static ChartFragment newInstance() {
        return new ChartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chart_fragment, container, false);

        List<String> list =  DBTableManager.getInstance(getContext()).selectExpensesName("三");
        if (!ListUtil.isEmpty(list)) {
            for (String name: list) {
                LogUtils.e(name);
            }
        }

        list =  DBTableManager.getInstance(getContext()).selectIncomeName("三");
        if (!ListUtil.isEmpty(list)) {
            for (String name: list) {
                LogUtils.e(name);
            }
        }

        return view;
    }

}
