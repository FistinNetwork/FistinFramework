package fr.fistin.fistinframework.eventbus;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.function.Supplier;

public class EventBusTest
{
    @Test
    public void testEventBusAPI()
    {
        final FistinEventBus<Supplier<? extends FistinEvent>> bus = new DefaultEventBus();
        bus.registerEvent(TestEvent.class);
        bus.registerEvent(AnotherTestEvent.class);
        bus.addListener(new FistinEventListener() {
            @FistinEventHandler
            public void handleTestEvent(TestEvent event)
            {
                System.out.println("Handling event " + event.getName() + ", printing: " + event.getToPrint());
            }
        });
        bus.handleEvent(() -> new TestEvent("waaaaaw"));
        bus.handleEvent(() -> new AnotherTestEvent("waaaaaw"));
        bus.addListener(new FistinEventListener() {
            @FistinEventHandler
            public void handleTestEvent(AnotherTestEvent event)
            {
                System.out.println("Handling another event " + event.getName() + ", printing: " + event.getToPrint());
            }
        });
        bus.handleEvent(() -> new AnotherTestEvent("waaaaaw"));
        bus.clean();
    }

    private static class TestEvent implements FistinEvent
    {
        private final String toPrint;

        public TestEvent(String toPrint)
        {
            this.toPrint = toPrint;
        }

        public String getToPrint()
        {
            return this.toPrint;
        }

        @Override
        public @NotNull String getName()
        {
            return "TestEvent";
        }
    }

    private static class AnotherTestEvent implements FistinEvent
    {
        private final String toPrint;

        public AnotherTestEvent(String toPrint)
        {
            this.toPrint = toPrint;
        }

        public String getToPrint()
        {
            return this.toPrint;
        }

        @Override
        public @NotNull String getName()
        {
            return "AnotherTestEvent";
        }
    }
}
