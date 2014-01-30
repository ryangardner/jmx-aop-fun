import drips.MXBeanSwaparoo
import groovy.transform.CompileStatic
import spock.lang.Specification

import java.lang.management.ManagementFactory
import java.lang.management.MemoryMXBean
import java.lang.management.OperatingSystemMXBean

class MXBeanSwaparooSpec extends Specification {

    def "when code asks the mbean server asks for the memory mbean, it gets our special one"() {
        setup:
            MemoryMXBean memoryMXBean = Helper.getMemoryMXBean()
        expect:
            memoryMXBean instanceof MXBeanSwaparoo.InstrumentedMemoryMXBean;
        when:
            memoryMXBean.heapMemoryUsage
        then:
            notThrown(UnsupportedOperationException)

    }

    def "when code asks the mbean server asks for the os mbean, it gets our special one"() {
        setup:
            OperatingSystemMXBean osMXbean = Helper.getOsMXBean();
        expect:
            osMXbean instanceof MXBeanSwaparoo.FakeOSMXBean;
        when:
            int processCount = osMXbean.getAvailableProcessors()
        then:
            processCount == 64
        expect:
            osMXbean.arch != null;
    }

    // this is so the weaver can weave something's "call" advice
    @CompileStatic
    static class Helper {
        public static MemoryMXBean getMemoryMXBean() {
            return ManagementFactory.getMemoryMXBean();
        }

        public static OperatingSystemMXBean getOsMXBean() {
            return ManagementFactory.getOperatingSystemMXBean();
        }
    }




}