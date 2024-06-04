package com.egabi.tls.config;

import com.egabi.tls.model.LaneData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;


@Configuration
@Getter
public class LaneDataConfig {

    private LaneData laneData;

    @Value("classpath:lane-data.json")
    private Resource laneDataFile;

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        laneData = objectMapper.readValue(laneDataFile.getInputStream(), LaneData.class);
    }
}
