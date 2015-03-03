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
					Toast.makeText(TimeTimerActivity.this, "��û������ʱ��Ŷ",
							Toast.LENGTH_SHORT).show();
					return;
				}
				showText.setText(startTime + "��");
				// ���ÿ�ʼ��ʱʱ��
				chronometer.setBase(SystemClock.elapsedRealtime());
				/**
				 * ��ʼ��ʱ �ÿ�ʼ��ť�����ã�ͬʱ�ı俪ʼ��ť���� ֹͣ��ť���ã��ı�ֹͣ���� ͬʱ��seekbar�����϶�
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
				 * ֹͣ��ʱ �ÿ�ʼ��ť���ã��ı䱳�� ֹͣ�����ã����ı䱳�� ͬʱ��seekbar���϶� ���ð�ť���ã����ı䱳��
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
		 * ���ü�ʱ�� ͬʱ����һ�κ󲻿���,ͬʱ�ı䱳��ͼƬ
		 */
		btnRest.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				chronometer.setBase(SystemClock.elapsedRealtime());
				TimerView.ismove = false;
				seekbar.setProgress(0);
				disableReset();
			}

		});
		//��ʼ����reset������
		disableReset();

		/**
		 * ��ʱ������
		 */
		chronometer
				.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
					public void onChronometerTick(Chronometer chronometer) {
						chronometer.setText(startTime
								- (SystemClock.elapsedRealtime() - chronometer
										.getBase()) / 1000 + "");
						// �����ʼ��ʱ�����ڳ�����startime��
						if (SystemClock.elapsedRealtime()
								- chronometer.getBase() >= startTime * 1000) {
							chronometer.stop();
							// ���û���ʾ
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
			showText.setText(seekbar.getProgress() + "��");
		}

		public void onStartTrackingTouch(SeekBar seekBar) {
			showText.setText(seekbar.getProgress() + "��");
		}

		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			showText.setText(seekbar.getProgress() + "��");

		}
	};

	protected void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(R.drawable.timer_caution);
		builder.setTitle("����").setMessage("ʱ�䵽")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				});

		AlertDialog dialog = builder.create();
		dialog.show();
	}

	
	/**
	 * reset��ť������
	 */
	private void disableReset() {
		btnRest.setClickable(false);
		btnRest.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * reset��ť����
	 */
	private void enableReset() {
		btnRest.setClickable(true);
		btnRest.setBackgroundResource(R.drawable.btn_blue_selector);
	}
	/**
	 * stop��ť������
	 */
	private void disableStop() {
		btnStop.setClickable(false);
		btnStop.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * stop��ť����
	 */
	private void enableStop() {
		btnStop.setClickable(true);
		btnStop.setBackgroundResource(R.drawable.btn_blue_selector);
	}
	/**
	 * start��ť������
	 */
	private void disableStart() {
		btnStart.setClickable(false);
		btnStart.setBackgroundResource(R.drawable.fuction_fond_selectdate_pressed);
	}
	/**
	 * start��ť����
	 */
	private void enableStart() {
		btnStart.setClickable(true);
		btnStart.setBackgroundResource(R.drawable.btn_blue_selector);
	}

}