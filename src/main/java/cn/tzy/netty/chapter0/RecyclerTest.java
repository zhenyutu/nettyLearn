package cn.tzy.netty.chapter0;

import io.netty.util.Recycler;

/**
 * Created by tuzhenyu on 17-11-17.
 * @author tuzhenyu
 */
public class RecyclerTest {
    static class WrapRecycler{

        private boolean tmp ;
        private final static Recycler<WrapRecycler> RECYCLER = new Recycler<WrapRecycler>() {
            @Override
            protected WrapRecycler newObject(Handle<WrapRecycler> handle) {
                return new WrapRecycler(handle);
            }
        };

        Recycler.Handle<WrapRecycler> handle;
        WrapRecycler(Recycler.Handle<WrapRecycler> handle){
            this.handle = handle;
            this.tmp = false;
        }

        static WrapRecycler getInstance(){
            return RECYCLER.get();
        }

        void recycle(){
            this.tmp = false;
            handle.recycle(this);
        }

        public boolean getTmp(){
            return tmp;
        }

        public void setTmp(boolean tmp){
            this.tmp = tmp;
        }
    }


    public static void main(String[] args) {
        WrapRecycler instance = WrapRecycler.getInstance();
        System.out.println(instance.hashCode());
        System.out.println(instance.getTmp());
        instance.setTmp(true);
        System.out.println(instance.getTmp());
        instance.recycle();
        instance = WrapRecycler.getInstance();
        System.out.println(instance.hashCode());
        System.out.println(instance.getTmp());
    }
}
