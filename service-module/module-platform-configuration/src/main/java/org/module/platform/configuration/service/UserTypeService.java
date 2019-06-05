package org.module.platform.configuration.service;

import org.module.platform.configuration.dao.UserTypeMapper;
import org.module.platform.configuration.domain.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Li.ZhiHao
 * @Date 2018/3/19
 * @Version
 * @Description
 */
@Service
public class UserTypeService {

    @Autowired
    private UserTypeMapper userTypeMapper;

    /**
     * @author Li.ZhiHao
     * @param userType
     * @return
     * @date 2018/3/19
     * @version 1.0
     * @description 检查数据是否存在
     */
    public Map<String,Object> getRepeat(UserType userType){
        return userTypeMapper.getRepeat(userType);
    }


    /**
     * @author Li.ZhiHao
     * @param userType
     * @return int
     * @date 2018/3/19
     * @version 1.0
     * @description 新增人群类型
     */
    public int insert(UserType userType) {
        return userTypeMapper.insert(userType);
    }

    /**
     * @author Li.ZhiHao
     * @param userType
     * @return
     * @date 2018/3/19
     * @version 1.0
     * @description 启用/禁用人群类型
     */
    public int update(UserType userType){
        return userTypeMapper.update(userType);
    }

    /**
     * @author Li.ZhiHao
     * @param userType
     * @return
     * @date 2018/3/19
     * @version 1.0
     * @description 人群记录详情
     */
    public Map<String,Object> getOne(UserType userType){
        return userTypeMapper.getOne(userType);
    }

	public List<Map<String, Object>> getList(UserType userType) {
		return userTypeMapper.getList(userType);
	}


	public int delete(UserType userType) {
		return userTypeMapper.delete(userType);
	}
}
