package cn.chuanwise.panda.exception;

import lombok.NoArgsConstructor;

/**
 * 框架抛出的异常
 *
 * @author Chuanwise
 */
@NoArgsConstructor
public class PandaException extends Exception {
    public PandaException(String message) {
        super(message);
    }

    public PandaException(Throwable cause) {
        super(cause);
    }

    public PandaException(String string, Throwable cause) {
        super(string, cause);
    }
}
