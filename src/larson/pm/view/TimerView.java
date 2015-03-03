package larson.pm.view;

import java.io.InputStream;

import larson.pm.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Larson on 14-6-29. ��ת����Ӧ����ͼ�������Ҳ���ǣ�viewWidth/2��viewHeight/2��
 * ͼ��Ļ��Ƶ��ǣ�viewWidth/2-bmpW/2��viewHeight/2-bmpH/2��
 */
public class TimerView extends View {
	/**
	 * �ؼ���С
	 */
	private int viewWidth, viewHeight;
	/**
	 * ���Ƶ�ͼ���С
	 */
	private int bmpW, bmpH, bmpx, bmpy;
	private Bitmap bitmap2;

	private Paint paint;
	/**
	 * ��ת���ĵ�
	 */
	public static float pointX;
	public static float pointY;
	/**
	 * ��ת�Ƕ�
	 */
	public static int digree1 = 0;
	public static int digree2 = 360;
	/**
	 * �Ƿ���ת
	 */
	public static boolean ismove = false;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.parseColor("#00000000"));
		// ʹ�� BitmapFactory.decodeStream������InputStream�����Bitmap����
		InputStream is = getResources().openRawResource(
				R.drawable.timer_button_up);
		bitmap2 = BitmapFactory.decodeStream(is);
		paint = new Paint();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = this.getWidth();
		viewHeight = this.getHeight();
		pointX = viewWidth / 2;
		pointY = viewHeight / 2;
		bmpH = bitmap2.getHeight();
		bmpW = bitmap2.getWidth();
		bmpx = viewWidth / 2 - bmpW / 2;
		bmpy = viewHeight / 2 - bmpH / 2;
		System.out.println(+viewWidth + "--------" + viewHeight + "--------"
				+ bmpH + "-----------" + bmpW + "-----------" + bmpx
				+ "-----------" + bmpy);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Matrix matrix = new Matrix();
		/**
		 * 1. ������ת�Ƕ���0-360֮��
		 */
		if (digree1 > 360)
			digree1 = 0;
		if (digree2 < 0)
			digree2 = 360;
		canvas.setMatrix(matrix);

		/**
		 * 2. ���ð�ť����ת�ǶȺ���ת��������
		 */
		if (ismove)
			matrix.setRotate(digree2--, pointX, pointY);
		canvas.setMatrix(matrix);

		/**
		 * 3. ����С��ͼ��
		 */
		canvas.drawBitmap(bitmap2, bmpx, bmpy, paint);

		/**
		 * 4. �����ػ�View�����ϵ���onDrow����
		 */
		invalidate();
	}
}