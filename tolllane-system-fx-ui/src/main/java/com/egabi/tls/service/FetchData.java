package com.egabi.tls.service;

import com.egabi.tls.controller.BaseFxController;

import java.util.Map;

public interface FetchData {

    void fetchAndUpdateDataScene(BaseFxController controller, Map<String, Object> values);
}
