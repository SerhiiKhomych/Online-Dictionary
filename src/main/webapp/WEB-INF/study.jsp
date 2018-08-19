<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="../resources/js/study.js"></script>
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

    <div id="word" class="basic"></div>
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
    <div id="loading_margin">
        <br><br><br><br>
    </div>
    <img id="smile" style="width:250px; height:250px; display:none;" src="../resources/smile.png" >
    <div id="basic">
        <div>
            <input class="textBox" id="translation" type='text' />
        </div>
        <button id="studyButton" class="green_button" onclick="studyWord()">CHECK</button>
    </div>
    <div id="extended" style="display:none;">
        <table align="center">
            <tr>
                <td><input type="radio" name="gender" class="radioBtnClass" value="1"></td>
                <td><div id="translation1" class="tooltip"></div></td>
                <td><div id="result1"></div></td>
            </tr>
            <tr>
                <td> <input type="radio" name="gender" class="radioBtnClass" value="2"></td>
                <td><div id="translation2" class="tooltip"></div></td>
                <td><div id="result2"></div></td>
            </tr>
            <tr>
                <td><input type="radio" name="gender" class="radioBtnClass" value="3"></td>
                <td><div id="translation3" class="tooltip"></div></td>
                <td><div id="result3"></div></td>
            </tr>
        </table>
    </div>
</body>
</html>