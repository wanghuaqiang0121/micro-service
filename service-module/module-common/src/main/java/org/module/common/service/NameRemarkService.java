package org.module.common.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.module.common.dao.NameRemarkMapper;
import org.module.common.domain.NameRemark;
import org.springframework.stereotype.Service;
@Service
public class NameRemarkService {
	
	@Resource
	private NameRemarkMapper mapper;
	  public Map<String, Object> getOne(NameRemark nameRemark) {
	        return mapper.getOne(nameRemark);
	    }
	    public List<Map<String, Object>> getList(NameRemark nameRemark) {
	        return mapper.getList(nameRemark);
	    }
}
