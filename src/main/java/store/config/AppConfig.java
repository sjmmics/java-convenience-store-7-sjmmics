package store.config;

import store.controller.StoreController;
import store.repository.MemoryRepository;
import store.repository.StoreRepository;
import store.service.StoreService;
import store.service.StoreServiceImpl;
import store.view.ConsoleInputView;
import store.view.ConsoleOutputView;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {
    
    private StoreRepository getRepository() {
        return new MemoryRepository();
    }
    
    private InputView getInputView() {
        return new ConsoleInputView();
    }
    
    private OutputView getOutputView() {
        return new ConsoleOutputView();
    }
    
    private StoreService getService() {
        return new StoreServiceImpl(getRepository());
    }
    
    public StoreController getStoreController() {
        return new StoreController(getInputView(), getOutputView(), getService());
    }
    
}
