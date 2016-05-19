package com.tzf.libo.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.mayigushi.common.util.ListUtil;
import com.mayigushi.common.util.StringUtil;
import com.tzf.libo.App;
import com.tzf.libo.R;
import com.tzf.libo.adapter.InputListAdapter;
import com.tzf.libo.db.DBTableManager;
import com.tzf.libo.model.InputItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzhifei on 15/11/20.
 */
public class InputFragment extends Fragment implements View.OnClickListener {

    private String title;

    private int inputType = -1;

    private FloatingActionButton addButton;

    private ListView listView;

    private InputListAdapter adapter;

    private List<InputItem> data = new ArrayList<>();

    private void resetData() {
        if (-1 != inputType) {
            List<InputItem> tempList = DBTableManager.getInstance(getActivity()).selectCommonItem(inputType);
            if (!ListUtil.isEmpty(tempList)) {
                data.clear();
                data.addAll(tempList);
                adapter.notifyDataSetChanged();
            }

            App.initDBData(getContext());
        }
    }

    private boolean validate(String name) {
        if (!ListUtil.isEmpty(data) && !StringUtil.isEmpty(name)) {
            for (InputItem item : data) {
                if (StringUtil.isEquals(name, item.getName())) {
                    return false;
                }
            }
        }

        return true;
    }

    public static InputFragment newInstance() {
        return new InputFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            title = bundle.getString("title");
            inputType = bundle.getInt("input_type");
            setRetainInstance(true);
        }

        adapter = new InputListAdapter(getActivity(), data);
        resetData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        registerForContextMenu(listView);
        if (null != adapter) {
            listView.setAdapter(adapter);
        }

        addButton = (FloatingActionButton) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        unregisterForContextMenu(listView);
        super.onDestroyView();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.input_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final InputItem inputItem = data.get(info.position);
        switch (item.getItemId()) {
            case R.id.edit:
                new MaterialDialog.Builder(getActivity())
                        .title("编辑")
                        .inputType(InputType.TYPE_CLASS_TEXT)
                        .input("请输入新" + title, inputItem.getName(), new MaterialDialog.InputCallback() {

                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence content) {
                                if (!StringUtil.isEmpty(content.toString())) {
                                    if (validate(content.toString())) {
                                        DBTableManager.getInstance(getActivity()).updateCommonItem(inputType, inputItem.getId(), content.toString());
                                        Snackbar.make(addButton, "修改成功" + title, Snackbar.LENGTH_SHORT).show();
                                        resetData();
                                    } else {
                                        Snackbar.make(addButton, "修改失败，" + content.toString() + "已经存在", Snackbar.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Snackbar.make(addButton, "修改失败，输入内容不能为空", Snackbar.LENGTH_SHORT).show();
                                }
                            }

                        })
                        .positiveText("确定")
                        .negativeText("取消")
                        .show();
                break;
            case R.id.delete:
                new AlertDialogWrapper.Builder(getActivity())
                        .setTitle("确定删除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (DBTableManager.getInstance(getActivity()).checkInputItemDelete(inputType, inputItem.getId())) {
                                    DBTableManager.getInstance(getActivity()).deleteCommonItem(inputType, inputItem.getId());
                                    Snackbar.make(addButton, "删除成功", Snackbar.LENGTH_SHORT).show();
                                    resetData();
                                } else {
                                    Snackbar.make(addButton, "该数据已被使用，不能删除", Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.addButton) {
            new MaterialDialog.Builder(getActivity())
                    .title("创建" + title)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input("请输入新" + title, null, new MaterialDialog.InputCallback() {

                        @Override
                        public void onInput(MaterialDialog dialog, CharSequence content) {
                            if (!StringUtil.isEmpty(content.toString())) {
                                if (validate(content.toString())) {
                                    DBTableManager.getInstance(getActivity()).insertCommonItem(inputType, content.toString());
                                    Snackbar.make(addButton, "创建成功", Snackbar.LENGTH_SHORT).show();
                                    resetData();
                                } else {
                                    Snackbar.make(addButton, "创建失败，不能创建重复" + title, Snackbar.LENGTH_SHORT).show();
                                }
                            } else {
                                Snackbar.make(addButton, "创建失败，输入内容不能为空", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                    })
                    .positiveText("确定")
                    .negativeText("取消")
                    .show();
        }
    }

}
