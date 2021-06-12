package fr.fistin.fistinframework.impl;

import fr.fistin.api.plugin.providers.IBukkitPluginProvider;
import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.player.FistinPlayer;
import fr.fistin.fistinframework.runnable.FistinRunnableTimer;
import fr.fistin.fistinframework.runnable.RunnableUtils;
import fr.fistin.fistinframework.scoreboard.IScoreboardSign;
import fr.fistin.fistinframework.utils.FistinValidate;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.jetbrains.annotations.ApiStatus;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.logging.Level;

import static fr.fistin.fistinframework.runnable.RunnableUtils.*;

/**
 * Don't use directly this class, use {@link IScoreboardSign} instead!
 */
@ApiStatus.Internal
public class ScoreboardSign implements IScoreboardSign
{
    private static final IFistinFramework FRAMEWORK = IFistinFramework.framework();
    private static final char CHAR = '\u00A7';

    private boolean created = false;
    private final IVirtualTeam[] lines = new IVirtualTeam[15];
    private final FistinPlayer player;
    private final IBukkitPluginProvider caller;
    private String objectiveName;

    public ScoreboardSign(FistinPlayer player, String objectiveName, IBukkitPluginProvider caller)
    {
        this.player = player;
        this.caller = caller;
        this.objectiveName = CHAR + "b" + objectiveName;

        final int length = this.objectiveName.length();
        FistinValidate.numberInferior(length + 4, 32, "Scoreboard name is too long!");
        FistinValidate.numberSuperior(length - 2, 0, "Scoreboard name is too short!");
    }

