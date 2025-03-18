package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class RemoveItemEvent extends Event
{
    public static final String COMMAND = "remove-item";
    private final       int    id;

    public RemoveItemEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping idOption = event.getOption(OPTION_ITEM);

        assert idOption != null;

        id = idOption.getAsInt();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove an item from a list.")
            .addOption(OptionType.INTEGER, OPTION_ITEM, "Item.", true, true)
            .queue();
    }

    public int getId()
    {
        return id;
    }
}
