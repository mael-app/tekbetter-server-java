package app.mael.tekbetter.service.impl;

import app.mael.tekbetter.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String getHelloMessage() {
        return "world";
    }
}