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
import com.tzf.libo.model.Income;
import com.tzf.libo.util.DBConstants;

import java.util.TreeMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/11/29.
 */
public class IncomeEditFragment extends Fragment implements View.OnClickListener, SublimePickerFragment.Callback {

    @Bind(R.id.senderEditText)
    MaterialEditText senderEditText;
    @Bind(R.id.sendTimeEditText)
    MaterialEditText sendTimeEditText;
    @Bind(R.id.subjectTypeEditText)
    MaterialEditText subjectTypeEditText;
    @Bind(R.id.sendTypeEditText)
    MaterialEditText sendTypeEditText;
    @Bind(R.id.moneyTypeEditText)
    MaterialEditText moneyTypeEditText;
    @Bind(R.id.moneyEditText)
    MaterialEditText moneyEditText;
    @Bind(R.id.remarkEditText)
    MaterialEditText remarkEditText;

    private int id;

    public static IncomeEditFragment newInstance() {
        return new IncomeEditFragment();
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
        View view = inflater.inflate(R.layout.income_edit_fragment, container, false);
        ButterKnife.bind(this, view);
        sendTimeEditText.setOnClickListener(this);
        subjectTypeEditText.setOnClickListener(this);
        moneyTypeEditText.setOnClickListener(this);
        sendTypeEditText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(final View v) {
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        String title = StringUtil.EMPTY_STRING;
        switch (v.getId()) {
            case R.id.sendTypeEditText:
                title = "选择收礼方式";
                treeMap.putAll(DataManager.getBaseMap(DBConstants.COMMON_ITEM_SENDTYPE));
                break;
            case R.id.subjectTypeEditText:
                title = "选择礼簿主题";
                treeMap.putAll(DataManager.getBaseMap(DBConstants.COMMON_ITEM_SUBJECT));
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
            case R.id.subjectTypeEditText:
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
            case R.id.sendTimeEditText:
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
        sendTimeEditText.setText(dateBuffer.toString());
    }

    public void updateIncome() {
        String sender = senderEditText.getText().toString();
        if (sender.length() < 2) {
            Snackbar.make(senderEditText, "收礼人姓名不少于2位", Snackbar.LENGTH_LONG).show();
            return;
        }

        String sendTime = sendTimeEditText.getText().toString();
        if (0 == sendTime.length()) {
            Snackbar.make(sendTimeEditText, "选择收礼时间", Snackbar.LENGTH_LONG).show();
            return;
        }

        int subjectType = (int) subjectTypeEditText.getTag();
        if (0 == subjectType) {
            Snackbar.make(subjectTypeEditText, "选择收礼时间", Snackbar.LENGTH_LONG).show();
            return;
        }

        int sendType = (int) sendTypeEditText.getTag();
        if (0 == sendType) {
            Snackbar.make(sendTypeEditText, "选择收礼方式", Snackbar.LENGTH_LONG).show();
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
            Income income = dbTableManager.selectOneIncome(id);
            income.setMoney(Float.valueOf(money));
            income.setSender(sender);
            income.setSendtime(sendTime);
            income.setSendType(sendType);
            income.setSubject(subjectType);
            income.setMoneyType(moneyType);
            income.setRemark(remarkEditText.getText().toString().trim());
            boolean result = dbTableManager.updateIncome(income.getId(), income);
            if (result) {
                Snackbar.make(moneyTypeEditText, "修改成功", Snackbar.LENGTH_LONG).show();
                getActivity().finish();
            }
        } else {
            Income tempIncome = new Income();
            tempIncome.setMoney(Float.valueOf(money));
            tempIncome.setSender(sender);
            tempIncome.setSendtime(sendTime);
            tempIncome.setSendType(sendType);
            tempIncome.setSubject(subjectType);
            tempIncome.setMoneyType(moneyType);
            tempIncome.setRemark(remarkEditText.getText().toString().trim());
            boolean result = dbTableManager.insertIncome(tempIncome);
            if (result) {
                Snackbar.make(moneyTypeEditText, "添加成功", Snackbar.LENGTH_LONG).show();
                getActivity().finish();
            }
        }
    }

    private void setData() {
        if (id > 0) {
            Income income = DBTableManager.getInstance(getContext()).selectOneIncome(id);
            if (null != income) {
                senderEditText.setText(income.getSender());
                sendTimeEditText.setText(income.getSendtime());
                moneyEditText.setText(String.valueOf(income.getMoney()));
                subjectTypeEditText.setText(DataManager.getBaseKey(income.getSubject(), DBConstants.COMMON_ITEM_SUBJECT));
                subjectTypeEditText.setTag(income.getSubject());
                sendTypeEditText.setText(DataManager.getBaseKey(income.getSendType(), DBConstants.COMMON_ITEM_SENDTYPE));
                sendTypeEditText.setTag(income.getSendType());
                moneyTypeEditText.setText(DataManager.getBaseKey(income.getMoneyType(), DBConstants.COMMON_ITEM_MONEYTYPE));
                moneyTypeEditText.setTag(income.getMoneyType());

                String remark = income.getRemark();
                if (!StringUtil.isEmpty(remark)) {
                    remarkEditText.setText(remark);
                    remarkEditText.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
