package io.github.emanual.java.app.adapter;

import io.github.emanual.java.app.R;
import io.github.emanual.java.app.entity.FileTreeObject;
import io.github.emanual.java.app.utils.EManualUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FileTreeAdapter extends BaseAdapter {
	List<FileTreeObject> data;
	Context context;
	public FileTreeAdapter(Context context,List<FileTreeObject> data){
		this.data = data;
		this.context = context;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder h =  null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.adapter_topiclist, null);
			h = new ViewHolder(convertView);
			convertView.setTag(h);
		}else{
			h =(ViewHolder)convertView.getTag();
		}
		FileTreeObject item = data.get(position);
		if(item.getMode().equals(FileTreeObject.MODE_FILE)){
			h.title.setText(EManualUtils.getFileNameWithoutExt(item.getRname()));
		}else{
			h.title.setText(item.getRname());
		}
		return convertView;
	}
	
	class ViewHolder{
		@InjectView(R.id.tv_title)
		TextView title;
		
		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
