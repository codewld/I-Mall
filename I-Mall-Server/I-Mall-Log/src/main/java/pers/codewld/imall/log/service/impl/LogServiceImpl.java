package pers.codewld.imall.log.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pers.codewld.imall.common.model.vo.PageVO;
import pers.codewld.imall.log.model.entity.Log;
import pers.codewld.imall.log.repository.LogRepository;
import pers.codewld.imall.log.service.LogService;

import java.util.List;

/**
 * <p>
 * 日志 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-16
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogRepository logRepository;

    @Override
    public boolean add(Log log) {
        logRepository.save(log);
        return true;
    }

    @Override
    public PageVO<Log> page(Integer pageNum, Integer pageSize, String summary, String username, Boolean status) {
        // 搜索配置
        Log log = new Log();
        log.setSummary(summary);
        log.setUsername(username);
        log.setStatus(status);
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withMatcher("summary", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<Log> example = Example.of(log, matcher);

        // 分页配置
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize);

        // 查询
        Page<Log> page = logRepository.findAll(example, pageRequest);

        // 数据转换
        long total = page.getTotalElements();
        List<Log> list = page.getContent();
        return new PageVO<>(total, list);
    }

}
