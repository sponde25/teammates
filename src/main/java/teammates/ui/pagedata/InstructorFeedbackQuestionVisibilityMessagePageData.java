package teammates.ui.pagedata;

import java.util.List;

import teammates.common.datatransfer.AccountAttributes;

public class InstructorFeedbackQuestionVisibilityMessagePageData extends PageData {
    public List<String> visibilityMessage;

    public InstructorFeedbackQuestionVisibilityMessagePageData(AccountAttributes account) {
        super(account);
    }
}
