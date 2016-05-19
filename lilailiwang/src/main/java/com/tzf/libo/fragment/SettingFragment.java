package com.tzf.libo.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzf.libo.R;
import com.tzf.libo.activity.InputSettingActivity;
import com.tzf.libo.activity.PasswordSettingActivity;
import com.tzf.libo.data.DataManager;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.Expenses;
import com.tzf.libo.model.Income;
import com.tzf.libo.util.DBConstants;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileOutputStream;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author tangzhifei on 15/11/15.
 */
public class SettingFragment extends Fragment implements View.OnClickListener {

    @Bind(R.id.inputTextView)
    TextView inputTextView;
    @Bind(R.id.passwordTextView)
    TextView passwordTextView;
    @Bind(R.id.exportTextView)
    TextView exportTextView;

    ProgressDialog progressDialog;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.setting_fragment, container, false);
        ButterKnife.bind(this, view);
        inputTextView.setOnClickListener(this);
        passwordTextView.setOnClickListener(this);
        exportTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inputTextView:
                Intent inputSetting = new Intent(getActivity(), InputSettingActivity.class);
                startActivity(inputSetting);
                break;
            case R.id.passwordTextView:
                Intent passwordSetting = new Intent(getActivity(), PasswordSettingActivity.class);
                startActivity(passwordSetting);
                break;
            case R.id.exportTextView:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setTitle("请耐心等待");
                    progressDialog.setMessage("正在Excel导出...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    exportExcel();
                }
                break;
        }

    }

    private void exportExcel() {
        DBTableManager dbTableManager = DBTableManager.getInstance(getContext());

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet1 = null, sheet2 = null;
        HSSFRow row = null;
        HSSFCell nameCell, moneyCell, timeCell, reasonCell, moneyTypeCell, sendTypeCell, subjectCell, remarkCell, placeCell;

        //表头样式
        HSSFCellStyle titleStyle = wb.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);

        // 写支出数据
        List<Expenses> expenses_list = dbTableManager.selectExpenses();
        sheet1 = wb.createSheet("我的支出");
        row = sheet1.createRow(0);
        nameCell = row.createCell(0);
        nameCell.setCellStyle(titleStyle);
        nameCell.setCellValue("收礼人");
        reasonCell = row.createCell(1);
        reasonCell.setCellStyle(titleStyle);
        reasonCell.setCellValue("事由");
        moneyCell = row.createCell(2);
        moneyCell.setCellStyle(titleStyle);
        moneyCell.setCellValue("金额");
        timeCell = row.createCell(3);
        timeCell.setCellStyle(titleStyle);
        timeCell.setCellValue("送礼时间");
        placeCell = row.createCell(4);
        placeCell.setCellStyle(titleStyle);
        placeCell.setCellValue("地点");
        moneyTypeCell = row.createCell(5);
        moneyTypeCell.setCellStyle(titleStyle);
        moneyTypeCell.setCellValue("礼金形式");
        sendTypeCell = row.createCell(6);
        sendTypeCell.setCellStyle(titleStyle);
        sendTypeCell.setCellValue("送达方式");
        remarkCell = row.createCell(7);
        remarkCell.setCellStyle(titleStyle);
        remarkCell.setCellValue("备注");
        if (expenses_list != null && expenses_list.size() > 0) {
            for (int i = 0; i < expenses_list.size(); i++) {
                Expenses expenses = expenses_list.get(i);
                row = sheet1.createRow(i + 1);
                nameCell = row.createCell(0);
                nameCell.setCellValue(expenses.getReceiver());
                reasonCell = row.createCell(1);
                reasonCell.setCellValue(DataManager.getBaseKey(expenses.getReason(), DBConstants.COMMON_ITEM_REASON));
                moneyCell = row.createCell(2);
                moneyCell.setCellValue(expenses.getMoney());
                timeCell = row.createCell(3);
                timeCell.setCellValue(expenses.getReceivetime());
                placeCell = row.createCell(4);
                placeCell.setCellValue(expenses.getPlace());
                moneyTypeCell = row.createCell(5);
                moneyTypeCell.setCellValue(DataManager.getBaseKey(expenses.getMoneyType(),
                        DBConstants.COMMON_ITEM_MONEYTYPE));
                sendTypeCell = row.createCell(6);
                sendTypeCell.setCellValue(DataManager.getBaseKey(expenses.getSendType(),
                        DBConstants.COMMON_ITEM_SENDTYPE));
                remarkCell = row.createCell(7);
                remarkCell.setCellValue(expenses.getRemark());
            }
        }
        // 写礼薄数据
        List<Income> income_list = dbTableManager.selectIncome();
        sheet2 = wb.createSheet("我的礼薄");
        row = sheet2.createRow(0);
        nameCell = row.createCell(0);
        nameCell.setCellStyle(titleStyle);
        nameCell.setCellValue("送礼人");
        subjectCell = row.createCell(1);
        subjectCell.setCellStyle(titleStyle);
        subjectCell.setCellValue("礼薄主题");
        moneyCell = row.createCell(2);
        moneyCell.setCellStyle(titleStyle);
        moneyCell.setCellValue("金额");
        timeCell = row.createCell(3);
        timeCell.setCellStyle(titleStyle);
        timeCell.setCellValue("送礼时间");
        placeCell = row.createCell(4);
        placeCell.setCellStyle(titleStyle);
        placeCell.setCellValue("地点");
        moneyTypeCell = row.createCell(5);
        moneyTypeCell.setCellStyle(titleStyle);
        moneyTypeCell.setCellValue("礼金形式");
        sendTypeCell = row.createCell(6);
        sendTypeCell.setCellStyle(titleStyle);
        sendTypeCell.setCellValue("送达方式");
        remarkCell = row.createCell(7);
        remarkCell.setCellStyle(titleStyle);
        remarkCell.setCellValue("备注");

        if (income_list != null && income_list.size() > 0) {
            for (int i = 0; i < income_list.size(); i++) {
                Income income = income_list.get(i);
                row = sheet2.createRow(i + 1);
                nameCell = row.createCell(0);
                nameCell.setCellValue(income.getSender());
                subjectCell = row.createCell(1);
                subjectCell.setCellValue(DataManager.getBaseKey(income.getSubject(), DBConstants.COMMON_ITEM_SUBJECT));
                moneyCell = row.createCell(2);
                moneyCell.setCellValue(income.getMoney());
                timeCell = row.createCell(3);
                timeCell.setCellValue(income.getSendtime());
                placeCell = row.createCell(4);
                placeCell.setCellValue(income.getPlace());
                moneyTypeCell = row.createCell(5);
                moneyTypeCell.setCellValue(DataManager.getBaseKey(income.getMoneyType(),
                        DBConstants.COMMON_ITEM_MONEYTYPE));
                sendTypeCell = row.createCell(6);
                sendTypeCell.setCellValue(DataManager.getBaseKey(income.getSendType(), DBConstants.COMMON_ITEM_SENDTYPE));
                remarkCell = row.createCell(7);
                remarkCell.setCellValue(income.getRemark());
            }
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("/sdcard/libo/lilailiwang.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Snackbar.make(exportTextView, "Excel导出失败", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        Snackbar.make(exportTextView, "数据保存在SD卡下libo/lilailiwang.xls中", Snackbar.LENGTH_LONG).show();
    }

}
