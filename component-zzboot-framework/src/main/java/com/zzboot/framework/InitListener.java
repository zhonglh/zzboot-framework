package com.zzboot.framework;

import com.zzboot.framework.core.Constant;
import com.zzboot.framework.core.IModuleAutoRun;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.util.Map;

/**
 *
 * @author Administrator
 */
@Slf4j
@WebListener
public class InitListener implements javax.servlet.ServletContextListener {


    @Override
    public void contextInitialized(ServletContextEvent sce) {

        Constant.servletRealPath = sce.getServletContext().getRealPath("/");
        sce.getServletContext().setAttribute("startServerTime" , System.currentTimeMillis());

        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
        Map<String, IModuleAutoRun> map = webApplicationContext.getBeansOfType(IModuleAutoRun.class);

        if(map != null && !map.isEmpty()){
            for(Map.Entry<String, IModuleAutoRun> entry : map.entrySet()){
                try{
                    entry.getValue().initiallzed(sce);
                }catch(Exception e){
                    log.error(e.getMessage(),e);
                }
            }
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
