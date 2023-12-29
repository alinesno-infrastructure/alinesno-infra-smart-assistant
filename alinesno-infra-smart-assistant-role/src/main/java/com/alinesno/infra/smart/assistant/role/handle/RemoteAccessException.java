package com.alinesno.infra.smart.assistant.role.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedRuntimeException;

@Slf4j
public class RemoteAccessException extends NestedRuntimeException {

    public RemoteAccessException(String msg) {
        super(msg);
    }

    public RemoteAccessException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
