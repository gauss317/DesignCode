package com.go.gauss.spring;

// @Aspect:给spring说明这个组件是切面类

/*
 * try{
 * 		@Before
 * 		Result = method.invoke(obj,args);
 * 		@AfterReturing
 * }catch(e){
 * 		@AfterReturing
 * }finally{
 * 		@After
 * }
 * */


/*
 * 五个注解：
 * @Before:在目标方法之前运行；                       前置通知
 * @After:在目标方法结束之后运行；                     后置通知
 * @AfterReturing:在目标方法正常返回之后；				返回通知
 * @AfterThrowing:在目标方法抛出异常之后运行；			异常通知
 *
 * @Around:环绕										环绕通知

 * */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectDemo {
    @Pointcut("execution(public * com.go.gauss.spring.*.*(..))")
    public void myPoint() {
    }

    @Around(value = "myPoint()")
    public Object myAround(ProceedingJoinPoint pointPoint) throws Throwable {
        Object[] args = pointPoint.getArgs();
        System.out.println(args.length);;
        String name = pointPoint.getSignature().getName();
        Object proceed = null;
        try {
            // @Before
            System.out.println("【环绕前置通知】【" + name + "方法开始】");
            /*这句相当于method.invoke(obj,args)，通过反射来执行接口中的方法;因此在反射执行完方法后会有一个返回值proceed*/
            proceed = pointPoint.proceed();
            // @AfterReturning
            System.out.println("【环绕返回通知】【" + name + "方法返回，返回值：" + proceed + "】");
        } catch (Exception e) {
            // @AfterThrowing
            System.out.println("【环绕异常通知】【" + name + "方法异常，异常信息：" + e + "】");
        } finally {
            // @After
            System.out.println("【环绕后置通知】【" + name + "方法结束】");
        }
        return proceed;
    }

    public static void main(String[] args) {
        new AspectDemo();
    }
}
