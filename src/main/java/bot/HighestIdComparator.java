package bot;

import bot.entity.Item;

import java.util.Comparator;

public class HighestIdComparator implements Comparator<Item>
{
    @Override
    public int compare(Item item1, Item item2)
    {
        return item1.getId().compareTo(item2.getId());
    }
}
