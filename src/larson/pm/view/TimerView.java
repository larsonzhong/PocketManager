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
 * Created by Larson on 14-6-29. 旋转中心应该是图像的中心也就是（viewWidth/2，viewHeight/2）
 * 图像的绘制点是（viewWidth/2-bmpW/2，viewHeight/2-bmpH/2）
 */
public class TimerView extends View {
	/**
	 * 控件大小
	 */
	private int viewWidth, viewHeight;
	/**
	 * 绘制的图像大小
	 */
	private int bmpW, bmpH, bmpx, bmpy;
	private Bitmap bitmap2;

	private Paint paint;
	/**
	 * 旋转中心点
	 */
	public static float pointX;
	public static float pointY;
	/**
	 * 旋转角度
	 */
	public static int digree1 = 0;
	public static int digree2 = 360;
	/**
	 * 是否旋转
	 */
	public static boolean ismove = false;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setBackgroundColor(Color.parseColor("#00000000"));
		// 使用 BitmapFactory.decodeStream方法将InputStream解码成Bitmap对象
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
		 * 1. 设置旋转角度在0-360之间
		 */
		if (digree1 > 360)
			digree1 = 0;
		if (digree2 < 0)
			digree2 = 360;
		canvas.setMatrix(matrix);

		/**
		 * 2. 设置按钮的旋转角度和旋转轴心坐标
		 */
		if (ismove)
			matrix.setRotate(digree2--, pointX, pointY);
		canvas.setMatrix(matrix);

		/**
		 * 3. 绘制小球图像
		 */
		canvas.drawBitmap(bitmap2, bmpx, bmpy, paint);

		/**
		 * 4. 不短重绘View，不断调用onDrow方法
		 */
		invalidate();
	}
}