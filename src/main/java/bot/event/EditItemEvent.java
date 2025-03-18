package bot.event;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

public class EditItemEvent extends Event
{
    public static final  String COMMAND         = "edit-item";
    private static final String OPTION_NEW_NAME = "new-name";
    private final        int    id;
    private final        String newList;
    private final        String newName;

    public EditItemEvent(@NotNull SlashCommandInteractionEvent event) throws Exception
    {
        OptionMapping idOption      = event.getOption(OPTION_ITEM);
        OptionMapping newNameOption = event.getOption(OPTION_NEW_NAME);
        OptionMapping newListOption = event.getOption(OPTION_LIST);

        assert idOption != null;

        if (newNameOption == null && newListOption == null) {
            throw new Exception("No new attributes given to edit item.");
        }

        id = idOption.getAsInt();
        newName = newNameOption != null ? newNameOption.getAsString() : null;
        newList = newListOption != null ? newListOption.getAsString() : null;
    }

    public static void addCommand(Guild guild)
    {
        guild.upsertCommand(COMMAND, "Edit item attributes.")
            .addOption(OptionType.STRING, OPTION_ITEM, "Item.", true, true)
            .addOptions(
                new OptionData(OptionType.STRING, OPTION_NEW_NAME, "New name.", false),
                new OptionData(OptionType.STRING, OPTION_LIST, "List to move item to.", false, true)
            )
            .queue();
    }

    public int getId()
    {
        return id;
    }

    public String getNewList()
    {
        return newList;
    }

    public String getNewName()
    {
        return newName;
    }
}
