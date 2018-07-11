package com.office.online.ssc.web;

import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.web.context.ContextLoader;

import javax.servlet.http.HttpServletRequest;

/**
 * baseController
 *
 */
public class BaseController {

    protected PageOfficeCtrl create(HttpServletRequest request) {
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        poCtrl.setServerPage(request.getContextPath() + "/poServer");
        poCtrl.setSaveFilePage(request.getContextPath() + "/save/file");
        return poCtrl;
    }

    protected String webPath() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/");
    }

    protected String webPath(String dir) {
        if (!dir.startsWith("/")) {
            dir = '/' + dir;
        }
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(dir);
    }

}
