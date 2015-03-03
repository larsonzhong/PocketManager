package larson.pm.adapter;

import java.util.List;

import larson.pm.R;
import larson.pm.bean.SmsBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SmsAdapter extends BaseAdapter {
	private Context context;
	private List<SmsBean> sms_datas;

	public SmsAdapter(Context context, List<SmsBean> sms_datas) {
		this.context = context;
		this.sms_datas = sms_datas;
	}

	@Override
	public int getCount() {
		return sms_datas.size();
	}

	@Override
	public Object getItem(int position) {
		return sms_datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		SmsBean sms = sms_datas.get(position);
		convertView = LayoutInflater.from(context).inflate(R.layout.sms_item, null);
		TextView sms_name = (TextView) convertView.findViewById(R.id.sms_name);
		String text = sms.getReceiver().replace("$", "\n");
		sms_name.setText(text);
		
		TextView sms_id = (TextView) convertView.findViewById(R.id.sms_id);
		sms_id.setText(sms.getId()+"");
		
		TextView sms_content = (TextView) convertView.findViewById(R.id.sms_content);
		sms_content.setText(sms.getContent());
		
		TextView sms_time = (TextView) convertView.findViewById(R.id.sms_time);
		sms_time.setText(sms.getTime());
		return convertView;
	}

}
