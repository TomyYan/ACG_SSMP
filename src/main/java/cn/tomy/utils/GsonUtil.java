package cn.tomy.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;

/**
 * Gson������
 * @author XY
 *
 */
public class GsonUtil {
	private static Gson gson = new Gson();
	
	/**
	 * �������л�Ϊjson�ַ���
	 * @param object
	 * @return
	 */
	public static <T> String objectToJsonStr(T object) {
		return gson.toJson(object);
	}
	
	/**
	 * json�ַ�������ΪList����
	 * @param //args
	 * @return
	 */
	public static <T> List<T> jsonToList(String jsonStr, Type type) {
		return gson.fromJson(jsonStr, type);
	}
	
	/**
	 * json�ַ�������Ϊ����
	 * @param jsonStr
	 * @param classOfT
	 * @return
	 */
	public static <T> T jsonStrToObject(String jsonStr, Class<T> classOfT) {
		return gson.fromJson(jsonStr, classOfT);
	}
	
	public static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			
			@Override
			public Type getRawType() {
				return raw;
			}
			
			@Override
			public Type getOwnerType() {
				return null;
			}
			
			@Override
			public Type[] getActualTypeArguments() {
				return args;
			}
		};
	}
	
}
