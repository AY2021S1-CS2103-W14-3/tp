package seedu.expense.model;

import static java.util.Objects.requireNonNull;
import static seedu.expense.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.expense.commons.core.GuiSettings;
import seedu.expense.commons.core.LogsCenter;
import seedu.expense.model.budget.Budget;
import seedu.expense.model.budget.CategoryBudget;
import seedu.expense.model.budget.UniqueCategoryBudgetList;
import seedu.expense.model.expense.Amount;
import seedu.expense.model.expense.Expense;
import seedu.expense.model.tag.Tag;

/**
 * Represents the in-memory model of the expense book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ExpenseBook expenseBook;
    private final CategoryExpenseBook categoryExpenseBook;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given expenseBook and userPrefs.
     */
    public ModelManager(ReadOnlyExpenseBook expenseBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(expenseBook, userPrefs);

        logger.fine("Initializing with expense book: " + expenseBook + " and user prefs " + userPrefs);

        this.expenseBook = new ExpenseBook(expenseBook);
        this.userPrefs = new UserPrefs(userPrefs);
        categoryExpenseBook = new CategoryExpenseBook(this.expenseBook);
    }

    public ModelManager() {
        this(new ExpenseBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getExpenseBookFilePath() {
        return userPrefs.getExpenseBookFilePath();
    }

    @Override
    public void setExpenseBookFilePath(Path expenseBookFilePath) {
        requireNonNull(expenseBookFilePath);
        userPrefs.setExpenseBookFilePath(expenseBookFilePath);
    }

    //=========== ExpenseBook ================================================================================

    @Override
    public void setExpenseBook(ReadOnlyExpenseBook expenseBook) {
        this.expenseBook.resetData(expenseBook);
    }

    @Override
    public ReadOnlyExpenseBook getExpenseBook() {
        return expenseBook;
    }

    @Override
    public ReadOnlyExpenseBook getCategoryExpenseBook() {
        return categoryExpenseBook;
    }

    @Override
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenseBook.hasExpense(expense);
    }

    @Override
    public void deleteExpense(Expense target) {
        expenseBook.removeExpense(target);
    }

    @Override
    public void addExpense(Expense expense) {
        expenseBook.addExpense(expense);
        updateFilteredExpenseList(PREDICATE_SHOW_ALL_EXPENSES);
    }

    @Override
    public void setExpense(Expense target, Expense editedExpense) {
        requireAllNonNull(target, editedExpense);

        expenseBook.setExpense(target, editedExpense);
    }

    @Override
    public Budget getTotalBudget() {
        return expenseBook.getBudgets();
    }

    @Override
    public void topupBudget(Amount amount) {
        expenseBook.topupBudget(amount);
    }

    //=========== Filtered Expense List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Expense} backed by the internal list of
     * {@code versionedExpenseBook}
     */
    @Override
    public ObservableList<Expense> getFilteredExpenseList() {
        return categoryExpenseBook.getFilteredExpenses();
    }

    @Override
    public void updateFilteredExpenseList(Predicate<Expense> predicate) {
        requireNonNull(predicate);
        categoryExpenseBook.updateFilteredExpenses(predicate);
        //filteredExpenses.setPredicate(predicate);
    }

    /**
     * Checks if the given Tag is present in any of the category budget.
     */
    @Override
    public boolean hasCategory(Tag toCheck) {
        return categoryExpenseBook.containsCategory(toCheck);
    }

    /**
     * Switches the expense book into the one that matches the given Tag.
     */
    @Override
    public void switchCategory(Tag category) {
        requireAllNonNull(category);
        if (hasCategory(category)) {
            updateFilteredExpenseList(expense -> expense.getTags().contains(category));
            updateCategoryExpenseBook(category);
        }
    }

    /**
     * Updates the CategoryExpenseBook to the given category
     *
     * @param category
     */
    @Override
    public void updateCategoryExpenseBook(Tag category) {
        requireNonNull(category);

        if (category.equals(new Tag("Default"))) {
            categoryExpenseBook.updateFilteredBudgets(budget -> true);
            categoryExpenseBook.updateFilteredExpenses(expense -> true);
        } else {
            categoryExpenseBook.updateFilteredBudgets(budget -> budget.getTag().equals(category));
            categoryExpenseBook.updateFilteredExpenses(expense -> expense.getTags().contains(category));
        }
    }

    @Override
    public void updateFilteredBudgetList(Predicate<CategoryBudget> predicate) {
        requireNonNull(predicate);
        categoryExpenseBook.updateFilteredBudgets(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return expenseBook.equals(other.expenseBook)
                && userPrefs.equals(other.userPrefs);
    }

}
