package com.office.online.ssc.web;

import com.office.online.ssc.domain.Users;
import com.office.online.ssc.util.DocEditor;
import com.office.online.ssc.util.Global;
import com.zhuozhengsoft.pageoffice.FileSaver;
import com.zhuozhengsoft.pageoffice.OfficeVendorType;
import com.zhuozhengsoft.pageoffice.OpenModeType;
import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author : Zhong Junbin
 * @email : <a href="mailto:junbinzhong@linkcm.com">发送邮件</a>
 * @createDate : 2017/2/4 9:59
 * @description :
 */
@Controller
@RequestMapping("/index")
public class IndexController extends BaseController {


    @Override
    protected PageOfficeCtrl create(HttpServletRequest request) {
        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
        poCtrl.setServerPage(request.getContextPath() + "/poserver.zz");
        poCtrl.setSaveFilePage(request.getContextPath() + "/index/save/file");
        poCtrl.setOfficeToolbars(true);
        return poCtrl;
    }

    @ModelAttribute
    public void populateUserIntoModel(HttpSession session, Model model) {
        model.addAttribute(Global.SESSION_USER_KEY, session.getAttribute(Global.SESSION_USER_KEY));
    }
    
    @RequestMapping(value = "/fileList", method=RequestMethod.GET)
	public @ResponseBody List<String> getFileList(){
		List<String> list=new ArrayList<>();
		for(File file:new File("D:/ssc").listFiles()){
			list.add(file.getAbsolutePath());
		}
		return list;
	}  
    
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute(Global.SESSION_USER_KEY) Users user, Model model,
                       HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            // 用户为空，跳转到 index 进行逻辑处理
            return "redirect:/index";
        }
//        Doc doc = user.getDoc();

//        DocEditor.LOCK.lock();
//        String path = ".docx";
//        try {
//            String editor =DocEditor.getEditor(path);
//            if (editor == null) {
//                DocEditor.markDocEditingStatus(path, user.getUsername());
//            }
//        } finally {
//            DocEditor.LOCK.unlock();
//        }

//        if (!doc.isEditable()) { // 该用户对该文档不可编辑，直接重定向到只读页面
//            return "redirect:/index/read/only";
//        }

        PageOfficeCtrl excelCtrl = this.create(request);

        excelCtrl.addCustomToolButton("全屏显示", "fullScreen", 4);
        excelCtrl.addCustomToolButton("关闭全屏", "cancelFullScreen", 4);
        excelCtrl.addCustomToolButton("保存文件", "saveFile", 1);
        
        //关闭菜单栏
        excelCtrl.setMenubar(false);
        //关闭Office工具栏
        excelCtrl.setOfficeToolbars(false);
        excelCtrl.setOfficeVendor(OfficeVendorType.WPSOffice);

//        String documentPath = Paths.get(webPath(path)).toUri().toString();
        excelCtrl.webOpen("D:\\\\ssc\\\\简历.doc", OpenModeType.docNormalEdit, "周季军");

        excelCtrl.setTagId("excelCtrl");
        return "excel/edit";
    }

    @RequestMapping(value = "/save/file", method = RequestMethod.POST)
    public void saveFile(@ModelAttribute(Global.SESSION_USER_KEY) Users user,
                         HttpServletRequest request, HttpServletResponse response)  {
        // 新文档直接覆盖
    	Path srcPath = Paths.get(webPath("D:\\\\ssc\\\\简历.doc"));
        FileSaver fileSaver = new FileSaver(request, response);
        fileSaver.saveToFile(srcPath.toString());
        fileSaver.close();
    }
    
