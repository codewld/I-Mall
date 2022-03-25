package pers.codewld.imall.chat.server.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pers.codewld.imall.chat.server.model.entity.Msg;
import pers.codewld.imall.chat.server.service.MsgService;

/**
 * <p>
 * 信息 服务实现类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    MongoTemplate mongoTemplate;

    final String UNREAD_MSG = "unreadMsg";

    @Override
    public void addUnreadMsg(Msg msg) {
        mongoTemplate.insert(msg, UNREAD_MSG);
    }

    @Override
    public Long countUnreadMsg(String recipient) {
        Criteria criteria = Criteria.where("recipient").is(recipient);
        // 创建查询对象，然后将条件对象添加到其中，然后根据指定字段进行排序
        Query query = new Query(criteria);
        return mongoTemplate.count(query, UNREAD_MSG);
    }
}
