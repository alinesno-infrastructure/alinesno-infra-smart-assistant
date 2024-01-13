package com.alinesno.infra.smart.assistant.plugin.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;

/**
 * 加载服务类
 */
@Slf4j
public class ClassLoaderUtil {

    /**
     * 从插件中心下载jar到指定的目录结构
     * @param jarUrl
     * @param targetDirectory
     */
    public static void downloadJar(String jarUrl, String targetDirectory) {
        try {
            URL url = new URL(jarUrl);
            URLConnection conn = url.openConnection();

            // 打开输入流
            try (InputStream in = conn.getInputStream();
                 BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(targetDirectory + "/downloaded.jar"))) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("Jar downloaded to: " + targetDirectory + "/downloaded.jar");
        } catch (IOException e) {
            log.error("服务异常:{}" , e.getMessage());
        }
    }

    public static ClassLoader getClassLoader(String url) {
        try {
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);

            if (!method.isAccessible()) {
                method.setAccessible(true);
            }

            URLClassLoader classLoader = new URLClassLoader(new URL[]{}, ClassLoaderUtil.class.getClassLoader());
            method.invoke(classLoader, new URL(url));
            return classLoader;
        } catch (Exception e) {
            log.error("getClassLoader-error", e);
            return null;
        }
    }
}
