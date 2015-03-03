package larson.pm.adapter;

import java.util.List;

import larson.pm.R;
import larson.pm.bean.Contact;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SmsContactAdapter extends BaseAdapter {
	private Context context;
	private List<Contact> datas;

	public SmsContactAdapter(Context context, List<Contact> datas) {
		this.context = context;
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return datas.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.contact_item, null);
		TextView setting_uid = (TextView) convertView
				.findViewById(R.id.setting_uid);
		setting_uid.setText(datas.get(position).getContactName());
		TextView setting_unick = (TextView) convertView
				.findViewById(R.id.setting_unick);
		setting_unick.setText(datas.get(position).getPhoneNumber());
		return convertView;
	}

}
