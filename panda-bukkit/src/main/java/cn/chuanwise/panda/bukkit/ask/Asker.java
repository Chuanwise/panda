package cn.chuanwise.panda.bukkit.ask;

import cn.chuanwise.common.util.Objects;
import cn.chuanwise.common.util.Preconditions;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;

@Data
public class Asker {
    protected final AskerManager askerManager;
    
    protected final CommandSender commandSender;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    protected volatile AskResult result;
    
    @Setter(AccessLevel.NONE)
    private volatile boolean operated;
    
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private final Object mutex = new Object();
    
    private boolean setResult(AskResult result) {
        if (operated) {
            return false;
        }
    
        this.result = result;
        operated = true;
    
        synchronized (mutex) {
            mutex.notifyAll();
        }
    
        return true;
    }

    public boolean accept() {
        return setResult(AskResult.ACCEPTED);
    }

    public boolean deny() {
        return setResult(AskResult.DENIED);
    }

    public boolean ignore() {
        return setResult(AskResult.IGNORED);
    }

    public AskResult get(long timeout, TimeUnit timeUnit) throws InterruptedException {
        Preconditions.namedArgumentNonNull(timeUnit, "timeout");
        Preconditions.argument(timeout > 0, "timeout must be bigger than or equals to 0!");
    
        if (!isOperated()) {
            if (!Objects.await(mutex, timeout, timeUnit)) {
                setResult(AskResult.TIMEOUT);
            }
            askerManager.askers.remove(commandSender);
        
        }
        return result;
    }
    
    public AskResult get(long timeout) throws InterruptedException {
        return get(timeout, TimeUnit.MILLISECONDS);
    }
}