package com.cyn.test;

import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;

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
public class TestFastDfsUpload {
    /**
     * entry point
     *
     * @param args comand arguments
     *             <ul><li>args[0]: config filename</li></ul>
     *             <ul><li>args[1]: local filename to upload</li></ul>
     */
    public static void main(String args[]) {
        /*if (args.length < 2) {
            System.out.println("Error: Must have 2 parameters, one is config filename, "
                    + "the other is the local filename to upload");
            return;
        }*/

        System.out.println("java.version=" + System.getProperty("java.version"));

       /* String conf_filename = args[0];
        String local_filename = args[1];*/

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

            //文件下载
            /*int i = 0;
            while (i++ < 10) {
                byte[] result = client.download_file1(fileId);
                System.out.println(i + ", download result is: " + result.length);
            }*/

            //关闭tracker服务
            trackerServer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
