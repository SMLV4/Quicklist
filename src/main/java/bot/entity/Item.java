package bot.entity;

import java.util.ArrayList;
import java.util.List;

public class Item
{
    public static final  String            BLOCKED_BULLET   = ":small_orange_diamond:";
    public static final  String            UNBLOCKED_BULLET = ":white_small_square:";
    private static final String            BLOCKING_EMOJI   = ":no_entry:";
    private static final String            NOTE_BULLET      = ":black_small_square:";
    private final        ArrayList<ItemId> blockingIds;
    private final        ItemId            id;
    private final        ListTitle         listTitle;
    private final        ArrayList<String> notes;
    private              String            name;

    public Item(String name, ItemId id, ArrayList<ItemId> blockingIds, ArrayList<String> notes, ListTitle listTitle)
    {
        this.name = name;
        this.id = id;
        this.blockingIds = blockingIds;
        this.notes = notes;
        this.listTitle = listTitle;
    }

    public Item(ListTitle listTitle, String itemString)
    {
        this.listTitle = listTitle;

        itemString = itemString.replace(UNBLOCKED_BULLET, "")
            .replace(BLOCKED_BULLET, "")
            .trim();

        this.id = new ItemId(itemString.substring(0, ItemId.LENGTH - 1));

        String[] itemLines = itemString.split("\n");

        String[] itemSegments = itemLines[0].substring(ItemId.LENGTH)
            .split(BLOCKING_EMOJI);

        this.name = itemSegments[0].trim();
        this.blockingIds = new ArrayList<>();
        this.notes = new ArrayList<>();

        if (itemSegments.length > 1) {
            ArrayList<String> blockingIdStrings = new ArrayList<>(List.of(itemSegments[1].trim().split(" ")));
            for (String blockingIdString : blockingIdStrings) {
                blockingIds.add(new ItemId(blockingIdString));
            }
        }

        int lineCount = 0;
        for (String line : itemLines) {
            if (lineCount > 0) {
                notes.add(line.substring(line.indexOf(".") + 1).trim());
            }
            lineCount++;
        }
    }

    public void addBlock(ItemId id) throws Exception
    {
        if (isBlockedBy(id)) {
            throw new Exception("Item " + this.id + " is already blocked by " + id);
        }

        blockingIds.add(id);
    }

    public void addNote(String note)
    {
        notes.add(note);
    }

    public ArrayList<ItemId> getBlockingIds()
    {
        return blockingIds;
    }

    public ItemId getId()
    {
        return id;
    }

    public ListTitle getListTitle()
    {
        return listTitle;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getNotes()
    {
        return notes;
    }

    public boolean is(ItemId id)
    {
        return this.id.equals(id);
    }

    public boolean isBlocked()
    {
        return !blockingIds.isEmpty();
    }

    public boolean isBlockedBy(ItemId id)
    {
        for (ItemId blockingId : blockingIds) {
            if (blockingId.equals(id)) {
                return true;
            }
        }

        return false;
    }

    public void removeBlock(ItemId id) throws Exception
    {
        if (!isBlockedBy(id)) {
            throw new Exception("Item " + this.id + " is not blocked by " + id);
        }

        blockingIds.removeIf(blockingId -> blockingId.equals(id));
    }

    public void removeNote(int index)
    {
        if (index >= notes.size()) {
            throw new RuntimeException("Item __" + name + "__ does not have that many notes.");
        }

        notes.remove(index);
    }

    @Override
    public String toString()
    {
        String        bullet = isBlocked() ? BLOCKED_BULLET : UNBLOCKED_BULLET;
        StringBuilder result = new StringBuilder(bullet + " " + id + " " + name);

        if (isBlocked()) {
            result.append(" " + BLOCKING_EMOJI);
            for (ItemId blockingId : blockingIds) {
                result.append(" ")
                    .append(blockingId);
            }
        }

        int noteIndex = 1;
        for (String note : notes) {
            result.append("\n-# ")
                .append(NOTE_BULLET)
                .append(" ")
                .append(noteIndex++)
                .append(". ")
                .append(note);
        }

        return result.toString();
    }

    public void updateName(String name)
    {
        this.name = name;
    }
}
