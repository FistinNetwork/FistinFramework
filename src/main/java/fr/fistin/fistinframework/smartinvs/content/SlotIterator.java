package fr.fistin.fistinframework.smartinvs.content;

import fr.fistin.fistinframework.smartinvs.ClickableItem;

import java.util.Optional;

public interface SlotIterator
{
    enum Type
    {
        HORIZONTAL,
        VERTICAL
    }

    Optional<ClickableItem> get();
    SlotIterator set(ClickableItem item);

    SlotIterator previous();
    SlotIterator next();

    SlotIterator blacklist(int row, int column);
    SlotIterator blacklist(SlotPos slotPos);

    int row();
    SlotIterator row(int row);

    int column();
    SlotIterator column(int column);

    boolean started();
    boolean ended();

    boolean doesAllowOverride();
    SlotIterator allowOverride(boolean override);
}