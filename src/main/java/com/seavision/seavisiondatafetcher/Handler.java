package com.seavision.seavisiondatafetcher;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.logging.Logger;

public class Handler  implements RequestHandler<Object, String> {
    Logger logger = Logger.getLogger(Handler.class.getName());

    @Override
    public String handleRequest(Object o, Context context) {
        return null;
    }
}
