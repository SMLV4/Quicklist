package bot.entity;

import bot.HighestIdComparator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Random;

public class Catalog
{
    private final ArrayList<List> lists;

    public Catalog(ArrayList<List> lists)
    {
        this.lists = lists;
    }

    public void addList(List list) throws Exception
    {
        ListTitle listTitle = list.getListTitle();
        if (hasList(listTitle)) {
            throw new Exception("List " + listTitle + " already exists.");
        }

        lists.add(list);
    }

    public @NotNull Item getItem(ItemId id) throws Exception
    {
        for (List list : lists) {
            ArrayList<Item> items = list.getItems();
            for (Item item : items) {
                if (item.is(id)) {
                    return item;
                }
            }
        }

        throw new Exception("Item " + id + " not found.");
    }

    public List getList(ListTitle title)
    {
        for (List list : lists) {
            if (list.getListTitle()
                .is(title)) {
                return list;
            }
        }

        return null;
    }

    public List getList(String listIdentifier)
    {
        for (List list : lists) {
            ListTitle listTitle = list.getListTitle();
            if (listTitle.is(listIdentifier)) {
                return list;
            }
        }

        return null;
    }

    public ArrayList<List> getLists()
    {
        return lists;
    }

    public boolean hasItem(ItemId id)
    {
        ArrayList<Item> items = getAllItems();
        for (Item item : items) {
            if (item.is(id)) {
                return true;
            }
        }

        return false;
    }

    public boolean hasList(ListTitle title)
    {
        return getList(title) != null;
    }

    public int highestId()
    {
        ArrayList<Item> items = getAllItems();
        if (items.isEmpty()) {
            return 0;
        }

        items.sort(new HighestIdComparator());

        return items.get(items.size() - 1)
            .getId()
            .getValue();
    }

    public Item random()
    {
        ArrayList<Item> items = getAllItems();
        int             roll  = (new Random()).nextInt(items.size());

        return items.get(roll);
    }

    public void removeItem(ItemId id) throws Exception
    {
        removeItemFromList(id);
        removeBlockedIdFromItems(id);
    }

    public void removeItemFromList(ItemId id) throws Exception
    {
        for (List list : lists) {
            ArrayList<Item> items = list.getItems();
            for (Item item : items) {
                if (item.is(id)) {
                    items.remove(item);
                    return;
                }
            }
        }

        throw new Exception("Item " + id + " does not exist.");
    }

    public void removeList(String listIdentifier) throws Exception
    {
        List list = getList(listIdentifier);
        if (list == null) {
            throw new Exception("List " + listIdentifier + " does not exist.");
        }

        for (Item item : list.getItems()) {
            list.getItems()
                .remove(item);
            removeBlockedIdFromItems(item.getId());
        }

        lists.remove(list);
    }

    @NotNull
    private ArrayList<Item> getAllItems()
    {
        ArrayList<Item> items = new ArrayList<>();
        for (List list : lists) {
            items.addAll(list.getItems());
        }

        return items;
    }

    private void removeBlockedIdFromItems(ItemId id) throws Exception
    {
        for (Item item : getAllItems()) {
            if (item.isBlockedBy(id)) {
                item.removeBlock(id);
            }
        }
    }
}
