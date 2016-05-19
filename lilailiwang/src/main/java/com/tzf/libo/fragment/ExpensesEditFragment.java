package com.tzf.libo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.mayigushi.common.util.StringUtil;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.tzf.libo.R;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.util.DBConstants;

import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/11/29.
 */
public class ExpensesEditFragment extends Fragment implements View.OnClickListener, SublimePickerFragment.Callback {

    @Bind(R.id.receiverEditText)
    MaterialEditText receiverEditText;
    @Bind(R.id.receiverTimeEditText)
    MaterialEditText receiverTimeEditText;
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

    public static ExpensesEditFragment newInstance() {
        return new ExpensesEditFragment();
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
        View view = inflater.inflate(R.layout.expenses_edit_fragment, container, false);
        ButterKnife.bind(this, view);
        receiverTimeEditText.setOnClickListener(this);
        reasonTypeEditText.setOnClickListener(this);
        moneyTypeEditText.setOnClickListener(this);
        sendTypeEditText.setOnClickListener(this);
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
                receiverTimeEditText.setText(expenses.getReceivetime());
                moneyEditText.setText(String.valueOf(expenses.getMoney()));
                reasonTypeEditText.setText(DataManager.getBaseKey(expenses.getReason(), DBConstants.COMMON_ITEM_REASON));
                reasonTypeEditText.setTag(expenses.getReason());
                sendTypeEditText.setText(DataManager.getBaseKey(expenses.getSendType(), DBConstants.COMMON_ITEM_SENDTYPE));
                sendTypeEditText.setTag(expenses.getSendType());
                moneyTypeEditText.setText(DataManager.getBaseKey(expenses.getMoneyType(), DBConstants.COMMON_ITEM_MONEYTYPE));
                moneyTypeEditText.setTag(expenses.getMoneyType());

                String remark = expenses.getRemark();
                if (!StringUtil.isEmpty(remark)) {
                    remarkEditText.setText(remark);
                    remarkEditText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onClick(final View v) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        String title = StringUtil.EMPTY_STRING;
        switch (v.getId()) {
            case R.id.sendTypeEditText:
                title = "选择送礼方式";
                treeMap.putAll(DataManager.getBaseMap(DBConstants.COMMON_ITEM_SENDTYPE));
                break;
            case R.id.reasonTypeEditText:
                title = "选择送礼事由";
                treeMap.putAll(DataManager.getBaseMap(DBConstants.COMMON_ITEM_REASON));
                break;
            case R.id.moneyTypeEditText:
                title = "选择礼金形式";
                treeMap.putAll(DataManager.getBaseMap(DBConstants.COMMON_ITEM_MONEYTYPE));
                break;
        }

        String[] valuesArray = new String[treeMap.size()];
        valuesArray = treeMap.keySet().toArray(valuesArray);
        Integer[] tempArray = new Integer[treeMap.size()];
        final Integer[] keysArray = treeMap.values().toArray(tempArray);

        Object tag = v.getTag();
        int index = null != tag ? getSelectedIndex(keysArray, (int) tag) : -1;
        switch (v.getId()) {
            case R.id.sendTypeEditText:
            case R.id.reasonTypeEditText:
            case R.id.moneyTypeEditText:
                new MaterialDialog.Builder(getActivity())
                        .title(title)
                        .items(valuesArray)
                        .itemsCallbackSingleChoice(index, new MaterialDialog.ListCallbackSingleChoice() {

                            @Override
                            public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                                ((MaterialEditText) v).setText(text);
                                v.setTag(keysArray[which]);
                                return true;
                            }

                        })
                        .show();
                break;
            case R.id.receiverTimeEditText:
                SublimePickerFragment fragment1 = new SublimePickerFragment();
                fragment1.setCallback(this);
                fragment1.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                fragment1.show(getChildFragmentManager(), "SUBLIME_PICKER");
                break;
        }
    }

    private int getSelectedIndex(Integer[] keysArray, int current) {
        for (int i = 0; i < keysArray.length; i++) {
            if (current == keysArray[i]) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onCancelled() {
    }

    @Override
    public void onDateTimeRecurrenceSet(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
        StringBuffer dateBuffer = new StringBuffer();
        dateBuffer.append(year);
        dateBuffer.append("-");
        dateBuffer.append((monthOfYear + 1) > 9 ? (monthOfYear + 1) : ("0" + (monthOfYear + 1)));
        dateBuffer.append("-");
        dateBuffer.append(dayOfMonth > 9 ? dayOfMonth : ("0" + dayOfMonth));
        dateBuffer.append(" ");
        dateBuffer.append(hourOfDay > 9 ? hourOfDay : ("0" + hourOfDay));
        dateBuffer.append(":");
        dateBuffer.append(minute > 9 ? minute : ("0" + minute));
        dateBuffer.append(":00");
        receiverTimeEditText.setText(dateBuffer.toString());
    }

    public void updateExpenses() {
        String receiver = receiverEditText.getText().toString();
        if (receiver.length() < 2) {
            Snackbar.make(receiverEditText, "收礼人姓名不少于2位", Snackbar.LENGTH_LONG).show();
            return;
        }

        String receiverTime = receiverTimeEditText.getText().toString();
        if (0 == receiverTime.length()) {
            Snackbar.make(receiverTimeEditText, "选择送礼时间", Snackbar.LENGTH_LONG).show();
            return;
        }

        int reasonType = (int) reasonTypeEditText.getTag();
        if (0 == reasonType) {
            Snackbar.make(reasonTypeEditText, "选择送礼事由", Snackbar.LENGTH_LONG).show();
            return;
        }

        int sendType = (int) sendTypeEditText.getTag();
        if (0 == sendType) {
            Snackbar.make(sendTypeEditText, "选择送礼方式", Snackbar.LENGTH_LONG).show();
            return;
        }

        int moneyType = (int) moneyTypeEditText.getTag();
        if (0 == moneyType) {
            Snackbar.make(moneyTypeEditText, "选择礼金形式", Snackbar.LENGTH_LONG).show();
            return;
        }

        String money = moneyEditText.getText().toString();
        if (0 == money.length()) {
            Snackbar.make(moneyTypeEditText, "收礼金额不能为空", Snackbar.LENGTH_LONG).show();
            return;
        }

        DBTableManager dbTableManager = DBTableManager.getInstance(getContext());
        if (0 != id) {
            Expenses expenses = dbTableManager.selectOneExpenses(id);
            expenses.setMoney(Float.valueOf(money));
            expenses.setReceiver(receiver);
            expenses.setReceivetime(receiverTime);
            expenses.setSendType(sendType);
            expenses.setReason(reasonType);
            expenses.setMoneyType(moneyType);
            expenses.setRemark(remarkEditText.getText().toString().trim());
            boolean result = dbTableManager.updateExpenses(expenses.getId(), expenses);
            if (result) {
                Snackbar.make(moneyTypeEditText, "修改成功", Snackbar.LENGTH_LONG).show();
                getActivity().finish();
            }
        } else {
            Expenses tempExpenses = new Expenses();
            tempExpenses.setMoney(Float.valueOf(money));
            tempExpenses.setReceiver(receiver);
            tempExpenses.setReceivetime(receiverTime);
            tempExpenses.setSendType(sendType);
            tempExpenses.setReason(reasonType);
            tempExpenses.setMoneyType(moneyType);
            tempExpenses.setRemark(remarkEditText.getText().toString().trim());
            boolean result = dbTableManager.insertExpenses(tempExpenses);
            if (result) {
                Snackbar.make(moneyTypeEditText, "添加成功", Snackbar.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

}
