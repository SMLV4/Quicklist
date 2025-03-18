package bot.entity;

import java.awt.*;
import java.util.ArrayList;

public class List
{
    private final ArrayList<Item> items;
    private       Color           color;
    private       ListTitle       listTitle;

    public List(ListTitle listTitle, Color color)
    {
        this.listTitle = listTitle;
        this.color = color;
        this.items = new ArrayList<>();
    }

    public List(ListTitle listTitle, Color color, ArrayList<Item> items)
    {
        this.listTitle = listTitle;
        this.color = color;
        this.items = items;
    }

    public void addItem(Item item)
    {
        items.add(item);
    }

    public Color getColor()
    {
        return color;
    }

    public ArrayList<Item> getItems()
    {
        return items;
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }

    public String getTitle()
    {
        return listTitle.toString();
    }

    public void updateColor(Color color)
    {
        this.color = color;
    }

    public void updateShortcut(String shortcut)
    {
        listTitle = new ListTitle(listTitle.getTitle(), shortcut);
    }

    public void updateTitle(String title)
    {
        listTitle = new ListTitle(title, listTitle.getShortcut());
    }
}
