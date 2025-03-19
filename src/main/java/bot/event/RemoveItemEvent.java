package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class RemoveItemEvent implements AcceptsItemEvent
{
    public static final String COMMAND = "remove-item";
    private final       ItemId    itemId;

    public RemoveItemEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping idOption = event.getOption(OPTION_ITEM);

        assert idOption != null;

        itemId = new ItemId(idOption);
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove an item from a list.")
            .addOption(OptionType.INTEGER, OPTION_ITEM, "Item.", true, true)
            .queue();
    }

    public ItemId getItemId()
    {
        return itemId;
    }
}
