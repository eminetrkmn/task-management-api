package com.taskmanager.exception;

public class DuplicateTaskException extends RuntimeException {
    public DuplicateTaskException(String title) {
        super("Aynı isimde görev zaten mevcut: " + title);
    }
}
