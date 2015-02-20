package io.github.emanual.app.fragment;

import io.github.emanual.app.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Explore extends BaseFragment {
	
	
	
	@Override public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_explore, null);
		ButterKnife.inject(this, v);
		return v;
	}
	
	@OnClick(R.id.btn_rate_app)
	public void rate_app(){
		String packageName = getActivity().getPackageName();
		getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
	}

}
