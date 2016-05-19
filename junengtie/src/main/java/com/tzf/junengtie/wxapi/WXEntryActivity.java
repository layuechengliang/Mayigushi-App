package com.tzf.junengtie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mayigushi.common.util.CommonUtil;
import com.tencent.mm.sdk.openapi.BaseReq;
import com.tencent.mm.sdk.openapi.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tzf.junengtie.R;
import com.tzf.junengtie.activity.BaseActivity;
import com.tzf.junengtie.db.DataManager;
import com.tzf.junengtie.db.Message;
import com.tzf.junengtie.util.StringUtil;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String WEIXIN_APP_ID = "wx082f81f1380067c8";

    private IWXAPI api;

    private Toolbar toolbar;

    private Message message;

    private EditText contentEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weixin);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("内容详情");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        contentEditText = (EditText) findViewById(R.id.contentEditText);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        message = (Message) bundle.getSerializable("message");
        if (null != message) {
            contentEditText.setText(message.getContent());
            contentEditText.setSelection(message.getContent().length());
        }
        api = WXAPIFactory.createWXAPI(this, WEIXIN_APP_ID, false);
        api.registerApp(WEIXIN_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        String msg = String.valueOf(resp.errCode);
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                msg = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                msg = "分享取消";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                msg = "分享失败";
                break;
        }
        Snackbar.make(contentEditText, msg, Snackbar.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.weixin_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String content = contentEditText.getText().toString();
        if (!StringUtil.isEmpty(content)) {
            int id = item.getItemId();
            switch (id) {
                case R.id.done:
                    message.setContent(content);
                    DataManager.update(message);
                    finish();
                    break;
                case R.id.shareToWXHY:
                    CommonUtil.sendMsgToWeixin(this, api, SendMessageToWX.Req.WXSceneSession, content);
                    break;
                case R.id.shareToWXPYQ:
                    CommonUtil.sendMsgToWeixin(this, api, SendMessageToWX.Req.WXSceneTimeline, content);
                    break;
            }
        } else {
            Snackbar.make(contentEditText, "内容不能为空", Snackbar.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

}