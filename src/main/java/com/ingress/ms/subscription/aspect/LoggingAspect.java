package com.ingress.ms.subscription.aspect;

import com.ingress.ms.subscription.annotation.LogIgnore;
import com.ingress.ms.subscription.logger.CustomLogger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;

import static com.ingress.ms.subscription.mapper.factory.ObjectMapperFactory.OBJECT_MAPPER_FACTORY;

@Component
@Aspect
public class LoggingAspect {

    @Around(value = "within(@com.ingress.ms.subscription.annotation.Log *)")
    public Object around (ProceedingJoinPoint joinPoint) throws Throwable {
        var signature = ((MethodSignature) joinPoint .getSignature ()) ;
        var log = CustomLogger.getLogger(joinPoint.getTarget().getClass());
        var parameters = buildParameters (signature, joinPoint.getArgs ());
        logEvent ("start", log, signature, parameters) ;
        Object response;
        try {
            response = joinPoint.proceed();
        } catch (Throwable throwable) {
                logEvent ("error", log, signature, parameters);
                throw throwable;
        }
        logEndAction(log, signature, response);
        return response;
    }

    private StringBuilder buildParameters (MethodSignature signature, Object[] args) {
        var builder = new StringBuilder();
        var parameters = signature.getMethod ().getParameters();
        for (int i = 0; i < parameters. length; i++) {
            var currentParam = parameters[i];
            if(currentParam.isAnnotationPresent(LogIgnore.class)) continue;
            builder
                    .append (" ")
                    .append (currentParam.getName())
                    .append (":")
                    .append (writeObjectAsString(args [i], currentParam)) ;
        }
        return builder;
    }

    private String writeObjectAsString(Object obj, Parameter parameter){
        try {
            return parameter.getType().getTypeName() + OBJECT_MAPPER_FACTORY.getLogIgnoreObjectMapper()
                    .writeValueAsString(obj)
                    .replace("\"", " ");
        } catch (Exception e){
            return obj.toString();
        }
    }

    private void logEvent(String eventName, CustomLogger log, MethodSignature signature, StringBuilder parameters){
        log.info("ActionLog.{}.{}{}", signature.getName(), eventName, parameters);
    }

    private void logEndAction(CustomLogger log, MethodSignature signature, Object response) {
        if (void.class.equals (signature.getReturnType ())) log. info("ActionLog.{}.end", signature.getName());
        else log.info ("ActionLog.{}.end {}", signature.getName (), response);
    }
}