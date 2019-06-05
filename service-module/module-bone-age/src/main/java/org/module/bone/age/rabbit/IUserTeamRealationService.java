package org.module.bone.age.rabbit;

import java.util.Map;

import org.service.core.api.JsonApi;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "${service.task.delay}")
public interface IUserTeamRealationService {

	/** 
	* @author <font color="green"><b>Gong.YiYang</b></font>
	* @param param
	* @return 
	* @date 2018年7月4日
	* @version 1.0
	* @description 更新用户团队关系
	*/
	@RequestMapping(value = { "/user/team/relation" }, method = RequestMethod.POST)
	JsonApi userRabbit(@RequestBody Map<String, Object> param);
}
