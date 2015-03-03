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

		// 获取到选中目标
		Goal goal = listGoal.get(position);

		// 如果状态改变，就要刷新数据库(0表示未选择)
		// 从数据库获取状态并显示
		CheckBox isFinish = (CheckBox) convertView
				.findViewById(R.id.item_isFinish);
		if (goal.getIsFinish() == 1)
			isFinish.setChecked(true);
		else
			isFinish.setChecked(false);

		// 设置期限
		TextView limitTime = (TextView) convertView
				.findViewById(R.id.item_limit_time);
		limitTime.setText("最终期限:" + goal.getStep3LimitTime());

		/**
		 * 设置目标名
		 * 如果是今天就要变红
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

		// 目标等级
		RatingBar goallevel = (RatingBar) convertView
				.findViewById(R.id.goal_level);
		float emergency = goal.getEmergencyLevel();// 紧急程度
		goallevel.setRating(emergency);

		// // //用这个东西判别点击的事那个id
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
