package larson.pm;

import java.util.Calendar;
import java.util.List;

import larson.pm.adapter.FondLVAdapter;
import larson.pm.bean.Fond;
import larson.pm.dao.FondDao;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FondFindActivity extends Activity {
	private Context context = FondFindActivity.this;
	private Button fond_find_BtnDate;
	private Button fond_find_query;
	private EditText fond_find_date_tv;
	private ListView fond_find_lv;
	private Calendar calendar = Calendar.getInstance();;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fond_find);

		init();
	}

	public void init() {
		fond_find_BtnDate = (Button) findViewById(R.id.fond_find_BtnDate);
		fond_find_BtnDate.setOnClickListener(listener);

		fond_find_query = (Button) findViewById(R.id.fond_find_query);
		fond_find_query.setOnClickListener(listener);

		fond_find_date_tv = (EditText) findViewById(R.id.fond_find_date_tv);

		fond_find_lv = (ListView) findViewById(R.id.fond_find_lv);
		
		fond_find_in = (TextView) findViewById(R.id.fond_find_in);
		fond_find_out = (TextView) findViewById(R.id.fond_find_out);
	}

	private OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {

			// 修改日期
			if (v.getId() == fond_find_BtnDate.getId()) {
				DatePickerDialog dpd = new DatePickerDialog(context,
						new DatePickerDialog.OnDateSetListener() {

							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								calendar.set(Calendar.YEAR, year);
								calendar.set(Calendar.MONTH, monthOfYear);
								calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								fond_find_date_tv.setText(year + "-"
										+ monthOfYear + "-" + dayOfMonth);
							}
						}, calendar.get(Calendar.YEAR), calendar
								.get(Calendar.MONTH), calendar
								.get(Calendar.DAY_OF_MONTH));
				dpd.show();
			}

			// 开始查询
			if (v.getId() == fond_find_query.getId()) {
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int month = calendar.get(Calendar.MONTH)+1;
				int year = calendar.get(Calendar.YEAR);

				String date = year + "-" + month + "-" + day;
				FondDao dao = new FondDao(context);
				List<Fond> fonds = dao.queryByTime(date);
				
				int count_in = 0,count_out=0;
				for(Fond f:fonds){
					float count = Float.parseFloat("".equals(f.getCount())? 0+"":f.getCount());
					if(f.getIfIn()==0)
						count_in+=count;
					else
						count_out+=count;
				}
				fond_find_in.setText(count_in+"元");
				fond_find_out.setText(count_out+"元");
//				Log.v("larson", fonds.get(0).getTime());

				FondLVAdapter adapter = new FondLVAdapter(context, fonds);
				fond_find_lv.setAdapter(adapter);
			}

		}
	};
	private TextView fond_find_in;
	private TextView fond_find_out;

}
