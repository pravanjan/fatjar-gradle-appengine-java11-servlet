package com.pravanjan.main;

import com.google.common.reflect.ClassPath;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.lang.annotation.Annotation;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private final static String packageName ="com.pravanjan.controllers";
    private final static Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String args[])throws Exception{

        Server server = new Server(8080);
        HandlerList handlers = new HandlerList();
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");

        registerServlets(context);

        handlers.addHandler(context);
        server.setHandler(handlers);
        server.start();
        server.join();
    }



    private static void registerServlets(ServletContextHandler context)  {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {

                if (info.getName().startsWith(packageName)) {
                    final Class<?> object = info.load();
                    if(object.isAnnotationPresent(WebServlet.class)){
                        Annotation annotation = object.getAnnotation(WebServlet.class);
                        WebServlet webServlet = (WebServlet) annotation;

                        for (String pattern : webServlet.urlPatterns()) {
                            HttpServlet servlet = (HttpServlet) object.getConstructor().newInstance();
                            context.addServlet(new ServletHolder(servlet), pattern);
                        }

                    }

                }
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage(),e.getCause());
        }

    }


}
