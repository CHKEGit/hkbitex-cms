<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		
		<link rel="stylesheet" href="static/css/ace.min.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		
		<link rel="stylesheet" href="static/css/datepicker.css" /><!-- 日期框 -->
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		
<script type="text/javascript">
	
	
	//保存
	function save(){
			if($("#ID").val()==""){
			$("#ID").tips({
				side:3,
	            msg:'请输入id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ID").focus();
			return false;
			}
			if($("#WAY").val()==""){
				$("#WAY").tips({
					side:3,
		            msg:'请输入方向（0买入1卖出）',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#WAY").focus();
				return false;
			}
		if($("#USER_ID").val()==""){
			$("#USER_ID").tips({
				side:3,
	            msg:'请输入用户id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#USER_ID").focus();
			return false;
		}
		if($("#PRICE").val()==""){
			$("#PRICE").tips({
				side:3,
	            msg:'请输入原始价格',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#PRICE").focus();
			return false;
		}
		if($("#NUMBER").val()==""){
			$("#NUMBER").tips({
				side:3,
	            msg:'请输入数量',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#NUMBER").focus();
			return false;
		}
		if($("#CREATE_TIME").val()==""){
			$("#CREATE_TIME").tips({
				side:3,
	            msg:'请输入时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TIME").focus();
			return false;
		}
		if($("#CURRENCY").val()==""){
			$("#CURRENCY").tips({
				side:3,
	            msg:'请输入币种id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CURRENCY").focus();
			return false;
		}
		if($("#FROM_USER_ID").val()==""){
			$("#FROM_USER_ID").tips({
				side:3,
	            msg:'请输入触发用户id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#FROM_USER_ID").focus();
			return false;
		}
		if($("#LEGAL").val()==""){
			$("#LEGAL").tips({
				side:3,
	            msg:'请输入法币id',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#LEGAL").focus();
			return false;
		}
		if($("#RATE").val()==""){
			$("#RATE").tips({
				side:3,
	            msg:'请输入手续费率',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RATE").focus();
			return false;
		}
		if($("#IN_FEE").val()==""){
			$("#IN_FEE").tips({
				side:3,
	            msg:'请输入收入',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#IN_FEE").focus();
			return false;
		}
		if($("#OUT_FEE").val()==""){
			$("#OUT_FEE").tips({
				side:3,
	            msg:'请输入支出',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#OUT_FEE").focus();
			return false;
		}
		if($("#AUTO_TYPE").val()==""){
			$("#AUTO_TYPE").tips({
				side:3,
	            msg:'请输入机器人类型',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#AUTO_TYPE").focus();
			return false;
		}
		if($("#STATE").val()==""){
			$("#STATE").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STATE").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	
</script>
	</head>
<body>
	<form action="complete/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">id:</td>
				<td><input type="number" name="ID" id="ID" value="${pd.ID}" maxlength="32" placeholder="这里输入id" title="id"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">方向（0买入1卖出）:</td>
				<td><input type="number" name="WAY" id="WAY" value="${pd.WAY}" maxlength="32" placeholder="这里输入方向（0买入1卖出）" title="方向（0买入1卖出）"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">用户id:</td>
				<td><input type="number" name="USER_ID" id="USER_ID" value="${pd.USER_ID}" maxlength="32" placeholder="这里输入用户id" title="用户id"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">原始价格:</td>
				<td><input type="number" name="PRICE" id="PRICE" value="${pd.PRICE}" maxlength="32" placeholder="这里输入原始价格" title="原始价格"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">数量:</td>
				<td><input type="number" name="NUMBER" id="NUMBER" value="${pd.NUMBER}" maxlength="32" placeholder="这里输入数量" title="数量"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">时间:</td>
				<td><input type="number" name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" maxlength="32" placeholder="这里输入时间" title="时间"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">币种id:</td>
				<td><input type="number" name="CURRENCY" id="CURRENCY" value="${pd.CURRENCY}" maxlength="32" placeholder="这里输入币种id" title="币种id"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">触发用户id:</td>
				<td><input type="number" name="FROM_USER_ID" id="FROM_USER_ID" value="${pd.FROM_USER_ID}" maxlength="32" placeholder="这里输入触发用户id" title="触发用户id"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">法币id:</td>
				<td><input type="number" name="LEGAL" id="LEGAL" value="${pd.LEGAL}" maxlength="32" placeholder="这里输入法币id" title="法币id"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">手续费率:</td>
				<td><input type="number" name="RATE" id="RATE" value="${pd.RATE}" maxlength="32" placeholder="这里输入手续费率" title="手续费率"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">收入:</td>
				<td><input type="number" name="IN_FEE" id="IN_FEE" value="${pd.IN_FEE}" maxlength="32" placeholder="这里输入收入" title="收入"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">支出:</td>
				<td><input type="number" name="OUT_FEE" id="OUT_FEE" value="${pd.OUT_FEE}" maxlength="32" placeholder="这里输入支出" title="支出"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">机器人类型:</td>
				<td><input type="number" name="AUTO_TYPE" id="AUTO_TYPE" value="${pd.AUTO_TYPE}" maxlength="32" placeholder="这里输入机器人类型" title="机器人类型"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">状态:</td>
				<td><input type="number" name="STATE" id="STATE" value="${pd.STATE}" maxlength="32" placeholder="这里输入状态" title="状态"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
		
	</form>
	
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript">
		$(top.hangge());
		$(function() {
			
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
			
			//日期框
			$('.date-picker').datepicker();
			
		});
		
		</script>
</body>
</html>