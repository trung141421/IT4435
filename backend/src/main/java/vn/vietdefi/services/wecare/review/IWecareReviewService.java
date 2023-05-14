package vn.vietdefi.services.wecare.review;

import com.google.gson.JsonObject;

public interface IWecareReviewService {
    JsonObject reviewCustomer(JsonObject json);

    JsonObject reviewService(JsonObject json);

    JsonObject getCustomerReview(JsonObject json);

    JsonObject getServiceReview(JsonObject json);
}
