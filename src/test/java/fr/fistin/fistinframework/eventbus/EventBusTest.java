package fr.fistin.fistinframework.eventbus;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;

public class EventBusTest
{
    @Test
    public void testEventBusAPI()
    {
        final String[] test = {"", ""};

        final FistinEventBus<Supplier<? extends FistinEvent>> bus = new DefaultEventBus();
        bus.registerEvent(TestEvent.class);
        bus.registerEvent(AnotherTestEvent.class);
        bus.addListener(new FistinEventListener() {
            @FistinEventHandler
            public void handleTestEvent(TestEvent event)
            {
                test[0] = event.getToPrint();
            }
        });
        bus.handleEvent(() -> new TestEvent("test1"));
        bus.handleEvent(() -> new AnotherTestEvent("test2"));
        bus.addListener(new FistinEventListener() {
            @FistinEventHandler
            public void handleTestEvent(AnotherTestEvent event)
            {
                test[1] = event.getToPrint();
            }
        });
        bus.handleEvent(() -> new AnotherTestEvent("test3"));
        bus.clean();

        assertEquals("test1", test[0]);
        assertEquals("test3", test[1]);
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
