package larson.pm.bean;

public class Goal {
	private int id;// ���к�
	private int isFinish;// Ŀ���Ƿ����

	private float challengeLevel;// ��ս����
	private float emergencyLevel;// ��������
	private float importanceLevel;// ��Ҫ����
	private float publicLevel;// ��������
	private float clearLevel;// ��ȷ����
	private float step1Degree;// ��һ����ɶ�
	private float step2Degree;// �ڶ�����ɶ�
	private float step3Degree;// ��������ɶ�

	private String step1LimitTime;// ��һ�����ʱ��
	private String step2LimitTime;// �ڶ������ʱ��
	private String step3LimitTime;// ���������ʱ��

	private String step1Describe;// ��һ������
	private String step2Describe;// �ڶ�������
	private String step3Describe;// ����������

	private String goalName;// Ŀ����
	private String define;// Ŀ�궨��

	public String getDefine() {
		return define;
	}

	public void setDefine(String define) {
		this.define = define;
	}

	private String reason;// Ϊʲô�趨���Ŀ��
	private String benefit;// ���Ŀ������ĺô�
	private String damage;// �������Ŀ������ĺ��

	public Goal() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIsFinish() {
		return isFinish;
	}

	public void setIsFinish(int isFinish) {
		this.isFinish = isFinish;
	}

	public float getChallengeLevel() {
		return challengeLevel;
	}

	public void setChallengeLevel(float challengeLevel) {
		this.challengeLevel = challengeLevel;
	}

	public float getEmergencyLevel() {
		return emergencyLevel;
	}

	public void setEmergencyLevel(float emergencyLevel) {
		this.emergencyLevel = emergencyLevel;
	}

	public float getImportanceLevel() {
		return importanceLevel;
	}

	public void setImportanceLevel(float importanceLevel) {
		this.importanceLevel = importanceLevel;
	}

	public float getPublicLevel() {
		return publicLevel;
	}

	public void setPublicLevel(float publicLevel) {
		this.publicLevel = publicLevel;
	}

	public float getClearLevel() {
		return clearLevel;
	}

	public void setClearLevel(float clearLevel) {
		this.clearLevel = clearLevel;
	}

	public float getStep1Degree() {
		return step1Degree;
	}

	public void setStep1Degree(float step1Degree) {
		this.step1Degree = step1Degree;
	}

	public float getStep2Degree() {
		return step2Degree;
	}

	public void setStep2Degree(float step2Degree) {
		this.step2Degree = step2Degree;
	}

	public float getStep3Degree() {
		return step3Degree;
	}

	public void setStep3Degree(float step3Degree) {
		this.step3Degree = step3Degree;
	}

	public String getStep1LimitTime() {
		return step1LimitTime;
	}

	public void setStep1LimitTime(String step1LimitTime) {
		this.step1LimitTime = step1LimitTime;
	}

	public String getStep2LimitTime() {
		return step2LimitTime;
	}

	public void setStep2LimitTime(String step2LimitTime) {
		this.step2LimitTime = step2LimitTime;
	}

	public String getStep3LimitTime() {
		return step3LimitTime;
	}

	public void setStep3LimitTime(String step3LimitTime) {
		this.step3LimitTime = step3LimitTime;
	}

	public String getStep1Describe() {
		return step1Describe;
	}

	public void setStep1Describe(String step1Describe) {
		this.step1Describe = step1Describe;
	}

	public String getStep2Describe() {
		return step2Describe;
	}

	public void setStep2Describe(String step2Describe) {
		this.step2Describe = step2Describe;
	}

	public String getStep3Describe() {
		return step3Describe;
	}

	public void setStep3Describe(String step3Describe) {
		this.step3Describe = step3Describe;
	}

	public String getGoalName() {
		return goalName;
	}

	public void setGoalName(String goalName) {
		this.goalName = goalName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getBenefit() {
		return benefit;
	}

	public void setBenefit(String benefit) {
		this.benefit = benefit;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
	}
	
	/**
	 * ����̶�ƽ��ֵ
	 * @return
	 */
	public float getGoalAvgLevel(){
		return (challengeLevel+emergencyLevel+importanceLevel+publicLevel+clearLevel)/5;// ��ս����
	}

}
