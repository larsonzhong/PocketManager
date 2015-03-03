package larson.pm.adapter;

import java.util.List;

import larson.pm.R;
import larson.pm.bean.Autoprofiles;
import larson.pm.utils.Constant;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfilesAdapter extends BaseAdapter {
	private Context context;
	private List<Autoprofiles> autoprofiles;

	public ProfilesAdapter(Context context, List<Autoprofiles> autoprofiles) {
		this.context = context;
		this.autoprofiles = autoprofiles;
	}

	public int getCount() {
		return autoprofiles.size();
	}

	public Object getItem(int position) {
		return autoprofiles.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(
					R.layout.auto_profiles_item, null);

		// ��ȡ��ѡ����Ŀ
		Autoprofiles ap = autoprofiles.get(position);

		// ��������ʱ��
		TextView autoprofiles_time = (TextView) convertView
				.findViewById(R.id.autoprofiles_time);
		autoprofiles_time.setText(ap.getTime());

		ImageView iv = (ImageView) convertView.findViewById(R.id.ring_ico);
		if (ap.getProfiles().equals(Constant.PROFILES_OUTDOOR_NAME))
			iv.setImageResource(R.drawable.profiles_outdoor);
		else if(ap.getProfiles().equals(Constant.PROFILES_SILENT_NAME))
			iv.setImageResource(R.drawable.profiles_silent);
		else if(ap.getProfiles().equals(Constant.PROFILES_OFFLINE_NAME))
			iv.setImageResource(R.drawable.profiles_fly);
		else if(ap.getProfiles().equals(Constant.PROFILES_MEETING_NAME))
			iv.setImageResource(R.drawable.profiles_vibrate);
		else if(ap.getProfiles().equals(Constant.PROFILES_INDOOR_NAME))
			iv.setImageResource(R.drawable.profiles_ring);
		// // ���״̬�ı䣬��Ҫˢ�����ݿ�(0��ʾδѡ��)
		// // �����ݿ��ȡ״̬����ʾ
		// ToggleButton isFinish = (ToggleButton) convertView
		// .findViewById(R.id.autoprofiles_tog);
		// if (ap.getIsStart() == 1)
		// isFinish.setChecked(true);
		// else
		// isFinish.setChecked(false);

		// �龰ģʽ
		TextView autoprofiles_profiles = (TextView) convertView
				.findViewById(R.id.autoprofiles_profiles);
		autoprofiles_profiles.setText(ap.getProfiles());

		// ����(����ٷ��㲥�¼����¼�����Ϊid)-----------------XXXXXXXXX
		// final int switch2_position = position;
		// ToggleButton tb = (ToggleButton)
		// convertView.findViewById(R.id.autoprofiles_tog);
		// tb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// public void onCheckedChanged(CompoundButton buttonView, boolean
		// isChecked) {
		// if(!isChecked){
		// Intent intent = new Intent(AlarmBroadcastReceiver.SWITCH);
		// intent.putExtra("position", switch2_position);
		// }
		// }
		// });

		return convertView;
	}

}
