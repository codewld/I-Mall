package pers.codewld.imall.log.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pers.codewld.imall.log.model.entity.Log;

/**
 * <p>
 * 日志 Repository接口
 * </p>
 *
 * @author codewld
 * @since 2022-03-17
 */
@Repository
public interface LogRepository extends MongoRepository<Log, String> {

}
