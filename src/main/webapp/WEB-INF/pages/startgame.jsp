<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Start Game - Crazy Ace</title>   
</head>
<body>
<p>
	<form action="/startGame" method="post">
		<input type="text" name="playerName" value="${defaultName}">
		<br><br>
		<input type="submit" value="Start">
		<input type="reset"> 
	</form>

</body>
</html>