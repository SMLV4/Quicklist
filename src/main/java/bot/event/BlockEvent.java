package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.jetbrains.annotations.NotNull;

public class BlockEvent extends BlockingEvent
{
    public static final String COMMAND = "block";

    public BlockEvent(@NotNull SlashCommandInteractionEvent event)
    {
        super(event);
    }

    public static void addCommand(Guild guild)
    {
        addOptions(guild.upsertCommand(COMMAND, "Mark first item as blocked by the second item.")).queue();
    }
}
