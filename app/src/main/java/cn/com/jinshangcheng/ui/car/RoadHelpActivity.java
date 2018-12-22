package cn.com.jinshangcheng.ui.car;

import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import butterknife.BindView;
import cn.com.jinshangcheng.R;
import cn.com.jinshangcheng.base.BaseActivity;
import cn.com.jinshangcheng.config.ConstParams;

/**
 * 道路救援
 */
public class RoadHelpActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;

    String url;

    @Override
    public int setContentViewResource() {
        return R.layout.activity_road_help;
    }

    @Override
    public void initData() {

        url = ConstParams.readSaveUrl;
    }

    @Override
    public void initView() {

        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        //开启DomStorage缓存
        webSetting.setDomStorageEnabled(true);
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();
            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

}
