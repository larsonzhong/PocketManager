package larson.pm.utils;


public class SuggestionTools {

	public SuggestionTools() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ����Ŀ��ĳ̶ȸ�������
	 * @param emergencyLevel
	 * @param importanceLevel
	 * @param publicLevel
	 * @param challengeLevel
	 * @param clearLevel
	 * @return
	 */
	public static String makeSuggesstion(float emergencyLevel,
			float importanceLevel, float publicLevel, float challengeLevel,
			float clearLevel) {
		// TODO Auto-generated method stub
		
		
		StringBuilder builder = new StringBuilder();
		if(emergencyLevel>3&&emergencyLevel<=4)
			builder.append("����±ȽϽ�����������������");
		else if(emergencyLevel>4&&emergencyLevel<=5)
			builder.append("����·ǳ���������Ҫ���ӣ�����ȥ����");
		else if(emergencyLevel>1&&emergencyLevel<=3)
			builder.append("�����̶�һ�㣬��ο��������ء�!");
			
		if(importanceLevel>3&&importanceLevel<=4)
			builder.append("����±Ƚ���Ҫ��������������");
		else if(emergencyLevel>4&&emergencyLevel<=5)
			builder.append("����·ǳ���Ҫ��ʱ�̼�ס�������");
		else if(emergencyLevel>1&&emergencyLevel<=3)
			builder.append("�����̶�һ�㣬��ο��������ء�!");
		
		if(clearLevel<=3)
			builder.append("����Ŀ���趨�Ĳ���ȷ�������������������Ŀ��");
		else
			builder.append("��ϲ��Ŀ����ȷ�������趨��");
			
		if(publicLevel<3)
			builder.append("����~����Ŀ��̫���ܲ�����ʵ�е�Ŷ����Ŀ����߸�����˻��������Ŀ��");
		else
			builder.append("�ţ��ڹ����̶��������ò���");
			
		if(challengeLevel<=2)
			builder.append("���ʺ����񰡣���ȫ������ս�ԡ�");
		else if(challengeLevel<=4)
			builder.append("�Ѷ����У�����");
		else 
			builder.append("�ۣ����ѣ�Ҫ��Ҫ�����¡�");
			
		
		return builder.toString();
	}

}
