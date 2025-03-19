package bot.entity;

import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import org.jetbrains.annotations.NotNull;

public class ItemId implements Comparable<ItemId>
{
    private static final int NUM_LENGTH = 3;
    public static final  int LENGTH     = NUM_LENGTH + 3;

    private final int value;

    public ItemId(int value)
    {
        assert value > 0;

        this.value = value;
    }

    public ItemId(OptionMapping option)
    {
        int value;
        try {
            value = option.getAsInt();
        } catch (Throwable exception) {
            throw new RuntimeException("Item could not be found. Please try selecting one from the autocomplete list.");
        }

        assert value > 0;

        this.value = value;
    }

    ItemId(String stringValue)
    {
        this.value = Integer.parseInt(stringValue.replace("`", "").replace("#", ""));
    }

    @Override
    public int compareTo(@NotNull ItemId that)
    {
        return this.value - that.getValue();
    }

    public boolean equals(ItemId that)
    {
        return this.value == that.getValue();
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public String toString()
    {
        return String.format("`#%0" + NUM_LENGTH + "d`", value);
    }
}
