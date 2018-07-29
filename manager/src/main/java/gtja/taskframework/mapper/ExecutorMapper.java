package gtja.taskframework.mapper;

import gtja.taskframework.entity.ExecutorInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wenqi on 2018/7/27.
 */
@Component
public interface ExecutorMapper {

    @Select("select * from self_executor where id=#{id}")
    @Results({
            @Result(property = "ipAddress", column = "ip_address")
    })
    ExecutorInfo findExecutorById(long id);

    @Select("select * from self_executor")
    @Results({
            @Result(property = "ipAddress", column = "ip_address")
    })
    List<ExecutorInfo> selectAll();

    @Insert("insert into self_executor(id, ip_address, port, username, password, status, description)" +
            "values(#{id}, #{ipAddress}, #{port}, #{username}, #{password}, #{status}, #{description})")
    @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
    void insert(ExecutorInfo executorInfo);

    @Update("update self_executor set ip_address=#{ipAddress}, port=#{port}, username=#{username}," +
            "password=#{password}, status=#{status}, description=#{description} where id=#{id}")
    void update(ExecutorInfo executorInfo);

    @Delete("delete from self_executor where id=#{id}")
    void delete(long id);
}
