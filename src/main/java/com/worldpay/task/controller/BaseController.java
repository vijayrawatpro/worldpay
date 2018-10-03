package com.worldpay.task.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public static <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> ok(T object) {
        return new ResponseEntity<>(object, HttpStatus.OK);
    }

    public static <T> ResponseEntity<T> created() {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> created(T object) {
        return new ResponseEntity<>(object, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<T> badRequest() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> badRequest(T object) {
        return new ResponseEntity<>(object, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<T> notFound() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public static <T> ResponseEntity<T> notFound(T object) {
        return new ResponseEntity<>(object, HttpStatus.NOT_FOUND);
    }
}
