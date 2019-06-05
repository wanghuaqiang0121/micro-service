package org.module.platform.configuration.service;

import org.module.platform.configuration.dao.ServicePackageTypeMapper;
import org.module.platform.configuration.domain.ServicePackageType;
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
public class ServicePackageTypeService {

    @Autowired
    private ServicePackageTypeMapper servicePackageTypeMapper;

    /** 
     * @author Li.ZhiHao
     * @param servicePackageType
     * @return 
     * @date 2018/3/19
     * @version 1.0
     * @description 服务包类型列表
     */
    public List<Map<String,Object>> getList(ServicePackageType servicePackageType){
        return servicePackageTypeMapper.getList(servicePackageType);
    }

    /**
     * @author Li.ZhiHao
     * @param
     * @return
     * @date 2018/3/19
     * @version 1.0
     * @description 新增服务包类型
     */
    public int insert(ServicePackageType servicePackageType){
        return servicePackageTypeMapper.insert(servicePackageType);
    }

	public Map<String, Object> getRepeat(ServicePackageType servicePackageType) {
		return servicePackageTypeMapper.getRepeat(servicePackageType);
	}

	public Map<String, Object> getOne(ServicePackageType servicePackageType) {
		return servicePackageTypeMapper.getOne(servicePackageType);
	}

	public int update(ServicePackageType servicePackageType) {
		return servicePackageTypeMapper.update(servicePackageType);
	}
}
