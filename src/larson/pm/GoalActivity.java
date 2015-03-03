package larson.pm;

import java.util.List;

import larson.pm.adapter.GoalAdapter;
import larson.pm.bean.Goal;
import larson.pm.dao.GoalDao;
import larson.pm.utils.Constant;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GoalActivity extends Activity {
	private ImageButton goal_add_btn;
	private List<Goal> goals;
	private ListView goal_lv;
	private GoalDao dao;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_main);

		init();
	}

	/**
	 * ��ʼ���ؼ�
	 */
	public void init() {
		goal_add_btn = (ImageButton) findViewById(R.id.goal_add_btn);
		goal_add_btn.setOnClickListener(listener);
		ImageView bill_btn = (ImageView) findViewById(R.id.bill_btn);
		bill_btn.setOnClickListener(listener);
		
		ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(listener);

		dao = new GoalDao(this);
		//��һ���ڵ���Ϣ
		goals = dao.queryByLimit(Constant.QUERY_BY_WEEK);
		adapter = new GoalAdapter(this, goals);

		goal_lv = (ListView) findViewById(R.id.goal_lv);
		goal_lv.setAdapter(adapter);
		goal_lv.setOnItemClickListener(itemClickListener);
	}

	// ���ؽ����������
	@Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetInvalidated();
	}

	/**
	 * item����¼�
	 */
	private OnItemClickListener itemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View view, int id,
				long position) {
			// Ŀ������ҳ��
			Intent intent = new Intent(GoalActivity.this,
					GoalDetailActivity.class);

			TextView idTV = (TextView) view.findViewById(R.id.goal_item_id);
			int temp_id = Integer.parseInt(idTV.getText().toString());
			int _id = queryId(temp_id);

			intent.putExtra("_id", _id);
			Log.v("larson", id + "");
			startActivity(intent);
			finish();// ��ת������ҳ���Ҫ�ͷ��ڴ�
			overridePendingTransition(android.R.anim.fade_in,
					android.R.anim.fade_out);

		}
	};

	/**
	 * ��ѯѡ����Ŀ��id
	 * 
	 * @param id
	 *            item���ڵ�λ��
	 * @return ���item��Ӧ��id��
	 */
	public int queryId(int id) {
		GoalDao dao = new GoalDao(this);
		List<Goal> goals = dao.query("id", id + "");
		return goals.get(0).getId();
	}

	private OnClickListener listener = new OnClickListener() {
		// Ŀ�����ҳ��
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_back:
				startActivity(new Intent(GoalActivity.this,
						MainActivity.class));
				finish();
				break;
			case R.id.goal_add_btn:
				startActivity(new Intent(GoalActivity.this,
						GoalAddActivity.class));
				finish();
				break;
			case R.id.bill_btn:
				startActivity(new Intent(GoalActivity.this,
						GoalBillActivity.class));
				finish();
				break;
			default:
				break;
			}
		}
	};
	private GoalAdapter adapter;

}