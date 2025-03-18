package bot.entity;

import org.jetbrains.annotations.Nullable;

public class ListTitle
{
    @Nullable
    private final String shortcut;
    private final String title;

    public ListTitle(String line)
    {
        if (line.contains("[")) {
            String[] parts = line.split("]");
            this.title = line.split("]")[parts.length - 1].trim();
            this.shortcut = line.replace("[", "")
                .split("]")[0];
        } else {
            this.title = line;
            this.shortcut = null;
        }
    }

    public ListTitle(String title, @Nullable String shortcut)
    {
        this.title = title;
        this.shortcut = shortcut;
    }

    public @Nullable String getShortcut()
    {
        return shortcut;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean is(String listIdentifier)
    {
        return title.equals(listIdentifier) | (shortcut != null && shortcut.equals(listIdentifier));
    }

    public boolean is(ListTitle that)
    {
        return title.equals(that.getTitle()) | (shortcut != null && shortcut.equals(that.getShortcut()));
    }

    public boolean isShortcut(@Nullable String shortcut)
    {
        return this.shortcut != null && this.shortcut.equals(shortcut);
    }

    public boolean isTitle(@Nullable String title)
    {
        return this.title.equals(title);
    }

    @Override
    public String toString()
    {
        return shortcut != null ? String.format("[%s] %s", shortcut, title) : title;
    }
}
