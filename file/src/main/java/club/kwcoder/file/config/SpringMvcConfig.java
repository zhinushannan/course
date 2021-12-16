package club.kwcoder.file.config;/**
 * @author zhinushannan
 * @date 2021/12/16 23:40
 * @subject
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: zhinushannan
 * @create: 2021/12/16  23:40
 * @description:
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/f/**").addResourceLocations("file:C:/CODE/course/file_dir/");

        /*
        http://127.0.0.1:9003/file/f/teacher/omuhoL5d-1_BigPic.png
         */

    }
}
