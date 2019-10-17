<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" type="text/javascript"></script>
</head>
<script>
	$(function() {
		$('#category-add-btn').on('click', function() {
			let category = {
				name: $('#category-name').val(),
				description: $('#category-description').val()
			}
			
			$.ajax({
				url: '${pageContext.servletContext.contextPath }/api/${authUser.id}/category',
				method: 'post',
				data: category,
				success: function(response) {
					console.log(response)
					if (response.result == 'success') {
						// 화면에 추가
						addCategory(response.data)
						// input clear
					}
				},
				error: function() {
					console.log('error')
				}
			})	
		})
		
		$('.category-remove-btn').on('click', removeCategory)
		
		
		function addCategory(category) {
			console.log('category:', category)
			let trTag = $('<tr/>').attr('id', 'cid-' + category.no)
				.append($('<td/>').text(category.no))
				.append($('<td/>').text(category.name))
				.append($('<td/>').text(category.count))
				.append($('<td/>').text(category.description))
				.append($('<td/>').append(
						$('<img/>').attr('src', '${pageContext.request.contextPath}/assets/images/delete.jpg').attr('category-no', category.no)
						.on('click', removeCategory).addClass('category-remove-btn')))
			
			$('#category-table').append(trTag)
		}
		
		function removeCategory(event) {
			let category={
				no: $(event.target).attr('category-no')
			}
			console.log(category.no)
		
			$.ajax({
				url: '${pageContext.servletContext.contextPath }/api/${authUser.id}/removecategory',
				method: 'post',
				data: category,
				success: function(response) {
					if (response.result != 'success') {
						console.log('remove error')
					}
					console.log(response.result)
					$('#category-table tr').remove('#cid-'+category.no)
				},
				error: function() {
					console.log('remove error')
				}
			})
		}
	})
	
	
	
</script>

<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp" />
		<div id="wrapper">
			<div id="content" class="full-screen">
				<c:import url="/WEB-INF/views/includes/admin-menu.jsp"/>
		      	<table class="admin-cat" id="category-table">
		      		<tr>
		      			<th>번호</th>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		<c:forEach items="${catelist}" var="catevo">
					<tr id="cid-${catevo.no }">
						<td>${catevo.no }</td>
						<td>${catevo.name }</td>
						<td>${catevo.count }</td>
						<td>${catevo.description}</td>
						<td><img class="category-remove-btn" src="${pageContext.request.contextPath}/assets/images/delete.jpg" category-no="${catevo.no }"></td>
					</tr>
					</c:forEach>  					  
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
		      	<table id="admin-cat-add">
		      		<tr>
		      			<td class="t">카테고리명</td>
		      			<td><input type="text" name="name" id="category-name"></td>
		      		</tr>
		      		<tr>
		      			<td class="t">설명</td>
		      			<td><input type="text" name="description" id="category-description"></td>
		      		</tr>
		      		<tr>
		      			<td class="s">&nbsp;</td>
		      			<td><input type="button" value="카테고리 추가" id="category-add-btn"></td>
		      		</tr>      		      		
		      	</table> 
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>