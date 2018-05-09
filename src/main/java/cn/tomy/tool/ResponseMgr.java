package cn.tomy.tool;


import cn.tomy.config.Constant;
import cn.tomy.domain.CommonResult;

public class ResponseMgr {
	
	/**
	 * �ɹ����󲻴�����
	 * @return
	 */
	public static String success() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_SUCCESS, "success");
		return commonResult.general();
	}
	
	/**
	 * �ɹ��������������
	 * @param data
	 * @return
	 */
	public static String successWithData(Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_SUCCESS_DATA, data, "success");
		return commonResult.general();
	}
	
	/**
	 * �������쳣��������
	 * @return
	 */
	public static String err() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION, "���Ժ�����");
		return commonResult.general();
	}

	/**
	 * �������쳣������
	 * @param data
	 * @return
	 */
	public static String errWhitData(Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION_DATA, data, "���Ժ�����");
		return commonResult.general();
	}
	
	/**
	 * �������쳣�����ݺ���Ϣ
	 * @param data
	 * @return
	 */
	public static String errWhitData(String msg, Object data) {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_EXCEPTION_DATA, data, msg);
		return commonResult.general();
	}
	
	/**
	 * �û�δ��¼
	 * @return
	 */
	public static String noLogin() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOLOGIN, "�û�δ��¼");
		return commonResult.general();
	}
	
	/**
	 * ���Ϊ��
	 * @return
	 */
	public static String noExist() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOEXIST, "���Ϊ��");
		return commonResult.general();
	}
	
	/**
	 * �޲���Ȩ��
	 * @return
	 */
	public static String noAuth() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_NOAUTH, "�ܾ���Ȩ");
		return commonResult.general();
	}
	
	/**
	 * ��¼����
	 * @return
	 */
	public static String loginExpire() {
		CommonResult commonResult = new CommonResult(Constant.RESCODE_LOGINEXPIRE, "��¼����");
		return commonResult.general();
	}
}
