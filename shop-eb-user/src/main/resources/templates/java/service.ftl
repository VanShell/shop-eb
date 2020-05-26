package ${packageMap.service_path};

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import ${packageMap.entity_path}.${Entity};

/**
 * <p>
 * ${Entity} service
 * </p>
 *
 * @author ${author}<br>
 * ${datetime}
 */
public interface I${Entity}Service extends IService<${Entity}> {

	PageInfo<${Entity}> page(Page<${Entity}> page, ${Entity} ${entity});

}
