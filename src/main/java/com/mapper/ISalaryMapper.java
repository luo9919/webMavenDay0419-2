package com.mapper;

import java.util.*;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.po.*;

@Service("ISalaryMapper")
public interface ISalaryMapper {
	/**保存薪资*/
    public int save(Salary sa);
    /**修改薪资*/
    public int updateByEid(Salary sa);
    /**删除薪资*/
    public int delByEid(Integer eid);
    /**查找薪资*/
    public Salary findByEid(Integer eid);

   
}