    @Override
    public void create()
    {
        if (this.created)
            return;

        final PlayerConnection playerConnection = this.getPlayerConnection();
        playerConnection.sendPacket(this.createObjectivePacket(PacketScoreboardMode.CREATE.ordinal(), this.objectiveName));
        playerConnection.sendPacket(this.setObjectiveSlot());
        int i = 0;
        while (i < this.lines.length)
            this.sendLine(i++);

        this.created = true;

        RunnableUtils.runRepeatedBukkitRunnable(newBukkitRunnable(new Runnable() {
            @Override
            public void run()
            {
                if(!ScoreboardSign.this.created)
                    return;

                RunnableUtils.runTimerBukkitRunnable(newBukkitRunnableTimer(new FistinRunnableTimer()
                {
                    private final AtomicLong timer = new AtomicLong(ScoreboardSign.this.objectiveName.length() - 2);

                    @Override
                    public void onTimerPass(long timer)
                    {
                        if(timer == 0)
                            return;

                        final ScoreboardSign scoreboard = ScoreboardSign.this;
                        final int threeIndex = scoreboard.objectiveName.indexOf(CHAR + "3");
                        final int bIndex = scoreboard.objectiveName.lastIndexOf(CHAR + "b");

                        if(threeIndex == -1)
                        {
                            final String empty = this.calcEmpty();
                            final char[] chars = empty.toCharArray();
                            final char[] clean = new char[chars.length - 1];
                            System.arraycopy(chars, 1, clean, 0, chars.length - 1);
                            final String replacement = CHAR + "3" + empty.charAt(0) + CHAR + "b" + new String(clean);
                            scoreboard.setObjectiveName(replacement);
                        }
                        else
                        {
                            final String empty = this.calcEmpty();
                            final char[] emptyArray = empty.toCharArray();
                            final char[] result = new char[empty.length() + 6];

                            result[0] = CHAR;
                            result[1] = 'b';

                            if(threeIndex == 0)
                            {
                                result[3] = CHAR;
                                result[4] = '3';
                                result[bIndex + 3] = CHAR;
                                result[bIndex + 4] = 'b';
                            }
                            else
                            {
                                result[threeIndex + 1] = CHAR;
                                result[threeIndex + 2] = '3';
                                if(bIndex + 2 < result.length)
                                {
                                    result[bIndex + 1] = CHAR;
                                    result[bIndex + 2] = 'b';
                                }
                            }

                            int iterated = 0, iterator = 0;
                            while (iterated < empty.length())
                            {
                                if(result[iterator] == '\u0000')
                                {
                                    result[iterator] = emptyArray[iterated];
                                    iterated++;
                                }
                                iterator++;
                            }

                            scoreboard.setObjectiveName(new String(result));
                        }
                    }

                    private String calcEmpty()
                    {
                        return ScoreboardSign.this.objectiveName.replace(CHAR + "3", "").replace(CHAR + "b", "");
                    }

                    @Override
                    public void onTimerEnd()
                    {
                        ScoreboardSign.this.setObjectiveName(CHAR + "b" + ScoreboardSign.this.objectiveName.replace(CHAR + "3", "").replace(CHAR + "b", ""));
                        this.timer.set(ScoreboardSign.this.objectiveName.length() - 1);
                    }

                    @Override
                    public AtomicLong timer()
                    {
                        return this.timer;
                    }
                }), false, ScoreboardSign.this.caller, 0L, TimeUnit.SECONDS, 350L, TimeUnit.MILLISECONDS);
            }
        }), false, this.caller, 0L, TimeUnit.SECONDS, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void destroy()
    {
        if (!this.created)
            return;

        this.getPlayerConnection().sendPacket(this.createObjectivePacket(PacketScoreboardMode.REMOVE.ordinal(), null));
        for (IVirtualTeam team : this.lines)
            if (team != null)
                this.getPlayerConnection().sendPacket((PacketPlayOutScoreboardTeam)team.removeTeam().get());

        this.created = false;
    }

    @Override
    public void setObjectiveName(String name)
    {
        this.objectiveName = name;
        if (this.created)
            this.getPlayerConnection().sendPacket(this.createObjectivePacket(PacketScoreboardMode.UPDATE.ordinal(), name));
    }

    @Override
    public void setLine(int line, String value)
    {
        final IVirtualTeam team = this.getOrCreateTeam(line);
        final String old = team.getCurrentPlayer();

        if (old != null && this.created)
            this.getPlayerConnection().sendPacket(this.removeLine(old));

        team.setValue(value);
        this.sendLine(line);
    }

    @Override
    public void removeLine(int line)
    {
        final IVirtualTeam team = this.getOrCreateTeam(line);
        final String old = team.getCurrentPlayer();

        if (old != null && this.created)
        {
            this.getPlayerConnection().sendPacket(this.removeLine(old));
            this.getPlayerConnection().sendPacket((PacketPlayOutScoreboardTeam)team.removeTeam().get());
        }

        this.lines[line] = null;
    }

    @Override
    public String getLine(int line)
    {
        if (line > 14)
            return null;
        if (line < 0)
            return null;
        return this.getOrCreateTeam(line).getValue();
    }

    @Override
    public IVirtualTeam getTeam(int line)
    {
        if (line > 14)
            return null;
        if (line < 0)
            return null;
        return this.getOrCreateTeam(line);
    }

    @Override
    public IBukkitPluginProvider getCaller()
    {
        return this.caller;
    }

    private PlayerConnection getPlayerConnection()
    {
        return ((CraftPlayer)this.player.getPlayer()).getHandle().playerConnection;
    }

    private void sendLine(int line)
    {
        if (line > 14)
            return;
        if (line < 0)
            return;
        if (!this.created)
            return;

        final int score = (15 - line);
        final IVirtualTeam val = this.getOrCreateTeam(line);
        
        for (Object packet : val.sendLine())
            this.getPlayerConnection().sendPacket((PacketPlayOutScoreboardTeam)packet);
        
        this.getPlayerConnection().sendPacket(this.sendScore(val.getCurrentPlayer(), score));
        val.reset();
    }

    private IVirtualTeam getOrCreateTeam(int line)
    {
        if (this.lines[line] == null)
            this.lines[line] = new VirtualTeam("__fakeScore" + line);

        return this.lines[line];
    }

    private PacketPlayOutScoreboardObjective createObjectivePacket(int mode, String displayName)
    {
        final PacketPlayOutScoreboardObjective packet = new PacketPlayOutScoreboardObjective();
        setField(packet, "a", this.player.getName());

        // Mode
        // 0 : create
        // 1 : remove
        // 2 : update
        setField(packet, "d", mode);

        if (mode == 0 || mode == 2)
        {
            setField(packet, "b", displayName);
            setField(packet, "c", IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER);
        }

        return packet;
    }

    private PacketPlayOutScoreboardDisplayObjective setObjectiveSlot()
    {
        final PacketPlayOutScoreboardDisplayObjective packet = new PacketPlayOutScoreboardDisplayObjective();
        setField(packet, "a", 1);
        setField(packet, "b", this.player.getName());

        return packet;
    }

    private PacketPlayOutScoreboardScore sendScore(String line, int score)
    {
        final PacketPlayOutScoreboardScore packet = new PacketPlayOutScoreboardScore(line);
        setField(packet, "b", this.player.getName());
        setField(packet, "c", score);
        setField(packet, "d", PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE);

        return packet;
    }

    private PacketPlayOutScoreboardScore removeLine(String line)
    {
        return new PacketPlayOutScoreboardScore(line);
    }

    public static class VirtualTeam implements IVirtualTeam
    {
        private final String name;
        private String prefix;
        private String suffix;
        private String currentPlayer;
        private String oldPlayer;

        private boolean prefixChanged, suffixChanged, playerChanged = false;
        private boolean first = true;

        private VirtualTeam(String name, String prefix, String suffix)
        {
            this.name = name;
            this.prefix = prefix;
            this.suffix = suffix;
        }

        private VirtualTeam(String name)
        {
            this(name, "", "");
        }

        @Override
        public String getPrefix()
        {
            return this.prefix;
        }

        @Override
        public void setPrefix(String prefix)
        {
            if (this.prefix == null || !this.prefix.equals(prefix))
                this.prefixChanged = true;
            this.prefix = prefix;
        }

        @Override
        public String getSuffix()
        {
            return this.suffix;
        }

        @Override
        public void setSuffix(String suffix)
        {
            if (this.suffix == null || !this.suffix.equals(prefix))
                this.suffixChanged = true;
            this.suffix = suffix;
        }

        private PacketPlayOutScoreboardTeam createPacket(int mode)
        {
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
            setField(packet, "a", this.name);
            setField(packet, "h", mode);
            setField(packet, "b", "");
            setField(packet, "c", this.prefix);
            setField(packet, "d", this.suffix);
            setField(packet, "i", 0);
            setField(packet, "e", "always");
            setField(packet, "f", 0);

            return packet;
        }

        @Override
        public Supplier<?> createTeam()
        {
            return () -> this.createPacket(PacketScoreboardMode.CREATE.ordinal());
        }

        @Override
        public Supplier<?> updateTeam()
        {
            return () -> this.createPacket(PacketScoreboardMode.UPDATE.ordinal());
        }

        @Override
        public Supplier<?> removeTeam()
        {
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
            setField(packet, "a", this.name);
            setField(packet, "h", PacketScoreboardMode.REMOVE.ordinal());
            this.first = true;
            return () -> packet;
        }

        @Override
        public void setPlayer(String name)
        {
            if (this.currentPlayer == null || !this.currentPlayer.equals(name))
                this.playerChanged = true;
            this.oldPlayer = this.currentPlayer;
            this.currentPlayer = name;
        }

        @Override
        public List<?> sendLine()
        {
            final List<PacketPlayOutScoreboardTeam> packets = new ArrayList<>();

            if (this.first) packets.add((PacketPlayOutScoreboardTeam)this.createTeam().get());
            else if (this.prefixChanged || this.suffixChanged) packets.add((PacketPlayOutScoreboardTeam)this.updateTeam().get());
            
            if (this.first || this.playerChanged)
            {
                if (this.oldPlayer != null)
                    packets.add((PacketPlayOutScoreboardTeam)this.addOrRemovePlayer(4, this.oldPlayer).get());
                packets.add((PacketPlayOutScoreboardTeam)this.changePlayer().get());
            }

            if (this.first) this.first = false;

            return packets;
        }

        @Override
        public void reset()
        {
            this.prefixChanged = false;
            this.suffixChanged = false;
            this.playerChanged = false;
            this.oldPlayer = null;
        }

        @Override
        public Supplier<?> changePlayer()
        {
            return this.addOrRemovePlayer(3, this.currentPlayer);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Supplier<?> addOrRemovePlayer(int mode, String playerName)
        {
            final PacketPlayOutScoreboardTeam packet = new PacketPlayOutScoreboardTeam();
            
            setField(packet, "a", name);
            setField(packet, "h", mode);

            try
            {
                final Field field = packet.getClass().getDeclaredField("g");
                field.setAccessible(true);
                ((List<String>) field.get(packet)).add(playerName);
            }
            catch (NoSuchFieldException | IllegalAccessException e)
            {
                FRAMEWORK.getLogger().log(Level.SEVERE, e.getMessage(), e);
            }

            return () -> packet;
        }

        @Override
        public String getCurrentPlayer()
        {
            return this.currentPlayer;
        }

        @Override
        public String getValue()
        {
            return this.getPrefix() + this.getCurrentPlayer() + this.getSuffix();
        }

        @Override
        public void setValue(String value)
        {
            if (value.length() <= 16)
            {
                this.setPrefix("");
                this.setSuffix("");
                this.setPlayer(value);
            }
            else if (value.length() <= 32)
            {
                this.setPrefix(value.substring(0, 16));
                this.setPlayer(value.substring(16));
                this.setSuffix("");
            }
            else if (value.length() <= 48)
            {
                this.setPrefix(value.substring(0, 16));
                this.setPlayer(value.substring(16, 32));
                this.setSuffix(value.substring(32));
            }
            else throw new IllegalArgumentException("Too long value ! Max 48 characters, value was " + value.length() + " !");
        }
    }

    private static void setField(Object edit, String fieldName, Object value)
    {
        try
        {
            final Field field = edit.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(edit, value);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            FRAMEWORK.getLogger().log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private enum PacketScoreboardMode
    {
        CREATE,
        REMOVE,
        UPDATE
    }
}
