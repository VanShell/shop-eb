package ${packageMap.controller_path};

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import ${packageMap.entity_path}.${Entity};

/**
 * ${Entity}控制器
 * 
 * @author ${author}<br>
 * ${datetime}
 *
 */
@RestController
@RequestMapping("/${entity}")
public class ${Entity}Controller extends BaseController {

	/**
	 * 查询所有<br>
	 * 返回${Entity}集合
	 */
	@RequestMapping("/list")
	public Object list() {
		return i${Entity}Service.list();
	}

	/**
	 * 分页查询<br>
	 * 返回page对象<br>
	 * list:数据列表<br>
	 * total:总数<br>
	 * pageSize:每页显示条数，默认 10<br>
	 * pageNum:当前页<br>
	 * pages:当前分页总页数<br>
	 */
	@RequestMapping("/page")
	public Object page${Entity}(Page<${Entity}> page, ${Entity} ${entity}) {
		return i${Entity}Service.page(page, ${entity});
	}
		
	/**
	 * 根据 ID 选择修改<br>
	 * 返回true或false
	 * 
	 * @param ${entity}
	 * @return
	 */
	@RequestMapping("/update")
	public Object update(${Entity} ${entity}) {
		${entity}.setUpdate_time(LocalDateTime.now());
		return i${Entity}Service.updateById(${entity});
	}

	/**
	 * 根据 ID 删除<br>
	 * 返回true或false<br>
	 * 删除默认需要权限
	 * 
	 * @param ${entity}
	 * @return
	 */
	@RequestMapping("/delete")
	public Object delete(${Entity} ${entity}) {
		${entity}.setUpdate_time(LocalDateTime.now());
		return i${Entity}Service.removeById(${entity}.getId());
	}

	/**
	 * 插入一条记录<br>
	 * 返回true或false
	 * 
	 * @param ${entity}
	 * @return
	 */
	@RequestMapping("/insert")
	public Object insert(${Entity} ${entity}) {
		${entity}.setAdd_time(LocalDateTime.now());
		return i${Entity}Service.save(${entity});
	}
	
}
