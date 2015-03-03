package larson.pm;

import java.util.ArrayList;
import java.util.List;

import larson.pm.adapter.GoalBillAdapter;
import larson.pm.bean.Goal;
import larson.pm.dao.GoalDao;
import larson.pm.utils.Constant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;

public class GoalBillActivity extends Activity {
	private ArrayList<List<Goal>> childData;
	private ArrayList<String> groupData;
	private GoalBillAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_bill);
		
		initData();
		initView();
	}
	
	/**
	 * 初始化数据
	 */
	private void initData(){
		groupData = new ArrayList<String>();
		groupData.add("一周内");
		groupData.add("一个月内");
		groupData.add("一个月前");
		
		/**
		 * 初始化child数据
		 */
		GoalDao dao = new GoalDao(this);
		List<Goal> child1_list_data = dao.queryByLimit(Constant.QUERY_BY_WEEK);
		List<Goal> child2_list_data = dao.queryByLimit(Constant.QUERY_BY_MONTH);
		List<Goal> child3_list_data = dao.queryByLimit(Constant.QUERY_THREE_MONTH);
		childData = new ArrayList<List<Goal>>();
		childData.add(child1_list_data);
		childData.add(child2_list_data);
		childData.add(child3_list_data);
	}
	
	/**
	 * 初始化系统组件
	 */
	private void initView(){
		
		ExpandableListView goal_bill_elv = (ExpandableListView) findViewById(R.id.goal_bill_elv);
		adapter = new GoalBillAdapter(this,childData,groupData);
		goal_bill_elv.setAdapter(adapter);
		
		ImageView bill_btn_back = (ImageView) findViewById(R.id.bill_btn_back);
		bill_btn_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(GoalBillActivity.this, GoalActivity.class));
				finish();
			}
		});
		
		/**
		 * 子条目点击监听
		 */
		goal_bill_elv.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println("----------------------");
				
					Object g = adapter.getChild(groupPosition, childPosition);
					Goal goal = (Goal) g;
					int _id = goal.getId();
					Intent intent = new Intent(GoalBillActivity.this, GoalDetailActivity.class);
					intent.putExtra("_id", _id);
					startActivity(intent);
				return true;
			}
		});
	}
}
