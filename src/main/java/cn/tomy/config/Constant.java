package cn.tomy.config;

/**
 * ȫ�����ó���
 * @author XY
 *
 */
public class Constant {
	
	/**
	 * �������󷵻���
	 */
	/**
	 * �ɹ�
	 */
	public static final int RESCODE_SUCCESS = 1000;				//�ɹ�
	public static final int RESCODE_SUCCESS_DATA = 1001;		//�ɹ�(�з�������)
	public static final int RESCODE_NOEXIST = 1004;				//��ѯ���Ϊ��
	/**
	 * ʧ��
	 */
	public static final int RESCODE_EXCEPTION = 1002;			//�����׳��쳣
	public static final int RESCODE_EXCEPTION_DATA = 1008;		//�쳣������
	public static final int RESCODE_NOLOGIN = 1003;				//δ��½״̬
	public static final int RESCODE_NOAUTH = 1005;				//�޲���Ȩ��
	public static final int RESCODE_LOGINEXPIRE = 1009;			//��¼����
	/**
	 * token����ʱû��ˢ���Զ�token���ƣ�ͨ�����µ�¼��ȡ��
	 */
	public static final int RESCODE_REFTOKEN_MSG = 1006;		//ˢ��TOKEN(�з�������)
	public static final int RESCODE_REFTOKEN = 1007;			//ˢ��TOKEN
	
	public static final int JWT_ERRCODE_EXPIRE = 4001;			//Token����
	public static final int JWT_ERRCODE_FAIL = 4002;			//��֤��ͨ��

	/**
	 * jwt
	 */
	public static final String JWT_ID = "5236A";										//jwtid
	public static final String JWT_SECERT = "7786df7fc3a34e26a61c034d5ec8245d";			//�ܳ�
	public static final long JWT_TTL = 24*60*60 * 1000;									//token��Чʱ��
	
}
