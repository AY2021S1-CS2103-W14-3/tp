package seedu.expense.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.expense.commons.core.GuiSettings;
import seedu.expense.model.budget.Budget;
import seedu.expense.model.expense.Amount;
import seedu.expense.model.expense.Expense;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Expense> PREDICATE_SHOW_ALL_EXPENSES = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' expense book file path.
     */
    Path getExpenseBookFilePath();

    /**
     * Sets the user prefs' expense book file path.
     */
    void setExpenseBookFilePath(Path expenseBookFilePath);

    /**
     * Replaces expense book data with the data in {@code expenseBook}.
     */
    void setExpenseBook(ReadOnlyExpenseBook expenseBook);

    /**
     * Returns the ExpenseBook
     */
    ReadOnlyExpenseBook getExpenseBook();

    /**
     * Returns true if an expense with the same identity as {@code expense} exists in the expense book.
     */
    boolean hasExpense(Expense expense);

    /**
     * Deletes the given expense.
     * The expense must exist in the expense book.
     */
    void deleteExpense(Expense target);

    /**
     * Adds the given expense.
     * {@code expense} must not already exist in the expense book.
     */
    void addExpense(Expense expense);

    /**
     * Replaces the given expense {@code target} with {@code editedExpense}.
     * {@code target} must exist in the expense book.
     * The expense identity of {@code editedExpense} must not be the same as another existing expense
     * in the expense book.
     */
    void setExpense(Expense target, Expense editedExpense);

    /**
     * Returns an unmodifiable view of the filtered expense list
     */
    ObservableList<Expense> getFilteredExpenseList();

    /**
     * Updates the filter of the filtered expense list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredExpenseList(Predicate<Expense> predicate);

    /**
     * Returns the budget.
     */
    Budget getTotalBudget();

    /**
     * Adds the given amount to the budget.
     */
    void topupBudget(Amount amount);
}
