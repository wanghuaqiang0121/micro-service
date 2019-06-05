package org.module.organization.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalRolePermission;
import org.service.core.dao.IBaseMapper;

public interface OperationalRolePermissionMapper extends IBaseMapper<OperationalRolePermission>{
public  List<Map<String, Object>> getPermissionOperationIsChoose(OperationalRolePermission operationalRolePermission);
}
