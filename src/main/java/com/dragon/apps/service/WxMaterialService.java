package com.dragon.apps.service;

import com.dragon.apps.model.WxAdmin;
import com.dragon.apps.model.WxMaterial;
import com.dragon.apps.utils.ConstantsUtils;
import com.dragon.apps.utils.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: liuxi
 * Date: 11/7/14
 * Time: 8:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class WxMaterialService implements Serializable {
    private static final long serialVersionUID = 1L;

    public WxMaterial uploadMaterialFile(HttpServletRequest httpRequest) {
        WxMaterial wxMaterial = new WxMaterial();

        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(5*1024);
        //设置临时文件目录
        factory.setRepository(new File(ConstantsUtils.MATERIAL_PATH_TEMP));
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(httpRequest);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    //文件名
                    String fileName = item.getName();
                    //检查文件后缀格式
                    String fileEnd = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

                    String uuid = UUID.randomUUID().toString();
                    //真实上传路径
                    StringBuffer sbRealPath = new StringBuffer();
                    sbRealPath.append(ConstantsUtils.MATERIAL_PATH).append(uuid).append(".").append(fileEnd);

                    String realPathName = sbRealPath.toString();
                    //写入文件
                    File file = new File(sbRealPath.toString());
                    item.write(file);
                    wxMaterial.setFilePath(String.format("%s%s.%s", ConstantsUtils.MATERIAL_PATH_WEB, uuid, fileEnd));
                } else {
                    String name=item.getFieldName();

                    String value= item.getString();
                    wxMaterial.set(name, value);
                }
            }

            return wxMaterial;
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
