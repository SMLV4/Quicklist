package bot.event;

import bot.entity.ItemId;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class RemoveItemEvent implements AcceptsItemEvent, RemoveCommand
{
    private static final String SUBCOMMAND   = "item";
    public static final  String COMMAND_PATH = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ItemId itemId;

    public RemoveItemEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping idOption = event.getOption(OPTION_ITEM);

        assert idOption != null;

        itemId = new ItemId(idOption);
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Remove an item from a list."))
            .addOption(OptionType.INTEGER, OPTION_ITEM, "Item.", true, true);
    }

    public ItemId getItemId()
    {
        return itemId;
    }
}
