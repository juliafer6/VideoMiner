package aiss.videominer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class webConfig implements WebMvcConfigurer {

    // Traemos el interceptor que creamos antes
    @Autowired
    private ApiKeyInterceptor apiKeyInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        // Aquí registramos el interceptor
        registry.addInterceptor(apiKeyInterceptor)
                .addPathPatterns("/videominer/**")// Se aplica a cualquier ruta que empiece por /videominer/
                .excludePathPatterns("/h2-ui/**");// Dejamos libre el acceso a la consola de la base de datos H2
    }
}
