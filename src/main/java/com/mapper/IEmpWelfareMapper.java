package com.mapper;

import java.util.*;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.po.*;

@Service("IEmpWelfareMapper")
public interface IEmpWelfareMapper {
	/**保存员工福利*/
    public int save(EmpWelfare ewf);
    /**根据员工编号删除福利数据*/
    public int delByEid(Integer eid);
    /**根据员工编号查询福利数据*/
    public List<Welfare> findByEid(Integer eid);
   
}
