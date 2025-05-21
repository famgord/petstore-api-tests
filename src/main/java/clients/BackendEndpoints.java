package clients;

public class BackendEndpoints {

    public static class Store {
        public static final String ORDER = "/store/order";
        public static final String ORDER_ID = "/store/order/{id}";
    }

    public static class Pet {
        public static final String PET = "/pet";
        public static final String PET_ID = "/pet/{id}";
    }
}
