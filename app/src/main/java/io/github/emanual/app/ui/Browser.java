package io.github.emanual.app.ui;

import io.github.emanual.app.R;
import io.github.emanual.app.utils.EManualUtils;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class Browser extends BaseActivity {

	public static final String EXTRA_URL = "url";
	
	String share_tpl = "我正在浏览: %s %s (来自编程助手)";
	MyWebChromeClient mWebChromeClient = new MyWebChromeClient();;
	MyWebViewClient	mWebViewClient = new MyWebViewClient();
	

	@InjectView(R.id.webview) WebView webview;
	@InjectView(R.id.progress) ProgressBar mProgressBar;
	String url;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.acty_browser);
		ButterKnife.inject(this);
		initData();
		initLayout();
	}

	@Override protected void initData() {
		if (getIntent().getStringExtra(EXTRA_URL) != null) {
			url = getIntent().getStringExtra(EXTRA_URL);
		}else{
			url = EManualUtils.URL_HOME_PAGE;
			toast("无URL，跳转至主页..");
		}
	}

	@SuppressLint("SetJavaScriptEnabled") @Override protected void initLayout() {
		setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setTitle(R.string.acty_browser);
		
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(mWebViewClient);
		webview.setWebChromeClient(mWebChromeClient);

		
		if (url != null) {
			webview.loadUrl(url);
		}
		
		
	}
	
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.browser, menu);
		return true;
	}
	
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		case R.id.action_share:
			ShareCompat.IntentBuilder
			.from(this)
			.setType("text/plain")
			.setText(String.format(share_tpl, webview.getTitle(),webview.getUrl()))
			.setChooserTitle("分享到")

			.startChooser();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	@OnClick(R.id.btn_left)
	public void goBack(){
		if(webview.canGoBack()){
			webview.goBack();
		}
	}
	
	@OnClick(R.id.btn_right)
	public void goNext(){
		if(webview.canGoForward()){
			webview.goForward();
		}
	}
	@OnClick(R.id.btn_refresh)
	public void refresh(){
		webview.reload();
	}
	
	public void showProgressBar(){
		mProgressBar.setVisibility(View.VISIBLE);
	}
	
	public void hideProgressBar(){
		mProgressBar.setVisibility(View.GONE);
		
	}
	
	class MyWebViewClient extends WebViewClient {

		@Override public void onLoadResource(WebView view, String url) {
			Log.d("debug", "onLoadResource--> " + url);
		}

		@Override public void onPageFinished(WebView view, String url) {
			hideProgressBar();
		}

		@Override public void onPageStarted(WebView view, String url,
				Bitmap favicon) {
			showProgressBar();
		}

		@Override public boolean shouldOverrideUrlLoading(WebView view,
				String url) {
			webview.loadUrl(url);
			return true;
		}

		@Override public void onReceivedError(WebView view, int errorCode,
				String description, String failingUrl) {
			if(description!=null){
				toast(description);
			}
		}
	}
	
	class MyWebChromeClient extends WebChromeClient{

		@Override public void onReceivedTitle(WebView view, String title) {
			getSupportActionBar().setTitle(title);
		}
	}

}
