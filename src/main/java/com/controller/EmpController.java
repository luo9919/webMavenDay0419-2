package com.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.po.Dep;
import com.po.Emp;
import com.po.PageBean;
import com.po.Welfare;
import com.util.AJAXUtils;
import com.util.ServiceUtil;


@Controller
public class EmpController {
	@Resource(name="BizService")
	private ServiceUtil bizService;
    
	public ServiceUtil getBizService() {
		return bizService;
	}
	public void setBizService(ServiceUtil bizService) {

		this.bizService = bizService;
	}



	@RequestMapping(value="save_Emp.do")
public String save(HttpServletRequest request,HttpServletResponse response,Emp emp){
	  System.out.println("save:"+emp.toString());
		String realpath=request.getRealPath("/");
		/*********文件上传begin***********/
		//获取文件上传对象
		MultipartFile multipartFile=emp.getPic();
		if(multipartFile!=null && !multipartFile.isEmpty()){
			//获取上传文件名称
			String fname=multipartFile.getOriginalFilename();
			//获取文件后缀
			if(fname.lastIndexOf(".")!=-1){
				//截取后缀
				String ext=fname.substring(fname.lastIndexOf("."));
				//限制上传文件类型
				if((ext.equalsIgnoreCase(".jpg"))||(ext.equalsIgnoreCase(".png"))){
					//改名字
					fname=new Date().getTime()+ext;
				}
			}
			//创建文件对象完成上传
			File dostFile=new File(realpath+"/uppic/"+fname);
			try {
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dostFile);
				emp.setPhoto(fname);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*********文件上传end***********/
		boolean flag=bizService.getEmpService().save(emp);
		if(flag){
			System.out.println("添加成功....");
			String jsonStr=JSONObject.toJSONString(1);
			AJAXUtils.printString(response, jsonStr);
		}else{
			String jsonStr=JSONObject.toJSONString(0);
			AJAXUtils.printString(response, jsonStr);
		}
		return null;
}
	@RequestMapping(value="update_Emp.do")
	public String update(HttpServletRequest request,HttpServletResponse response,Emp emp){
		System.out.println("update:"+emp.toString());
		String realpath=request.getRealPath("/");
		/*********文件上传begin***********/
		//获取文件上传对象
		MultipartFile multipartFile=emp.getPic();
		if(multipartFile!=null && !multipartFile.isEmpty()){
			//获取上传文件名称
			String fname=multipartFile.getOriginalFilename();
			//获取文件后缀
			if(fname.lastIndexOf(".")!=-1){
				//截取后缀
				String ext=fname.substring(fname.lastIndexOf("."));
				//限制上传文件类型
				if((ext.equalsIgnoreCase(".jpg"))||(ext.equalsIgnoreCase(".jpeg"))){
					//改名字
					fname=new Date().getTime()+ext;
				}
			}
			//创建文件对象完成上传
			File dostFile=new File(realpath+"/uppic/"+fname);
			try {
				FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), dostFile);
				emp.setPhoto(fname);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String oldPhoto=bizService.getEmpService().findById(emp.getEid()).getPhoto();
			emp.setPhoto(oldPhoto);
		}
		/*********文件上传end***********/
		boolean flag=bizService.getEmpService().update(emp);
		if(flag){
			System.out.println("修改成功....");
			String jsonStr=JSONObject.toJSONString(1);
			AJAXUtils.printString(response, jsonStr);
		}else{
			String jsonStr=JSONObject.toJSONString(0);
			AJAXUtils.printString(response, jsonStr);
		}
		return null;
	}
	@RequestMapping(value="delById_Emp.do")
	public String delById(HttpServletRequest request,HttpServletResponse response,Integer eid){
		System.out.println("delById:"+eid);
		//获取员工
		String realpath=request.getRealPath("/");
		Emp emp=bizService.getEmpService().findById(eid);
		boolean flag=bizService.getEmpService().delById(eid);
		if(flag){
			System.out.println("删除成功....");
			if(emp!=null){
				String oldPhoto=emp.getPhoto();
				System.out.println("oldPhoto"+oldPhoto);
				File dostFile=new File(realpath+"/uppic/"+oldPhoto);
				if(!oldPhoto.equals("default.jpg")&& oldPhoto!=null){
				dostFile.delete();
				}
			}
			String jsonStr=JSONObject.toJSONString(1);
			AJAXUtils.printString(response, jsonStr);
		}else{
			String jsonStr=JSONObject.toJSONString(0);
			AJAXUtils.printString(response, jsonStr);
		}
		return null;
		
	}
	@RequestMapping(value="findById_Emp.do")
	public String findById(HttpServletRequest request,HttpServletResponse response,Integer eid){
		System.out.println("findById:"+eid);
		Emp oldEmp=bizService.getEmpService().findById(eid);
		PropertyFilter propertyFilter=AJAXUtils.filterProperts("birthday","pic");
		String jsonStr=JSONObject.toJSONString(oldEmp,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AJAXUtils.printString(response, jsonStr);
		return null;
	}
	@RequestMapping(value="findPageAll_Emp.do")
	public String findPageAll(HttpServletRequest request,HttpServletResponse response,Integer page,Integer rows){
		System.out.println("findPageAll：page=="+page+"==rows==="+rows);
		Map<String,Object> map=new HashMap<String,Object>();
		PageBean pb=new PageBean();
		page=page==null || page<1 ?pb.getPage():page;
		rows=rows==null || rows<1 ?pb.getRows():rows;
		if(rows>10)rows=10;
		pb.setPage(page);
		pb.setRows(rows);
		//获取当前页面集合
		List<Emp> lsemp=bizService.getEmpService().findPageAll(pb);
		//获取总记录数
		int maxRows=bizService.getEmpService().findMaxRows();
		map.put("page", page);
		map.put("rows", lsemp);
		map.put("total",maxRows);
		PropertyFilter propertyFilter=AJAXUtils.filterProperts("birthday","pic");
		String jsonStr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AJAXUtils.printString(response, jsonStr);
		return null;
		
	}
	@RequestMapping(value="doinit_Emp.do")
	public void doinit(HttpServletRequest request,HttpServletResponse response){
		System.out.println("doinit方法........");
		Map<String,Object> map=new HashMap<String,Object>();
		List<Dep> lsdep=bizService.getDepService().findAll();
		List<Welfare> lswf=bizService.getWelfareService().findAll();
		map.put("lsdep", lsdep);
		map.put("lswf", lswf);
		PropertyFilter propertyFilter=AJAXUtils.filterProperts("birthday","pic");
		String jsonStr=JSONObject.toJSONString(map,propertyFilter,SerializerFeature.DisableCircularReferenceDetect);
		AJAXUtils.printString(response, jsonStr);
	}

}
