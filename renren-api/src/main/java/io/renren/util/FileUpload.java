package io.renren.util;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;


/**
 * io.renren.until
 * 文件上传工具类
 *
 * @author JunCheng He
 * @date 2019/3/21 10:56
 */
@Component
public class FileUpload {

    /**
     * 文件上传
     *
     * @param file
     * @return 文件名
     */
    public static String upload(MultipartFile file, String path, String fileName) {
        File targetFile = new File(path);
        String name = file.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        //路径文件夹是否存在 若不存在则创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(path + fileName + "." + suffix);
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName + "." + suffix;
    }
}
