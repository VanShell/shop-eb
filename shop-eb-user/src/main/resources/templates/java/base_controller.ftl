package ${packageMap.package_path}.controller;

import org.springframework.beans.factory.annotation.Autowired;

<#list list as listitem>
import ${packageMap.service_path}.I${listitem.Entity}Service;
</#list>

/**
 * <p>
 * 所有controller继承这个控制器方便调用
 * </p>
 *
 */
public class BaseController extends BaseUserController {
	
<#list list as listitem>
	@Autowired
	protected I${listitem.Entity}Service i${listitem.Entity}Service;
	
</#list>
}
