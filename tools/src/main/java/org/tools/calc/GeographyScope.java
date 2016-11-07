package org.tools.calc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <font color="red"><b>Liu.Gang.Qiang</b></font>
 * @Date 2016年10月28日
 * @Version 1.0
 * @Description 经纬度工具类
 */
public class GeographyScope {
	/**
	 * @author <font color="green"><b>Liu.Gang.Qiang</b></font>
	 * @param lat
	 * @param lon
	 * @param raidus
	 * @return 矩形边界值
	 * @date 2016年3月28日 下午4:47:52
	 * @version 1.0
	 * @description 根据经纬度和半径算出极点
	 */
	public static Map<String, Object> getAround(double lat, double lon, int raidus) {

		Map<String, Object> aroundMap = new HashMap<>();
		Double latitude = lat;
		Double longitude = lon;

		Double degree = (24901 * 1609) / 360.0;
		double raidusMile = raidus;

		Double dpmLat = 1 / degree;
		Double radiusLat = dpmLat * raidusMile;
		Double minLat = latitude - radiusLat;
		Double maxLat = latitude + radiusLat;

		Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));
		Double dpmLng = 1 / mpdLng;
		Double radiusLng = dpmLng * raidusMile;
		Double minLng = longitude - radiusLng;
		Double maxLng = longitude + radiusLng;
		aroundMap.put("minLat", minLat);
		aroundMap.put("minLng", minLng);
		aroundMap.put("maxLat", maxLat);
		aroundMap.put("maxLng", maxLng);
		return aroundMap;
	}
}
