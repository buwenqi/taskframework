package gtja.taskframework.mapper;

import gtja.taskframework.entity.ExecutorInfo;

/**
 * Created by wenqi on 2018/7/27.
 */
public interface ExecutorMapper {

    public ExecutorInfo findExecutorById(long id);
}
