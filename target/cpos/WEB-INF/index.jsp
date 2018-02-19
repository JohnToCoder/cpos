<%--
  Created by IntelliJ IDEA.
  User: john
  Date: 2017/4/17
  Time: 下午3:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="<%=request.getContextPath() %>/assets/echitec.ico" type="image/x-icon">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/font-awesome-4.7.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/ionicons-2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/dist/css/AdminLTE.css">
	  <link rel="stylesheet" href="<%=request.getContextPath()%>/assets/dist/css/alt/AdminLTE-select2.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/dist/css/skins/_all-skins.min.css">
    <!-- iCheck -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/iCheck/flat/blue.css">
    <!-- Morris chart -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/morris/morris.css">
    <!-- jvectormap -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
    <!-- Date Picker -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/datepicker/datepicker3.css">
    <!-- Daterange picker -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/daterangepicker/daterangepicker.css">
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/assets/bootstrap/css/bootstrap-table.css">

    <!-- jQuery 2.2.3 -->
    <script src="<%=request.getContextPath() %>/assets/plugins/jQuery/jquery-2.2.3.min.js"></script>
    <!-- jQuery UI 1.11.4 -->
    <script src="<%=request.getContextPath() %>/assets/plugins/jQueryUI/jquery-ui.min.js"></script>
    <!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
    <script>
      $.widget.bridge('uibutton', $.ui.button);
    </script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/jquery.min.js"></script>
    <!-- Bootstrap 3.3.6 -->
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap.min.js"></script>
    <!-- Morris.js charts -->
    <script src="<%=request.getContextPath() %>/assets/dist/js/raphael-min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/plugins/morris/morris.js"></script>
    <!-- Sparkline -->
    <script src="<%=request.getContextPath() %>/assets/plugins/sparkline/jquery.sparkline.min.js"></script>
    <!-- jvectormap -->
    <script src="<%=request.getContextPath() %>/assets/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
    <!-- jQuery Knob Chart -->
    <script src="<%=request.getContextPath() %>/assets/plugins/knob/jquery.knob.js"></script>
    <!-- daterangepicker -->
    <script src="<%=request.getContextPath() %>/assets/dist/js/moment.min.js"></script>
    <script src="<%=request.getContextPath() %>/assets/plugins/daterangepicker/daterangepicker.js"></script>
    <!-- datepicker -->
    <script src="<%=request.getContextPath() %>/assets/plugins/datepicker/bootstrap-datepicker.js"></script>
    <!-- Bootstrap WYSIHTML5 -->
    <script src="<%=request.getContextPath() %>/assets/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    <!-- Slimscroll -->
    <script src="<%=request.getContextPath() %>/assets/plugins/slimScroll/jquery.slimscroll.min.js"></script>
    <!-- FastClick -->
    <script src="<%=request.getContextPath() %>/assets/plugins/fastclick/fastclick.js"></script>
    <!-- AdminLTE App -->
    <script src="<%=request.getContextPath() %>/assets/dist/js/app.js"></script>
    <!--ChartJS-->
    <script src="<%=request.getContextPath() %>/assets/plugins/chartjs/Chart.js"></script>
    <!-- bootstrap datepicker -->
    <script src="<%=request.getContextPath() %>/assets/plugins/datepicker/bootstrap-datepicker.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap-table.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap-table-export.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap-table-zh-CN.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/tableExport.js"></script>
    <script src="<%=request.getContextPath() %>/assets/bootstrap/js/bootstrap-tab.js"></script>
    <script src="<%=request.getContextPath() %>/assets/pagejs/indexjs.js"></script>
    <script src="<%=request.getContextPath() %>/assets/pagejs/geersjs.js"></script>
    <script src="<%=request.getContextPath() %>/assets/pagejs/goodsjs.js"></script>
  </head>
  <body class="hold-transition skin-blue sidebar-mini" onload="initpages()" >
    <div class="wrapper">
      <header class="main-header">
        <a href="<%=request.getContextPath() %>/" class="logo">
          <span class="logo-mini">POS</span>
          <span class="logo-lg"><b>智慧门店管理</b>CPOS</span>
        </a>
        <nav class="navbar navbar-static-top">
          <a href="javascript:void(0)" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <li class="dropdown messages-menu">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-envelope-o"></i>
                  <span class="label label-success">0</span>
                </a>
              </li>
              <li class="dropdown notifications-menu">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-bell-o"></i>
                  <span class="label label-warning">0</span>
                </a>
              </li>
              <li class="dropdown tasks-menu">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-flag-o"></i>
                  <span class="label label-danger">0</span>
                </a>
              </li>
              <li class="dropdown user-menu">
                <a href="javascript:void(0)" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-user-circle"></i>
                  <span id="ddemmenu" class="hidden-xs">${emp.empName}</span>
                </a>
                <ul class="dropdown-menu">

                  <li class="user-body">
                    <ul class="control-sidebar-menu">
                      <li>
                        <a>
                          <i class="menu-icon fa fa-sellsy "></i>
                          <div class="menu-info">
                            <h4 class="control-sidebar-subheading">单位编号: ${emp.storeCode}</h4>
                            <h4 class="control-sidebar-subheading">单位名称: ${emp.storeName}</h4>
                            <h4 class="control-sidebar-subheading">单位电话: ${emp.storeTel}</h4>
                          </div>
                        </a>
                      </li>

                      <li>
                        <a>
                          <i class="menu-icon fa fa-drivers-license-o "></i>
                          <div class="menu-info">
                            <h4 class="control-sidebar-subheading">员工姓名: ${emp.empName}</h4>
                            <h4 class="control-sidebar-subheading">员工工号: ${emp.empCode}</h4>
                          </div>
                        </a>
                      </li>
                    </ul>
                  </li>
                  <li class="user-footer">
                    <div class="pull-left">
                      <a id="userset" onclick="UserSet()" class="btn btn-default btn-flat" ><i class="fa fa-gears"></i> 设 置 </a>
                    </div>
                    <div class="pull-right">
                      <a id="usersignout" href="<%=request.getContextPath() %>/loginout" class="btn btn-default btn-flat"><i class="fa fa-sign-out"></i> 登 出 </a>
                    </div>
                  </li>
                </ul>
              </li>
              <li><a href="javascript:void(0)" data-toggle="control-sidebar"><i class="fa fa-user"></i> </a></li>
            </ul>
          </div>
        </nav>
      </header>
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- search form -->
          <form action="#" method="get" class="sidebar-form">
            <div class="input-group">
              <input type="text" name="q" class="form-control" placeholder="Search...">
              <span class="input-group-btn">
	              <button type="submit" name="search" id="search-btn" class="btn btn-flat">
		              <i class="fa fa-search"></i>
	              </button>
              </span>
            </div>
          </form>
          <!-- /.search form -->
          <ul class="sidebar-menu" id="mainmenu">
            <li class="header"><i class="fa fa-home"><span> CPOS 主菜单</span></i></li>
            <li class=" treeview">
              <a href="javascript:void(0)" >
                <i class="fa fa-object-group"></i>
                <span>货品管理</span>
                <span class="pull-right-container">
					<i class="fa fa-angle-left pull-right"></i>
				</span>
              </a>
              <ul class="treeview-menu">
                <li><a href="javascript:void(0)" onclick="pageWareInvoice()"><i class="fa fa-cubes"></i> 仓库发货单</a></li>
                <li><a href="javascript:void(0)" onclick="pageStoreRecipe()"><i class="fa fa-file-text-o"></i> 门店收货单</a></li>
                <li><a href="javascript:void(0)" onclick="pageStoreWare()"><i class="fa fa-cloud-upload"></i> 门店库存查询</a></li>
                <li><a href="javascript:void(0)" onclick="pageStoreInvoice()"><i class="fa fa-cart-arrow-down"></i> 门店转货单</a></li>
              </ul>
            </li>
            <li class=" treeview">
              <a href="javascript:void(0)" >
                <i class="fa fa-rmb"></i>
                <span>销售管理</span>
                <span class="pull-right-container">
					<i class="fa fa-angle-left pull-right"></i>
				</span>
              </a>
              <ul class="treeview-menu">
                <li><a href="javascript:void(0)" onclick="pageStoreMag()"><i class="fa fa-crop"></i> 销售开单</a></li>
                <li><a href="javascript:void(0)" onclick="pageEmployeeMag()"><i class="fa fa-user-plus"></i> 销售统计</a></li>
                <li><a href="javascript:void(0)" onclick="pageGeersMag()"><i class="fa fa-gears"></i> 基础配置</a></li>

              </ul>
            </li>
            <li class=" treeview">
              <a href="javascript:void(0)" >
                <i class="fa fa-users"></i>
                <span>综合管理</span>
                <span class="pull-right-container">
					<i class="fa fa-angle-left pull-right"></i>
				</span>
              </a>
              <ul class="treeview-menu">
                <li><a href="javascript:void(0)" onclick="pageStoreMag()"><i class="fa fa-crop"></i> 单位管理</a></li>
                <li><a href="javascript:void(0)" onclick="pageEmployeeMag()"><i class="fa fa-user-plus"></i> 人员管理</a></li>
                <li><a href="javascript:void(0)" onclick="pageGeersMag()"><i class="fa fa-gears"></i> 基础配置</a></li>

              </ul>
            </li>
          </ul>
          <ul class="sidebar-menu" id="helpmenu">
            <li><a href="javascript:void(0)" onclick="PagesUpdateAPP()"><i class="fa fa-upload"></i> <span>软件更新</span></a></li>
            <li>
              <a href="javascript:void(0)">
                <i class="fa fa-envelope"></i> <span>Mailbox</span>
                <span class="pull-right-container">
					<small class="label pull-right bg-yellow">0</small>
					<small class="label pull-right bg-green">0</small>
					<small class="label pull-right bg-red">0</small>
                </span>
              </a>
            </li>
            <li><a href="javascript:void(0)"><i class="fa fa-book"></i> <span>帮助说明</span></a></li>
            <li class="header">LABELS</li>
            <li><a href="javascript:void(0)"><i class="fa fa-circle-o text-red"></i> <span>Important</span></a></li>
            <li><a href="javascript:void(0)"><i class="fa fa-circle-o text-yellow"></i> <span>Warning</span></a></li>
            <li><a href="javascript:void(0)"><i class="fa fa-circle-o text-aqua"></i> <span>Information</span></a></li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <div id="mainpages" class="content-wrapper">
        <div class="row">
          <div class="col-xs-12" style="padding-left: 5px;" >
            <ul class="nav nav-tabs"  role="tablist"></ul>
            <div class="tab-content"></div>
          </div>
        </div>
      </div>

      <footer class="main-footer">
        <div class="pull-right hidden-xs"><b>Version</b> 1.0.1</div>
        <strong>Copyright &copy; 2014-2018 <a href="http://www.echitec.com">云智慧门店管理系统</a>.</strong> All rights reserved.
      </footer>
      <!-- Control Sidebar -->
    </div>
  </body>
</html>
