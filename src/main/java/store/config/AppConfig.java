package store.config;

import store.controller.StoreController;
import store.repository.MemoryStoreRepository;
import store.repository.StoreRepository;
import store.service.StoreService;
import store.service.StoreServiceImpl;
import store.view.InputView;
import store.view.OutputView;

/** Controller, Service, Repository, View 객체 구현체를 설정하여 객체를 생성하는 클래스 */
public class AppConfig {
    
    private StoreRepository getRepository() {
        return new MemoryStoreRepository();
    }
    
    private InputView getInputView() {
        return new InputView();
    }
    
    private OutputView getOutputView() {
        return new OutputView();
    }
    
    private StoreService getService() {
        return new StoreServiceImpl(getRepository());
    }
    
    public StoreController getStoreController() {
        return new StoreController(getInputView(), getOutputView(), getService());
    }
    
}
