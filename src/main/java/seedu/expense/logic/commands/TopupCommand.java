package seedu.expense.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.expense.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.expense.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expense.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.expense.model.ExpenseBook.DEFAULT_TAG;

import seedu.expense.logic.commands.exceptions.CommandException;
import seedu.expense.model.Model;
import seedu.expense.model.expense.Amount;
import seedu.expense.model.tag.Tag;

/**
 * Tops up the budget by a specified amount.
 */
public class TopupCommand extends Command {

    public static final String COMMAND_WORD = "topup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tops up the category-budget by a specified amount.\n"
            + "If category is unspecified, tops up the default category-budget. "
            + "Parameters: "
            + PREFIX_AMOUNT + " AMOUNT "
            + "[" + PREFIX_TAG + " CATEGORY] \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_AMOUNT + "59.90 "
            + PREFIX_TAG + "Food";

    public static final String MESSAGE_SUCCESS = "New budget amount for %s: $%s";
    public static final String MESSAGE_CATEGORY_NOT_FOUND = "The expense book does not contain the category %s";

    private final Amount toAdd;
    private final Tag category;

    /**
     * Creates a TopupCommand to top up the {@code defaultBudget} by the specified {@code Amount}.
     */
    public TopupCommand(Amount amount) {
        requireNonNull(amount);
        toAdd = amount;
        category = DEFAULT_TAG;
    }

    /**
     * Creates a TopupCommand to top up the {@code CategoryBudget} that matches the {@code tag} by the specified
     * {@code amount}.
     */
    public TopupCommand(Amount amount, Tag tag) {
        requireAllNonNull(amount, tag);
        toAdd = amount;
        category = tag;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (!model.hasCategory(category)) {
            throw new CommandException(String.format(MESSAGE_CATEGORY_NOT_FOUND, category.toString()));
        }

        model.topupCategoryBudget(category, toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, category.tagName,
                model.getCategoryBudget(category).getAmount()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TopupCommand // instanceof handles nulls
                && toAdd.equals(((TopupCommand) other).toAdd))
                && category.equals(((TopupCommand) other).category);
    }
}
