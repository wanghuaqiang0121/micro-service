package org.module.organization.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalPermissionOperation;
import org.service.core.dao.IBaseMapper;

public interface OperationalPermissionOperationMapper extends IBaseMapper<OperationalPermissionOperation>{
public   List<Map<String, Object>> getPermissionOperationIsChoose(OperationalPermissionOperation operationalPermissionOperation);
}
