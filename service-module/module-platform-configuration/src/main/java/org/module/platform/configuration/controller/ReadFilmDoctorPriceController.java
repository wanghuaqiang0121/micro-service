package org.module.platform.configuration.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.module.platform.configuration.domain.ReadFilmDoctorPrice;
import org.module.platform.configuration.service.ReadFilmDoctorPriceService;
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
 * @Description 阅片医生价格配置
 */
@RestController
public class ReadFilmDoctorPriceController {

	@Resource
	private ReadFilmDoctorPriceService readFilmDoctorPriceService;

	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmDoctorPrice
	 * @param result
	 * @return
	 * @date 2019年2月25日
	 * @version 1.0
	 * @description 新增
	 */
	@RequiresAuthentication( level = Level.OPERATION, value = { "platform:configure:readFilmDoctorPrice:insert" })
	@RequestMapping(value = { "/read/film/doctor/price" }, method = RequestMethod.POST)
	public JsonApi insert(@Validated({ BaseEntity.Insert.class }) @RequestBody ReadFilmDoctorPrice readFilmDoctorPrice,
			BindingResult result) {
		Map<String, Object> readFilmDoctorPriceOneMap = readFilmDoctorPriceService.getRepeat(readFilmDoctorPrice);
		if (readFilmDoctorPriceOneMap!=null) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		/*新增*/ 
		readFilmDoctorPrice.setCreateTime(new Date());
		if (readFilmDoctorPriceService.insert(readFilmDoctorPrice) > 0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.FAIL);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmDoctorPrice
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 列表
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmDoctorPrice:list" })
	@RequestMapping(value = { "/read/film/doctor/prices" }, method = RequestMethod.GET)
	public JsonApi getList( @Validated({ BaseEntity.SelectAll.class })  ReadFilmDoctorPrice readFilmDoctorPrice, BindingResult result) {
		Page<?> page = PageHelper.startPage(readFilmDoctorPrice.getPage(), readFilmDoctorPrice.getPageSize());
		List<Map<String, Object>> readFilmDoctorPriceList = readFilmDoctorPriceService.getList(readFilmDoctorPrice);
		if (readFilmDoctorPriceList != null && !readFilmDoctorPriceList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), readFilmDoctorPriceList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param readFilmDoctorPrice
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 查询机构用户表
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmDoctorPrice:doctor-list" })
	@RequestMapping(value = { "/read/film/doctor/price/doctors" }, method = RequestMethod.GET)
	public JsonApi getDoctorList( @Validated({ BaseEntity.SelectAll.class })  ReadFilmDoctorPrice readFilmDoctorPrice, BindingResult result) {
		Page<?> page = PageHelper.startPage(readFilmDoctorPrice.getPage(), readFilmDoctorPrice.getPageSize());
		List<Map<String, Object>> readFilmDoctorPriceDoctorList = readFilmDoctorPriceService.getDoctorList(readFilmDoctorPrice);
		if (readFilmDoctorPriceDoctorList != null && !readFilmDoctorPriceDoctorList.isEmpty()) {
			return new JsonApi(ApiCode.OK, new MultiLine(page.getTotal(), readFilmDoctorPriceDoctorList));
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param readFilmDoctorPrice
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 详情
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmDoctorPrice:detail" })
	@RequestMapping(value = { "/read/film/doctor/price/{id}" }, method = RequestMethod.GET)
	public JsonApi getDetail(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.SelectOne.class })  ReadFilmDoctorPrice readFilmDoctorPrice, BindingResult result) {
		readFilmDoctorPrice.setId(id);
		/*数据是否存在*/
		Map<String, Object> readFilmDoctorPriceOneMap = readFilmDoctorPriceService.getOne(readFilmDoctorPrice);
		if (MapUtils.isNotEmpty(readFilmDoctorPriceOneMap)) {
			return new JsonApi(ApiCode.OK,readFilmDoctorPriceOneMap);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
	
	/**
	 * @author <font color="green"><b>Chen.Yan</b></font>
	 * @param id
	 * @param readFilmDoctorPrice
	 * @param result
	 * @return
	 * @date 2019年2月27日
	 * @version 1.0
	 * @description 修改
	 */
	@RequiresAuthentication( level=Level.OPERATION, value = { "platform:configure:readFilmDoctorPrice:update" })
	@RequestMapping(value = { "/read/film/doctor/price/{id}" }, method = RequestMethod.PUT)
	public JsonApi update(@PathVariable("id")Integer id,
			@Validated({ BaseEntity.Update.class })  @RequestBody ReadFilmDoctorPrice readFilmDoctorPrice, BindingResult result) {
		readFilmDoctorPrice.setId(id);
		/*数据是否存在*/
		Map<String, Object> readFilmDoctorPriceOneMap = readFilmDoctorPriceService.getOne(readFilmDoctorPrice);
		if (readFilmDoctorPriceOneMap==null) {
			return new JsonApi(ApiCode.NOT_FOUND);
		}
		Map<String, Object> readFilmDoctorPriceMap = readFilmDoctorPriceService.getRepeat(readFilmDoctorPrice);
		if (readFilmDoctorPriceMap!=null) {
			return new JsonApi(ApiCode.CONFLICT);
		}
		if (readFilmDoctorPriceService.update(readFilmDoctorPrice)>0) {
			return new JsonApi(ApiCode.OK);
		}
		return new JsonApi(ApiCode.NOT_FOUND);
	}
}
