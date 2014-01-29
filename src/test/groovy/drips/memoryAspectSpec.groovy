import drips.MemoryAspect
import spock.lang.Specification

import java.lang.management.ManagementFactory
import java.lang.management.MemoryMXBean

class memoryAspectSpec extends Specification {

    def "when code asks the mbean server asks for the memory mbean, it gets our special one"() {
        setup:
            MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean()
        expect:
            memoryMXBean instanceof MemoryAspect.InstrumentedMemoryMXBean;
        when:
            memoryMXBean.heapMemoryUsage
        then:
            thrown(UnsupportedOperationException)

    }

}