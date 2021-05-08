package fr.fistin.fistinframework.hostconfig.settings;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class TimeSetting extends AbstractSetting<TimeSetting.TimeObject>
{
    public TimeSetting(String id, String displayName, Material displayItem, List<String> lore)
    {
        this(id, displayName, displayItem, lore, new TimeObject());
    }

    public TimeSetting(String id, String displayName, Material displayItem, List<String> lore, TimeObject value)
    {
        super(id, displayName, displayItem, lore, value);
    }

    @Override
    public SettingType getType()
    {
        return SettingType.TIME;
    }

    @Override
    public Consumer<InventoryClickEvent> rightClickConsumer()
    {
        return e -> {
            if(e.isShiftClick())
                this.getValue().backTimeUnit();
            else this.getValue().setValue(this.getValue().getValue() - 1);
        };
    }

    @Override
    public Consumer<InventoryClickEvent> leftClickConsumer()
    {
        return e -> {
            if(e.isShiftClick())
                this.getValue().nextTimeUnit();
            else this.getValue().setValue(this.getValue().getValue() + 1);
        };
    }

    public static class TimeObject
    {
        private TimeUnit timeUnit;
        private long value;

        public TimeObject()
        {
            this(TimeUnit.SECONDS, 0);
        }

        public TimeObject(TimeUnit timeUnit, long value)
        {
            this.timeUnit = timeUnit;
            this.value = value;
        }

        public TimeUnit getTimeUnit()
        {
            return this.timeUnit;
        }

        public void setTimeUnit(TimeUnit timeUnit)
        {
            this.timeUnit = timeUnit;
        }

        public long getValue()
        {
            return this.value;
        }

        public void setValue(long value)
        {
            this.value = value;
        }

        public void backTimeUnit()
        {
            for (TimeUnit unit : TimeUnit.values())
            {
                if (unit.ordinal() == this.timeUnit.ordinal() - 1)
                {
                    this.setTimeUnit(unit);
                    return;
                }
            }

            this.setTimeUnit(TimeUnit.DAYS);
        }

        public void nextTimeUnit()
        {
            for (TimeUnit unit : TimeUnit.values())
            {
                if (unit.ordinal() == this.timeUnit.ordinal() + 1)
                {
                    this.setTimeUnit(unit);
                    return;
                }
            }

            this.setTimeUnit(TimeUnit.NANOSECONDS);
        }
    }
}
