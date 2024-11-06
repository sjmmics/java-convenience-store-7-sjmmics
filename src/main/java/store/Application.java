package store;

import store.config.AppConfig;
import store.controller.StoreController;

public class Application {
    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        StoreController controller = config.getStoreController();
        controller.run();
        
        
    }
}
