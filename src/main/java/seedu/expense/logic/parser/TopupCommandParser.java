package seedu.expense.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.expense.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.expense.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.expense.commons.exceptions.IllegalValueException;
import seedu.expense.logic.commands.TopupCommand;
import seedu.expense.logic.parser.exceptions.ParseException;
import seedu.expense.model.expense.Amount;

/**
 * Parses input arguments and creates a new TopupCommand object
 */
public class TopupCommandParser implements Parser<TopupCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the {@code TopupCommand}
     * and returns a {@code TopupCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TopupCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Amount amount;
        try {
            amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TopupCommand.MESSAGE_USAGE), ive);
        }
        return new TopupCommand(amount);
    }

}
