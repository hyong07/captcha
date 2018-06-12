<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>


</head>
<body>
	<script>
	$(document).ready(function() {
		$.ajax({
			url : "captcha.do",
			type:"get",
			success : function(data) {
				console.log(data);
				$("#div01").html("<img src='captchaImage/"+data+"'>");

			},
			error:function(data){
				console.log(data);
			},
			complete:function() {
				
			}
		});
		
		$("#btn").click(function() {
			var value = $("#value").val();
			$.ajax({
				url : "captcha.do",
				type:"get",
				data : {
					value:value
				},
				
				success:function(response){
					alert(response);
				}
			})
		});
	
	});
</script>
<body>
	<div id="div01"></div>

	<input type="hidden" id="key" name="key">
	<input type="text" id="value">
	<button type="button" id="btn">전송</button>

</body>
</html>