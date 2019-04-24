package com.tom.framework.servlet;

import com.tom.framework.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public class TomDispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private List<String> classNames = new ArrayList<>();

    private Map<String, Object> iocMap = new ConcurrentHashMap<>();

    private List<Handler> handlerMapping = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 6.运行
            doDispatcher(req, resp);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws IOException, InvocationTargetException, IllegalAccessException {
        Handler handler = getHandler(req);
        if (null == handler) {
            resp.getWriter().write("404 not Found !!!");
            return;
        }
        Object[] paramValues = new Object[handler.getMethod().getParameterTypes().length];

        paramValues = buildParamInfo(req, resp, handler, paramValues);

        Object retValue = handler.getMethod().invoke(handler.getController(), paramValues);
        resp.getWriter().write(null != retValue? retValue.toString() : "无返回");
    }

    private Object[] buildParamInfo(HttpServletRequest req, HttpServletResponse resp, Handler handler, Object[] paramValues) {
        System.out.println("uri=" + req.getRequestURI());
        System.out.println("parameterNames=" + req.getParameterNames());
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println("param key = " + entry.getKey()
                    + ",  value = " + Arrays.asList(entry.getValue())
                    + " or " + Arrays.toString(entry.getValue())
                    + ", formatValue = " + Arrays.toString(entry.getValue()).replaceAll("\\[|\\]", ""));
            if (!handler.getParamIndexMapping().containsKey(entry.getKey())) continue;
            int paramIndex = handler.getParamIndexMapping().get(entry.getKey());
            paramValues[paramIndex] = conver(handler.getMethod().getParameterTypes()[paramIndex], entry.getValue());
        }
        if (handler.getParamIndexMapping().containsKey(HttpServletRequest.class.getName())) {
            int reqIndex = handler.getParamIndexMapping().get(HttpServletRequest.class.getName());
            paramValues[reqIndex] = req;
        }
        if (handler.getParamIndexMapping().containsKey(HttpServletResponse.class.getName())) {
            int respIndex = handler.getParamIndexMapping().get(HttpServletResponse.class.getName());
            paramValues[respIndex] = resp;
        }
        return paramValues;
    }

    private Object conver(Class<?> parameterType, String[] value) {
        String formatValue = Arrays.toString(value).replaceAll("\\[|\\]", "");
        if (Integer.class == parameterType) {
            return Integer.valueOf(formatValue);
        }
        if (Double.class == parameterType) {
            return Double.valueOf(formatValue);
        }
        return formatValue;
    }

    private Handler getHandler(HttpServletRequest req) {
        for (Handler handler : handlerMapping) {
            if (handler.getUri().equals(req.getRequestURI())) return handler;
        }
        return null;
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        // 1. 加载配置文件
        doLoadConfig(config.getInitParameter("contextConfigLocation"));
        // 2. 扫描相关类
        doScanner(contextConfig.getProperty("scanPackage"));
        // 3. 初始化类，放到IOC容器中
        doInstance();
        // 4. 依赖注入
        doAutowired();
        // 5. 初始化HandlerMapping
        initHandlerMapping();
    }


    private void initHandlerMapping() {

        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Class<?> aClass = entry.getValue().getClass();
            if (!aClass.isAnnotationPresent(TomRequestMapping.class)) {return;}

            String preUrl = "";
            String url = "";
            if (aClass.isAnnotationPresent(TomRequestMapping.class)) {
                preUrl = aClass.getAnnotation(TomRequestMapping.class).value();
            }

            for (Method method : aClass.getMethods()) {
                if (!method.isAnnotationPresent(TomRequestMapping.class)) {continue;}
                url = preUrl + method.getAnnotation(TomRequestMapping.class).value();
                handlerMapping.add(new Handler(url, entry.getValue(), method));
                System.out.println("Mapped: " + url + ", " + method);
            }
        }
    }

    private void doAutowired() {
        for (Map.Entry<String, Object> entry : iocMap.entrySet()) {
            Field[] declaredFields = entry.getValue().getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (declaredField.isAnnotationPresent(TomAutowired.class)) {
                    try {
                        declaredField.setAccessible(true);
                        System.out.println(entry.getValue());
                        System.out.println(declaredField.getName());
                        System.out.println(iocMap.get(declaredField.getName()));
                        String name = declaredField.getName().replaceFirst(String.valueOf(declaredField.getName().charAt(0)), String.valueOf(declaredField.getName().charAt(0)).toUpperCase());
                        declaredField.set(entry.getValue(),iocMap.get(name));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void doInstance() {
        if (classNames.isEmpty()) {return;}
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                if (aClass.isAnnotationPresent(TomController.class) ||
                        aClass.isAnnotationPresent(TomService.class)) {
                    Object newInstance = aClass.newInstance();
                    iocMap.put(aClass.getSimpleName(), newInstance);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getClassLoader().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        System.out.println(url);
        File classPath = new File(url.getFile());
        for (File file: classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                System.out.println();
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                classNames.add(className);
                System.out.println();
            }
        }

    }

    private void doLoadConfig(String contextConfigLocation) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(contextConfigLocation);
        try {
            contextConfig.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    System.out.println();
                    e.printStackTrace();
                }
            }
        }
    }

    public class Handler {
        private String uri;
        private Object controller;
        private Method method;

        private Map<String, Integer> paramIndexMapping;

        public Handler(String uri, Object controller, Method method) {
            this.uri = uri;
            this.controller = controller;
            this.method = method;
            paramIndexMapping = new HashMap<>();
            putIndexMapping(method);
        }

        public String getUri() {
            return uri;
        }

        public Object getController() {
            return controller;
        }

        public Method getMethod() {
            return method;
        }

        public Map<String, Integer> getParamIndexMapping() {
            return paramIndexMapping;
        }

        private void putIndexMapping(Method method) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            for (int i = 0; i < parameterTypes.length; i++) {
                if (HttpServletRequest.class == parameterTypes[i] ||
                        HttpServletResponse.class == parameterTypes[i]) {
                    paramIndexMapping.put(parameterTypes[i].getName(), i);
                }
            }
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                for (Annotation parameterAnnotation : parameterAnnotations[i]) {
                    if (parameterAnnotation instanceof TomRequestParam) {
                        String paramName = ((TomRequestParam) parameterAnnotation).value();
                        if (!"".equals(paramName.trim())) paramIndexMapping.put(paramName, i);
                    }
                }
            }
        }
    }
}