package com.cts.skilltrkr.util;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProfileUtil {

    public static String convertObjectToJson(Object profile) {

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(profile);
        } catch (JsonProcessingException ex) {
            log.error("Not able to convert to Json :{}",ex);
        }
        return null;
    }
}
