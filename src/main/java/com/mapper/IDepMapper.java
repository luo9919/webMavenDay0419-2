package com.mapper;

import java.util.*;
import org.springframework.stereotype.Service;
import com.po.*;
@Service("IDepMapper")
public interface IDepMapper {
	/**查询部门所有*/
    public List<Dep> findAll();

   
}
