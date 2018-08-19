<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" media="only screen and (max-width: 760px)" href="../resources/css/mobile.css" />
    <link rel="stylesheet" media="only screen and (min-width: 761px)" href="../resources/css/main.css" />
</head>
<body class="centered-wrapper">
<div id="tittle" class="tittle">English UP</div>
<nav id="menu" role='navigation'>
    <ul>
        <li><a href="/study">start</a></li>
        <li><a href="/profile">profile</a></li>
        <li><a href="/dictionary">dictionary</a></li>
        <li><a href="/logout">log out</a></li>
    </ul>
</nav>
<font color="red">${SPRING_SECURITY_LAST_EXCEPTION.message}</font>
<form action="/login" method="POST">
    Username:	<input type="text" name="username"/><br/><br/>
    Password: <input type="password" name="password"/> <br/><br/>
    <input type="submit" value="Login"/>
</form>
</body>
</html>  