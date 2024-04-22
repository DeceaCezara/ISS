package com.example.demo.controller;


import com.example.demo.service.ServiceImpl;
import com.example.demo.utils.observer.Observer;

public interface Controller extends Observer {
    void setService(ServiceImpl service);
}

