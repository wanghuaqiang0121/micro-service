package org.module.organization.permission.dao;

import java.util.List;
import java.util.Map;

import org.module.organization.permission.domain.OperationalPermissionMenu;
import org.service.core.dao.IBaseMapper;

public interface OperationalPermissionMenuMapper extends IBaseMapper<OperationalPermissionMenu>{
 public  List<Map<String, Object>> getPermissionMenuIsChoose(OperationalPermissionMenu operationalPermissionMenu);
}
