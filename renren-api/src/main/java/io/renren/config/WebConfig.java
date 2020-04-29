package io.renren.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * io.renren.config
 *
 * @author JunCheng He
 * @date 2019/3/21 14:57
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Value("${apiupload.path}")
    private String upladPath;
    /**
     * 访问外部文件配置
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:///"+upladPath);
    }
}
