package larson.pm;

import java.lang.ref.WeakReference;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 模拟打电话的类
 * @author Larson
 *
 */
public class CallInActivity extends Activity {
	private ImageButton answer;
	private ImageButton decline;
	private WeakReference<Context> mApp;
	private TextView slide;
	private LinearLayout lin1, lin2;
	private MediaPlayer mp;
	private Chronometer calling_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_in_ui);
		mApp = new WeakReference<Context>(this);
		initView();
		startRing();
	}

	private void initView() {
		answer = (ImageButton) findViewById(R.id.answer);
		decline = (ImageButton) findViewById(R.id.decline);

		answer.setOnTouchListener(new myOnTouchListener());
		decline.setOnTouchListener(new myOnTouchListener());

		slide = (TextView) findViewById(R.id.slide);

		lin1 = (LinearLayout) findViewById(R.id.lin1);
		lin2 = (LinearLayout) findViewById(R.id.lin2);

		Button endcall = (Button) findViewById(R.id.endcall);
		endcall.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		calling_time = (Chronometer) findViewById(R.id.calling_time);
	}

	/**
	 * 开始播放来电铃声
	 */
	private void startRing() {
		mp = new MediaPlayer();
		try {
			mp.setDataSource(this, RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
			mp.prepare();
		} catch (Exception e) {
		}
		mp.start();
	}

	/**
	 * 结束播放
	 */
	private void endRing() {
		if (mp != null) {
			mp.stop();
			mp.release();
			mp = null;
		}
	}

	private class myOnTouchListener implements OnTouchListener {
		private int lastX;
		private int screenWidth;
		private int screenHeight;
		private ViewGroup.LayoutParams aparam;
		private ViewGroup.LayoutParams dparam;
		private DisplayMetrics dm;

		myOnTouchListener() {
			dm = mApp.get().getResources().getDisplayMetrics();
			screenWidth = dm.widthPixels;
			screenHeight = dm.heightPixels;
			aparam = answer.getLayoutParams();
			dparam = decline.getLayoutParams();
		}

		public boolean onTouch(View v, MotionEvent event) {

			if (v.getId() == R.id.answer
					&& decline.getVisibility() == View.VISIBLE) {
				decline.setVisibility(View.INVISIBLE);
				slide.setText("右滑接听");
			}
			if (v.getId() == R.id.decline
					&& answer.getVisibility() == View.VISIBLE) {
				answer.setVisibility(View.INVISIBLE);
				slide.setText("左滑拒接");
			}
			int ea = event.getAction();
			switch (ea) {
			case MotionEvent.ACTION_DOWN:

				lastX = (int) event.getRawX();
				// lastY=(int)event.getRawY();
				break;
			case MotionEvent.ACTION_MOVE:
				int dx = (int) event.getRawX() - lastX;
				int dy = 0;// (int)event.getRawY()-lastY;

				int l = v.getLeft() + dx;
				int b = v.getBottom() + dy;
				int r = v.getRight() + dx;
				int t = v.getTop() + dy;
				if (l < 0) {
					l = 0;
					r = l + v.getWidth();
				}

				if (t < 0) {
					t = 0;
					b = t + v.getHeight();
				}

				if (r > screenWidth) {
					r = screenWidth;
					l = r - v.getWidth();
				}

				if (b > screenHeight) {
					b = screenHeight;
					t = b - v.getHeight();
				}
				v.layout(l, t, r, b);

				lastX = (int) event.getRawX();
				// lastY=(int)event.getRawY();

				v.postInvalidate();

				break;
			case MotionEvent.ACTION_UP:
				if (v.getId() == R.id.answer)
					v.setLayoutParams(aparam);
				else
					v.setLayoutParams(dparam);
				if (v.getId() == R.id.answer) {
					decline.setVisibility(View.VISIBLE);
					if (lastX > screenWidth - backpx(60)) {
						lin1.setVisibility(View.GONE);
						lin2.setVisibility(View.VISIBLE);
						// TODO toAnswer();接听电话并让计时器计时
						calling_time.setVisibility(View.VISIBLE);
	                	   if(calling_time != null)
	           	   			{
	                		   calling_time.setBase(SystemClock.elapsedRealtime());
	                		   calling_time.start();
	           	   			}
						endRing();
					}
				}
				if (v.getId() == R.id.decline) {
					answer.setVisibility(View.VISIBLE);
					if (lastX < backpx(60)) {
						lin1.setVisibility(View.GONE);
						// TODO toEnd();结束播放和界面
						endRing();
						finish();
					}
				}
				slide.setText("移动滑块");
				break;
			}
			return false;
		}

		int backpx(float dpValue) {
			final float scale = dm.density;
			return (int) (dpValue * scale + 0.5f);
		}
	}
}
