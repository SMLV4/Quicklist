package bot.event;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public interface EditCommand
{
    String MAIN_COMMAND = "edit";

    static CommandData buildCommand()
    {
        return Commands.slash(EditCommand.MAIN_COMMAND, "EDIT")
            .addSubcommands(
                EditItemEvent.buildSubcommand(),
                EditListEvent.buildSubcommand()
            );
    }
}
