<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2018/2/2
  Time: 下午6:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>云智慧门店管理系统</title>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<meta name="description" content="">
		<meta name="author" content="">
		<link rel="shortcut icon" href="<%=request.getContextPath() %>/assets/echitec.ico" type="image/x-icon">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/font-awesome-4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/ionicons-2.0.1/css/ionicons.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/dist/css/AdminLTE.min.css">
		<link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/iCheck/square/blue.css">
	</head>
	<body>
		<body class="hold-transition login-page">
			<div class="login-box">
				<div class="login-logo">
					<a href="<%=request.getContextPath() %>/"><b>CPOS 云智慧门店</b></a>
				</div>
				<!-- /.login-logo -->
				<div class="login-box-body">
					<p class="login-box-msg">Sign in to start your system</p>

					<form id="tologin" method="post">
						<div id="tip" class="alert-warning"></div>
						<div class="form-group has-feedback">
							<input id="empcode" name="empcode" type="text" class="form-control" placeholder="Username">
							<span class="glyphicon glyphicon-user form-control-feedback"></span>
						</div>
						<div class="form-group has-feedback">
							<input id="pwd" name="pwd" type="password" class="form-control" placeholder="Password">
							<span class="glyphicon glyphicon-lock form-control-feedback"></span>
						</div>
						<div class="row">
							<div class="col-xs-8">
								<div class="checkbox icheck">
									<label>
										<input id="chkremember" type="checkbox"> Remember Me
									</label>
								</div>
							</div>
							<!-- /.col -->
							<div class="col-xs-4">
								<button type="submit" class="btn btn-primary btn-block btn-flat">登 陆</button>
							</div>
							<!-- /.col -->
						</div>
					</form>

					<a href="<%=request.getContextPath() %>#">I forgot my password</a><br>
					<a href="<%=request.getContextPath() %>/register" class="text-center">Register a new membership</a>
					<p></p>
					<p class="login-box-msg">Copyright © 简逸信息</p>
				</div>
				<!-- /.login-box-body -->
			</div>
			<script src="<%=request.getContextPath() %>/assets/plugins/jQuery/jquery-2.2.3.min.js"></script>
			<script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap.min.js"></script>
			<script src="<%=request.getContextPath() %>/assets/plugins/iCheck/icheck.min.js"></script>
			<script >
                $(function () {
                    $('input').iCheck({
                        checkboxClass:'icheckbox_square-blue',radioClass:'iradio_square-blue',increaseArea:'20%'
                    });
                });
                window.onload = function () {
                    var onForm = document.getElementById('tologin');
                    var onemp = document.getElementById('empcode');
                    var onpwd = document.getElementById('pwd');
                    var onchk = document.getElementById('chkremember');
                    if(getCookie('emp') && getCookie('pwd')){
                        onemp.value = getCookie('emp');
                        onpwd.value = getCookie('pwd');
                        onchk.checked = true;
                    }
                    onchk.onchange = function () {
                        if(!this.checked){
                            delCookie('emp');
                            delCookie('pwd');
                        }
                    };
                    onForm.onsubmit = function(){
                        if(onchk.checked){
                            setCookie('emp',onemp.value,7);
                            setCookie('pwd',onpwd.value,7);
                        }
                    };
                };
                function setCookie(name,value,day) {
	                var date = new Date();
	                date.setDate(date.getDate()+day);
	                document.cookie = name+'='+value+';expires='+date;
                };
                function getCookie(name) {
	                var reg = RegExp(name+'=([^;]+)');
	                var arr = document.cookie.match(reg);
	                if(arr){
	                    return arr[1];
	                }else{
	                    return '';
	                }
                };
                function delCookie(name){
                    setCookie(name,null,-1);
                };
			</script>
			<script type="text/javascript">
                $(document).ready(
                    function(){
                        var tip=$('#tip');
                        var tologin = $('#tologin');
                        tologin.submit(
                            function (e) {
                                e.preventDefault();
                                var u = $('#empcode').val();
                                var p = $('#pwd').val();
                                if(u =="" || p=="" ){
                                    showTip('emp id & password required !');
                                    return;
                                }
                                hideTip();
                                $.get('<%=request.getContextPath() %>/tologin',tologin.serialize(),function(result){
                                    if(result.code != 0){
                                        showTip(result.data);
                                        return;
                                    }
                                    document.location='<%=request.getContextPath() %>/index';
                                });
                            }
                        );
                        function showTip(m){tip.html(m);tip.show();}
                        function hideTip(){tip.hide();}
                    }
                );
			</script>
		</body>
	</body>
</html>
