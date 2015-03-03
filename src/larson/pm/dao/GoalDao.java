package larson.pm.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.Goal;
import larson.pm.utils.Constant;
import larson.pm.utils.TimeTools;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class GoalDao extends GoalOpenHelper {
	private Context context;

	public GoalDao(Context context) {
		super(context);
		this.context = context;
	}

	// ��������
	/**
	 * int isSet, int isFinish, int challengeLevel, int emergencyLevel, int
	 * importanceLevel, int publicLevel, int clearLevel, int step1Degree, int
	 * step2Degree, int step3Degree, String step1LimitTime, String
	 * step2LimitTime, String step3LimitTime, String goalName, String reason,
	 * String benefit, String damage
	 */
	public boolean insert(Goal g) {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		String cmd = "INSERT INTO goal(isFinish,goalName,challengeLevel,emergencyLevel,importanceLevel,publicLevel,clearLevel,step1Degree,step2Degree,step3Degree,step1LimitTime,step2LimitTime,step3LimitTime,step1Describe,step2Describe,step3Describe,define,reason,benefit,damage) VALUES("
				+ g.getIsFinish()
				+ ",'"
				+ g.getGoalName()
				+ "',"
				+ g.getChallengeLevel()
				+ ","
				+ g.getEmergencyLevel()
				+ ","
				+ g.getImportanceLevel()
				+ ","
				+ g.getPublicLevel()
				+ ","
				+ g.getClearLevel()
				+ ","
				+ g.getStep1Degree()
				+ ","
				+ g.getStep2Degree()
				+ ","
				+ g.getStep3Degree()
				+ ",'"
				+ g.getStep1LimitTime()
				+ "','"
				+ g.getStep2LimitTime()
				+ "','"
				+ g.getStep3LimitTime()
				+ "','"
				+ g.getStep1Describe()
				+ "','"
				+ g.getStep2Describe()
				+ "','"
				+ g.getStep3Describe()
				+ "','"
				+ g.getDefine()
				+ "','"
				+ g.getReason()
				+ "','"
				+ g.getBenefit() + "','" + g.getDamage() + "')";
		db.execSQL(cmd);
		db.close();
		return true;
	}

	// �Ƿ���³ɹ�------2014-3-28-0:04�޸�-----����
	public void update(int id, int isFinish) {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("UPDATE goal SET isFinish='" + isFinish + "' WHERE id=" + id);
		db.close();
	}

	// //�Ƿ�ɾ���ɹ�������true����false
	public boolean delete(String time) {
		return false;
	}

	// //ɾ���ɹ�-----int id
	public void delete(int id) {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		db.execSQL("DELETE FROM goal WHERE id=" + id);
		db.close();
		Log.v("larson", "delere------------id" + id);
	}

	/**
	 * ��ѯ������Ϣ������goals���󼯺�
	 * 
	 * @return
	 */
	public List<Goal> queryAll() {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String cmd = "SELECT id, goalName,isFinish,challengeLevel,emergencyLevel,importanceLevel,publicLevel,clearLevel,step1Degree,step2Degree,step3Degree,step1LimitTime,step2LimitTime,step3LimitTime,step1Describe,step2Describe,step3Describe,define,reason,benefit,damage FROM goal ";
		Cursor c = db.rawQuery(cmd, null);
		List<Goal> goals = new ArrayList<Goal>();
		while (c.moveToNext()) {
			Goal goal = getGoalFromCursor(c);
			goals.add(goal);
		}
		db.close();
		return goals;
	}

	/**
	 * ʹ��ֵ��ѯ���ز�ѯ���ϣ����ڷ��㣬�Ƽ�ʹ�ò�ѯʱ��ķ�ʽ
	 * 
	 * @param query_key
	 *            ��ѯ����
	 * @param value
	 *            ��ѯƥ��ֵ
	 * @return
	 */
	public List<Goal> query(String query_key, String value) {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String cmd = "SELECT id, goalName,isFinish,challengeLevel,emergencyLevel,importanceLevel,publicLevel,clearLevel,step1Degree,step2Degree,step3Degree,step1LimitTime,step2LimitTime,step3LimitTime,step1Describe,step2Describe,step3Describe,goalName,reason,benefit,damage FROM goal WHERE "
				+ query_key + "=?";
		Cursor c = db.rawQuery(cmd, new String[] { value });

		List<Goal> goals = new ArrayList<Goal>();
		while (c.moveToNext()) {
			Goal goal = getGoalFromCursor(c);
			goals.add(goal);
		}
		db.close();
		return goals;
	}

	/**
	 * �����α��õ����ݣ�����goal����
	 * 
	 * @param c
	 * @return
	 */
	private Goal getGoalFromCursor(Cursor c) {
		Goal goal = new Goal();
		goal.setId(c.getInt(0));
		goal.setGoalName(c.getString(1));
		goal.setIsFinish(c.getInt(2));
		goal.setChallengeLevel(c.getFloat(3));
		goal.setEmergencyLevel(c.getFloat(4));
		goal.setImportanceLevel(c.getFloat(5));
		goal.setPublicLevel(c.getFloat(6));
		goal.setClearLevel(c.getFloat(7));
		goal.setStep1Degree(c.getFloat(8));
		goal.setStep2Degree(c.getFloat(9));
		goal.setStep3Degree(c.getFloat(10));
		goal.setStep1LimitTime(c.getString(11));
		goal.setStep2LimitTime(c.getString(12));
		goal.setStep3LimitTime(c.getString(13));
		goal.setStep1Describe(c.getString(14));
		goal.setStep2Describe(c.getString(15));
		goal.setStep3Describe(c.getString(16));
		goal.setDefine(c.getString(17));
		goal.setReason(c.getString(18));
		goal.setBenefit(c.getString(19));
		goal.setDamage(c.getString(20));
		return goal;
	}

	// // ----------------buxibuxiebuxi��м
	// public void query(String key, int id) {
	//
	// }

	/**
	 * ��������Ϊ���ܵ����ݣ�����step1��2��3��
	 * 
	 * @return
	 */
	public List<Goal> queryByLimit(int limit_key) {
		GoalOpenHelper helper = new GoalOpenHelper(context);
		SQLiteDatabase db = helper.getReadableDatabase();
		String cmd = "SELECT id, goalName,isFinish,challengeLevel,emergencyLevel,importanceLevel,publicLevel,clearLevel,step1Degree,step2Degree,step3Degree,step1LimitTime,step2LimitTime,step3LimitTime,step1Describe,step2Describe,step3Describe,define,reason,benefit,damage FROM goal ";
		Cursor c = db.rawQuery(cmd, null);
		List<Goal> goals = new ArrayList<Goal>();
		while (c.moveToNext()) {
			Goal goal = getGoalFromCursor(c);
			try {
				switch (limit_key) {
				/**
				 * ���ܵ�����
				 */
				case Constant.QUERY_BY_WEEK:
					if (TimeTools.isThisWeek(goal.getStep1LimitTime())
							|| TimeTools.isThisWeek(goal.getStep2LimitTime())
							|| TimeTools.isThisWeek(goal.getStep3LimitTime()))
						goals.add(goal);
					break;
				/**
				 * һ�����ڵ�����
				 */
				case Constant.QUERY_BY_MONTH:
					if (TimeTools.isThisMonth(goal.getStep1LimitTime())
							|| TimeTools.isThisMonth(goal.getStep2LimitTime())
							|| TimeTools.isThisMonth(goal.getStep3LimitTime()))
						goals.add(goal);
					break;
				/**
				 * �������ڵ�����
				 */
				case Constant.QUERY_THREE_MONTH:
					if (TimeTools.isThreeMonth(goal.getStep1LimitTime())
							|| TimeTools.isThreeMonth(goal.getStep2LimitTime())
							|| TimeTools.isThreeMonth(goal.getStep3LimitTime()))
						goals.add(goal);
					break;
				default:
					break;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		db.close();
		return goals;
	}

	/**
	 * ������ݱ�����������
	 * 
	 * @param context
	 * @param dbTypeGoal
	 */
	public void deleteTable() {
		FondOpenHelper helper = new FondOpenHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		// �������
		String sql1 = "DELETE FROM goal";
		// ����������
		String sql2 = "DELETE FROM sqlite_sequence WHERE name = 'goal'";
		db.execSQL(sql1);
		db.execSQL(sql2);
		db.close();
	}

}
