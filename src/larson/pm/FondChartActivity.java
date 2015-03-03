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
		 * ÿ���µ�֧����״ͼ
		 */
		case R.id.budget_out_Chart_btn:
			int[] colors = { Color.TRANSPARENT, Color.BLUE, Color.CYAN,
					Color.DKGRAY, Color.GREEN, Color.GRAY, Color.MAGENTA,
					Color.LTGRAY, Color.RED, Color.WHITE, Color.YELLOW };
			double[] values = getOutPie();
			String[] out_itemNames = { "��������", "���ڱ���", "ҽ�Ʊ���", "��������", "ѧϰ����",
					"��������", "����ͨѶ", "�г���ͨ", "�Ӽ���ҵ", "ʳƷ��ˮ", "�·���Ʒ" };
			//
			BudgetPieChart chart = new BudgetPieChart();
			chart.setItemNames(out_itemNames);

			chart.setColorAndValues(colors, values);
			Intent intent = chart.execute(FondChartActivity.this);
			startActivity(intent);
			break;
		/**
		 * ÿ���µ�����֧������ͼ
		 */
		case R.id.month_average_btn:
			System.out.println("month_average_btn");
			;

			// 1.��ʼ������
			String[] titles2 = new String[] { "����", "֧��" };
			// 2.��ʼ��ֵ
			List<double[]> values2 = getMonthDoubleList();
			// 3.��ʼ����ɫ(���룬֧��)
			int[] colors2 = new int[] { Color.GREEN, Color.RED };

			// 4.���ý�ȥ
			AverageTemperatureChart chart2 = new AverageTemperatureChart();
			chart2.setData(titles2, values2, colors2);
			Intent intent2 = chart2.execute(FondChartActivity.this);
			startActivity(intent2);
			break;
		/**
		 * ÿ���µ������״ͼ
		 */
		case R.id.budget_in_Chart_btn:

			int[] colors3 = { Color.LTGRAY, Color.BLUE, Color.CYAN,
					Color.DKGRAY, Color.GREEN, Color.GRAY, Color.MAGENTA,
					Color.RED, Color.WHITE, Color.YELLOW };
			double[] values3 = getInPie();
			String[] in_itemNames = { "��Ӫ����", "������Ǯ", "�н�����", "�������", "��ְ����",
					"Ͷ������", "��������", "�Ӱ�����", "��Ϣ����", "��������" };
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
	 * ��ȡ�ܹ�����ĸ�����Ϣ,����Ƶ�event
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
			if (f.getEvent().equals("��Ӫ����"))
				inpie[0] += count;
			else if ("������Ǯ".equals(event)) {
				inpie[1] += count;
			} else if ("�н�����".equals(event)) {
				inpie[2] += count;
			} else if ("�������".equals(event)) {
				inpie[3] += count;
			} else if ("��ְ����".equals(event)) {
				inpie[4] += count;
			} else if ("Ͷ������".equals(event)) {
				inpie[5] += count;
			} else if ("��������".equals(event)) {
				inpie[6] += count;
			} else if ("�Ӱ�����".equals(event)) {
				inpie[7] += count;
			} else if ("��Ϣ����".equals(event)) {
				inpie[8] += count;
			} else if ("��������".equals(event)) {
				inpie[9] += count;
			}
		}
		return inpie;
	}

	/**
	 * ��ȡ�ܹ�֧���ĸ�����Ϣ��֧���ǵ�type
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
			if (type.equals("��������"))
				outpie[0] += count;
			else if ("���ڱ���".equals(type)) {
				outpie[1] += count;
			} else if ("ҽ�Ʊ���".equals(type)) {
				outpie[2] += count;
			} else if ("��������".equals(type)) {
				outpie[3] += count;
			} else if ("ѧϰ����".equals(type)) {
				outpie[4] += count;
			} else if ("��������".equals(type)) {
				outpie[5] += count;
			} else if ("����ͨѶ".equals(type)) {
				outpie[6] += count;
			} else if ("�г���ͨ".equals(type)) {
				outpie[7] += count;
			} else if ("�Ӽ���ҵ".equals(type)) {
				outpie[8] += count;
			} else if ("ʳƷ��ˮ".equals(type)) {
				outpie[9] += count;
			} else if ("�·���Ʒ".equals(type)) {
				outpie[10] += count;
			}
		}
		return outpie;
	}

	/**
	 * ��ȡÿ���µ�����
	 * 
	 * @return
	 */
	private List<double[]> getMonthDoubleList() {
		List<double[]> values2 = new ArrayList<double[]>();
		// ÿ����������ʽ���
		double[] in_fond = new double[12];
		// ÿ����֧�����ʽ���
		double[] out_fond = new double[12];
		FondDao dao = new FondDao(this);
		List<Fond> fonds = dao.queryAll();
		if (fonds.size() > 0)
			for (Fond f : fonds) {
				try {
					int month = TimeTools.getMonthValue(f.getTime());
					int ifIn = f.getIfIn();
					// Ϊ�˷��㣬����ëүү����
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
