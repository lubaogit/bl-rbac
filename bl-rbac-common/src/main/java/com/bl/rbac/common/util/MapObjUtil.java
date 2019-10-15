package com.bl.rbac.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author bl
 *
 */
public class MapObjUtil {

	/**
	 * 实体对象转成Map
	 *
	 * @param obj 实体对象
	 * @return
	 */
	public static Map<String, Object> object2Map(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (obj == null) {
			return map;
		}
		Class<? extends Object> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (Field field : fields) {
				field.setAccessible(true);
				if (field.get(obj) != null && !"serialVersionUID".equals(field.getName())) {
					map.put(field.getName(), field.get(obj).toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
