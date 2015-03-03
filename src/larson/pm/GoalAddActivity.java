package larson.pm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.RatingBar.OnRatingBarChangeListener;

/**
 * Ŀ�괴����
 * 
 * @author Larson
 * 
 */
public class GoalAddActivity extends Activity {

	// Ŀ������
	private float challengeLevel = 0.0f;
	private float clearLevel = 0.0f;
	private float emergencyLevel = 0.0f;
	private float importanceLevel = 0.0f;
	private float publicLevel = 0.0f;

	private Bundle b;// ��������

	private RatingBar goal_challengeLevel;
	private RatingBar goal_clearLevel;
	private RatingBar goal_emergencyLevel;
	private RatingBar goal_importanceLevel;
	private RatingBar goal_publicLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.goal_add_level);

		initView();
		initHint();
	}
	
	/**
	 * ��ʼ��ratingbar�϶���ʾ
	 */
	private void initHint(){
		goal_hint_challenge_title = (TextView) findViewById(R.id.goal_hint_challenge_title);
		goal_hint_clear_title = (TextView) findViewById(R.id.goal_hint_clear_title);
		goal_hint_emergency_title = (TextView) findViewById(R.id.goal_hint_emergency_title);
		goal_hint_importance_title = (TextView) findViewById(R.id.goal_hint_importance_title);
		goal_hint_public_title = (TextView) findViewById(R.id.goal_hint_public_title);
		
		goal_hint_challenge_content = (TextView) findViewById(R.id.goal_hint_challenge_content);
		goal_hint_clear_content = (TextView) findViewById(R.id.goal_hint_clear_content);
		goal_hint_emergency_content = (TextView) findViewById(R.id.goal_hint_emergency_content);
		goal_hint_importance_content = (TextView) findViewById(R.id.goal_hint_importance_content);
		goal_hint_public_content = (TextView) findViewById(R.id.goal_hint_public_content);
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView() {

		/*--------------- ���ð�������------------*/
		ImageButton goal_challengeLevel_help = (ImageButton) findViewById(R.id.goal_challengeLevel_help);
		goal_challengeLevel_help.setOnClickListener(listener);
		ImageButton goal_emergencyLevel_help = (ImageButton) findViewById(R.id.goal_emergencyLevel_help);
		goal_emergencyLevel_help.setOnClickListener(listener);
		ImageButton goal_importanceLevel_help = (ImageButton) findViewById(R.id.goal_importanceLevel_help);
		goal_importanceLevel_help.setOnClickListener(listener);
		ImageButton goal_publicLevel_help = (ImageButton) findViewById(R.id.goal_publicLevel_help);
		goal_publicLevel_help.setOnClickListener(listener);
		ImageButton goal_clearLevel_help = (ImageButton) findViewById(R.id.goal_clearLevel_help);
		goal_clearLevel_help.setOnClickListener(listener);

		/*-------------��ʼ��������---------------*/
		goal_challengeLevel = (RatingBar) findViewById(R.id.goal_challengeLevel);
		goal_challengeLevel.setOnRatingBarChangeListener(rating_listener);
		goal_clearLevel = (RatingBar) findViewById(R.id.goal_clearLevel);
		goal_clearLevel.setOnRatingBarChangeListener(rating_listener);
		goal_emergencyLevel = (RatingBar) findViewById(R.id.goal_emergencyLevel);
		goal_emergencyLevel.setOnRatingBarChangeListener(rating_listener);
		goal_importanceLevel = (RatingBar) findViewById(R.id.goal_importanceLevel);
		goal_importanceLevel.setOnRatingBarChangeListener(rating_listener);
		goal_publicLevel = (RatingBar) findViewById(R.id.goal_publicLevel);
		goal_publicLevel.setOnRatingBarChangeListener(rating_listener);

		ImageView add_btn_next = (ImageView) findViewById(R.id.add_btn_next);
		add_btn_next.setOnClickListener(listener);
	}

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			switch (view.getId()) {

			// ----------����Ϊ�������------------
			case R.id.goal_challengeLevel_help:
				popDialog("��ս����\n��Ա�Ŀ���Ѷȶ��壬�Ѷȼ����1�ǵ�5�ǣ��Ǽ�Խ�ߣ��Ѷ�Խ��");
				break;
			case R.id.goal_clearLevel_help:
				popDialog("�������\n���Ƿ��������趨��Ŀ�꣬��������Ŀ�����ȷ��������Ŀ�������ʵ�֣�������Ŀ�������Ա�׶����");
				break;
			case R.id.goal_emergencyLevel_help:
				popDialog("��������\n��Զ��ס������������������Ȼ������Ҫ���£������ܱ����ʧ����");
				break;
			case R.id.goal_importanceLevel_help:
				popDialog("��Ҫ����\n������������Ŀ���������е�λ�ã������ж���Ҫ���Ȱ���Ҫ��������ȥ������Ҫ��");
				break;
			case R.id.goal_publicLevel_help:
				popDialog("��������\nÿ�����������ޣ�Ҫ�����ļ�֣���Ҫ�˼ල�����ԣ������Ŀ�������5-7�����ѣ������ǰ�æ�ල");
				break;

			// -------------����
			case R.id.add_btn_next:

				b = new Bundle();

				b.putFloat("challengeLevel", challengeLevel);
				b.putFloat("clearLevel", clearLevel);
				b.putFloat("emergencyLevel", emergencyLevel);
				b.putFloat("importanceLevel", importanceLevel);
				b.putFloat("publicLevel", publicLevel);
				Intent intent = new Intent(GoalAddActivity.this,
						GoalAddStepActivity.class);
				intent.putExtras(b);
				startActivity(intent);
				finish();
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ������ʾ�Ի���
	 * 
	 * @param id
	 *            �û����µ���ʾ��
	 */
	private void popDialog(String str) {
		AlertDialog dialog = new AlertDialog.Builder(this).setMessage(str)
				.create();
		dialog.show();

		// ���¼������öԻ����͸����
		Window window = dialog.getWindow();
		LayoutParams lp = window.getAttributes();
		lp.alpha = 0.5f;
		window.setAttributes(lp);
	}

	/**
	 * �̶��������϶�����
	 */
	private OnRatingBarChangeListener rating_listener = new OnRatingBarChangeListener() {

		public void onRatingChanged(RatingBar ratingBar, float rating,
				boolean fromUser) {
			/**
			 * �Ѷȼ���
			 */
			if (ratingBar == goal_challengeLevel) {
				challengeLevel = rating;
				if(rating<2.5){
					goal_hint_challenge_content.setText("�ѶȽϵͣ������ڶ����ڿ�����ɵļƻ�");
					goal_hint_challenge_title.setText("�ϵ��Ѷ�");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=2.5&&rating<4){
					goal_hint_challenge_content.setText("�Ѷ��еȣ�������Ŀǰ�����ܹ�ʤ�Σ������Ǻ����׾�����ɵļƻ�");
					goal_hint_challenge_title.setText("�е��Ѷ�");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=4){
					goal_hint_challenge_content.setText("�ϸ��Ѷȣ�����ȷ�Ƿ��ܹ���ɣ�����Ը�Ⳣ�ԣ���ս�Լ�");
					goal_hint_challenge_title.setText("�ϸ��Ѷ�");
					goal_hint_challenge_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			/**
			 * ��ȷ����
			 */
			if (ratingBar == goal_clearLevel) {
				clearLevel = rating;
				if(rating<2.5){
					goal_hint_clear_content.setText("Ŀ�겻��ȷ�������������޸ĵĻ�����������Ū���Ŀ�����趨");
					goal_hint_clear_title.setText("��̫��ȷ");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}else if(rating>=2.5&&rating<4){
					goal_hint_clear_content.setText("һ����ȷ��������Ŀ������һ�����˽⣬���ǻ����Ǻ���ȷ��Ū���Ŀ��������ʵ��");
					goal_hint_clear_title.setText("һ����ȷ");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_clear_content.setText("����ȷ�����Ѿ�֪����Ҫ��ʲô�ˣ�Ҳ֪����ôȥʵ���ˣ�����");
					goal_hint_clear_title.setText("�ǳ���ȷ");
					goal_hint_clear_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}
			}
			if (ratingBar == goal_emergencyLevel) {
				emergencyLevel = rating;
				if(rating<2.5){
					goal_hint_emergency_content.setText("��������������Էŵ������");
					goal_hint_emergency_title.setText("�����ż�");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=2.5&&rating<4){
					goal_hint_emergency_content.setText("���Ǿ���ʵ�ֱȽϺ�");
					goal_hint_emergency_title.setText("�ȽϽ���");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_emergency_content.setText("���̣����ϣ�ʵ�����Ŀ�꣬�̲��ݻ�");
					goal_hint_emergency_title.setText("�ϸ��Ѷ�");
					goal_hint_emergency_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			if (ratingBar == goal_importanceLevel) {
				importanceLevel = rating;
				if(rating<2.5){
					goal_hint_importance_content.setText("��̫��Ҫ���������Ŀ��������Ա���ʱ֮�衣���������пյ�ʱ��ʵ��");
					goal_hint_importance_title.setText("��̫��Ҫ");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
				}else if(rating>=2.5&&rating<4){
					goal_hint_importance_content.setText("�Ƚ���Ҫ����֪�����Ŀ��������ã���Ӧ�þ�����ʵ��");
					goal_hint_importance_title.setText("�Ƚ���Ҫ");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_importance_content.setText("����Ҫ�����Ŀ���ʵ�ֲ��ɣ���Ȼ��ʳ�Ѱ�");
					goal_hint_importance_title.setText("�ǳ���Ҫ");
					goal_hint_importance_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}
			}
			if (ratingBar == goal_publicLevel) {
				publicLevel = rating;
				if(rating<2.5){
					goal_hint_public_content.setText("�����̶�̫�ͣ���������Ŀ����������ˣ������Ǽල��ʵ��");
					goal_hint_public_title.setText("ƫ����");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_high));
				}else if(rating>=2.5&&rating<4){
					goal_hint_public_content.setText("��΢�������������㲻�������");
					goal_hint_public_title.setText("һ�㹫��");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_middle));
				}else if(rating>=4){
					goal_hint_public_content.setText("�ܲ������Ѿ���������֪�������Ŀ��");
					goal_hint_public_title.setText("��ȫ����");
					goal_hint_public_title.setBackgroundDrawable(getResources().getDrawable(R.drawable.goal_state_normal));
					
				}
			}
		}
	};

	/**
	 * �û��İ�������
	 */

	private TextView goal_hint_challenge_title;
	private TextView goal_hint_clear_title;
	private TextView goal_hint_emergency_title;
	private TextView goal_hint_importance_title;
	private TextView goal_hint_public_title;
	private TextView goal_hint_challenge_content;
	private TextView goal_hint_clear_content;
	private TextView goal_hint_emergency_content;
	private TextView goal_hint_importance_content;
	private TextView goal_hint_public_content;	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			startActivity(new Intent(GoalAddActivity.this, GoalActivity.class));
			finish();
		}
		return true;
	}

}
