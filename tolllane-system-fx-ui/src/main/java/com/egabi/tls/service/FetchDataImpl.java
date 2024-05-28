package com.egabi.tls.service;

import com.egabi.tls.controller.BaseFxController;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service("normal")
public class FetchDataImpl implements FetchData{
    @Override
    public void fetchAndUpdateDataScene(BaseFxController controller, Map<String, Object> values) {
        System.err.println("inside FetchDataImpl");
    }
}
