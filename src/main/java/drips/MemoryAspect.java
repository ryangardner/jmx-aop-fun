package drips;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.management.ObjectName;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author Ryan Gardner
 * @date 1/28/14
 */
@Aspect
public class MemoryAspect {
    static InstrumentedMemoryMXBean fakeMemoryMbean = new InstrumentedMemoryMXBean();

    @Pointcut("execution( public static java.lang.management.MemoryMXBean java.lang.management.ManagementFactory.getMemoryMXBean()")
    public void returningMemoryMXBean() {}

    @Around("returningMemoryMXBean")
    public Object swapMemoryMXBean() throws Exception {
        return fakeMemoryMbean;
    }

    public static class InstrumentedMemoryMXBean implements MemoryMXBean {
        @Override
        public int getObjectPendingFinalizationCount() {
            throw new UnsupportedOperationException("getObjectPendingFinalizationCount is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public MemoryUsage getHeapMemoryUsage() {
            throw new UnsupportedOperationException("getHeapMemoryUsage is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public MemoryUsage getNonHeapMemoryUsage() {
            throw new UnsupportedOperationException("getNonHeapMemoryUsage is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public boolean isVerbose() {
            throw new UnsupportedOperationException("isVerbose is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public void setVerbose(boolean value) {
            throw new UnsupportedOperationException("setVerbose is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public void gc() {
            throw new UnsupportedOperationException("gc is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }

        @Override
        public ObjectName getObjectName() {
            throw new UnsupportedOperationException("getObjectName is not implemented in drips.MemoryAspect.InstrumentedMemoryMXBean");
        }
    }
}

