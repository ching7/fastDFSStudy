package com.cyn.controller;

import com.cyn.bean.UploadFile;
import org.csource.fastdfs.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: fastdfs-demo
 * @Package: com.cyn.controller
 * @Description: note
 * @Author: hspcadmin
 * @CreateDate: 2019/11/20 19:15
 * @UpdateUser: hspcadmin
 * @UpdateDate: 2019/11/20 19:15
 * @UpdateRemark: The modified content
 * @Date
 * @Version: 1.0
 * <p>
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 **/
@RestController
@RequestMapping("/fileSystem")
public class FileServer {
    @Value("${upload_location}")
    private String uploadLocation;

    @RequestMapping("/test")
    @ResponseBody
    public void test(){
        System.out.println("测试");
    }

    @RequestMapping("/upload")
    @ResponseBody
    public UploadFile upload(@RequestParam("file") MultipartFile file){
        String originalFileName = file.getOriginalFilename();
        // 获取文件拓展名
        String extention = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extention;
        File f = new File(uploadLocation + newFileName);
        UploadFile uploadFile = new UploadFile();

        try {
            file.transferTo(f);
            String local_filename = f.getAbsolutePath();
            // 加载配置文件
            //ClientGlobal.init(conf_filename);
            ClientGlobal.initByProperties("fastdfs-client.properties");
            System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
            System.out.println("charset=" + ClientGlobal.g_charset);

            //创建tracker客户端
            TrackerClient tracker = new TrackerClient();
            TrackerServer trackerServer = tracker.getConnection();
            StorageServer storageServer = null;

            //定义一个storage客户端
            StorageClient1 client = new StorageClient1(trackerServer, storageServer);
            String fileId = client.upload_file1(local_filename, null,null);

            //文件在文件系统中的路径
            uploadFile.setFilePath(fileId);
            uploadFile.setFileId(fileId);
            long size = file.getSize();//文件大小
            //文件大小
            uploadFile.setFileSize(size);
            String contentType = file.getContentType();
            //文件类型
            uploadFile.setFileType(contentType);
            //文件名称
            if (uploadFile.getFileName() == null || uploadFile.getFileName().equals("")) {
                //如果没有传入文件名称则存储文件的原始名称
                uploadFile.setFileName(file.getOriginalFilename());
            }
            //删除web服务器上的文件
            f.deleteOnExit();
            System.out.println("upload success. file id is: " + fileId);

            //关闭tracker服务
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return uploadFile;
    }
}
