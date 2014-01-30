package drips;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;

/**
 * @author Ryan Gardner
 * @date 1/28/14
 */
@Aspect
public class MXBeanSwaparoo {
    static InstrumentedMemoryMXBean fakeMemoryMbean = new InstrumentedMemoryMXBean();
    static FakeOSMXBean fakeOSMXbean = new FakeOSMXBean();

    @Pointcut("call(static * java.lang.management.ManagementFactory.getMemoryMXBean())")
    public void returningMemoryMXBean() {}

    @Pointcut("call(static * java.lang.management.ManagementFactory.getOperatingSystemMXBean())")
    public void getOperatingSystemMBean() {}


    @Around("returningMemoryMXBean() && !within(drips.MXBeanSwaparoo.InstrumentedMemoryMXBean)")
    public Object swapMemoryMXBean() throws Exception {
        return fakeMemoryMbean;
    }

    @Around("getOperatingSystemMBean() && !within(drips.MXBeanSwaparoo.FakeOSMXBean)")
    public Object swapOSMxBean() throws Exception {
        return fakeOSMXbean;
    }

    public static class FakeOSMXBean implements OperatingSystemMXBean {
        private static final OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();


        @Override
        public String getName() {
            return operatingSystemMXBean.getName();
        }

        @Override
        public String getArch() {
            return operatingSystemMXBean.getArch();
        }

        @Override
        public String getVersion() {
            return operatingSystemMXBean.getVersion();
        }

        @Override
        public int getAvailableProcessors() {
            return 64;
        }

        @Override
        public double getSystemLoadAverage() {
            return operatingSystemMXBean.getSystemLoadAverage();
        }

        @Override
        public ObjectName getObjectName() {
            return operatingSystemMXBean.getObjectName();
        }
    }

    public static class InstrumentedMemoryMXBean implements MemoryMXBean {
        private static final MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        @Override
        public int getObjectPendingFinalizationCount() {
            return memoryMXBean.getObjectPendingFinalizationCount();
        }

        @Override
        public MemoryUsage getHeapMemoryUsage() {
            return memoryMXBean.getHeapMemoryUsage();
        }

        @Override
        public MemoryUsage getNonHeapMemoryUsage() {
            return memoryMXBean.getNonHeapMemoryUsage();
        }

        @Override
        public boolean isVerbose() {
            return memoryMXBean.isVerbose();
        }

        @Override
        public void setVerbose(boolean value) {
            memoryMXBean.setVerbose(value);
        }

        @Override
        public void gc() {
            memoryMXBean.gc();
        }

        @Override
        public ObjectName getObjectName() {
            return memoryMXBean.getObjectName();
        }
    }
}

