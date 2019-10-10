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
		<style type="text/css">
			.yhkxt{color:red}
		</style>
<script type="text/javascript">
	var term ;
	function yourfunction(){
		var RECEIVING_PAYMENT_BANK = $("#RECEIVING_PAYMENT_BANKN").val();
		$.ajax({
			url:"<%=basePath%>bank/selectByyhk",
			type:"GET",
			data:{RECEIVING_PAYMENT_BANK:RECEIVING_PAYMENT_BANK},
			success:function(data){
				if(data == 1){
					term = 1;
					$("#yhkxt").html("银行卡号已存在");
				}else if(data == 2){
					term = 2;
					$("#yhkxt").html("");
				}else{
					alert("未知错误！");
				} 
			},
			error:function () {
				alert("错误");
			}
		});
	}
	//保存
	function save(){
		if(term == 1){
			alert("请检查输入是否有误！");
		}else{
			if($("#RECEIVING_BANK_NAME").val()==""){
			$("#RECEIVING_BANK_NAME").tips({
				side:3,
	            msg:'请输入收款银行',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIVING_BANK_NAME").focus();
			return false;
		}
		if($("#RECEIVING_PAYMENT_BANK").val()==""){
			$("#RECEIVING_PAYMENT_BANK").tips({
				side:3,
	            msg:'请输入收款银行卡号',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIVING_PAYMENT_BANK").focus();
			return false;
		}
		if($("#RECEIVING_BANK_BRANCH").val()==""){
			$("#RECEIVING_BANK_BRANCH").tips({
				side:3,
	            msg:'请输入收款开户支行',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIVING_BANK_BRANCH").focus();
			return false;
		}
		if($("#RECEIVING_NAME").val()==""){
			$("#RECEIVING_NAME").tips({
				side:3,
	            msg:'请输入收款账户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#RECEIVING_NAME").focus();
			return false;
		}
		if($("#CREATE_TIME").val()==""){
			$("#CREATE_TIME").tips({
				side:3,
	            msg:'请输入创建时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#CREATE_TIME").focus();
			return false;
		}
		if($("#UPDATE_TIME").val()==""){
			$("#UPDATE_TIME").tips({
				side:3,
	            msg:'请输入修改时间',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#UPDATE_TIME").focus();
			return false;
		}
		if($("#STATUS").val()==""){
			$("#STATUS").tips({
				side:3,
	            msg:'请输入状态',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#STATUS").focus();
			return false;
		}
		$("#Form").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	}
</script>
	</head>
<body>
	<form action="bank/${msg }.do" name="Form" id="Form" method="post">
		<input type="hidden" name="bank_id" id="bank_id" value="${pd.bank_id}"/>
		<div id="zhongxin">
		<table id="table_report" class="table table-striped table-bordered table-hover">
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">收款银行:</td>
				<td><input type="text" name="RECEIVING_BANK_NAME" id="RECEIVING_BANK_NAME" value="${pd.RECEIVING_BANK_NAME}" maxlength="32" placeholder="这里输入收款银行" title="收款银行"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">收款银行卡号:</td>
				<td><input onblur="yourfunction()" type="text" name="RECEIVING_PAYMENT_BANK" id="RECEIVING_PAYMENT_BANKN" value="${pd.RECEIVING_PAYMENT_BANK}" maxlength="32" placeholder="这里输入收款银行卡号" title="收款银行卡号"/>
				<span id="yhkxt" class="yhkxt"></span></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">收款开户支行:</td>
				<td><input type="text" name="RECEIVING_BANK_BRANCH" id="RECEIVING_BANK_BRANCH" value="${pd.RECEIVING_BANK_BRANCH}" maxlength="32" placeholder="这里输入收款开户支行" title="收款开户支行"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">收款账户名:</td>
				<td><input type="text" name="RECEIVING_NAME" id="RECEIVING_NAME" value="${pd.RECEIVING_NAME}" maxlength="32" placeholder="这里输入收款账户名" title="收款账户名"/></td>
			</tr>
			<tr hidden>
				<td style="width:70px;text-align: right;padding-top: 13px;">创建时间:</td>
				<td><input name="CREATE_TIME" id="CREATE_TIME" value="${pd.CREATE_TIME}" type="text"/></td>
			</tr>
			<tr hidden>
				<td style="width:70px;text-align: right;padding-top: 13px;">修改时间:</td>
				<td><input name="UPDATE_TIME" id="UPDATE_TIME" value="${pd.UPDATE_TIME}" type="text"/></td>
			</tr>
			<tr>
				<td style="width:70px;text-align: right;padding-top: 13px;">是否启用:</td>
				<td>
					<select class="chzn-select" name="STATUS" id="STATUS" data-placeholder="请选择" style="vertical-align:top;width: 120px;">
						<option value="1">启用</option>
						<option value="2">禁用</option>
					</select>
				</td>
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
			var dt = new Date();
			var year,month,day,honrs,minutes,seconds = "";
			year=dt.getFullYear();
		    if(dt.getMonth()<10){
		        month = "0"+(dt.getMonth()+1);
		    }else{
		        month = dt.getMonth()+1;
		    }
		    if(dt.getDate()<10){
		        day = "0"+dt.getDate();
		    }else{
		    	day = dt.getDate();
		    }
		    if(dt.getHours()<10){
		    	honrs = "0"+dt.getHours();
		    }else{
		    	honrs = dt.getHours();
		    }
		    if(dt.getMinutes()<10){
		    	minutes = "0"+dt.getMinutes();
		    }else{
		    	minutes = dt.getMinutes();
		    }
		    if(dt.getSeconds()<10){
		    	seconds = "0"+dt.getSeconds();
		    }else{
		    	seconds = dt.getSeconds();
		    }
		    var datew = year+"-"+month+"-"+day+" "+honrs+":"+minutes+":"+seconds;
		    var datew = datew.toString();
		    $("#CREATE_TIME").val(datew);
		    $("#UPDATE_TIME").val(datew);
		});
		</script>
</body>
</html>