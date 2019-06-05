package org.module.platform.configuration.service;

import org.module.platform.configuration.dao.ServiceTypeMapper;
import org.module.platform.configuration.domain.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author yi.zhou
 * @Date 2018/3/20
 * @Version
 * @Description
 */
@Service
public class ServiceTypeService {

    @Autowired
    private ServiceTypeMapper serviceTypeMapper;

    /**
     * @author yi.zhou
     * @param
     * @return
     * @date 2018/3/20
     * @version 1.0
     * @description 新增服务类型
     */
    public int insert(ServiceType serviceType){
        return serviceTypeMapper.insert(serviceType);
    }


	public Map<String, Object> getOne(ServiceType serviceType) {
		return serviceTypeMapper.getOne(serviceType);
	}

	public int update(ServiceType serviceType) {
		return serviceTypeMapper.update(serviceType);
	}

	public Map<String, Object> getRepeat(ServiceType serviceType) {
		return serviceTypeMapper.getRepeat(serviceType);
	}

	public List<Map<String, Object>> getList(ServiceType serviceType) {
		return serviceTypeMapper.getList(serviceType);
	}

}