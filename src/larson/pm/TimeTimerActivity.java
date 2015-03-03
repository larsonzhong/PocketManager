package larson.pm;

import larson.pm.view.TimerView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TimeTimerActivity extends Activity {
	private int startTime = 0;
	private SeekBar seekbar;
	private TextView showText = null;
	private Button btnStart;
	private Button btnStop;
	private Button btnRest;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.time_timer_page);

		final Chronometer chronometer = (Chronometer) findViewById(R.id.chronometer);
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStop = (Button) findViewById(R.id.btnStop);
		btnRest = (Button) findViewById(R.id.btnReset);

		seekbar = (SeekBar) findViewById(R.id.seekbar);
		seekbar.setOnSeekBarChangeListener(barlistener);

		showText = (TextView) findViewById(R.id.Timer_show);

		btnStart.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				startTime = seekbar.getProgress();
				if (startTime <= 0) {
					Toast.makeText(TimeTimerActivity.this, "你没有设置时间哦",
							Toast.LENGTH_SHORT).show();
					return;
				}
				showText.setText(startTime + "秒");
				// 设置开始讲时时间
				chronometer.setBase(SystemClock.elapsedRealtime());
				/**
				 * 开始记时 让开始按钮不可用，同时改变开始按钮背景 停止按钮可用，改变停止背景 同时让seekbar不可拖动
				 */
				chronometer.start();
				TimerView.ismove = true;// ------
				disableStart();
				seekbar.setEnabled(false);
				enableStop();
			}

		});

		btnStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/**
				 * 停止计时 让开始按钮可用，改变背景 停止不可用，并改变背景 同时让seekbar可拖动 重置按钮可用，并改变背景
				 */
				chronometer.stop();
				TimerView.ismove = false;
				enableStart();
				disableStop();
				seekbar.setEnabled(true);

				enableReset();
			}

		});

		/**
		 * 重置计时器 同时按下一次后不可用,同时改变背景图片
		 */
		btnRest.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chronometer.setBase(SystemClock.elapsedRealtime());
				TimerView.ismove = false;
				seekbar.setProgress(0);
				disableReset();
			}

		});
		//初始化让reset不可用
		disableReset();

		/**
		 * 计时器监听
		 */
		chronometer
				.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
					public void onChronometerTick(Chronometer chronometer) {
						chronometer.setText(startTime
								- (SystemClock.elapsedRealtime() - chronometer
										.getBase()) / 1000 + "");
						// 如果开始计时到现在超过了startime秒
						if (SystemClock.elapsedRealtime()
								- chronometer.getBase() >= startTime * 1000) {
							chronometer.stop();
							// 给用户提示
							showDialog();
							TimerView.ismove = false;// -----
							MediaPlayer mediaPlayer = MediaPlayer.create(
									TimeTimerActivity.this, R.raw.beeps);
							mediaPlayer.start();
						}
					}
				});
	}

	private OnSeekBarChangeListener barlistener = new OnSeekBarChangeListener() {

		public void onStopTrackingTouch(SeekBar seekBar) {
			showText.setText(seekbar.getProgress() + "秒");
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			showText.setText(seekbar.getProgress() + "秒");
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			showText.setText(seekbar.getProgress() + "秒");

		}
	};

	protected void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.timer_caution);
		builder.setTitle("警告").setMessage("时间到")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	
	/**
	 * reset按钮不可用
	 */
	private void disableReset() {
		btnRest.setClickable(false);
		btnRest.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * reset按钮可用
	 */
	private void enableReset() {
		btnRest.setClickable(true);
		btnRest.setBackgroundResource(R.drawable.btn_blue_selector);
	}
	/**
	 * stop按钮不可用
	 */
	private void disableStop() {
		btnStop.setClickable(false);
		btnStop.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * stop按钮可用
	 */
	private void enableStop() {
		btnStop.setClickable(true);
		btnStop.setBackgroundResource(R.drawable.btn_blue_selector);
	}
	/**
	 * start按钮不可用
	 */
	private void disableStart() {
		btnStart.setClickable(false);
		btnStart.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * start按钮可用
	 */
	private void enableStart() {
		btnStart.setClickable(true);
		btnStart.setBackgroundResource(R.drawable.btn_blue_selector);
	}

}