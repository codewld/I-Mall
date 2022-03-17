package pers.codewld.imall.log.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pers.codewld.imall.log.model.entity.ControllerLog;

/**
 * <p>
 * 接口日志 Repository 接口
 * </p>
 *
 * @author codewld
 * @since 2022-03-17
 */
@Repository
public interface ControllerLogRepository extends MongoRepository<ControllerLog, String> {

}
