package larson.pm;

import java.util.ArrayList;
import java.util.List;

import larson.pm.adapter.ReminderAdapter;
import larson.pm.bean.Reminder;
import larson.pm.dao.ReminderDao;
import larson.pm.receiver.ReminderReceiver;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

public class ReminderActivity extends Activity {

	private ListView reminder_lv;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.reminder);
        
        reminder_lv = (ListView) findViewById(R.id.reminder_lv);
        List<Reminder> reminders = readFromSQL();
        ReminderAdapter adapter = new ReminderAdapter(this, reminders);
        reminder_lv.setAdapter(adapter);
        
       ImageButton reminder_addbtn = (ImageButton) findViewById(R.id.reminder_addbtn);
       reminder_addbtn.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent inetnt = new Intent(ReminderActivity.this,ReminderAddActivity.class);
			startActivity(inetnt);
		}
	});
        
    }
    
    public List<Reminder> readFromSQL(){
    	List<Reminder> reminders = new ArrayList<Reminder>();
    	ReminderDao dao = new ReminderDao(this);
    	try {
			reminders = dao.quertAll();
		} catch (Exception e) {
//			dao.createTable();
		}
    	return reminders;
    }
    

	@Override
	protected void onResume() {
		List<Reminder> reminders = readFromSQL();
		ReminderAdapter adapter = new ReminderAdapter(this, reminders);
        reminder_lv.setAdapter(adapter);
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1, 0, "新增");
		menu.add(0, 2, 0, "取消所有闹钟提醒");
		menu.add(0,3, 0, "清楚所有数据");
		menu.add(0, 4, 0, "联系我们");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent inetnt = new Intent(ReminderActivity.this,ReminderAddActivity.class);
			startActivity(inetnt);
			break;
		case 2:
			ReminderDao cancel_dao = new ReminderDao(this);
			List<Reminder> reminders  = cancel_dao.quertAll();
			for(Reminder r :reminders){
				AlarmManager alarm = (AlarmManager) this.getSystemService(Service.ALARM_SERVICE);
				Intent intent = new Intent(ReminderReceiver.ALARM_CLOCK);
				PendingIntent alarmIntent = PendingIntent.getBroadcast(ReminderActivity.this,
						r.getId(), intent, 0);
				alarm.cancel(alarmIntent);
			}
			break;
		case 3:
			ReminderDao dao = new ReminderDao(this);
			dao.deleteTable();
			
			//重设适配器
			 List<Reminder> rds = readFromSQL();
		        ReminderAdapter adapter = new ReminderAdapter(this, rds);
		        reminder_lv.setAdapter(adapter);
		case 4:
//			----------------？？？？？？？这里填写对话框？？？？---------------
			
			break;
		default:
			break;
		}
		return true;
	}
}