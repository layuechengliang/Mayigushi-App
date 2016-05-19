package com.tzf.libo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayigushi.common.util.StringUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tzf.libo.R;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Income;
import com.tzf.libo.util.DBConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/11/29.
 */
public class IncomeDetailFragment extends Fragment {

    @Bind(R.id.senderEditText)
    MaterialEditText senderEditText;
    @Bind(R.id.sendTimeEditText)
    MaterialEditText sendTimeEditText;
    @Bind(R.id.sendTypeEditText)
    MaterialEditText sendTypeEditText;
    @Bind(R.id.moneyTypeEditText)
    MaterialEditText moneyTypeEditText;
    @Bind(R.id.moneyEditText)
    MaterialEditText moneyEditText;
    @Bind(R.id.remarkEditText)
    MaterialEditText remarkEditText;

    private int id;

    public static IncomeDetailFragment newInstance() {
        return new IncomeDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            id = bundle.getInt("id");
            setRetainInstance(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.income_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void setData() {
        if (id > 0) {
            Income income = DBTableManager.getInstance(getContext()).selectOneIncome(id);
            if (null != income) {
                senderEditText.setText(income.getSender());
                sendTimeEditText.setText(income.getSendtime());
                moneyEditText.setText(String.valueOf(income.getMoney()));
                sendTypeEditText.setText(DataManager.getBaseKey(income.getSendType(), DBConstants.COMMON_ITEM_SENDTYPE));
                moneyTypeEditText.setText(DataManager.getBaseKey(income.getMoneyType(), DBConstants.COMMON_ITEM_MONEYTYPE));

                String remark = income.getRemark();
                if (!StringUtil.isEmpty(remark)) {
                    remarkEditText.setText(remark);
                    remarkEditText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
