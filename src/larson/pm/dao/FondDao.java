package larson.pm.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import larson.pm.bean.Fond;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class FondDao extends FondOpenHelper {
	private Context context;

	public FondDao(Context context) {
		super(context);
		this.context = context;
	}
	
	// 插入数据
		public void insert(Fond f) {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			String cmd = "INSERT INTO fond(user,event,count,time,describe,ifIn) VALUES('"
					+ f.getType()
					+ "','"
					+ f.getEvent()
					+ "','"
					+ f.getCount()
					+ "','"
					+ f.getTime()
					+ "','"
					+ f.getDescribe()
					+ "',"
					+f.getIfIn()+")";
			db.execSQL(cmd);
			db.close();
		}

		// 是否更新成功------
		public void update(int id, String describe) {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			db.execSQL("UPDATE fond SET describe='" + describe + "' WHERE id=" + id);
			db.close();
		}

		// //是否删除成功，返回true或者false
		public boolean delete(String time) {
			return false;
		}

		/**
		 * private String goalName;//目标名 private String goalStartTime;//目标开始时间
		 * private String goalEndTime;//目标结束时间 private int isGoalFinish;//目标是否完成
		 * private int goalLevel;//目标级别
		 * 
		 * @return
		 */
		// 查询所有信息，返回goals对象集合
		public List<Fond> queryAll() {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			String cmd = "SELECT id, user,event,count,time,describe,ifIn FROM fond ";
			Cursor c = db.rawQuery(cmd, null);
			List<Fond> fonds = new ArrayList<Fond>();
			while (c.moveToNext()) {
				Fond fond = new Fond( c.getInt(0),c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getString(5),c.getInt(6));
				fonds.add(fond);
			}
			db.close();
			return fonds;
		}

		/**
		 * 痛过值查询返回查询集合，便于方便，推荐使用查询时间的方式
		 * 
		 * @param query_key
		 *            查询条件
		 * @param value
		 *            查询匹配值
		 * @return
		 */
		public List<Fond> query(String query_key, String value) {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			Fond f ;

			String cmd = "SELECT id, user,event,count,time,describe FROM fond WHERE "
					+ query_key + "=?";
			Cursor c = db.rawQuery(cmd, new String[] { value });

			List<Fond> fonds = new ArrayList<Fond>();
			while (c.moveToNext()) {
				int id = c.getInt(0);//
				String user = c.getString(1);// 
				String event = c.getString(2);// 
				String count = c.getString(3);//
				String time = c.getString(4);// 
				String describe = c.getString(5);// 
				int ifIn = c.getInt(6);
				f = new Fond(id, user, event, count, time, describe,ifIn);
				fonds.add(f);
			}
			db.close();
			return fonds;
		}
		
		public void insertEvent(String event){
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			String cmd = "INSERT INTO matter(event) VALUES('"
					+ event+"')";
			db.execSQL(cmd);
			db.close();
		}
		
		public List<String> queryEventAll() {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			String cmd = "SELECT id,event FROM fond ";
			Cursor c = db.rawQuery(cmd, null);
			List<String> list = new ArrayList<String>();
			while (c.moveToNext()) {
				list.add(c.getString(0));
			}
			db.close();
			return list;
		}

		public List<Fond> queryByTime(String date) {
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getReadableDatabase();
			String cmd = "SELECT id, user,event,count,time,describe,ifIn FROM fond ";
			Cursor c = db.rawQuery(cmd, null);
			List<Fond> fonds = new ArrayList<Fond>();
			while (c.moveToNext()) {
				Fond fond = new Fond( c.getInt(0),c.getString(1), c.getString(2),
						c.getString(3), c.getString(4), c.getString(5),c.getInt(6));
				if(sameMonth(c.getString(4), date))
				fonds.add(fond);
			}
			db.close();
			return fonds;
		}

		private boolean sameMonth(String time,String date) {
			boolean flag = false;
			SimpleDateFormat sdf_crr = new SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.CHINA);
			try {
				Date date_curr = sdf_crr.parse(time);
				Calendar c_crr = Calendar.getInstance();
				c_crr.setTime(date_curr);
				int year_crr = c_crr.get(Calendar.YEAR);
				int month_crr = c_crr.get(Calendar.MONTH);
				int day_crr = c_crr.get(Calendar.DAY_OF_MONTH);
				
				SimpleDateFormat sdf_cmp = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
				Date date_cmp = sdf_cmp.parse(date);
				Calendar c_cmp = Calendar.getInstance();
				c_cmp.setTime(date_cmp);
				int year_cmp = c_cmp.get(Calendar.YEAR);
				int month_cmp = c_cmp.get(Calendar.MONTH);
				int day_cmp = c_cmp.get(Calendar.DAY_OF_MONTH);
				
				if(year_crr==year_cmp&&month_crr==month_cmp&&day_crr==day_cmp)
					flag = true;
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return flag;
		}
		
		/**
		 * 清空数据表，并自增归零
		 * @param context 
		 * @param dbTypeGoal
		 */
		public void deleteTable(){
			FondOpenHelper helper = new FondOpenHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			//清空数据
			String sql1 = "DELETE FROM fond";
			//计数器归零
			String sql2 = "DELETE FROM sqlite_sequence WHERE name = 'fond'";
			db.execSQL(sql1);
			db.execSQL(sql2);
		}
}
