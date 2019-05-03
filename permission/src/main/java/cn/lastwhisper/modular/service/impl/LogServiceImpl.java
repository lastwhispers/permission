package cn.lastwhisper.modular.service.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.lastwhisper.core.util.EasyUIDataGridResult;
import cn.lastwhisper.modular.mapper.LogMapper;
import cn.lastwhisper.modular.pojo.Log;
import cn.lastwhisper.modular.service.LogService;

/**
 * 
 * @ClassName: LogServiceImpl
 * @Description: 日志管理
 * @author: 最后的轻语_dd43
 * @date: 2019年4月30日
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
@Service
public class LogServiceImpl implements LogService {
	@Autowired
	private LogMapper logMapper;

	@Override
	public boolean addLog(Log log) throws SQLException {
		return logMapper.insertLog(log) > 0 ? true : false;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public EasyUIDataGridResult findLoglistByPage(Integer page, Integer rows, Log log) {
		PageHelper.startPage(page, rows);
		List<Log> list = logMapper.selectLoglistByPage(log);
		PageInfo<Log> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Log> findLogOperateor(String q) {
		return logMapper.selectLogOperateor(q);
	}

}