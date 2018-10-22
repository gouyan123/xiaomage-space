package com.gupao.designpatterndemos.facade;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class FacadeDemo {

    public static void main(String[] args) {


    }

    private static class ServiceA {

        public void save(){

        }

    }

    private static class ServiceB {

        public void delete(){

        }

    }

    private static class ServiceFacade {
        // 原子操作
        private ServiceA serviceA;
        // 原子操作
        private ServiceB serviceB;

        public void service(){
            serviceB.delete();
            serviceA.save();
        }

    }
}
