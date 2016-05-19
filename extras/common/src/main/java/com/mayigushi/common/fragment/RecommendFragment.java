package com.mayigushi.common.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mayigushi.common.R;
import com.mayigushi.common.adapter.RecommendListAdapter;
import com.mayigushi.common.model.Recommend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangzhifei on 15/11/20.
 */
public class RecommendFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView listView;

    private RecommendListAdapter adapter;

    private List<Recommend> recommendList = new ArrayList<>();

    public static RecommendFragment newInstance() {
        return new RecommendFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Recommend lilailiwang = new Recommend();
        lilailiwang.setIconRes(R.drawable.lilailiwang);
        lilailiwang.setTitle("礼来礼往");
        lilailiwang.setDescription("蚂蚁故事网推出的云礼簿，帮您纪录一切随礼账单。");
        lilailiwang.setUrl("http://xiaokeyi.com/apk/lilailiwang.apk");
        recommendList.add(lilailiwang);

        Recommend junengtie = new Recommend();
        junengtie.setIconRes(R.drawable.junengtie);
        junengtie.setTitle("巨能贴");
        junengtie.setDescription("蚂蚁故事网推出的超级剪切板工具，目前最好用的安卓剪切板，没有之一。");
        junengtie.setUrl("http://xiaokeyi.com/apk/junengtie.apk");
        recommendList.add(junengtie);

        Recommend huodong = new Recommend();
        huodong.setIconRes(R.drawable.yiqihuodong);
        huodong.setTitle("一起活动");
        huodong.setDescription("蚂蚁故事网推出的活动发布工具，轻轻松松组织一切活动。");
        huodong.setUrl("http://xiaokeyi.com/apk/yiqihuodong.apk");
        recommendList.add(huodong);
        adapter = new RecommendListAdapter(getActivity(), recommendList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_fragment, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse(recommendList.get(position).getUrl());
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

}
