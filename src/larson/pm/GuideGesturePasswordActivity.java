package larson.pm;

import larson.pm.utils.App;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GuideGesturePasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesturepassword_guide);
		findViewById(R.id.gesturepwd_guide_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				App.getInstance().getLockPatternUtils().clearLock();
				// 打开新的Activity
				startActivity(new Intent(GuideGesturePasswordActivity.this,
						CreateGesturePasswordActivity.class));
				finish();
			}
		});
	}

}
