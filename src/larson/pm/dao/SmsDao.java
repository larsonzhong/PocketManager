package larson.pm.dao;

import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.SmsBean;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SmsDao extends SmsOpenHelper {
	private Context context;

	public SmsDao(Context context) {
		super(context);
		this.context = context;
	}

	// 插入数据
	public void insert(SmsBean sms) {
		SmsOpenHelper helper = new SmsOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		String cmd = "INSERT INTO autosms(receiver,content,time) VALUES('"
				+ sms.getReceiver() + "','" + sms.getContent() + "','"
				+ sms.getTime() + "')";
		db.execSQL(cmd);
		db.close();
	}

	// 查询得到包括id的数据
	public List<SmsBean> query(String query_key, String value) {
		SmsOpenHelper helper = new SmsOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		SmsBean sms = new SmsBean();

		String cmd = "SELECT id,receiver,content,time FROM autosms WHERE "
				+ query_key + "=?";
		Cursor c = db.rawQuery(cmd, new String[] { value });

		List<SmsBean> smses = new ArrayList<SmsBean>();
		while (c.moveToNext()) {
			int id = c.getInt(0);
			String receiver = c.getString(1);
			String content = c.getString(2);
			String time = c.getString(3);

			sms.setId(id);
			sms.setContent(content);
			sms.setTime(time);
			sms.setReceiver(receiver);
			smses.add(sms);
		}
		return smses;
	}
	
	// 查询所有信息，返回SMS对象集合
			public List<SmsBean> queryAll() {
				SmsOpenHelper helper = new SmsOpenHelper(context);
				SQLiteDatabase db = helper.getReadableDatabase();
				String cmd = "SELECT id,receiver,content,time FROM autosms";
				Cursor c = db.rawQuery(cmd, null);
				List<SmsBean> smses = new ArrayList<SmsBean>();
				while (c.moveToNext()) {
					SmsBean sms = new SmsBean();
					sms.setId( c.getInt(0));
					sms.setReceiver(c.getString(1));
					sms.setContent(c.getString(2));
					sms.setTime(c.getString(3));
					smses.add(sms);
				}
				db.close();
				return smses;
			}

			/**
			 * 根据id删除一条记录
			 * @param id
			 */
			public void delete(int id) {
				SmsOpenHelper helper = new SmsOpenHelper(context);
				SQLiteDatabase db = helper.getWritableDatabase();
				String sql = "DELETE FROM autosms WHERE id="+id;
				db.execSQL(sql);
				db.close();
			}

}
