package larson.pm.adapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import larson.pm.R;
import larson.pm.bean.Fond;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FondLVAdapter extends BaseAdapter {
	private Context context;
	private List<Fond> fonds;

	public FondLVAdapter(Context context, List<Fond> fonds) {
		this.context = context;
		this.fonds = fonds;
	}
	

	public int getCount() {
		return fonds.size();
	}

	public Object getItem(int position) {
		return fonds.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = LayoutInflater.from(context).inflate(R.layout.fond_bill_item,
				null);
		Fond f = fonds.get(position);
		String event = f.getEvent();
		String count = f.getCount();
//		String time = f.getTime();
		String time = convertTime(f.getTime());
		int ifIn = f.getIfIn();
		TextView item_amount = (TextView) convertView
				.findViewById(R.id.fond_item_item_amount);
		if (ifIn == 1){
			item_amount.setTextColor(Color.RED);
			item_amount.setText("支出" + count+"元");
		}
		else{
			item_amount.setTextColor(Color.GREEN);
			item_amount.setText("收入" + count+"元");
		}

		TextView item_sub = (TextView) convertView
				.findViewById(R.id.fond_item_item_sub);
		item_sub.setText("主题："+event);

		TextView item_time = (TextView) convertView
				.findViewById(R.id.fond_item_item_time); 
		item_time.setText("Time:"+time);
		return convertView;
	}

	//sdsd
	private String convertTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = format.parse(time);
			Log.v("larson", date.toLocaleString());
			return date.toLocaleString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
