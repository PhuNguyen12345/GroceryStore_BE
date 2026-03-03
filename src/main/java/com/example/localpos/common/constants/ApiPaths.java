package com.example.localpos.common.constants;

public class ApiPaths {
    //base URI
    public static final String API_VERSION = "/api/v1";

    // --- PRODUCT MODULE ---
    public static final class ProductCtrl {
        public static final String PRODUCT = API_VERSION + "/products";
        public static final String CATEGORY = API_VERSION + "/categories";
        public static final String BRAND =  API_VERSION + "/brands";
    }

    // --- INVENTORY MODULE ---
    public static final class InventoryCtrl {
        public static final String SUPPLIER = API_VERSION + "/suppliers";
        public static final String WAREHOUSE = API_VERSION + "/warehouses";
        public static final String BATCH =  API_VERSION + "/inventory/batches";
        public static final String TRANSACTION =  API_VERSION + "/inventory/transactions";
    }

    // --- CRM MODULE ---
    public static final class CRMCtrl {
        public static final String CUSTOMER = API_VERSION + "/customers";
        public static final String PROMOTION =  API_VERSION + "/promotions";
        public static final String VOUCHER  =  API_VERSION + "/vouchers";
    }

    // --- HR MODULE ---
    public static final class HRCtrl {
        public static final String EMPLOYEE  = API_VERSION + "/employees";
        public static final String SHIFT =  API_VERSION + "/shifts";
        public static final String SCHEDULE =   API_VERSION + "/work-schedules";
    }

    public static final class POSCtrl {
        public static final String ORDER =  API_VERSION + "/orders";
        public static final String PAYMENT =   API_VERSION + "/payments";
    }
}
