package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.ReadFilmRechargeRecord;
import org.module.platform.configuration.message.Prompt;
import org.module.platform.configuration.service.ReadFilmRechargeRecordService;
import org.service.core.api.ApiCode;
import org.service.core.api.JsonApi;
import org.service.core.api.MultiLine;
import org.service.core.auth.control.RequiresAuthentication;
import org.service.core.auth.level.Level;
import org.service.core.entity.BaseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @author <font color="red"><b>Chen.Yan</b></font>
 * @Date 2019年2月25日
 * @Version
 * @Description 远程阅片充值记录
 */
@RestController
public class ReadFilmRechargeRecordController {

	@Resource
	private ReadFilmRechargeRecordService readFilmRechargeRecordService;

	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmRechargeRecord
	 * @param result
	 * @return
	 * @date 2019年2月25日
	 * @version 1.0
	 * @description 新增
	 */
	@RequiresAuthentication( level = Level.OPERATION, value = { "platform:configure:readFilmRechargeRecord:insert" })
	@RequestMapping(value = { "/read/film/recharge/record" }, method = RequestMethod.POST)
	public JsonApi insert(@Validated({ BaseEntity.Insert.class }) @RequestBody ReadFilmRechargeRecord readFilmRechargeRecord,
			BindingResult result) {
		/*数据是否存重复*/
		Map<String, Object> readFilmRechargeRecordMap = readFilmRechargeRecordService.getRepeat(readFilmRechargeRecord);
		if (readFilmRechargeRecordMap!=null) {
			return new JsonApi(ApiCode.CONFLICT).setMsg(Prompt.bundle("invoice.number.is.exists"));
		}
		
		/*新增*/
		readFilmRechargeRecord.setCreateTime(new Date());
		/*查询充值记录列表*/
		List<Map<String, Object>> readFilmRechargeRecordList = readFilmRechargeRecordService.getList(readFilmRechargeRecord);
		if (readFilmRechargeRecordList!=null && !readFilmRechargeRecordList.isEmpty()) {
			/*余额为空的时候，用余额等于充值金额*/
			if (readFilmRechargeRecordList.get(0).get("remainingSum")==null) {
				readFilmRechargeRecord.setRemainingSum(readFilmRechargeRecord.getRechargeMoney());
				
			}else {
				/*余额不为空的时候，用余额等于余额加上充值金额*/
				Double remainingSum=(Double) readFilmRechargeRecordList.get(0).get("remainingSum");
				readFilmRechargeRecord.setRemainingSum(remainingSum + readFilmRechargeRecord.getRechargeMoney());
			}
			
		}else {
			/*新机构充值*/
			readFilmRechargeRecord.setRemainingSum(readFilmRechargeRecord.getRechargeMoney());
		}
		
		if (readFilmRechargeRecordService.insert(readFilmRechargeRecord) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmRechargeRecord
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 列表
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmRechargeRecord:list" })
	@RequestMapping(value = { "/read/film/recharge/records" }, method = RequestMethod.GET)
	public JsonApi getList( @Validated({ BaseEntity.SelectAll.class })  ReadFilmRechargeRecord readFilmRechargeRecord, BindingResult result) {
		Page<?> page = PageHelper.startPage(readFilmRechargeRecord.getPage(), readFilmRechargeRecord.getPageSize());
		List<Map<String, Object>> readFilmRechargeRecordList = readFilmRechargeRecordService.getList(readFilmRechargeRecord);
		if (readFilmRechargeRecordList != null && !readFilmRechargeRecordList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), readFilmRechargeRecordList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmRechargeRecord
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 查询机构表
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmRechargeRecord:oganization-list" })
	@RequestMapping(value = { "/read/film/recharge/record/organizations" }, method = RequestMethod.GET)
	public JsonApi getDoctorList( @Validated({ BaseEntity.SelectAll.class })  ReadFilmRechargeRecord readFilmRechargeRecord, BindingResult result) {
		Page<?> page = PageHelper.startPage(readFilmRechargeRecord.getPage(), readFilmRechargeRecord.getPageSize());
		List<Map<String, Object>> readFilmRechargeRecordOrganizationList = readFilmRechargeRecordService.getOrganizationList(readFilmRechargeRecord);
		if (readFilmRechargeRecordOrganizationList!= null && !readFilmRechargeRecordOrganizationList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), readFilmRechargeRecordOrganizationList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param readFilmRechargeRecord
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 详情
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmRechargeRecord:detail" })
	@RequestMapping(value = { "/read/film/recharge/record/{id}" }, method = RequestMethod.GET)
	public JsonApi getDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  ReadFilmRechargeRecord readFilmRechargeRecord, BindingResult result) {
		readFilmRechargeRecord.setId(id);
		/*数据是否存在*/
		Map<String, Object> readFilmRechargeRecordOneMap = readFilmRechargeRecordService.getOne(readFilmRechargeRecord);
		if (MapUtils.isNotEmpty(readFilmRechargeRecordOneMap)) {
			return new JsonApi(ApiCode.OK,readFilmRechargeRecordOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param readFilmRechargeRecord
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 修改
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmRechargeRecord:update" })
	@RequestMapping(value = { "/read/film/recharge/record/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })  @RequestBody ReadFilmRechargeRecord readFilmRechargeRecord, BindingResult result) {
		readFilmRechargeRecord.setId(id);
		/*数据是否存在*/
		Map<String, Object> readFilmRechargeRecordOneMap = readFilmRechargeRecordService.getOne(readFilmRechargeRecord);
		if (readFilmRechargeRecordOneMap==null) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		if (readFilmRechargeRecordService.update(readFilmRechargeRecord)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
