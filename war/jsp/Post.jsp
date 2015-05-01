<%@ page language="java" contentType="text/html; charset=windows-1256"
    pageEncoding="windows-1256"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1256">
<title>Insert title here</title>
</head>
<body>
<form action="/social/userPost" method="post">
<input type="text" name="content" placeholder="what's in ur mind"><br>
<input type="text" name="timeLine" placeholder="Time Line"><br>
<input type="text"  name="hashTag" placeholder="hashTag">
select privacy type
<select name="userPrivacy">
<option value="private">private</option>
<option value="public">public</option>
</select>
<select name="feeling">
<option value=":)">:)</option>
<option value=":(">:(</option>
</select>
<input type="submit" value="Post"> 
</form>
</body>
</html>