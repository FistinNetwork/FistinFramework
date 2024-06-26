package fr.fistin.fistinframework.runnable;

import fr.fistin.fistinframework.utils.FistinValidate;
import fr.fistin.fistinframework.utils.IBukkitPluginProvider;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public final class RunnableUtils
{
    private static final long TICK_MULTIPLIER = 20L;
    private static final long MINUTE_MULTIPLIER = 60L;
    private static final long HOUR_MULTIPLIER = MINUTE_MULTIPLIER * MINUTE_MULTIPLIER;
    private static final long DAY_MULTIPLIER = 24L * HOUR_MULTIPLIER;
    private static final long MILLI_DIVIDER = 1000L;
    private static final long MICRO_DIVIDER = MILLI_DIVIDER * MILLI_DIVIDER;
    private static final long NANO_DIVIDER = MICRO_DIVIDER * MILLI_DIVIDER;

    /**
     * Instantiate a new {@link BukkitRunnable}.
     * @param runnable the action to wrap.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable newBukkitRunnable(Runnable runnable)
    {
        return new BukkitRunnable() {
            @Override
            public void run()
            {
                runnable.run();
            }
        };
    }

    /**
     * Instantiate a new {@link BukkitRunnable}.
     * @param runnable the action timer to wrap.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable newBukkitRunnableTimer(FistinRunnableTimer runnable)
    {
        return new BukkitRunnable() {
            @Override
            public void run()
            {
                runnable.onTimerPass(this::cancel, runnable.timer().get());

                if(runnable.timer().get() == 0)
                {
                    runnable.onTimerEnd(this::cancel);
                    this.cancel();
                }
                else runnable.timer().getAndDecrement();
            }
        };
    }

    /**
     * Run instantly a {@link Runnable} as a {@link BukkitRunnable}.
     * @param runnable the action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runBukkitRunnable(Runnable runnable, boolean async, IBukkitPluginProvider plugin)
    {
        return runBukkitRunnable(newBukkitRunnable(runnable), async, plugin);
    }

    /**
     * Run instantly a {@link BukkitRunnable}.
     * @param bukkitRunnable the action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runBukkitRunnable(BukkitRunnable bukkitRunnable, boolean async, IBukkitPluginProvider plugin)
    {
        if (async)
            bukkitRunnable.runTaskAsynchronously(plugin);
        else bukkitRunnable.runTask(plugin);

        return bukkitRunnable;
    }

    /**
     * Run later a {@link Runnable} as a {@link BukkitRunnable}.
     * @param runnable the action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runLaterBukkitRunnable(Runnable runnable, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit)
    {
        return runLaterBukkitRunnable(newBukkitRunnable(runnable), async, plugin, delay, delayUnit);
    }

    /**
     * Run later a {@link BukkitRunnable}.
     * @param bukkitRunnable the action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runLaterBukkitRunnable(BukkitRunnable bukkitRunnable, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit)
    {
        if(async)
            bukkitRunnable.runTaskLaterAsynchronously(plugin, convertToTick(delay, delayUnit));
        else bukkitRunnable.runTaskLater(plugin, convertToTick(delay, delayUnit));

        return bukkitRunnable;
    }

    /**
     * Run a repeated {@link FistinRunnableTimer} as a {@link BukkitRunnable}.
     * @param runnable the repeated action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @param timer the time before a new iteration of the task.
     * @param timerUnit the unit of the time.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runRepeatedBukkitRunnable(Runnable runnable, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit, long timer, TimeUnit timerUnit)
    {
        return runRepeatedBukkitRunnable(newBukkitRunnable(runnable), async, plugin, delay, delayUnit, timer, timerUnit);
    }

    /**
     * Run a repeated {@link BukkitRunnable}.
     * @param bukkitRunnable the repeated action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @param timer the time before a new iteration of the task.
     * @param timerUnit the unit of the time.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runRepeatedBukkitRunnable(BukkitRunnable bukkitRunnable, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit, long timer, TimeUnit timerUnit)
    {
        if(async)
            bukkitRunnable.runTaskTimerAsynchronously(plugin, convertToTick(delay, delayUnit), convertToTick(timer, timerUnit));
        else bukkitRunnable.runTaskTimer(plugin, convertToTick(delay, delayUnit), convertToTick(timer, timerUnit));

        return bukkitRunnable;
    }

    /**
     * Run a repeated {@link FistinRunnableTimer} as a {@link BukkitRunnable} while the timer is up.
     * @param fistinRunnableTimer the repeated action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @param timer the time before a new iteration of the task.
     * @param timerUnit the unit of the time.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runTimerBukkitRunnable(FistinRunnableTimer fistinRunnableTimer, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit, long timer, TimeUnit timerUnit)
    {
        return runTimerBukkitRunnable(newBukkitRunnableTimer(fistinRunnableTimer), async, plugin, delay, delayUnit, timer, timerUnit);
    }

    /**
     * Run a repeated {@link BukkitRunnable} while the timer is up.
     * @param bukkitRunnable the repeated action to wrap.
     * @param async If it will run asynchronously.
     * @param plugin the plugin who starts the task.
     * @param delay the delay before the task starts.
     * @param delayUnit the unit of the delay.
     * @param timer the time before a new iteration of the task.
     * @param timerUnit the unit of the time.
     * @return the bukkit runnable.
     */
    public static BukkitRunnable runTimerBukkitRunnable(BukkitRunnable bukkitRunnable, boolean async, IBukkitPluginProvider plugin, long delay, TimeUnit delayUnit, long timer, TimeUnit timerUnit)
    {
        if(async)
            bukkitRunnable.runTaskTimerAsynchronously(plugin, convertToTick(delay, delayUnit), convertToTick(timer, timerUnit));
        else bukkitRunnable.runTaskTimer(plugin, convertToTick(delay, delayUnit), convertToTick(timer, timerUnit));

        return bukkitRunnable;
    }

    /**
     * Convert to tick the given value.
     * @param numberOf the number of given unit.
     * @param unit seconds, days, minutes, hours...
     * @return the converted value.
     */
    public static long convertToTick(long numberOf, TimeUnit unit)
    {
        FistinValidate.numberPositive(numberOf, "numberOf must be positive ! Actual: %d, given params: %d %s.", numberOf, numberOf, unit.name());

        long result = 0;
        switch (unit)
        {
            case DAYS:
                return numberOf * TICK_MULTIPLIER * DAY_MULTIPLIER;
            case HOURS:
                return numberOf * TICK_MULTIPLIER * HOUR_MULTIPLIER;
            case MINUTES:
                return numberOf * TICK_MULTIPLIER * MINUTE_MULTIPLIER;
            case SECONDS:
                return numberOf * TICK_MULTIPLIER;
            case MILLISECONDS:
                final double milliResult = (double) numberOf * TICK_MULTIPLIER / MILLI_DIVIDER;
                FistinValidate.numberPositive(milliResult, "result must be positive ! Actual: %d, given params: %d %s.", milliResult, milliResult, unit.name());
                return Math.round(milliResult);
            case MICROSECONDS:
                final double microResult = (double) numberOf * TICK_MULTIPLIER / MICRO_DIVIDER;
                FistinValidate.numberPositive(microResult, "result must be positive ! Actual: %d, given params: %d %s.", microResult, microResult, unit.name());
                return Math.round(microResult);
            case NANOSECONDS:
                final double nanoResult = (double) numberOf * TICK_MULTIPLIER / NANO_DIVIDER;
                FistinValidate.numberPositive(nanoResult, "result must be positive ! Actual: %d, given params: %d %s.", nanoResult, nanoResult, unit.name());
                return Math.round(nanoResult);
        }
        return result;
    }
}
