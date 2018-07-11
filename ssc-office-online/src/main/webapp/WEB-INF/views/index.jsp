<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title>Index</title>
        <script type="text/javascript" src="jquery.min.js"></script>
     	<script type="text/javascript" src="pageoffice.js" id="po_js_main"></script>
    </head>
    <body>
        <h1 th:inline="text">PageOffice show</h1>
        <ul>

        </ul>
    </body>
    <script>
        $(function () {
            $.get("/ssc-office-online/index/fileList",function (response) {
                console.log(response);
                var html="";
                //此处的value是从服务器传递过来的服务器文件的绝对路径，客户端是访问不了的。。。
                $.each(response,function (index,value) {
                    html+="<li>"+getOpenClick(value)+"</li>"
                })
                $("ul").html(html);
            })
        })
    </script>
    <script type="text/javascript">
	    function getOpenClick(filePath){
		    //此处的filePath是从服务器传递过来的服务器文件的绝对路径，客户端是访问不了的。。。
		    //因为filePath可能会包含中文和特殊字符所以需要进行转码
		    var height=screen.availHeight;
		    var width=screen.availWidth;
		    return "<a href=\"javascript:POBrowser.openWindowModeless('/ssc-office-online/index/edit','width="+width+";height="+height+";');\">"+filePath+" </a>";
		}
    </script>
</html>
