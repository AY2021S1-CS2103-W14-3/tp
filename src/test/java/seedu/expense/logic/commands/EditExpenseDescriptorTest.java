package seedu.expense.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.expense.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.expense.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.expense.logic.commands.CommandTestUtil.VALID_AMOUNT_BOB;
import static seedu.expense.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.expense.logic.commands.CommandTestUtil.VALID_DESCRIPTION_BOB;
import static seedu.expense.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.expense.testutil.EditExpenseDescriptorBuilder;

public class EditExpenseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditExpenseDescriptor descriptorWithSameValues = new EditCommand.EditExpenseDescriptor(DESC_AMY);
        assertTrue(DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AMY.equals(DESC_AMY));

        // null -> returns false
        assertFalse(DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(DESC_AMY.equals(DESC_BOB));

        // different name -> returns false
        EditCommand.EditExpenseDescriptor editedAmy = new EditExpenseDescriptorBuilder(DESC_AMY)
                .withDescription(VALID_DESCRIPTION_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_AMY).withAmount(VALID_AMOUNT_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different email -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_AMY).withDate(VALID_DATE_BOB).build();
        assertFalse(DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExpenseDescriptorBuilder(DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(DESC_AMY.equals(editedAmy));
    }
}
