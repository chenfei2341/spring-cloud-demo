/*
 * <pre>
 * 标  题: UnicodePropertySourceLoader.java.
 * 版权所有: 版权所有(C)2001-2019
 * 公   司: 深圳中兴力维技术有限公司
 * 内容摘要: // 简要描述本文件的内容，包括主要模块、函数及其功能的说明
 * 其他说明: // 其它内容的说明
 * 完成日期: 2019-08-08 // 输入完成日期
 * </pre>
 * <pre>
 * 修改记录1:
 *    修改日期：
 *    版 本 号：
 *    修 改 人：
 *    修改内容：
 * </pre>
 * @version 1.0
 * @author Chenfei
 */

package com.znv.peim;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Chenfei
 */
public class UnicodePropertySourceLoader implements PropertySourceLoader {
    Logger logger = LoggerFactory.getLogger(UnicodePropertySourceLoader.class);

    @Override
    public String[] getFileExtensions() {
        return new String[]{"properties", "xml"};
    }

    @Override
    public List<PropertySource<?>> load(String name, Resource resource) throws IOException {

        Properties properties = getProperties(resource);
        if (!properties.isEmpty()) {
            PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(name, properties);
            return Collections.singletonList(propertiesPropertySource);
        }

        return Collections.emptyList();
    }

    private Properties getProperties(Resource resource) {
        Properties properties = new Properties();
        try (InputStream inputStream = resource.getInputStream()) {
            properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            logger.error("Load resource inputStream failed.", e);
        }
        return properties;
    }
}
