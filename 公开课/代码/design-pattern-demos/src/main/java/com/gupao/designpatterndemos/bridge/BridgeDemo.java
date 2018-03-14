package com.gupao.designpatterndemos.bridge;

/**
 * TODO...
 *
 * @author 小马哥 QQ 1191971402
 * @copyright 咕泡学院出品
 * @since 2018/3/14
 */
public class BridgeDemo {

    public static void main(String[] args) {

        ScanService scanService = new ScanServiceImpl(new ScanBuyService() {

            @Override
            public void buy() {
                System.out.println("JD 扫码购物");
            }
        }, null);

        // ScanService.scanBuy() -> ScanBuyService.buy();
        // 对应客户端而言，只关注粗粒度接口，具体执行方法是有运行时初始化而定。
        scanService.scanBuy();

        scanService = new ScanServiceImpl(new ScanBuyService() {

            @Override
            public void buy() {
                System.out.println("TB 扫码购物");
            }
        }, null);

        // ScanService.scanBuy() -> ScanBuyService.buy();
        scanService.scanBuy();


    }

    static class ScanServiceImpl implements ScanService {

        private ScanBuyService scanBuyService;

        private ScanLoginService scanLoginService;

        ScanServiceImpl(ScanBuyService scanBuyService, ScanLoginService scanLoginService) {
            this.scanBuyService = scanBuyService;
            this.scanLoginService = scanLoginService;
        }

        @Override
        public void scanBuy() {
            scanBuyService.buy();
        }

        @Override
        public void scanLogin() {
            scanLoginService.login();
        }
    }

    interface ScanService {

        void scanBuy();

        void scanLogin();

    }

    interface ScanBuyService {

        void buy();

    }

    interface ScanLoginService {

        void login();

    }
}


