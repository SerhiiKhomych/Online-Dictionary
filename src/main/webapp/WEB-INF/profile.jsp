<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="../resources/js/profile.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" media="only screen and (max-width: 760px)" href="../resources/css/mobile.css" />
    <link rel="stylesheet" media="only screen and (min-width: 761px)" href="../resources/css/main.css" />
</head>
<body class="centered-wrapper">
    <div id="tittle" class="tittle">English UP</div>
    <nav id="menu" class="menu" role='navigation'>
        <ul>
            <li><a href="/study">start</a></li>
            <li><a href="/profile">profile</a></li>
            <li><a href="/dictionary">dictionary</a></li>
            <li><a href="/logout">log out</a></li>
        </ul>
    </nav>
    <div id="loading" class="cssload-loader">
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
        <div class="cssload-side"></div>
    </div>
    <div id="basic" style="display:none;">
        <table align="center">
            <tr>
                <td>Mode:</td>
                <td><select style="width: 200px" id="mode_select"></select></td>
            </tr>
            <tr>
                <td>Algorithm:</td>
                <td><select style="width: 200px" id="algorithm_select"></select></td>
            </tr>
        </table>
        <button id="saveButton" class="" onclick="saveMetadata()">Save</button>
    </div>
</body>
</html>