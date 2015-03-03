package larson.pm.adapter;

import java.text.ParseException;
import java.util.List;

import larson.pm.R;
import larson.pm.bean.Goal;
import larson.pm.utils.TimeTools;
import larson.pm.view.SwitchButton;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.TextView;

public class GoalAdapter extends BaseAdapter {
	private Context context;
	private List<Goal> listGoal;

	public GoalAdapter(Context context, List<Goal> listGoal) {
		this.context = context;
		this.listGoal = listGoal;
	}

	public int getCount() {
		return listGoal.size();
	}

	public Object getItem(int position) {
		return listGoal.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = LayoutInflater.from(context).inflate(
					R.layout.goal_item, null);

		// ��ȡ��ѡ��Ŀ��
		Goal goal = listGoal.get(position);

		// ���״̬�ı䣬��Ҫˢ�����ݿ�(0��ʾδѡ��)
		// �����ݿ��ȡ״̬����ʾ
		CheckBox isFinish = (CheckBox) convertView
				.findViewById(R.id.item_isFinish);
		if (goal.getIsFinish() == 1)
			isFinish.setChecked(true);
		else
			isFinish.setChecked(false);

		// ��������
		TextView limitTime = (TextView) convertView
				.findViewById(R.id.item_limit_time);
		limitTime.setText("��������:" + goal.getStep3LimitTime());

		/**
		 * ����Ŀ����
		 * ����ǽ����Ҫ���
		 */
		TextView goalName = (TextView) convertView
				.findViewById(R.id.item_goalName);
		try {
			if (TimeTools.isThisDay(goal.getStep1LimitTime())
					|| TimeTools.isThisWeek(goal.getStep2LimitTime())
					|| TimeTools.isThisWeek(goal.getStep3LimitTime()))
				goalName.setTextColor(Color.RED);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		goalName.setText(goal.getGoalName());

		// Ŀ��ȼ�
		RatingBar goallevel = (RatingBar) convertView
				.findViewById(R.id.goal_level);
		float emergency = goal.getEmergencyLevel();// �����̶�
		goallevel.setRating(emergency);

		// // //����������б��������Ǹ�id
		TextView idTV = (TextView) convertView.findViewById(R.id.goal_item_id);
		idTV.setText(goal.getId() + "");

		SwitchButton isFinishSB = (SwitchButton) convertView
				.findViewById(R.id.item_isFinish);
		if (goal.getIsFinish() == 1)
			isFinishSB.setChecked(true);
		else
			isFinishSB.setChecked(false);
		return convertView;
	}

}
