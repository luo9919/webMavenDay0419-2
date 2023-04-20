<%@ page contentType="text/html; charset=utf-8" language="java" import="java.util.*" errorPage="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<!-- 引入easyui -->
<link rel="stylesheet" href="easyui/themes/default/easyui.css" type="text/css"></link>
<link rel="stylesheet" href="easyui/themes/icon.css" type="text/css"></link>
<script type="text/javascript" src="easyui/jquery-1.9.1.js"></script>
<script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
/******************** 页面加载原始数据************** */
	$(function(){
		//基本数据获取
		$.getJSON("doinit_Emp.do",function(map){
			var lsdep = map.lsdep;
			var lswf  = map.lswf;
			/*福利复选框 */
			for(var i=0;i<lswf.length;i++){
				var wf=lswf[i];
				$("#wf").append("<input type='checkbox' name='wids' value='"+wf.wid+"'/>"+wf.wname);
				
			}
			//部门下拉列表 
			$('#cc').combobox({     
				data:lsdep,    
			    valueField:'depid',    
			    textField:'depname',
			    value:1,
			    panelHeight:172
			});  
		}); 
	});
/******************** 页面加载原始数据end************** */
 
 /******************** 修改************** */
 function findById(eid){
	 $.getJSON('findById_Emp.do?eid='+eid,function(oldEmp){
		 //删除复选框所有选中
		 $(":checkbox[name='wids']").each(function(){
			$(this).prop("checked",false); 
		 });
		 //写入old对象数据
		 $('#ffemp').form('load',{
				eid:oldEmp.eid,
				ename:oldEmp.ename,
				sex:oldEmp.sex,
				address:oldEmp.address,
				birthday:oldEmp.sdate,
				depid:oldEmp.depid,
				emoney:oldEmp.emoney
			});
 
		 //处理照片
		 $("#imgg").attr('src','uppic/'+oldEmp.photo);
		 //设置复选框选中old福利
		 var wids = oldEmp.wids;
		 
		 $(":checkbox[name='wids']").each(function(){
			 for(var i=0;i<wids.length;i++){
				 if($(this).val()==wids[i]){
					 $(this).prop("checked",true); 
				 } 
			 } 
			 });
	 });
 }


 /********************end************** */
