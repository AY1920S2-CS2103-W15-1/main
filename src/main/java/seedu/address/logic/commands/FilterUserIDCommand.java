package seedu.address.logic.commands;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.conditions.Conditions;
import seedu.address.logic.conditions.UserIDConditions;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.storage.AppStorage;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterUserIDCommand implements AppCommand {
    private static int USER_ID;
    public static final String COMMAND_WORD = "id";
    private static final Pattern COMMAND_FORMAT = Pattern.compile("is (?<userid>\\d+)");
    private final Logger logger = LogsCenter.getLogger(FilterUserIDCommand.class);

    @Override
    public FilterUserIDCommand validate(String arguments) throws ParseException {
        logger.info(String.format("Validating: %s", arguments));
        final Matcher matcher = COMMAND_FORMAT.matcher(arguments.trim());

        if (!matcher.matches()) {
            String error = String.format("Command %s invalid", arguments);
            throw new ParseException(error);
        }

        this.USER_ID = Integer.parseInt(matcher.group("userid"));
        return this;
    }

    @Override
    public AppCommandResult execute(AppStorage dao) {
        Conditions cond = new UserIDConditions(USER_ID);
        ArrayList resp  = dao.search(cond);
        AppCommandResult result = new AppCommandResult("Identifying recods with User ID.", false);
        result.setToDisplayList(resp);
        return result;
    }
}
