package com.blog_zheng.myblog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // define a normal aspect.
    // All methods of all classes under the controller package
    // are considered part of this pointCut.
    @Pointcut("execution(* com.blog_zheng.myblog.controller.*.*(..))")
    public void logging() {
    }

    @Before("logging()")
    public void doBefore(JoinPoint jp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethodName = jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName();
        Object[] args = jp.getArgs();
        RequestLogging rl = new RequestLogging(url, ip, classMethodName, args);
        logger.info("Request : {}", rl);
    }

    @After("logging()")
    public void doAfter() {
        logger.info("-----doAfter-----");
    }

    @AfterReturning(returning = "result", pointcut = "logging()")
    public void doAfterReturn(Object result) {
        logger.info("-----Result : {}", result);
    }

    private class RequestLogging {
        private String url;
        private String ip;
        private String classMethodName;
        private Object[] args;

        public RequestLogging(String url, String ip, String classMethodName, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethodName = classMethodName;
            this.args = args;
        }

        @Override
        public String toString() {
            return "RequestLogging{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethodName='" + classMethodName + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
