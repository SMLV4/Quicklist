package bot.event;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.CommandCreateAction;
import org.jetbrains.annotations.NotNull;

abstract public class BlockingEvent
{
    public static final String OPTION_BLOCKED  = "blocked";
    public static final String OPTION_BLOCKING = "blocking";
    private final       int    blockedId;
    private final       int    blockingId;

    protected BlockingEvent(@NotNull SlashCommandInteractionEvent event)
    {
        OptionMapping blockedOption  = event.getOption(OPTION_BLOCKED);
        OptionMapping blockingOption = event.getOption(OPTION_BLOCKING);

        assert blockedOption != null;
        assert blockingOption != null;

        blockedId = blockedOption.getAsInt();
        blockingId = blockingOption.getAsInt();
    }

    protected static CommandCreateAction addOptions(CommandCreateAction command)
    {
        return command.addOption(OptionType.STRING, OPTION_BLOCKED, "Blocked item.", true, true)
            .addOption(OptionType.STRING, OPTION_BLOCKING, "Blocking item.", true, true);
    }

    public int getBlockedId()
    {
        return blockedId;
    }

    public int getBlockingId()
    {
        return blockingId;
    }
}
