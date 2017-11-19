package cn.tzy.chapter0;

import io.netty.util.concurrent.*;

/**
 * Created by tuzhenyu on 17-11-18.
 * @author tuzhenyu
 */
public class PromiseTest {
    public static void main(String[] args) {
        final DefaultEventExecutor executor = new DefaultEventExecutor();
        Promise<Integer> promise = executor.newPromise();
        promise.addListener(new GenericFutureListener<Future<? super Integer>>() {
            public void operationComplete(Future<? super Integer> future) throws Exception {
                System.out.println("promise is finish");
            }
        });
        System.out.println("after promise");
        promise.setSuccess(10);

//        try{
//            Thread.sleep(1000);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        System.out.println("after set promise");
    }
}