/******************** 保存与修改************** */
 $(function(){
	 $("#btsave").click(function(){
		 $.messager.progress();	// 显示进度条
		 $('#ffemp').form('submit', {
		 	url:'save_Emp.do',
		 	onSubmit: function(){
		 		var isValid = $(this).form('validate');
		 		if (!isValid){
		 			$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
		 		}
		 		return isValid;	// 返回false终止表单提交
		 	},
		 	//alert()
		 	success: function(code){
		 		//alert('code =  '+code);
		 		if(code=="1"){
		 			$.messager.alert('提示','添加成功');  
		 			$('#dg').datagrid('reload');   // 重新载入当前数据
		 		}else{
		 			//alert('code1=  '+data);
		 			alert('code2=  '+code);
		 			$.messager.alert('警告','添加失败');  
		 		}
		 		$.messager.progress('close');	// 如果提交成功则隐藏进度条
		 		
		 		
		 	}
		 });
	 });
 }) ;
 //////////////////修改////////////
 $(function(){
	 $("#btupdate").click(function(){
		 $.messager.progress();	// 显示进度条
		 $('#ffemp').form('submit', {
		 	url:'update_Emp.do',
		 	onSubmit: function(){
		 		var isValid = $(this).form('validate');
		 		if (!isValid){
		 			$.messager.progress('close');	// 如果表单是无效的则隐藏进度条
		 		}
		 		return isValid;	// 返回false终止表单提交
		 	},
		 	//alert()
		 	success: function(code){
		 		//alert('code =  '+code);
		 		if(code=="1"){
		 			$.messager.alert('提示','修改成功');  
		 			$('#dg').datagrid('reload');   // 重新载入当前数据
		 		}else{
		 			//alert('code1=  '+data);
		 			alert('code2=  '+code);
		 			$.messager.alert('警告','修改失败');  
		 		}
		 		$.messager.progress('close');	// 如果提交成功则隐藏进度条
		 		 
		 	}
		 });
	 });
 }) ;
 
 /******************** 保存与修改end************** */
 /******************** 删除 查找************** */
 function dodelById(eid){
	 
	 $.messager.confirm('确认','您确认想要删除记录吗？',function(r){    
	     if (r){    
	         alert('确认删除');  
	         $.get('delById_Emp.do?eid='+eid,function(code){
	        	 if(code=="1"){
			 			$.messager.alert('提示','删除成功');  
			 			$('#dg').datagrid('reload');   // 重新载入当前数据
			 		}else{
			 			 
			 			$.messager.alert('警告','删除失败');  
			 		}
	         });
	     }    
	 });
	 
 }
 
 
 /******************** 删除 查找end************** */

 /******************** 员工列表************** */
  $(function(){
	  $('#dg').datagrid({    
		    url:'findPageAll_Emp.do',
		    width:1000,
		    left:600,
		    striped:true,//显示斑马线
		    pagination:true,//分页工具栏
		    singleSelect:true,
		    pageSize:5,
		    pageList:[1,2,3,4,5,6],
		    columns:[[    
		        {field:'eid',title:'编号',width:100,align:'center'}, 
		        {field:'ename',title:'姓名',width:100,align:'center'},
		        {field:'sex',title:'性别',width:100,align:'center'}, 
		        {field:'address',title:'地址',width:100,align:'center'},
		        {field:'sdate',title:'生日',width:100,align:'center'}, 
		        {field:'photo',title:'照片',width:100,align:'center',
		        	formatter: function(value,row,index){
		        
					return "<img id='myphoto' src=uppic/"+row.photo+" width=50 height=60>";
			        }
		        },
		        {field:'depname',title:'部门',width:100,align:'center'}, 
		        {field:'opt',title:'操作',width:300,align:'center',
		        	formatter: function(value,row,index){
		        		var bt1='<input type="button" value="删除" onclick="dodelById('+row.eid+')">';
						var bt2='<input type="button" value="编辑" onclick="findById('+row.eid+')">';
						var bt3='<input type="button" value="详细" onclick="findDetail('+row.eid+')">';
						return bt1+'&nbsp;'+bt2+'&nbsp;'+bt3;
				}
                }    
		    ]]    
		});  

  });
 
 
 /********************  员工列表*end************** */


</script>
 
</head>

<body>
<!-- 员工展示 -->
  <p align="center">员工列表</p>
<hr></hr>
<table align="center" id="dg"></table>
<!-- 员工管理 -->

<form action="" id="ffemp"  name="ffemp" method="post" enctype="multipart/form-data">
 <table align="center" width="550px" border="1px">
  <tr align="center" bgcolor="#FFFFCC">
    <td colspan="3">员工管理</td>
  </tr>
  <tr>
    <td>姓名</td>
    <td>
      <input type="text" id="ename" name="ename" class="easyui-validatebox" data-options="required:true">
    </td>
    <td rowspan="7">
     <img id="imgg" src="WEB-INF/uppic/default.jpg" width="160" height="170" />
    </td>
  </tr>
  <tr>
    <td>性别</td>
    <td>
	    <input type="radio" id="sex" name="sex" value="男">男
    <input type="radio" id="sex" name="sex" value="女" checked="checked">女
    </td>
  </tr>
  <tr>
    <td>地址</td>
    <td>
     <input type="text" id="address" name="address" >
    </td>
  </tr>
  <tr>
    <td>生日</td>
    <td>
     <input type="date" id="sdate" name="sdate" value="1999-01-01">
    </td>
  </tr>
  <tr>
    <td>上传照片</td>
    <td>
     <input type="file" id="pic" name="pic">
    </td>
  </tr>
  <tr>
    <td>部门</td>
    <td>
     <input type="text" id="cc" name="depid">  
    </td>
  </tr>
  <tr>
    <td>薪资</td>
    <td>
     <input type="text" id="emoney" name="emoney" value="2000">
    </td>
  </tr>
  <tr>
    <td>福利</td>
    <td colspan="2">
      <span id="wf"></span>
    </td>
  </tr>
 <tr align="center" bgcolor="#FFFFCC">
    <td colspan="3">
       <input type="hidden" id="eid" name="eid">
       <input type="button" id="btsave" value="添加">
       <input type="button" id="btupdate" value="修改">
       <input type="reset" value="重置">
    </td>
  </tr>
</table>
 </form>

</body>
</html>