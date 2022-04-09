package cn.chuanwise.panda.exception;

import lombok.NoArgsConstructor;

/**
 * 框架抛出的运行时异常
 *
 * @author Chuanwise
 */
@NoArgsConstructor
public class PandaRuntimeException extends RuntimeException {
    public PandaRuntimeException(String message) {
        super(message);
    }

    public PandaRuntimeException(Throwable cause) {
        super(cause);
    }

    public PandaRuntimeException(String string, Throwable cause) {
        super(string, cause);
    }
}
