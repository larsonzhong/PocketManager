package larson.pm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.Fond;
import larson.pm.chart.AverageTemperatureChart;
import larson.pm.chart.BudgetPieChart;
import larson.pm.dao.FondDao;
import larson.pm.utils.TimeTools;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class FondChartActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fond_chart_page);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		/**
		 * 每个月的支出饼状图
		 */
		case R.id.budget_out_Chart_btn:
			int[] colors = { Color.TRANSPARENT, Color.BLUE, Color.CYAN,
					Color.DKGRAY, Color.GREEN, Color.GRAY, Color.MAGENTA,
					Color.LTGRAY, Color.RED, Color.WHITE, Color.YELLOW };
			double[] values = getOutPie();
			String[] out_itemNames = { "其他杂项", "金融保险", "医疗保健", "人情往来", "学习进修",
					"休闲娱乐", "交流通讯", "行车交通", "居家物业", "食品酒水", "衣服饰品" };
			//
			BudgetPieChart chart = new BudgetPieChart();
			chart.setItemNames(out_itemNames);

			chart.setColorAndValues(colors, values);
			Intent intent = chart.execute(FondChartActivity.this);
			startActivity(intent);
			break;
		/**
		 * 每个月的收入支出折线图
		 */
		case R.id.month_average_btn:
			System.out.println("month_average_btn");
			;

			// 1.初始化标题
			String[] titles2 = new String[] { "收入", "支出" };
			// 2.初始化值
			List<double[]> values2 = getMonthDoubleList();
			// 3.初始化颜色(收入，支出)
			int[] colors2 = new int[] { Color.GREEN, Color.RED };

			// 4.设置进去
			AverageTemperatureChart chart2 = new AverageTemperatureChart();
			chart2.setData(titles2, values2, colors2);
			Intent intent2 = chart2.execute(FondChartActivity.this);
			startActivity(intent2);
			break;
		/**
		 * 每个月的收入饼状图
		 */
		case R.id.budget_in_Chart_btn:

			int[] colors3 = { Color.LTGRAY, Color.BLUE, Color.CYAN,
					Color.DKGRAY, Color.GREEN, Color.GRAY, Color.MAGENTA,
					Color.RED, Color.WHITE, Color.YELLOW };
			double[] values3 = getInPie();
			String[] in_itemNames = { "经营所得", "意外来钱", "中奖收入", "礼金收入", "兼职收入",
					"投资收入", "奖金收入", "加班收入", "利息收入", "工资收入" };
			BudgetPieChart chart3 = new BudgetPieChart();
			chart3.setItemNames(in_itemNames);
			chart3.setColorAndValues(colors3, values3);
			Intent intent3 = chart3.execute(FondChartActivity.this);
			startActivity(intent3);
			break;

		default:
			break;
		}
	}

	/**
	 * 获取总共收入的各个信息,收入计的event
	 * 
	 * @return
	 */
	private double[] getInPie() {
		double[] inpie = new double[10];
		FondDao dao = new FondDao(this);
		List<Fond> fonds = dao.queryAll();

		for (Fond f : fonds) {
			String event = f.getEvent();

			double count = Double.valueOf(f.getCount());
			if (f.getEvent().equals("经营所得"))
				inpie[0] += count;
			else if ("意外来钱".equals(event)) {
				inpie[1] += count;
			} else if ("中奖收入".equals(event)) {
				inpie[2] += count;
			} else if ("礼金收入".equals(event)) {
				inpie[3] += count;
			} else if ("兼职收入".equals(event)) {
				inpie[4] += count;
			} else if ("投资收入".equals(event)) {
				inpie[5] += count;
			} else if ("奖金收入".equals(event)) {
				inpie[6] += count;
			} else if ("加班收入".equals(event)) {
				inpie[7] += count;
			} else if ("利息收入".equals(event)) {
				inpie[8] += count;
			} else if ("工资收入".equals(event)) {
				inpie[9] += count;
			}
		}
		return inpie;
	}

	/**
	 * 获取总共支出的各个信息，支出记得type
	 * 
	 * @return
	 */
	private double[] getOutPie() {
		double[] outpie = new double[11];
		FondDao dao = new FondDao(this);
		List<Fond> fonds = dao.queryAll();
		for (Fond f : fonds) {
			String type = f.getType();
			double count = Double.valueOf(f.getCount());
			if (type.equals("其他杂项"))
				outpie[0] += count;
			else if ("金融保险".equals(type)) {
				outpie[1] += count;
			} else if ("医疗保健".equals(type)) {
				outpie[2] += count;
			} else if ("人情往来".equals(type)) {
				outpie[3] += count;
			} else if ("学习进修".equals(type)) {
				outpie[4] += count;
			} else if ("休闲娱乐".equals(type)) {
				outpie[5] += count;
			} else if ("交流通讯".equals(type)) {
				outpie[6] += count;
			} else if ("行车交通".equals(type)) {
				outpie[7] += count;
			} else if ("居家物业".equals(type)) {
				outpie[8] += count;
			} else if ("食品酒水".equals(type)) {
				outpie[9] += count;
			} else if ("衣服饰品".equals(type)) {
				outpie[10] += count;
			}
		}
		return outpie;
	}

	/**
	 * 获取每个月的消费
	 * 
	 * @return
	 */
	private List<double[]> getMonthDoubleList() {
		List<double[]> values2 = new ArrayList<double[]>();
		// 每个月收入的资金数
		double[] in_fond = new double[12];
		// 每个月支出的资金数
		double[] out_fond = new double[12];
		FondDao dao = new FondDao(this);
		List<Fond> fonds = dao.queryAll();
		if (fonds.size() > 0)
			for (Fond f : fonds) {
				try {
					int month = TimeTools.getMonthValue(f.getTime());
					int ifIn = f.getIfIn();
					// 为了方便，都按毛爷爷来算
					double count = Double.parseDouble(f.getCount()) / 100;
					if (ifIn == 0) {
						processByMonth(month, in_fond, count);
					} else if (ifIn == 1) {
						processByMonth(month, out_fond, count);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		values2.add(out_fond);
		values2.add(in_fond);
		return values2;
	}

	private void processByMonth(int month, double[] in_fond, double count) {
		switch (month) {
		case 1:
			in_fond[0] += count;
			break;
		case 2:
			in_fond[1] += count;
			break;
		case 3:
			in_fond[2] += count;
			break;
		case 4:
			in_fond[3] += count;
			break;
		case 5:
			in_fond[4] += count;
			break;
		case 6:
			in_fond[5] += count;
			break;
		case 7:
			in_fond[6] += count;
			break;
		case 8:
			in_fond[7] += count;
			break;
		case 9:
			in_fond[8] += count;
			break;
		case 10:
			in_fond[9] += count;
			break;
		case 11:
			in_fond[10] += count;
			break;
		case 12:
			in_fond[11] += count;
			break;

		default:
			break;
		}
	}
}
