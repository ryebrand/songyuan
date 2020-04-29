package io.renren.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.dao.DefectDao;
import io.renren.entity.DefectEntity;
import io.renren.service.DefectService;
import org.springframework.stereotype.Service;


@Service("defectService")
public class DefectServiceImpl extends ServiceImpl<DefectDao, DefectEntity> implements DefectService {


}
