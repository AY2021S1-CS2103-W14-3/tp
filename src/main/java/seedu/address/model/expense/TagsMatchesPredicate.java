package seedu.address.model.expense;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Expense}'s {@code Description} matches any of the keywords given.
 */
public class TagsMatchesPredicate implements Predicate<Expense> {
    private final HashSet<Tag> tags;

    /**
     * Constructor that takes in a list of strings to match.
     */
    public TagsMatchesPredicate(List<String> tagStrings) {
        this.tags = new HashSet<>();
        for (String i: tagStrings) {
            this.tags.add(new Tag(i));
        }
    }

    @Override
    public boolean test(Expense expense) {
        for (Tag t: this.tags) {
            if (expense.getTags().contains(t)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if there are no tags to match in this predicate. Otherwise, return false.
     */
    public boolean isEmpty() {
        return tags.isEmpty();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagsMatchesPredicate // instanceof handles nulls
                && tags.equals(((TagsMatchesPredicate) other).tags)); // state check
    }

}
