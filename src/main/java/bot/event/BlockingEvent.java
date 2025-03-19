package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

abstract public class BlockingEvent
{
    public static final String OPTION_BLOCKED  = "blocked";
    public static final String OPTION_BLOCKING = "blocking";
    private final       ItemId blockedItemId;
    private final       ItemId blockingItemId;

    protected BlockingEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping blockedOption  = event.getOption(OPTION_BLOCKED);
        OptionMapping blockingOption = event.getOption(OPTION_BLOCKING);

        assert blockedOption != null;
        assert blockingOption != null;

        try {
            blockedItemId = new ItemId(blockedOption);
            blockingItemId = new ItemId(blockingOption);
        } catch (Throwable exception) {
            throw new RuntimeException("Item could not be found. Please try selecting one from the autocomplete list.");
        }
    }

    protected static ArrayList<OptionData> buildBlockingOptions()
    {
        ArrayList<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, OPTION_BLOCKED, "Blocked item.", true, true));
        options.add(new OptionData(OptionType.STRING, OPTION_BLOCKING, "Blocking item.", true, true));

        return options;
    }

    public ItemId getBlockedItemId()
    {
        return blockedItemId;
    }

    public ItemId getBlockingItemId()
    {
        return blockingItemId;
    }
}
