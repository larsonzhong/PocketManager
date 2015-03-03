package larson.pm.adapter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import larson.pm.R;
import larson.pm.bean.Goal;
import larson.pm.utils.TimeTools;
import larson.pm.view.SwitchButton;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class GoalBillAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<List<Goal>> childData;
	private ArrayList<String> groupData;
	//组的logo
	 private int[] logos = new int[] { R.drawable.week, R.drawable.month,R.drawable.all};

	public GoalBillAdapter(Context context, ArrayList<List<Goal>> childData,
			ArrayList<String> groupData) {
		this.context = context;
		this.childData = childData;
		this.groupData = groupData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childData.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(
				R.layout.goal_item, null);

		Goal goal = childData.get(groupPosition).get(childPosition);

		/**
		 * 目标名称
		 */
		TextView item_goalName = (TextView) convertView
				.findViewById(R.id.item_goalName);
		item_goalName.setText(goal.getGoalName());
		
		/**
		 * 目标级别（平均级别）
		 */
		RatingBar goal_level = (RatingBar) convertView
				.findViewById(R.id.goal_level);
		goal_level.setRating(goal.getGoalAvgLevel());
		/**
		 * 最近的限定时间
		 */
		TextView item_limit_time = (TextView) convertView
				.findViewById(R.id.item_limit_time);
		try {
			item_limit_time.setText(TimeTools.getRecentTime(
					goal.getStep1LimitTime(), goal.getStep2LimitTime(),
					goal.getStep3LimitTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/**
		 * 是否完成的开关
		 */
		SwitchButton isFinishSB = (SwitchButton) convertView
				.findViewById(R.id.item_isFinish);
		if (goal.getIsFinish() == 1)
			isFinishSB.setChecked(true);
		else
			isFinishSB.setChecked(false);

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childData.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return groupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.goal_bill_group, null);
		//组名
		TextView goal_bill_group_name = (TextView) convertView.findViewById(R.id.goal_bill_group_name);
		goal_bill_group_name.setText(groupData.get(groupPosition));
		//组图片
		ImageView goal_bill_group_iv = (ImageView) convertView.findViewById(R.id.goal_bill_group_iv);
		goal_bill_group_iv.setImageResource(logos[groupPosition]);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}


}
