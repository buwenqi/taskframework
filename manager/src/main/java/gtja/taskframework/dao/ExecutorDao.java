package gtja.taskframework.dao;

import gtja.taskframework.entity.ExecutorInfo;
import gtja.taskframework.mapper.ExecutorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ExecutorDao {

    @Autowired
    ExecutorMapper executorMapper;

    public List<ExecutorInfo> listAllExecutor() {
        return executorMapper.selectAll();
    }

    public ExecutorInfo findExecutorById(long id) {
        return executorMapper.findExecutorById(id);
    }

    public ExecutorInfo findExecutorByIpAndPort(String ip, Integer port) {
        return executorMapper.findExecutorByIpAndPort(ip, port);
    }

    public void saveExecutor(ExecutorInfo executorInfo) {
        executorMapper.insert(executorInfo);
    }

    public void updateExecutor(ExecutorInfo executorInfo) {
        executorMapper.update(executorInfo);
    }

    public void deleteExecutor(long id) {
        executorMapper.delete(id);
    }

    public List<ExecutorInfo> findOnlineExecutor() {return executorMapper.selectOnlineExecutor(); }
}
