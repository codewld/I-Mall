package pers.codewld.imall.chat.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import pers.codewld.imall.chat.model.entity.User;
import pers.codewld.imall.chat.model.message.queue.MsgMsg;

import java.util.List;

/**
 * <p>
 * 信息 Repository类
 * </p>
 *
 * @author codewld
 * @since 2022-03-25
 */
@Component
public class MsgRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    final String UNREAD_MSG = "unreadMsg";

    /**
     * 添加未读消息
     */
    public void addUnreadMsg(MsgMsg msgMsg) {
        mongoTemplate.insert(msgMsg, UNREAD_MSG);
    }

    /**
     * 查询用户的未读消息
     */
    public List<MsgMsg> listUnreadMsg(User user) {
        Criteria criteria = Criteria.where("recipient").is(user);
        Query query = new Query(criteria);
        return mongoTemplate.find(query, MsgMsg.class, UNREAD_MSG);
    }

    /**
     * 查询用户的未读消息数
     */
    public Long countUnreadMsg(User user) {
        Criteria criteria = Criteria.where("recipient").is(user);
        Query query = new Query(criteria);
        return mongoTemplate.count(query, UNREAD_MSG);
    }
}
