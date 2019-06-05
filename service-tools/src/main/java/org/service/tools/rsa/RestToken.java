package org.service.tools.rsa;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

public class RestToken {

	private static RestToken instance = null;

	private RestToken() {
	}

	public static RestToken getInstance() {
		if (instance == null) {
			instance = new RestToken();
		}
		return instance;
	}

	public Map<String, Object> getPermissionToken(String username, String password, String publicKey, String url,
			int acquisitive) {
		Map<String, Object> permissionMap = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		long time = System.currentTimeMillis();
		permissionMap.put("account", username);
		permissionMap.put("password", password);
		permissionMap.put("createDate", time);
		permissionMap.put("acquisitive", acquisitive <= 0 ? 1 : acquisitive);
		
		resultMap.put("token", RSAUtils.RSAEncode(RSAUtils.getPublicKey(publicKey), JSON.toJSONString(permissionMap)));
		resultMap.put("acquisitive", acquisitive);
		resultMap.put("createDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time)));
		resultMap.put("url", url);
		return resultMap;
	}
	
	public String getPermissionTokenString(String username, String password, String publicKey, int acquisitive) {
		Map<String, Object> permissionMap = new HashMap<>();
		long time = System.currentTimeMillis();
		permissionMap.put("account", username);
		permissionMap.put("password", password);
		permissionMap.put("createDate", time);
		permissionMap.put("acquisitive", acquisitive <= 0 ? 1 : acquisitive);
		return RSAUtils.RSAEncode(RSAUtils.getPublicKey(publicKey), JSON.toJSONString(permissionMap));
	}
	
}
