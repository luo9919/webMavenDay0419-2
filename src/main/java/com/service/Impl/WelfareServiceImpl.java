package com.service.Impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.po.Welfare;
import com.service.IWelfareService;
import com.util.MapperUtil;
@Service("WelfareServiceImpl")
@Transactional
public class WelfareServiceImpl implements IWelfareService {
	@Resource(name="DaoService")
    private MapperUtil mapperService;
    
	public MapperUtil getMapperService() {
		return mapperService;
	}

	public void setMapperService(MapperUtil mapperService) {
		this.mapperService = mapperService;
	}
	
	public List<Welfare> findAll() {
		// TODO Auto-generated method stub
		return mapperService.getWelfareMapper().findAll();
	}

}
