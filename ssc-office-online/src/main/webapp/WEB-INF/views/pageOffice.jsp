
<script type="text/javascript" src=" jquery.min.js"></script>
<script type="text/javascript" src=" pageoffice.js" id="po_js_main"></script>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
    <head>
        <title th:utext="${fileName}"></title>
        <script type="text/javascript">
          	function Save() {
              	document.getElementById("PageOfficeCtrl1").WebSave();
              	window.external.close();
        	}
        </script>
    </head>
    <body>
        <div style="width:1000px;height:700px;" th:utext="${pageoffice}"></div>
    </body>
</html>
