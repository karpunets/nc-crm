<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <spring:url value="/css/login.css" var="springCss" />
    <spring:url value="/img/ico.png" var="springIcon" />

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/css/materialize.min.css">

    <link href="${springCss}" rel="stylesheet" />
    <link href="${springIcon}" rel="icon" />

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Login</title>
</head>

<body>
<h1 class="teal-text darken-3">NC-CRM</h1>
<h5 class="teal-text lighten-5">Please, login into your account</h5>
<div class="form-wrapper z-depth-1 grey lighten-4">
    <form class="row" method="post">
        <div class='input-field col s12'>
            <input class='validate' type='email' name='email' id='email' />
            <label for='email'>Enter your email</label>
        </div>
        <div class='input-field col s12'>
            <input class='validate' type='password' name='password' id='password' />
            <label for='password'>Enter your password</label>
        </div>
        <div class='col s12'>
            <input type='checkbox' name='remember' id='remember' checked="checked"/>
            <label for='remember'>Remember Me</label>
        </div>
        <div class='col s12'>
            <button type='submit' name='btn_login' class='col s12 btn btn-large waves-effect teal'>Log in</button>
        </div>
        <div class="col s12">
            <label class="label-forgot center-align">
                <a class='blue-grey-text lighten-2' href='#!'><b>Forgot Password?</b></a>
            </label>
        </div>
    </form>
</div>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.2.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.5/js/materialize.min.js"></script>
</body>
</html>