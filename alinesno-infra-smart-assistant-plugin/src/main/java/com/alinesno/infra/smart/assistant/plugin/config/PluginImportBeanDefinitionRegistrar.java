package com.alinesno.infra.smart.assistant.plugin.config;

import com.alinesno.infra.smart.assistant.plugin.utils.ClassLoaderUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 启动时注册bean
 */
@Slf4j
@Component
public class PluginImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, EnvironmentAware {

    @Autowired
    private AssistantPluginProperties pluginProperties ;

    @Autowired
    private ApplicationContext context;

//    private List<URL> jarList = new ArrayList<>() ;

//    @SneakyThrows
//    @Override
//    public void registerBeanDefinitions(@NotNull AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//
//        for(URL url : jarList){
////            ClassLoader classLoader = ClassLoaderUtil.getClassLoader(url.getPath());
////            assert classLoader != null;
//
//            File jarFile = new File(url.getPath());
//            log.debug("jarFile = {} , path = {}" , jarFile , url.getPath());
//
//            // 使用反射机制遍历外部jar中的所有类
//            ClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader());
//
//            try (JarFile jf = new JarFile(jarFile)) {
//                Enumeration<JarEntry> entries = jf.entries();
//                while (entries.hasMoreElements()) {
//                    JarEntry jarEntry = entries.nextElement();
//
//                    log.debug("jar Entry = {}" , jarEntry);
//                }
//            }
//
//        }

//        ClassLoader classLoader = ClassLoaderUtil.getClassLoader(targetUrl);
//
//        assert classLoader != null;
//        Class<?> clazz = classLoader.loadClass(pluginClass);
//
//        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
//        BeanDefinition beanDefinition = builder.getBeanDefinition();
//        registry.registerBeanDefinition(clazz.getName(), beanDefinition);
//        log.info("register bean [{}],Class [{}] success.", clazz.getName(), clazz);

//    }

    @SneakyThrows
    @Override
    public void setEnvironment(@NotNull Environment environment) {

        log.debug("pluginProperties = {}" , pluginProperties);

        File directory = new File(pluginProperties.getPath());
        File[] jarFiles = directory.listFiles((dir, name) -> name.endsWith(".jar"));


        if (jarFiles != null) {
            for (File jarFile : jarFiles) {

                ClassLoader cl = new URLClassLoader(new URL[]{jarFile.toURI().toURL()}, getClass().getClassLoader());

                URL jarUrl = jarFile.toURI().toURL();
                log.debug("jarFile = {}" , jarUrl);

                try (JarFile jf = new JarFile(jarFile)) {
                    Enumeration<JarEntry> entries = jf.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();

                        if (jarEntry.getName().endsWith(".class")) {
                            String className = jarEntry.getName().replace("/", ".").substring(0, jarEntry.getName().length() - 6);

                            log.debug("className = {}" , className);

                            Class<?> loadedClass = cl.loadClass(className);
                            // 注册到Spring容器，使用默认的bean名称
                            context.getAutowireCapableBeanFactory()
                                    .applyBeanPostProcessorsAfterInitialization(
                                            loadedClass.newInstance(),
                                            StringUtils.uncapitalize(loadedClass.getSimpleName()));
                        }
                    }
                }
            }
        }
    }

}
