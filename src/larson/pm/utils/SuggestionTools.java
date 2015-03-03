package larson.pm.utils;


public class SuggestionTools {

	public SuggestionTools() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 根据目标的程度给出建议
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
			builder.append("这件事比较紧急，建议优先做！");
		else if(emergencyLevel>4&&emergencyLevel<=5)
			builder.append("这件事非常紧急，不要拖延，马上去做！");
		else if(emergencyLevel>1&&emergencyLevel<=3)
			builder.append("紧急程度一般，请参考其他因素。!");
			
		if(importanceLevel>3&&importanceLevel<=4)
			builder.append("这件事比较重要，建议优先做！");
		else if(emergencyLevel>4&&emergencyLevel<=5)
			builder.append("这件事非常重要，时刻记住做这件事");
		else if(emergencyLevel>1&&emergencyLevel<=3)
			builder.append("紧急程度一般，请参考其他因素。!");
		
		if(clearLevel<=3)
			builder.append("您对目标设定的不明确，请想好清楚再设立这个目标");
		else
			builder.append("恭喜！目标明确，可以设定！");
			
		if(publicLevel<3)
			builder.append("呃呃~你这目标太保密不利于实行的哦，把目标告诉更多的人会帮助你达成目标");
		else
			builder.append("嗯，在公开程度上你做得不错。");
			
		if(challengeLevel<=2)
			builder.append("不适合你风格啊，完全不具挑战性。");
		else if(challengeLevel<=4)
			builder.append("难度适中，不错。");
		else 
			builder.append("哇！好难，要不要调整下。");
			
		
		return builder.toString();
	}

}
