package fr.fistin.fistinframework.event;

public interface ICancellable
{
    void setCancelled(boolean cancelled);
    boolean isCancelled();
}
