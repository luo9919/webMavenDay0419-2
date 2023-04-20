package com.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Dep;
import com.service.IDepService;
import com.util.MapperUtil;
@Service("DepServiceImpl")
@Transactional
public class DepServiceImpl implements IDepService {
	@Resource(name="DaoService")
    private MapperUtil mapperService;
    
	public MapperUtil getMapperService() {
		return mapperService;
	}

	public void setMapperService(MapperUtil mapperService) {
		this.mapperService = mapperService;
	}

	
	public List<Dep> findAll() {
		
		return mapperService.getDepMapper().findAll();
	}

}
