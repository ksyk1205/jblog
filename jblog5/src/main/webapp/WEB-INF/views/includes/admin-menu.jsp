<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<ul class="admin-menu">
	<li class="selected"><a href="${pageContext.request.contextPath}/${authUser.id }/admin/basic">기본설정</a></li>
	<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/category">카테고리</a></li>
	<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/write">글작성</a></li>
</ul>
