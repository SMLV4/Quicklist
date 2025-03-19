package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class UnblockEvent extends BlockingEvent
{
    public static final String COMMAND = "unblock";

    public UnblockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        super(event);
    }

    public static void addCommand(Guild guild)
    {
        addOptions(guild.upsertCommand(COMMAND, "Remove a blocking item from another item.")).queue();
    }
}
