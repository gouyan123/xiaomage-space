package com.gupao.designpatterndemos.composite;

import java.util.ArrayList;
import java.util.Collection;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class CompositeDemo {

    private static interface A {
        void save();
    }

    private static class AImpl implements A {
        @Override
        public void save() {
            System.out.println("save()");
        }
    }

    private static class CompositeA
//            implements A
    {

        private Collection<A> values = new ArrayList();

//        @Override
        public void save() {
            for (A a : values) {
                a.save();
            }
        }
    }


}
