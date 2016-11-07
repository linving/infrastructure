package org.main.result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.main.message.SystemMessage;

public class ResultMap {
	private final static String total = "total";
	private final static String rows = "rows";

	/**
	 * 采用内定消息 有数据实体 用于单条
	 * 
	 * @param code
	 * @param body
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Map<String, Object> body) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

	/**
	 * 采用自定义消息 有数据实体 用于单条
	 * 
	 * @param code
	 * @param body
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Map<String, Object> body, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * 采用内定消息 有数据实体 用于多条
	 * 
	 * @param code
	 * @param total
	 * @param data
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, List<Map<String, Object>> dataList) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, dataList);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

	/**
	 * 采用自定义消息 有数据实体 用于多条
	 * 
	 * @param code
	 * @param data
	 * @param total
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, Integer total, List<Map<String, Object>> dataList,
			String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> body = new HashMap<String, Object>();
		body.put(ResultMap.total, total);
		body.put(ResultMap.rows, dataList);
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.date.key"), body);
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * 采用自定义消息 无数据实体 用于无数据返回
	 * 
	 * @param code
	 * @param message
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code, String message) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.message.key"), message);
		return map;
	}

	/**
	 * 采用内定消息 无数据实体 用于无数据返回
	 * 
	 * @param code
	 * @return {@link Map}
	 */
	public static Map<String, Object> convertMap(ResultCode code) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SystemMessage.bundle("result.code.key"), code.getValue());
		map.put(SystemMessage.bundle("result.message.key"), code.getMessage());
		return map;
	}

}