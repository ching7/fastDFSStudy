package com.cyn.test;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: fastdfs-demo
 * @Package: com.cyn.test
 * @Description: note
 * @Author: hspcadmin
 * @CreateDate: 2019/11/18 19:20
 * @UpdateUser: hspcadmin
 * @UpdateDate: 2019/11/18 19:20
 * @UpdateRemark: The modified content
 * @Date
 * @Version: 1.0
 * <p>
 * Copyright © 2019 Hundsun Technologies Inc. All Rights Reserved
 **/
public class FastDfsTest {

    /**
     * 文件上传
     */
    @Test
    public  void fileUpload() {
        System.out.println("java.version=" + System.getProperty("java.version"));
        try {
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

            //文件元信息
            NameValuePair[] metaList = new NameValuePair[1];
            metaList[0] = new NameValuePair("fileName", "C:\\Users\\hspcadmin\\Desktop\\cat.jpg");

            //执行上传
            String fileId = client.upload_file1("C:\\Users\\hspcadmin\\Desktop\\cat.jpg", "jpg", metaList);
            System.out.println("upload success. file id is: " + fileId);

            //关闭tracker服务
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 查询文件
     */
    @Test
    public void queryFile(){
        System.out.println("java.version=" + System.getProperty("java.version"));
        try {
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

            //查询文件
            FileInfo fileInfo = client.query_file_info("group1","M00/00/00/wKjRgF3PSxiAHuHtAAFl33KnvNs253.jpg");
            FileInfo fileInfo1 = client.query_file_info1("group1/M00/00/00/wKjRgF3PSxiAHuHtAAFl33KnvNs253.jpg");
            System.out.println(fileInfo1);
            //查询文件元信息
            NameValuePair[] fileInfos =  client.get_metadata1("group1/M00/00/00/wKjRgF3PSxiAHuHtAAFl33KnvNs253.jpg");
            System.out.println(fileInfos);

            //关闭tracker服务
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 文件下载
     */
    @Test
    public void fileDownload(){
        try {
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

            //下载文件
            byte[] fileBytes = client.download_file1("group1/M00/00/00/wKjRgF3PSxiAHuHtAAFl33KnvNs253.jpg");
            File file = new File("D:/chenyn.jpg");
            FileOutputStream  fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(fileBytes);
            fileOutputStream.close();
            //关闭tracker服务
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
