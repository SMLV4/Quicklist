package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import org.jetbrains.annotations.NotNull;

public class UnblockEvent extends Event
{
    public static final  String COMMAND            = "unblock";
    private static final String OPTION_BLOCKING_ID = "blocking-id";
    private static final String OPTION_ID          = "id";
    private final        int    blockingId;
    private final        int    id;

    public UnblockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping idOption         = event.getOption(OPTION_ID);
        OptionMapping blockingIdOption = event.getOption(OPTION_BLOCKING_ID);

        assert idOption != null;
        assert blockingIdOption != null;

        id = idOption.getAsInt();
        blockingId = blockingIdOption.getAsInt();
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Remove a blocking item from another item.")
            .addOption(OptionType.STRING, OPTION_ID, "Item id.", true)
            .addOption(OptionType.STRING, OPTION_BLOCKING_ID, "Blocking item id.", true)
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
