package bot.event;

import bot.entity.ItemId;
import bot.entity.ListTitle;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import org.jetbrains.annotations.NotNull;

public class EditItemEvent implements AcceptsItemEvent, AcceptsListEvent, EditCommand
{
    private static final String    OPTION_NAME  = "name";
    private static final String    SUBCOMMAND   = "item";
    public static final  String    COMMAND_PATH = MAIN_COMMAND + "/" + SUBCOMMAND;
    private final        ItemId    itemId;
    private final        ListTitle newListTitle;
    private final        String    newName;

    public EditItemEvent(@NotNull SlashCommandInteractionEvent event) throws Exception
    {
        OptionMapping idOption      = event.getOption(OPTION_ITEM);
        OptionMapping newNameOption = event.getOption(OPTION_NAME);
        OptionMapping newListOption = event.getOption(OPTION_LIST);

        assert idOption != null;

        if (newNameOption == null && newListOption == null) {
            throw new Exception("No new attributes given to edit item.");
        }

        itemId = new ItemId(idOption);
        newName = newNameOption != null ? newNameOption.getAsString() : null;
        newListTitle = newListOption != null ? new ListTitle(newListOption.getAsString()) : null;
    }

    protected static SubcommandData buildSubcommand()
    {
        return (new SubcommandData(SUBCOMMAND, "Edit item attributes."))
            .addOption(OptionType.STRING, OPTION_ITEM, "Item.", true, true)
            .addOption(OptionType.STRING, OPTION_NAME, "New name.", false)
            .addOption(OptionType.STRING, OPTION_LIST, "New list to move item to.", false, true);
    }

    public ItemId getItemId()
    {
        return itemId;
    }

    public ListTitle getNewListTitle()
    {
        return newListTitle;
    }

    public String getNewName()
    {
        return newName;
    }
}