/*
    *//**
     * 只读操作将读取最新已保存的文档，不使用锁机制
     *//*
    @RequestMapping(value = "/read/only", method = RequestMethod.GET)
    public String readOnly(@ModelAttribute(Global.SESSION_USER_KEY) User user, Model model,
                           HttpServletRequest request) {
        if (user == null) {
            // 用户为空，跳转到 index 进行逻辑处理
            return "redirect:/index";
        }

        Doc doc = user.getDoc();

        if (doc.isEditable()) { // 文档可编辑，直接重定向到可编辑页面
            return "redirect:/index/edit";
        }


        PageOfficeCtrl ctrl = this.create(request);
        ctrl.addCustomToolButton("全屏显示", "fullScreen", 4);
        ctrl.addCustomToolButton("关闭全屏", "cancelFullScreen", 4);
        ctrl.setMenubar(false);
        ctrl.setOfficeToolbars(false);

        String documentPath = Paths.get(webPath(doc.getDocPath())).toUri().toString();
        ctrl.webOpen(documentPath, OpenModeType.xlsReadOnly, user.getUsername());

        ctrl.setTagId("excelCtrl");

        return "excel/readOnly";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute(Global.SESSION_USER_KEY) User user, Model model,
                       HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            // 用户为空，跳转到 index 进行逻辑处理
            return "redirect:/index";
        }
        Doc doc = user.getDoc();

        DocEditor.LOCK.lock();
        try {
            String editor = getEditor(doc.getDocPath());
            if (editor == null) {
                DocEditor.markDocEditingStatus(doc.getDocPath(), user.getUsername());
            }
        } finally {
            DocEditor.LOCK.unlock();
        }

        if (!doc.isEditable()) { // 该用户对该文档不可编辑，直接重定向到只读页面
            return "redirect:/index/read/only";
        }

        PageOfficeCtrl excelCtrl = this.create(request);

        excelCtrl.addCustomToolButton("全屏显示", "fullScreen", 4);
        excelCtrl.addCustomToolButton("关闭全屏", "cancelFullScreen", 4);
        excelCtrl.addCustomToolButton("保存文件", "saveFile", 1);

        String documentPath = Paths.get(webPath(doc.getDocPath())).toUri().toString();
        excelCtrl.webOpen(documentPath, OpenModeType.xlsNormalEdit, user.getUsername());

        excelCtrl.setTagId("excelCtrl");
        return "excel/edit";
    }


    // 写操作使用锁，并且这把锁将是全局锁，文档写状态保存在全局的 {@link ConcurrentHashMap} 中
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(@ModelAttribute(Global.SESSION_USER_KEY) User user, Model model,
                       HttpServletRequest request, HttpServletResponse response) {
        if (user == null) {
            // 用户为空，跳转到 index 进行逻辑处理
            return "redirect:/index";
        }

        Doc doc = user.getDoc();

        DocEditor.LOCK.lock();
        try {
            String editor = DocEditor.getEditor(doc.getDocPath());
            if (editor != null && !StringUtils.equals(editor, user.getUsername())) {
                model.addAttribute("editor", editor);
                return "excel/editing";
            } else {
                // 标记该文档已经被 XXX 用户以编辑方式打开了
                DocEditor.markDocEditingStatus(doc.getDocPath(), user.getUsername());
            }
        } finally {
            DocEditor.LOCK.unlock();
        }

        if (!doc.isEditable()) { // 该用户对该文档不可编辑，直接重定向到只读页面
            return "redirect:/index/read/only";
        }

        PageOfficeCtrl excelCtrl = this.create(request);

        excelCtrl.addCustomToolButton("全屏显示", "fullScreen", 4);
        excelCtrl.addCustomToolButton("关闭全屏", "cancelFullScreen", 4);
        excelCtrl.addCustomToolButton("保存文件", "saveFile", 1);

        String documentPath = Paths.get(webPath(doc.getDocPath())).toUri().toString();
        excelCtrl.webOpen(documentPath, OpenModeType.xlsNormalEdit, user.getUsername());

        excelCtrl.setTagId("excelCtrl");
        return "excel/edit";
    }


    @RequestMapping(value = "/save/file", method = RequestMethod.POST)
    public void saveFile(@ModelAttribute(Global.SESSION_USER_KEY) User user,
                         HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (user == null || !user.getDoc().isEditable()) {
            // 用户为空或者相应的文档不可编辑，直接抛出权限不足异常
            throw new NoPermissionException("当前用户尚未登陆或者登陆的用户对该文档不具备可编辑的权限");
        }

        DocEditor.LOCK.lock();
        Doc doc = user.getDoc();
        try {
            String editor = DocEditor.getEditor(doc.getDocPath());
            if (!StringUtils.equals(editor, user.getUsername())) {
                FileSaver fileSaver = new FileSaver(request, response);
                String result = String.format("Can not save file! Because %s has already editing the file before your editing!", editor);
                fileSaver.setCustomSaveResult(result);
                fileSaver.close();
                return;
            }
        } finally {
            DocEditor.LOCK.unlock();
        }

        // 备份旧文档
        Path srcPath = Paths.get(webPath(doc.getDocPath()));
        String docName = doc.getDocName();
        int indexOfDot = docName.indexOf('.');
        String basename = docName.substring(0, indexOfDot);
        String ext = docName.substring(indexOfDot);
        Path bakPath = Paths.get(webPath(Global.WEB_BAK_PATH), basename + '_' + JodaUtil.timestamp() + "_bak" + ext);
        FileUtils.forceParentExists(bakPath);
        Files.copy(srcPath, bakPath, StandardCopyOption.REPLACE_EXISTING);

        // 添加更新记录
        docRepo.historyLog(user, DateTime.now(), bakPath.toString());

        // 消除文档的编辑状态
        DocEditor.LOCK.lock();
        try {
            DocEditor.removeDocEditingStatus(doc.getDocPath());
        } finally {
            DocEditor.LOCK.unlock();
        }

        // 新文档直接覆盖
        FileSaver fileSaver = new FileSaver(request, response);
        fileSaver.saveToFile(srcPath.toString());
        fileSaver.close();
    }*/

}
