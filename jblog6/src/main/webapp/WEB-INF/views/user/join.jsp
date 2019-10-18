<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
<script>
$(function(){
	$("#id").change(function(){
		$("#btn-check-id").show();
		$("#img-check").hide();
	});	
	
	$("#btn-check-id").click(function(){
		var id = $("#id").val();
		if(id == ""){
			return;
		}
	
		// ajax 통신
		$.ajax({
			url:s"${pageContext.servletContext.contextPath }/api/user/checkid?id=" + id,
			type: "get",
			dataType: "json",
			data: "",
			success: function(response){
				if(response.result == "fail"){
					console.error(response.message);
					return;
				}
				
				if(response.data == true){
					alert("이미 존재하는 아이디입니다.");
					$("#id").val("");
					$("#id").focus();
					return;
				}
				
				$("#btn-check-id").hide();
				$("#img-check").show();
			},
			error: function(xhr, error){
				console.error("error:" + error);
			}
		});
	});
});



</script>

</head>
<body>
	<div class="center-content">
		<h1 class="logo">JBlog</h1>
	<c:import url="/WEB-INF/views/includes/menu.jsp" />
		<form:form modelAttribute="userVo" class="join-form" id="join-form"
			method="post"
			action="${pageContext.servletContext.contextPath }/user/join">

			<label class="block-label" for="name">이름</label>
			<form:input path="name" />
			<p
				style="font-weight: bold; color: red; text-align: left; padding: 5px 0 0 0">
				<form:errors path="name" />
			</p>

			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id" />
			<input id="btn-check-id" type="button" value="id 중복체크">
			<img id="img-check" style='width: 20px; display: none'
				src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="font-weight: bold; color: red; text-align: left; padding: 5px 0 0 0">
				<form:errors path="id" />
			</p>
				
			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />
			<p style="font-weight: bold; color: red; text-align: left; padding: 5px 0 0 0">
				<form:errors path="password" />
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
