package seedu.expense.logic.parser;

import static seedu.expense.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expense.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.expense.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.expense.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.expense.logic.commands.TopupCommand;
import seedu.expense.model.expense.Amount;

class TopupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopupCommand.MESSAGE_USAGE);

    private final TopupCommandParser parser = new TopupCommandParser();

    @Test
    void parse_validValue_success() {
        String amount = "1";
        String userInput = " " + PREFIX_AMOUNT + amount;

        TopupCommand expectedCommand = new TopupCommand(new Amount(amount));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    void parse_invalidValue_failure() {
        assertParseFailure(parser, " " + PREFIX_AMOUNT + "one", MESSAGE_INVALID_FORMAT);
    }
}