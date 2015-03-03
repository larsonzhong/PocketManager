package larson.pm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import larson.pm.bean.Fond;
import larson.pm.bean.Goal;
import larson.pm.dao.FondDao;
import larson.pm.dao.GoalDao;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.util.Xml;

/**
 * ������ݵı���,������<br>
 * ͨѶ¼���ݵı���<br>
 * Ŀ��������ݵı���<br>
 * �ʽ�������ݵı���<br>
 * �˼ʹ������ݵı���<br>
 * 
 * @author Larson
 * 
 */
public class SaveDB {


	/**
	 * �����������ݵ����ص�xml�ļ���
	 * 
	 * @param context
	 * @return
	 */
	public boolean backupAll2File(Context context) {
		SystemInfoUtil.initExtraDBDir();
		try {
			backupGoal(context);
			backupFond(context);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private void backupGoal(Context context) throws Exception {
		GoalDao dao = new GoalDao(context);
		List<Goal> goals = dao.queryAll();
		generateGoalXml(goals);
	}

	private void backupFond(Context context) throws Exception {
		FondDao dao = new FondDao(context);
		List<Fond> fonds = dao.queryAll();
		generateFondXml(fonds);
	}

	/**
	 * ����Goal��xml�ļ�
	 * 
	 * @param lists
	 * @throws Exception
	 */
	private void generateGoalXml(List<Goal> lists) throws Exception {
		File file = new File(Constant.BACKUP_GOAL_PATH);
		if (!file.exists())
			file.createNewFile();
		// 1.���������
		FileOutputStream fos = new FileOutputStream(file);
		// 2.ʵ����һ�����л���
		XmlSerializer serializer = Xml.newSerializer();
		// 3.����serializer�����
		serializer.setOutput(fos, "utf-8");
		// 4.��ʼд�ĵ���ʼ�ͽ���
		serializer.startDocument("utf-8", true);
		// 5.��ѭ���õ�����д�뵽tag
		serializer.startTag(null, "goals");
		for (Goal goal : lists) {
			serializer.startTag(null, "goal");
			serializer.attribute(null, "id", goal.getId() + "");
			
			serializer.startTag(null, "goalName");
			serializer.text(goal.getGoalName());
			serializer.endTag(null, "goalName");
			// --------------------------------

			serializer.startTag(null, "benefit");
			serializer.text(goal.getBenefit());
			serializer.endTag(null, "benefit");
			// --------------------------------
			serializer.startTag(null, "damage");
			serializer.text(goal.getDamage());
			serializer.endTag(null, "damage");
			// --------------------------------
			serializer.startTag(null, "define");
			serializer.text(goal.getDefine());
			serializer.endTag(null, "define");
			// --------------------------------
			
			serializer.startTag(null, "reason");
			serializer.text(goal.getReason());
			serializer.endTag(null, "reason");
			// --------------------------------
			serializer.startTag(null, "step1Describe");
			serializer.text(goal.getStep1Describe());
			serializer.endTag(null, "step1Describe");
			// --------------------------------
			serializer.startTag(null, "step2Describe");
			serializer.text(goal.getStep2Describe());
			serializer.endTag(null, "step2Describe");
			// --------------------------------
			serializer.startTag(null, "step3Describe");
			serializer.text(goal.getStep3Describe());
			serializer.endTag(null, "step3Describe");
			// --------------------------------
			serializer.startTag(null, "step1LimitTime");
			serializer.text(goal.getStep1LimitTime());
			serializer.endTag(null, "step1LimitTime");
			// --------------------------------
			serializer.startTag(null, "step2LimitTime");
			serializer.text(goal.getStep2LimitTime());
			serializer.endTag(null, "step2LimitTime");
			// --------------------------------
			serializer.startTag(null, "step3LimitTime");
			serializer.text(goal.getStep3LimitTime());
			serializer.endTag(null, "step3LimitTime");
			// --------------------------------
			serializer.startTag(null, "challengeLevel");
			serializer.text(goal.getChallengeLevel() + "");
			serializer.endTag(null, "challengeLevel");
			// --------------------------------
			serializer.startTag(null, "clearLevel");
			serializer.text(goal.getClearLevel() + "");
			serializer.endTag(null, "clearLevel");
			// --------------------------------
			serializer.startTag(null, "emergencyLevel");
			serializer.text(goal.getEmergencyLevel() + "");
			serializer.endTag(null, "emergencyLevel");
			// --------------------------------
			serializer.startTag(null, "goalAvgLevel");
			serializer.text(goal.getGoalAvgLevel() + "");
			serializer.endTag(null, "goalAvgLevel");
			// --------------------------------
			serializer.startTag(null, "importanceLevel");
			serializer.text(goal.getImportanceLevel() + "");
			serializer.endTag(null, "importanceLevel");
			// --------------------------------
			serializer.startTag(null, "isFinish");
			serializer.text(goal.getIsFinish() + "");
			serializer.endTag(null, "isFinish");
			// --------------------------------
			serializer.startTag(null, "publicLevel");
			serializer.text(goal.getPublicLevel() + "");
			serializer.endTag(null, "publicLevel");
			// --------------------------------
			serializer.startTag(null, "step1Degree");
			serializer.text(goal.getStep1Degree() + "");
			serializer.endTag(null, "step1Degree");
			// --------------------------------
			serializer.startTag(null, "step2Degree");
			serializer.text(goal.getStep2Degree() + "");
			serializer.endTag(null, "step2Degree");
			// --------------------------------
			serializer.startTag(null, "step3Degree");
			serializer.text(goal.getStep3Degree() + "");
			serializer.endTag(null, "step3Degree");

			serializer.endTag(null, "goal");
		}
		serializer.endTag(null, "goals");

		serializer.endDocument();
		// �ǵùر���
		fos.close();
	}

	/**
	 * ����fond��xml�����ļ�
	 * 
	 * @param <T>
	 * @param goals
	 * @throws Exception
	 */
	private void generateFondXml(List<Fond> fonds) throws Exception {
		File file = new File(Constant.BACKUP_FOND_PATH);
		if (!file.exists())
			file.createNewFile();

		// 1.ʵ����һ�����л���
		XmlSerializer serializer = Xml.newSerializer();
		// 2.ָ�������
		FileOutputStream fos = new FileOutputStream(file);
		// 3.�������л������
		serializer.setOutput(fos, "utf-8");
		// ��ʼд�ĵ�
		serializer.startDocument("utf-8", true);

		serializer.startTag(null, "fonds");
		for (Fond f : fonds) {
			serializer.startTag(null, "fond");
			serializer.attribute(null, "id", f.getId() + "");
			serializer.startTag(null, "count");
			serializer.text(f.getCount());
			serializer.endTag(null, "count");
			// ----------------------------------
			serializer.startTag(null, "describe");
			serializer.text(f.getDescribe());
			serializer.endTag(null, "describe");
			// ----------------------------------
			serializer.startTag(null, "event");
			serializer.text(f.getEvent());
			serializer.endTag(null, "event");
			// ----------------------------------
			serializer.startTag(null, "time");
			serializer.text(f.getTime());
			serializer.endTag(null, "time");
			// ----------------------------------
			serializer.startTag(null, "type");
			serializer.text(f.getType());
			serializer.endTag(null, "type");
			// ----------------------------------
			serializer.startTag(null, "ifIn");
			serializer.text(f.getIfIn() + "");
			serializer.endTag(null, "ifIn");
			// ----------------------------------
			serializer.endTag(null, "fond");
		}
		serializer.endTag(null, "fonds");
		serializer.endDocument();

		// �ǵùر���
		fos.close();
	}

	/**
	 * ��xml�ļ��лָ����ݵ����ݿ�
	 * 
	 * @param context
	 * @return �Ƿ�ɹ�
	 */
	public boolean recoveryAllFromFile(Context context) {
		try {
			// 1.�ָ�goal
			recoveryGoalFromXml(context);
			// 2.�ָ�fond
			recoveryFondFromXml(context);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * ���ļ��ж�ȡ�����ݣ�����Щ����д�뵽���ݿ���
	 * @param context
	 * @throws Exception
	 */
	private void recoveryFondFromXml(Context context) throws Exception {
		deleteTable(Constant.DB_TYPE_FOND, context);
		List<Fond> fonds = readFondFromXml();
		FondDao dao = new FondDao(context);
		for(Fond f:fonds){
			dao.insert(f);
		}
	}

	/**
	 * ��fond��xml�ļ��ж�ȡ����
	 * 
	 * @throws Exception
	 */
	private List<Fond> readFondFromXml() throws Exception {
		List<Fond> fonds = null;
		Fond fond = null;

		// 1.ʵ����һ��xmlPullpaser
		XmlPullParser parser = Xml.newPullParser();
		// 2.��ȡ������
		FileInputStream fis = new FileInputStream(new File(
				Constant.BACKUP_FOND_PATH));
		// 3.����������
		parser.setInput(fis, "utf-8");
		// 4.��ȡ��ǩtype
		int eventType = parser.getEventType();
		// 5.һֱѭ���������õ��ڵ����������õ�������,ֻҪ�����ĵ������ͼ���
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if ("fonds".endsWith(parser.getName()))
					fonds = new ArrayList<Fond>();
				else if ("fond".endsWith(parser.getName())) {
					fond = new Fond();
					// ��Ϊid���Զ����ɵģ��������ﲻ��Ҫдid
					// String id = parser.getAttributeValue(0);
					// fond.setId(Integer.parseInt(id));
				} else if ("count".endsWith(parser.getName())) {
					String count = parser.nextText();
					fond.setCount(count);
				} else if ("describe".endsWith(parser.getName())) {
					String describe = parser.nextText();
					fond.setDescribe(describe);
				} else if ("event".endsWith(parser.getName())) {
					String event = parser.nextText();
					fond.setEvent(event);
				} else if ("time".endsWith(parser.getName())) {
					String time = parser.nextText();
					fond.setTime(time);
				} else if ("type".endsWith(parser.getName())) {
					String type = parser.nextText();
					fond.setType(type);
				} else if ("ifIn".endsWith(parser.getName())) {
					String ifIn = parser.nextText();
					fond.setIfIn(Integer.parseInt(ifIn));
				}
				break;
			case XmlPullParser.END_TAG:
				if ("fond".equalsIgnoreCase(parser.getName())) {
					fonds.add(fond);
					fond = null;
				}
				break;
			}
			eventType = parser.next();
		}
		return fonds;
	}

	/**
	 * ��goal��xml�ļ��ж�ȡ�����ݣ���������д�뵽���ݿ�
	 * @param context
	 * @throws Exception
	 */
	private void recoveryGoalFromXml(Context context) throws Exception {
		deleteTable(Constant.DB_TYPE_GOAL, context);
		List<Goal> goals = readGoalFromXml();
		GoalDao dao = new GoalDao(context);
		for(Goal g:goals){
			dao.insert(g);
		}
	}

	/**
	 * ��goal��xml�ļ��ж�ȡ����
	 * 
	 * @throws Exception
	 */
	private List<Goal> readGoalFromXml() throws Exception {
		List<Goal> goals = null;
		Goal goal = null;

		// 1.��ȡpullparser
		XmlPullParser parser = Xml.newPullParser();
		// ��ȡ������
		FileInputStream fis = new FileInputStream(new File(
				Constant.BACKUP_GOAL_PATH));
		// ����������
		parser.setInput(fis, "utf-8");
		// ��ȡ���Ϳ�ʼ����
		int type = parser.getEventType();
		while (type != XmlPullParser.END_DOCUMENT) {
			switch (type) {
			case XmlPullParser.START_TAG:
				if ("goals".equals(parser.getName())) {
					goals = new ArrayList<Goal>();
				} else if ("goal".equals(parser.getName())) {
					goal = new Goal();
					 String id = parser.getAttributeValue(0);
					 goal.setId(Integer.parseInt(id));
				} else if ("goalName".equals(parser.getName())) {
					String goalName = parser.nextText();
					if(null==goalName||"".equals(goalName))
						break;//�����Ŀ������û�оͲ��ûָ���
					goal.setGoalName(goalName+"");
					System.out.println("-------------"+goalName);
				} else if ("benefit".equals(parser.getName())) {
					String benefit = parser.nextText();
					goal.setBenefit(benefit);
					System.out.println("-------------"+benefit);
				} else if ("challengeLevel".equals(parser.getName())) {
					String challengeLevel = parser.nextText();
					goal.setChallengeLevel(Float.valueOf(challengeLevel));
					System.out.println("-------------"+challengeLevel);
				} else if ("clearLevel".equals(parser.getName())) {
					String clearLevel = parser.nextText();
					goal.setClearLevel(Float.valueOf(clearLevel));
					System.out.println("-------------"+clearLevel);
				} else if ("damage".equals(parser.getName())) {
					String damage = parser.nextText();
					goal.setDamage(damage+"");
					System.out.println("-------------"+damage);
				} else if ("define".equals(parser.getName())) {
					String define = parser.nextText();
					goal.setDefine(define+"");
					System.out.println("-------------"+define);
				} else if ("emergencyLevel".equals(parser.getName())) {
					String emergencyLevel = parser.nextText();
					goal.setEmergencyLevel(Float.valueOf(emergencyLevel));
					System.out.println("-------------"+emergencyLevel);
				} else if ("importanceLevel".equals(parser.getName())) {
					String importanceLevel = parser.nextText();
					goal.setImportanceLevel(Float.valueOf(importanceLevel));
					System.out.println("-------------"+importanceLevel);
				} else if ("isFinish".equals(parser.getName())) {
					String isFinish = parser.nextText();
					goal.setIsFinish(Integer.valueOf(isFinish));
					System.out.println("-------------"+isFinish);
				} else if ("publicLevel".equals(parser.getName())) {
					String publicLevel = parser.nextText();
					goal.setPublicLevel(Float.valueOf(publicLevel));
					System.out.println("-------------"+publicLevel);
				} else if ("reason".equals(parser.getName())) {
					String reason = parser.nextText();
					goal.setReason(reason+"");
					System.out.println("-------------"+reason);
				} else if ("step1Degree".equals(parser.getName())) {
					String step1Degree = parser.nextText();
					goal.setStep1Degree(Float.valueOf(step1Degree));
					System.out.println("-------------"+step1Degree);
				} else if ("step1Describe".equals(parser.getName())) {
					String step1Describe = parser.nextText();
					goal.setStep1Describe(step1Describe);
					System.out.println("-------------"+step1Describe);
				} else if ("step2Degree".equals(parser.getName())) {
					String step2Degree = parser.nextText();
					goal.setStep2Degree(Float.valueOf(step2Degree));
					System.out.println("-------------"+step2Degree);
				} else if ("step3Describe".equals(parser.getName())) {
					String step3Describe = parser.nextText();
					goal.setStep3Describe(step3Describe);
					System.out.println("-------------"+step3Describe);
				} else if ("step1LimitTime".equals(parser.getName())) {
					String step1LimitTime = parser.nextText();
					goal.setStep1LimitTime(step1LimitTime);
					System.out.println("-------------"+step1LimitTime);
				} else if ("step2LimitTime".equals(parser.getName())) {
					String step2LimitTime = parser.nextText();
					goal.setStep2LimitTime(step2LimitTime);
					System.out.println("-------------"+step2LimitTime);
				} else if ("step3LimitTime".equals(parser.getName())) {
					String step3LimitTime = parser.nextText();
					goal.setStep1LimitTime(step3LimitTime);
					System.out.println("-------------"+step3LimitTime);
				}
				break;
			case XmlPullParser.END_TAG:
				goals.add(goal);
				goal = null;
				break;
			default:
				break;
			}
			type = parser.next();
		}

		return goals;
	}

	/**
	 * ������ݱ�����������
	 * 
	 * @param context
	 * @param dbTypeGoal
	 */
	private void deleteTable(int dbType, Context context) {
		if (dbType == Constant.DB_TYPE_FOND) {
			GoalDao dao = new GoalDao(context);
			dao.deleteTable();
		} else if (dbType == Constant.DB_TYPE_GOAL) {
			FondDao dao = new FondDao(context);
			dao.deleteTable();
		}
	}
}
