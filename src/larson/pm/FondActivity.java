package larson.pm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import larson.pm.adapter.FondLVAdapter;
import larson.pm.bean.Fond;
import larson.pm.dao.FondDao;
import larson.pm.utils.Constant;
import larson.pm.utils.PopupWindowUtil;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class FondActivity extends Activity {
	private ListView fond_lv;
	private TextView fond_count_in;
	private TextView fond_count_out;
	private Context context = FondActivity.this;
	public static FondLVAdapter adapter;
	private ImageButton imgBtnRight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fond_page);
		initView();
	}

	/**
	 * ��ʼ����ͼ
	 */
	public void initView() {

		fond_lv = (ListView) findViewById(R.id.fond_lv);
		FondDao dao = new FondDao(context);
		List<Fond> fonds = dao.queryAll();
		Log.v("larson", fonds.size() + "-------");
		// ɸѡ�õ�����µ�����
		List<Fond> list = convertFondList(fonds);
		adapter = new FondLVAdapter(context, list);
		fond_lv.setAdapter(adapter);

		fond_count_in = (TextView) findViewById(R.id.fond_count_in);
		fond_count_out = (TextView) findViewById(R.id.fond_count_out);

		/**
		 * �������İ�ť����
		 */
		imgBtnRight = (ImageButton) findViewById(R.id.imgBtnRight);
		imgBtnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/**
				 * 1.ʵ����һ��PopupWindowTool����handler���ý�ȥ��handler�Ǵ���dw������ʱ�򷢳�����Ϣ
				 * �����ĺ�������PopupWindowUtil����ʵ��������̳�PopupWindowUtil����д���еķ����������Զ���Ĳ���
				 * �������𵽵����˵���Ч��
				 */
				PopupWindowTool dw = new PopupWindowTool(v, mHandler);
				//2.����popupWindow�ı���
				dw.setBackgroundDrawable(FondActivity.this.getResources()
						.getDrawable(R.drawable.title_function_bg));
				//3.��ʾpopupWindow
				dw.showLikePopDownMenu();
			}
		});
	}

	/**
	 * ɸѡ���µ��˵�
	 * 
	 * @param fonds
	 * @return
	 */
	private List<Fond> convertFondList(List<Fond> fonds) {
		List<Fond> list = new ArrayList<Fond>();
		for (Fond fond : fonds) {
			boolean thisMonth = isThisMonth(fond.getTime());
			if (thisMonth)
				list.add(fond);
		}
		return list;
	}

	/**
	 * �ж����fond��Ŀ�ǲ�������µ�
	 * 
	 * @param time
	 *            fond��Ŀ��ʱ��
	 * @return
	 */
	private boolean isThisMonth(String time) {
		boolean flag = false;
		if (Calendar.getInstance().get(Calendar.MONTH) == getMonth(time))
			flag = true;
		return flag;
	}

	/**
	 * ���ñ�������֧����textView
	 */
	public void setCount() {
		FondDao dao = new FondDao(context);
		List<Fond> fonds = dao.queryAll();
		float count_in = 0, count_out = 0;
		for (Fond f : fonds) {
			if (Calendar.getInstance().get(Calendar.MONTH) == getMonth(f
					.getTime())
					&& f.getCount() != null
					&& !"".equals(f.getCount())) {
				float count = Float.parseFloat(f.getCount());
				if (f.getIfIn() == 1)
					count_out -= count;
				else
					count_in += count;
			}
		}
		fond_count_in.setText("��������:" + count_in + "Ԫ");
		fond_count_out.setText("����֧��" + count_out + "Ԫ");
	}

	/**
	 * ��ȡ�����ʱ����·�
	 * 
	 * @param time
	 *            ʱ��
	 * @return
	 */
	private int getMonth(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
				Locale.CHINA);
		int month = 0;
		try {
			Date date = format.parse(time);
			month = date.getMonth();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return month;
	}

	public void onClick(View v) {
		if (v.getId() == R.id.fond_add_btn) {
			Intent intent = new Intent(this, FondAddActivity.class);
			startActivity(intent);
			finish();
		}
	}

	@Override
	protected void onResume() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
			fond_lv.setAdapter(adapter);
			setCount();
		}
		super.onResume();
	}

	/**
	 * �ͱ������ұ߰�ť����¼��й�
	 * 
	 * @author Larson
	 * 
	 */
	private static class PopupWindowTool extends PopupWindowUtil implements
			OnClickListener {
		private Handler mHandler;

		public PopupWindowTool(View anchor, Handler handler) {
			super(anchor);
			this.mHandler = handler;
		}

		@Override
		protected void onCreate() {
			// 1.inflateһ�����ֽ���
			LayoutInflater inflater = (LayoutInflater) this.anchor.getContext()
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			View root = null;
			int[] location = new int[2];
			anchor.getLocationOnScreen(location);
			root = (View) inflater.inflate(R.layout.popup_top_right, null);

			// 2.��䲼�ֽ��棬���ò�����������ݣ�����ÿ��������Ӽ�����-------------------------------------------------------------
			TextView txtOne = (TextView) root.findViewById(R.id.txtOne);
			txtOne.setText(R.string.popup_search);
			ImageView imgOne = (ImageView) root.findViewById(R.id.imgOne);
			imgOne.setImageResource(R.drawable.popup_find);
			LinearLayout btnOne = (LinearLayout) root.findViewById(R.id.btnOne);
			btnOne.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Message msg = mHandler.obtainMessage();
					msg.what = Constant.POPUP_BUTTON_ONE;
					mHandler.sendMessage(msg);
					dismiss();
				}
			});

			TextView txtTwo = (TextView) root.findViewById(R.id.txtTwo);
			txtTwo.setText(R.string.popup_bill);
			ImageView imgTwo = (ImageView) root.findViewById(R.id.imgTwo);
			imgTwo.setImageResource(R.drawable.popup_bill);
			LinearLayout btnTwo = (LinearLayout) root.findViewById(R.id.btnTwo);
			btnTwo.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = mHandler.obtainMessage();
					msg.what = Constant.POPUP_BUTTON_TWO;
					mHandler.sendMessage(msg);
					dismiss();
				}
			});

			TextView txtThree = (TextView) root.findViewById(R.id.txtThree);
			txtThree.setText(R.string.popup_chart);
			ImageView imgThree = (ImageView) root.findViewById(R.id.imgThree);
			imgThree.setImageResource(R.drawable.popup_chart);
			LinearLayout btnThree = (LinearLayout) root
					.findViewById(R.id.btnThree);
			btnThree.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Message msg = mHandler.obtainMessage();
					msg.what = Constant.POPUP_BUTTON_THREE;
					mHandler.sendMessage(msg);
					dismiss();
				}
			});
			this.setContentView(root);
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub

		}
	}
	
	/**
	 * ����������¼�
	 */
	public Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constant.POPUP_BUTTON_ONE:
					Intent intent3 = new Intent(FondActivity.this, FondFindActivity.class);
					startActivity(intent3);
				break;
			case Constant.POPUP_BUTTON_TWO:
					Intent intent2 = new Intent(FondActivity.this, FondBillActivity.class);
					startActivity(intent2);
				break;
			case Constant.POPUP_BUTTON_THREE:
				startActivity(new Intent(FondActivity.this, FondChartActivity.class));
				break;
			}
		}
	};

}
