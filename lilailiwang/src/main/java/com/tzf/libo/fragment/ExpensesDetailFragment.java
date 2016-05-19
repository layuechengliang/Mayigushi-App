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
import com.tzf.libo.model.Expenses;
import com.tzf.libo.util.DBConstants;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/11/29.
 */
public class ExpensesDetailFragment extends Fragment {

    @Bind(R.id.receiverEditText)
    MaterialEditText receiverEditText;
    @Bind(R.id.receiveTimeEditText)
    MaterialEditText receiveTimeEditText;
    @Bind(R.id.reasonTypeEditText)
    MaterialEditText reasonTypeEditText;
    @Bind(R.id.sendTypeEditText)
    MaterialEditText sendTypeEditText;
    @Bind(R.id.moneyTypeEditText)
    MaterialEditText moneyTypeEditText;
    @Bind(R.id.moneyEditText)
    MaterialEditText moneyEditText;
    @Bind(R.id.remarkEditText)
    MaterialEditText remarkEditText;

    private int id;

    public static ExpensesDetailFragment newInstance() {
        return new ExpensesDetailFragment();
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
        View view = inflater.inflate(R.layout.expenses_detail_fragment, container, false);
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
            Expenses expenses = DBTableManager.getInstance(getContext()).selectOneExpenses(id);
            if (null != expenses) {
                receiverEditText.setText(expenses.getReceiver());
                receiveTimeEditText.setText(expenses.getReceivetime());
                moneyEditText.setText(String.valueOf(expenses.getMoney()));
                reasonTypeEditText.setText(DataManager.getBaseKey(expenses.getReason(), DBConstants.COMMON_ITEM_REASON));
                sendTypeEditText.setText(DataManager.getBaseKey(expenses.getSendType(), DBConstants.COMMON_ITEM_SENDTYPE));
                moneyTypeEditText.setText(DataManager.getBaseKey(expenses.getMoneyType(), DBConstants.COMMON_ITEM_MONEYTYPE));

                String remark = expenses.getRemark();
                if (!StringUtil.isEmpty(remark)) {
                    remarkEditText.setText(remark);
                    remarkEditText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
