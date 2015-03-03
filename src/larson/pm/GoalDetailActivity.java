package larson.pm;

import java.util.List;

import larson.pm.bean.Goal;
import larson.pm.dao.GoalDao;
import larson.pm.utils.SuggestionTools;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GoalDetailActivity extends Activity {

	private Goal goal;
	private int _id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_item_detail);

		Intent intent = getIntent();
		_id = intent.getIntExtra("_id", -1);
		if (_id == -1) {
			Toast.makeText(this, "貌似有点小问题", Toast.LENGTH_SHORT).show();
			finish();
		}

		initData(_id);
		initView();
	}

	private void initData(int _id) {
		GoalDao dao = new GoalDao(this);
		List<Goal> list = dao.query("id", _id + "");
		goal = list.get(0);
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		
		//返回键
		ImageView detail_btn_back = (ImageView) findViewById(R.id.detail_btn_back);
		detail_btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GoalDetailActivity.this, GoalActivity.class));
				finish();
			}
		});
		
		// 目标名
		TextView goal_item_detail_name = (TextView) findViewById(R.id.goal_item_detail_name);
		goal_item_detail_name.setText("目标名：" + goal.getGoalName());

		/**
		 * 目标各种级别
		 */
		TextView goal_item_detail_emergencyLevel = (TextView) findViewById(R.id.goal_item_detail_emergencyLevel);
		if (goal.getEmergencyLevel() >= 4)
			goal_item_detail_emergencyLevel.setTextColor(Color.RED);
		goal_item_detail_emergencyLevel.setText("紧急程度："
				+ goal.getEmergencyLevel());
		TextView goal_item_detail_importanceLevel = (TextView) findViewById(R.id.goal_item_detail_importanceLevel);
		if (goal.getImportanceLevel() >= 4)
			goal_item_detail_importanceLevel.setTextColor(Color.RED);
		goal_item_detail_importanceLevel.setText("重要程度："
				+ goal.getImportanceLevel());
		TextView goal_item_detail_suggesstion = (TextView) findViewById(R.id.goal_item_detail_suggesstion);
		TextView goal_item_detail_publicLevel = (TextView) findViewById(R.id.goal_item_detail_publicLevel);
		goal_item_detail_publicLevel.setText("公开程度：" + goal.getPublicLevel());
		TextView goal_item_detail_challengeLevel = (TextView) findViewById(R.id.goal_item_detail_challengeLevel);
		goal_item_detail_challengeLevel.setText("难度系数："
				+ goal.getChallengeLevel());
		TextView goal_item_detail_clearLevel = (TextView) findViewById(R.id.goal_item_detail_clearLevel);
		goal_item_detail_clearLevel.setText("明确级别：" + goal.getClearLevel());

		/**
		 * 设置建议
		 */
		String suggestion = SuggestionTools.makeSuggesstion(
				goal.getEmergencyLevel(), goal.getImportanceLevel(),
				goal.getPublicLevel(), goal.getChallengeLevel(),
				goal.getClearLevel());
		if (suggestion == null)
			goal_item_detail_suggesstion.setText("系统没有任何建议，说明你有一个良好的目标意识");
		else
			goal_item_detail_suggesstion.setText(suggestion);

		/**
		 * 各阶段限定时间
		 */
		TextView goal_item_detail_deadline1 = (TextView) findViewById(R.id.goal_item_detail_deadline1);
		goal_item_detail_deadline1.setText(goal.getStep1LimitTime());
		TextView goal_item_detail_deadline2 = (TextView) findViewById(R.id.goal_item_detail_deadline2);
		goal_item_detail_deadline2.setText(goal.getStep2LimitTime());
		TextView goal_item_detail_deadline3 = (TextView) findViewById(R.id.goal_item_detail_deadline3);
		goal_item_detail_deadline3.setText(goal.getStep3LimitTime());

		/**
		 * 各阶段进度描述
		 */
		TextView goal_item_detail_describe1 = (TextView) findViewById(R.id.goal_item_detail_describe1);
		goal_item_detail_describe1.setText("阶段一："+goal.getStep1Describe());
		TextView goal_item_detail_describe2 = (TextView) findViewById(R.id.goal_item_detail_describe2);
		goal_item_detail_describe2.setText("阶段二："+goal.getStep2Describe());
		TextView goal_item_detail_describe3 = (TextView) findViewById(R.id.goal_item_detail_describe3);
		goal_item_detail_describe3.setText("阶段三："+goal.getStep3Describe());

		/**
		 * 目标的四大解释
		 */
		TextView goal_item_detail_define = (TextView) findViewById(R.id.goal_item_detail_define);
		goal_item_detail_define.setText(goal.getDefine());
		TextView goal_item_detail_benifit = (TextView) findViewById(R.id.goal_item_detail_benifit);
		goal_item_detail_benifit.setText(goal.getBenefit());
		TextView goal_item_detail_motivation = (TextView) findViewById(R.id.goal_item_detail_motivation);
		goal_item_detail_motivation.setText(goal.getReason());
		TextView goal_item_detail_damage = (TextView) findViewById(R.id.goal_item_detail_damage);
		goal_item_detail_damage.setText(goal.getDamage());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		int group1 = 1;
		menu.add(group1, 1, 1, "标记完成");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			GoalDao dao = new GoalDao(this);
			dao.update(_id, 1);
			break;

		default:
			break;
		}
		return true;
	}

}
