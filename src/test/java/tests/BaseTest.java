package tests;

import clients.StoreApiClient;
import helpers.OrderHelper;
import helpers.PetHelper;

public class BaseTest {

    protected static final PetHelper PET_HELPER = new PetHelper();
    protected static final OrderHelper ORDER_HELPER = new OrderHelper();

    protected final StoreApiClient storeApiClient = new StoreApiClient();
}
