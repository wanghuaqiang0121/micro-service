package org.module.common.service;

import java.util.List;
import java.util.Map;

import org.module.common.dao.CodeTableMapper;
import org.module.common.domain.CodeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodeTableService {

	@Autowired
	private CodeTableMapper codeTableMapper;

	public Map<String, Object> getOne( CodeTable codeTable) {
		return codeTableMapper.getOne(codeTable);
	}

	public List<Map<String, Object>> getList(CodeTable codeTable) {
		return codeTableMapper.getList(codeTable);
	}
}
