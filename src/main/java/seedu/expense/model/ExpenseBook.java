package seedu.expense.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.expense.model.budget.CategoryBudget;
import seedu.expense.model.budget.UniqueCategoryBudgetList;
import seedu.expense.model.expense.Expense;
import seedu.expense.model.expense.UniqueExpenseList;

/**
 * Wraps all data at the expense-book level
 * Duplicates are not allowed (by .isSameExpense comparison)
 */
public class ExpenseBook implements ReadOnlyExpenseBook {
    private final UniqueCategoryBudgetList budgets;
    private final UniqueExpenseList expenses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        budgets = new UniqueCategoryBudgetList();
        expenses = new UniqueExpenseList();
    }

    public ExpenseBook() {
    }

    /**
     * Creates an ExpenseBook using the Expenses in the {@code toBeCopied}
     */
    public ExpenseBook(ReadOnlyExpenseBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the expense list with {@code expenses}.
     * {@code expenses} must not contain duplicate expenses.
     */
    public void setExpenses(List<Expense> expenses) {
        this.expenses.setExpenses(expenses);
    }

    public void setBudgets(UniqueCategoryBudgetList budgets) {
        this.budgets.setBudgets(budgets);
    }

    /**
     * Resets the existing data of this {@code ExpenseBook} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenseBook newData) {
        requireNonNull(newData);

        setExpenses(newData.getExpenseList());
        setBudgets(newData.getBudgets());
    }

    //// budget-level operations

    @Override
    public UniqueCategoryBudgetList getBudgets() {
        return budgets;
    }

    public ObservableList<CategoryBudget> getBudgetList() {
        return budgets.asUnmodifiableObservableList();
    }

    //// expense-level operations

    /**
     * Returns true if a expense with the same identity as {@code expense} exists in the expense book.
     */
    public boolean hasExpense(Expense expense) {
        requireNonNull(expense);
        return expenses.contains(expense);
    }

    /**
     * Adds a expense to the expense book.
     * The expense must not already exist in the expense book.
     */
    public void addExpense(Expense p) {
        expenses.add(p);
    }

    /**
     * Replaces the given expense {@code target} in the list with {@code editedExpense}.
     * {@code target} must exist in the expense book.
     * The expense identity of {@code editedExpense} must not be the same as another existing
     * expense in the expense book.
     */
    public void setExpense(Expense target, Expense editedExpense) {
        requireNonNull(editedExpense);

        expenses.setExpense(target, editedExpense);
    }

    /**
     * Removes {@code key} from this {@code ExpenseBook}.
     * {@code key} must exist in the expense book.
     */
    public void removeExpense(Expense key) {
        expenses.remove(key);
    }

    /**
     * {@inheritDoc}
     *
     * @see UniqueExpenseList#tallyExpenses()
     */
    @Override
    public double tallyExpenses() {
        return expenses.tallyExpenses();
    }

    /**
     * {@inheritDoc}
     *
     * @see UniqueCategoryBudgetList#tallyAmounts()
     */
    @Override
    public double tallyBudgets() {
        return budgets.tallyAmounts();
    }

    /**
     * Tallies the balance of budgets and expenses in the expense book.
     *
     * @return tallied balance of the expense book
     */
    @Override
    public double tallyBalance() {
        return tallyBudgets() - tallyExpenses();
    }

    //// util methods

    @Override
    public String toString() {
        return expenses.asUnmodifiableObservableList().size() + " expenses";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expense> getExpenseList() {
        return expenses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenseBook // instanceof handles nulls
                && expenses.equals(((ExpenseBook) other).expenses));
    }

    @Override
    public int hashCode() {
        return expenses.hashCode();
    }
}
