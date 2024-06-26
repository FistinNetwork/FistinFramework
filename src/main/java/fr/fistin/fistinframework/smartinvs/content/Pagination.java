package fr.fistin.fistinframework.smartinvs.content;

import fr.fistin.fistinframework.smartinvs.ClickableItem;

public interface Pagination
{
    ClickableItem[] getPageItems();

    int getPage();
    Pagination page(int page);

    boolean isFirst();
    boolean isLast();

    Pagination first();
    Pagination previous();
    Pagination next();
    Pagination last();

    Pagination addToIterator(SlotIterator iterator);

    Pagination setItems(ClickableItem... items);
    Pagination setItemsPerPage(int itemsPerPage);
}
