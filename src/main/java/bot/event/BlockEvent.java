package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class BlockEvent extends Event
{
    public static final  String COMMAND         = "block";
    private static final String OPTION_BLOCKING = "blocking";
    private static final String OPTION_ID       = "id";
    private final        int    blockingId;
    private final        int    id;

    public BlockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping idOption         = event.getOption(OPTION_ID);
        OptionMapping blockingIdOption = event.getOption(OPTION_BLOCKING);

        assert idOption != null;
        assert blockingIdOption != null;

        id = idOption.getAsInt();
        blockingId = blockingIdOption.getAsInt();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(
                COMMAND,
                "Mark an item as blocked by another item. " +
                "Blocked items will be excluded until they become unblocked."
            )
            .addOption(OptionType.STRING, OPTION_ITEM, "Item.", true, true)
            .addOption(OptionType.STRING, OPTION_BLOCKING, "Blocking item.", true, true)
            .queue();
    }

    public int getBlockingId()
    {
        return blockingId;
    }

    public int getId()
    {
        return id;
    }
}
