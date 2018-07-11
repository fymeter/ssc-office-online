<%--
  Created by IntelliJ IDEA.
  User: Chung Junbin
  Date: 2017/2/3 16:41
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%-- <%@taglib prefix="po" uri="http://java.pageoffice.cn" %> --%>
<%@ page import="com.zhuozhengsoft.pageoffice.*"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po" %>
<!DOCTYPE html>
<html>
<head>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css"/>
</head>
<body>

<%-- 建议将 div 改成 form --%>
<span style="font-size: 24px;align-content: center;color: blue;float: right;">
    <%--欢迎您！${loginUser.nickname}--%>
    <a href="${pageContext.request.contextPath}/logout">登出</a></span>
<div class="officeContainer" style="width: 100%;height: 100%;">
    <po:PageOfficeCtrl id="excelCtrl"/>
</div>

<script type="text/javascript">

    // 全屏
    function fullScreen() {
        document.getElementById("excelCtrl").FullScreen = true;
    }

    // 取消全屏
    function cancelFullScreen() {
        document.getElementById("excelCtrl").FullScreen = false;
    }
//     function Save() {
//       	document.getElementById("PageOfficeCtrl1").WebSave();
//       	window.external.close();
// 	}
    // 保存
    function saveFile() {
        document.getElementById("excelCtrl").WebSave();

        var result = document.getElementById("excelCtrl").CustomSaveResult;
        // 中文乱码，不清楚插件内部的文本传输的字符编码，因此这里不会使用中文
        if (result.length !== 0) { // 当返回值非空的时候，必然是发生了错误或者冲突
            alert(result);
        }
    }

</script>
</body>
</html>
