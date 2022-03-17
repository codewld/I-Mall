package pers.codewld.imall.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.log.model.entity.ControllerLog;
import pers.codewld.imall.log.repository.ControllerLogRepository;
import pers.codewld.imall.log.service.ControllerLogService;

import java.util.List;

/**
 * <p>
 * 接口日志 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
@Service
public class ControllerLogServiceImpl implements ControllerLogService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ControllerLogRepository controllerLogRepository;

    @Override
    public boolean add(ControllerLog controllerLog) {
        controllerLogRepository.save(controllerLog);
        return true;
    }

    @Override
    public PageVO<ControllerLog> page(Integer pageNum, Integer pageSize, String summary, String username) {
        // 搜索配置
        ControllerLog controllerLog = new ControllerLog();
        controllerLog.setSummary(summary);
        controllerLog.setUsername(username);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("summary", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<ControllerLog> example = Example.of(controllerLog, matcher);

        // 分页配置
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        // 查询
        Page<ControllerLog> page = controllerLogRepository.findAll(example, pageRequest);

        // 数据转换
        long total = page.getTotalElements();
        List<ControllerLog> list = page.getContent();
        return new PageVO<>(total, list);
    }

}
