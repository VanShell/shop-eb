package ${packageMap.service_impl_path};

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${packageMap.dao_path}.${Entity}Dao;
import ${packageMap.entity_path}.${Entity};
import ${packageMap.service_path}.I${Entity}Service;

/**
 * ${Entity}Service<br>
 * 
 * @author ${author}<br>
 * ${datetime}
 *
 */
@Service
public class ${Entity}ServiceImpl extends ServiceImpl<${Entity}Dao, ${Entity}> implements I${Entity}Service {

	@Override
	public PageInfo<${Entity}> page(Page<${Entity}> page, ${Entity} ${entity}) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<${Entity}> list = super.list(new QueryWrapper<${Entity}>(${entity}));
		PageInfo<${Entity}> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
}
