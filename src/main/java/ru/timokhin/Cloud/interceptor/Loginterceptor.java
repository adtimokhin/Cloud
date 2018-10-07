package ru.timokhin.Cloud.interceptor;

import ru.timokhin.Cloud.api.annotations.Loggable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Loggable
@Interceptor
public class Loginterceptor {
    @AroundInvoke
    public Object intercept(final InvocationContext context)throws Exception{
        System.out.println(context.getTarget().getClass().getSimpleName()+" : "+context.getMethod().getName());
        return context.proceed();
    }
}
