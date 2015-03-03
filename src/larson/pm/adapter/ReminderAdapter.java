package larson.pm.adapter;

import java.util.List;

import larson.pm.R;
import larson.pm.bean.Reminder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ReminderAdapter extends BaseAdapter {

	private Context context;
	private List<Reminder> reminders;

	public ReminderAdapter(Context context, List<Reminder> reminder) {
		this.context = context;
		this.reminders = reminder;
	}

	public int getCount() {
		return reminders.size();
	}

	public Object getItem(int position) {
		return reminders.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(
					R.layout.reminder_irem, null);

		// 获取到选中条目
		Reminder reminder = reminders.get(position);

		// 设置启动时间
		TextView reminder_item_time = (TextView) convertView
				.findViewById(R.id.reminder_item_time);
		if (reminder.getTime()==null) 
			reminder_item_time.setText("无");
		else
			reminder_item_time.setText(reminder.getTime());
			
		//铃声
		TextView reminder_item_alarm = (TextView) convertView.findViewById(R.id.reminder_item_alarm);
		if(reminder.getRingType()!=-1)
			reminder_item_alarm.setText("是");

		// 便签名
		TextView tagName = (TextView) convertView
				.findViewById(R.id.reminder_item_tag);
		tagName.setText(reminder.getTagName());
		
		return convertView;
	}
}
