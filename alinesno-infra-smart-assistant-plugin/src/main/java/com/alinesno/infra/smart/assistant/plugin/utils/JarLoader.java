package com.alinesno.infra.smart.assistant.plugin.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Component
public class JarLoader {

    @Autowired
    private ApplicationContext context;

    /**
     * 加载jar包
     * @param jarUrl
     * @param targetDirectory
     * @throws Exception
     */
    public void loadJarFromExternalURL(String jarUrl, String targetDirectory) throws Exception {

        // 加载jar包到Spring容器
        File file = new File(targetDirectory);
        ClassLoader cl = new URLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
        File jarFile = new File(jarUrl);
        try (JarFile jf = new JarFile(jarFile)) {
            Enumeration<JarEntry> entries = jf.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().length() - 6);
                    Class<?> loadedClass = cl.loadClass(className);
                    // 注册到Spring容器，使用默认的bean名称
                    context.getAutowireCapableBeanFactory().applyBeanPostProcessorsAfterInitialization(loadedClass.newInstance(), StringUtils.uncapitalize(loadedClass.getSimpleName()));
                }
            }
        }

    }
}
