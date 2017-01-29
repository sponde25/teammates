package teammates.common.datatransfer;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import teammates.common.util.Const;
import teammates.common.util.Sanitizer;
import teammates.common.util.Templates;
import teammates.common.util.Templates.FeedbackQuestion.FormTemplates;
import teammates.common.util.Templates.FeedbackQuestion.Slots;
import teammates.ui.pagedata.PageData;
import teammates.ui.template.ElementTag;
import teammates.ui.template.InstructorFeedbackResultsResponseRow;

public class FeedbackRankRecipientsQuestionDetails extends FeedbackRankQuestionDetails {
    
    public FeedbackRankRecipientsQuestionDetails() {
        super(FeedbackQuestionType.RANK_RECIPIENTS);
    }

    @Override
    public String getQuestionTypeDisplayName() {
        return Const.FeedbackQuestionTypeNames.RANK_RECIPIENT;
    }

    @Override
    public String getQuestionWithExistingResponseSubmissionFormHtml(
                        boolean sessionIsOpen, int qnIdx, int responseIdx, String courseId,
                        int totalNumRecipients,
                        FeedbackResponseDetails existingResponseDetails) {
        
        FeedbackRankRecipientsResponseDetails existingResponse =
                (FeedbackRankRecipientsResponseDetails) existingResponseDetails;
        StringBuilder optionListHtml = new StringBuilder();
        String optionFragmentTemplate = FormTemplates.RANK_SUBMISSION_FORM_OPTIONFRAGMENT;
        
        String optionFragment =
                Templates.populateTemplate(optionFragmentTemplate,
                        Slots.QUESTION_INDEX, Integer.toString(qnIdx),
                        Slots.RESPONSE_INDEX, Integer.toString(responseIdx),
                        Slots.OPTION_INDEX, "0",
                        Slots.DISABLED, sessionIsOpen ? "" : "disabled",
                        Slots.RANK_OPTION_VISIBILITY, "style=\"display:none\"",
                        Slots.OPTIONS, getSubmissionOptionsHtmlForRankingRecipients(
                                           totalNumRecipients, existingResponse.answer),
                        Slots.FEEDBACK_RESPONSE_TEXT, Const.ParamsNames.FEEDBACK_RESPONSE_TEXT,
                        Slots.RANK_OPTION_VALUE, "");
        optionListHtml.append(optionFragment).append(Const.EOL);

        String html = Templates.populateTemplate(
                FormTemplates.RANK_SUBMISSION_FORM,
                Slots.RANK_SUBMISSION_FORM_OPTION_FRAGMENTS, optionListHtml.toString(),
                Slots.QUESTION_INDEX, Integer.toString(qnIdx),
                Slots.RESPONSE_INDEX, Integer.toString(responseIdx),
                Slots.RANK_OPTION_VISIBILITY, "style=\"display:none\"",
                Slots.RANK_PARAM_TO_RECIPIENT, Const.ParamsNames.FEEDBACK_QUESTION_RANKTORECIPIENTS,
                Slots.RANK_TO_RECIPIENTS_VALUE, "true",
                Slots.RANK_PARAM_NUM_OPTION, Const.ParamsNames.FEEDBACK_QUESTION_RANKNUMOPTIONS,
                Slots.RANK_NUM_OPTION_VALUE, Integer.toString(0),
                Slots.RANK_PARAM_IS_DUPLICATES_ALLOWED, Const.ParamsNames.FEEDBACK_QUESTION_RANKISDUPLICATESALLOWED,
                Slots.RANK_ARE_DUPLICATES_ALLOWED_VALUE, Boolean.toString(isAreDuplicatesAllowed())
                );
        
        return html;
    }

    @Override
    public String getQuestionWithoutExistingResponseSubmissionFormHtml(
            boolean sessionIsOpen, int qnIdx, int responseIdx, String courseId, int totalNumRecipients) {
        
        StringBuilder optionListHtml = new StringBuilder();
        String optionFragmentTemplate = FormTemplates.RANK_SUBMISSION_FORM_OPTIONFRAGMENT;
        
        String optionFragment =
                Templates.populateTemplate(optionFragmentTemplate,
                        Slots.QUESTION_INDEX, Integer.toString(qnIdx),
                        Slots.RESPONSE_INDEX, Integer.toString(responseIdx),
                        Slots.OPTION_INDEX, "0",
                        Slots.DISABLED, sessionIsOpen ? "" : "disabled",
                        Slots.RANK_OPTION_VISIBILITY, "style=\"display:none\"",
                        Slots.OPTIONS, getSubmissionOptionsHtmlForRankingRecipients(
                                           totalNumRecipients, Const.INT_UNINITIALIZED),
                        Slots.FEEDBACK_RESPONSE_TEXT, Const.ParamsNames.FEEDBACK_RESPONSE_TEXT,
                        Slots.RANK_OPTION_VALUE, "");
        optionListHtml.append(optionFragment).append(Const.EOL);

        return Templates.populateTemplate(
                FormTemplates.RANK_SUBMISSION_FORM,
                Slots.RANK_SUBMISSION_FORM_OPTION_FRAGMENTS, optionListHtml.toString(),
                Slots.QUESTION_INDEX, Integer.toString(qnIdx),
                Slots.RESPONSE_INDEX, Integer.toString(responseIdx),
                Slots.RANK_OPTION_VISIBILITY, "style=\"display:none\"",
                Slots.RANK_TO_RECIPIENTS_VALUE, "true",
                Slots.RANK_PARAM_TO_RECIPIENT, Const.ParamsNames.FEEDBACK_QUESTION_RANKTORECIPIENTS,
                Slots.RANK_PARAM_NUM_OPTION, Const.ParamsNames.FEEDBACK_QUESTION_RANKNUMOPTIONS,
                Slots.RANK_NUM_OPTION_VALUE, Integer.toString(0),
                Slots.RANK_PARAM_IS_DUPLICATES_ALLOWED, Const.ParamsNames.FEEDBACK_QUESTION_RANKISDUPLICATESALLOWED,
                Slots.RANK_ARE_DUPLICATES_ALLOWED_VALUE, Boolean.toString(isAreDuplicatesAllowed()));
    }

    private String getSubmissionOptionsHtmlForRankingRecipients(int totalNumRecipients, int rankGiven) {
        
        StringBuilder result = new StringBuilder(100);
  
        ElementTag option = PageData.createOption("", "", rankGiven == Const.INT_UNINITIALIZED);
        result.append("<option"
                     + option.getAttributesToString() + ">"
                     + option.getContent()
                     + "</option>");
        for (int i = 1; i <= totalNumRecipients; i++) {
            option = PageData.createOption(String.valueOf(i), String.valueOf(i), rankGiven == i);
            result.append("<option"
                        + option.getAttributesToString() + ">"
                        + option.getContent()
                        + "</option>");
        }

        return result.toString();
    }

    @Override
    public String getQuestionSpecificEditFormHtml(int questionNumber) {
        
        return Templates.populateTemplate(
                FormTemplates.RANK_EDIT_RECIPIENTS_FORM,
                Slots.QUESTION_NUMBER, Integer.toString(questionNumber),
                Slots.OPTION_RECIPIENT_DISPLAY_NAME, "recipient",
                Slots.RANK_PARAM_IS_DUPLICATES_ALLOWED,
                        Const.ParamsNames.FEEDBACK_QUESTION_RANKISDUPLICATESALLOWED,
                Slots.RANK_ARE_DUPLICATES_ALLOWED_CHECKED, isAreDuplicatesAllowed() ? "checked" : "");
    
    }

    @Override
    public String getNewQuestionSpecificEditFormHtml() {

        return "<div id=\"rankRecipientsForm\">"
                + this.getQuestionSpecificEditFormHtml(-1)
                + "</div>";
    }

    @Override
    public String getQuestionAdditionalInfoHtml(int questionNumber,
            String additionalInfoId) {
        String additionalInfo = this.getQuestionTypeDisplayName() + "<br>";
                
        String html = Templates.populateTemplate(
                        FormTemplates.FEEDBACK_QUESTION_ADDITIONAL_INFO,
                        Slots.MORE, "[more]",
                        Slots.LESS, "[less]",
                        Slots.QUESTION_NUMBER, Integer.toString(questionNumber),
                        Slots.ADDITIONAL_INFO_ID, additionalInfoId,
                        Slots.QUESTION_ADDITIONAL_INFO, additionalInfo);
        
        return html;
    }

    @Override
    public String getQuestionResultStatisticsHtml(
                        List<FeedbackResponseAttributes> responses,
                        FeedbackQuestionAttributes question,
                        String studentEmail,
                        FeedbackSessionResultsBundle bundle,
                        String view) {
        
        if ("student".equals(view) || responses.isEmpty()) {
            return "";
        }
        
        StringBuilder fragments = new StringBuilder();
        
        Map<String, List<Integer>> recipientRanks = generateOptionRanksMapping(responses);

        DecimalFormat df = new DecimalFormat("#.##");
        
        for (Entry<String, List<Integer>> entry : recipientRanks.entrySet()) {
            
            List<Integer> ranks = entry.getValue();
            double average = computeAverage(ranks);
            String ranksReceived = getListOfRanksReceivedAsString(ranks);
            
            String participantIdentifier = entry.getKey();
            String name = bundle.getNameForEmail(participantIdentifier);
            String teamName = bundle.getTeamNameForEmail(participantIdentifier);
            
            fragments.append(Templates.populateTemplate(FormTemplates.RANK_RESULT_STATS_RECIPIENTFRAGMENT,
                    Slots.RANK_OPTION_VALUE, Sanitizer.sanitizeForHtml(name),
                    Slots.TEAM, Sanitizer.sanitizeForHtml(teamName),
                    Slots.RANK_RECIEVED, ranksReceived,
                    Slots.RANK_AVERAGE, df.format(average)));

        }
     
        return Templates.populateTemplate(FormTemplates.RANK_RESULT_RECIPIENT_STATS,
                                                             Slots.RANK_OPTION_RECIPIENT_DISPLAY_NAME, "Recipient",
                                                             Slots.FRAGMENTS, fragments.toString());
        
        
    }

    @Override
    public String getQuestionResultStatisticsCsv(
                        List<FeedbackResponseAttributes> responses,
                        FeedbackQuestionAttributes question,
                        FeedbackSessionResultsBundle bundle) {
        if (responses.isEmpty()) {
            return "";
        }
        
        StringBuilder fragments = new StringBuilder();
        Map<String, List<Integer>> recipientRanks = generateOptionRanksMapping(responses);

        DecimalFormat df = new DecimalFormat("#.##");
        
        for (Entry<String, List<Integer>> entry : recipientRanks.entrySet()) {
            
            String teamName = bundle.getTeamNameForEmail(entry.getKey());
            String recipientName = bundle.getNameForEmail(entry.getKey());
            String option = Sanitizer.sanitizeForCsv(teamName)
                            + ","
                            + Sanitizer.sanitizeForCsv(recipientName);

            List<Integer> ranks = entry.getValue();
            double average = computeAverage(ranks);
            fragments.append(option).append(',').append(df.format(average)).append(Const.EOL);
        }
        
        return "Team, Recipient" + ", Average Rank" + Const.EOL + fragments + Const.EOL;
    }
    
    /**
     * From the feedback responses, generate a mapping of the option to a list of
     * ranks received for that option.
     * The key of the map returned is the recipient's participant identifier.
     * The values of the map are list of ranks received by the recipient.
     * @param responses  a list of responses
     */
    private Map<String, List<Integer>> generateOptionRanksMapping(List<FeedbackResponseAttributes> responses) {
        
        Map<FeedbackResponseAttributes, Integer> normalisedRankOfResponse = getNormalisedRankForEachResponse(responses);

        Map<String, List<Integer>> optionRanks = new HashMap<>();
        for (FeedbackResponseAttributes response : responses) {
            updateOptionRanksMapping(optionRanks, response.recipient, normalisedRankOfResponse.get(response));
        }
        
        return optionRanks;
    }

    /**
     * Returns a map of response to the normalised rank by resolving ties for each giver's set of responses
     * @param responses
     * @see FeedbackRankQuestionDetails#obtainMappingToNormalisedRanksForRanking(Map, List) for how ties are resolved
     */
    private Map<FeedbackResponseAttributes, Integer> getNormalisedRankForEachResponse(
                                                            List<FeedbackResponseAttributes> responses) {

        // collect each giver's responses
        Map<String, List<FeedbackResponseAttributes>> responsesGivenByPerson = new HashMap<>();
        for (FeedbackResponseAttributes response : responses) {
            if (!responsesGivenByPerson.containsKey(response.giver)) {
                responsesGivenByPerson.put(response.giver, new ArrayList<FeedbackResponseAttributes>());
            }
            
            responsesGivenByPerson.get(response.giver)
                                  .add(response);
        }
        
        // resolve ties for each giver's responses
        Map<FeedbackResponseAttributes, Integer> normalisedRankOfResponse = new HashMap<>();
        for (Map.Entry<String, List<FeedbackResponseAttributes>> entry : responsesGivenByPerson.entrySet()) {
            Map<FeedbackResponseAttributes, Integer> rankOfResponse = new HashMap<>();
            for (FeedbackResponseAttributes res : responses) {
                FeedbackRankRecipientsResponseDetails frd = (FeedbackRankRecipientsResponseDetails) res.getResponseDetails();
                rankOfResponse.put(res, frd.answer);
            }
            
            normalisedRankOfResponse.putAll(obtainMappingToNormalisedRanksForRanking(rankOfResponse, entry.getValue()));
        }
        
        return normalisedRankOfResponse;
    }

    @Override
    public boolean isChangesRequiresResponseDeletion(FeedbackQuestionDetails newDetails) {
        return false;
    }

    @Override
    public String getCsvHeader() {
        return "Feedback";
    }

    @Override
    public String getQuestionTypeChoiceOption() {
        return "<li data-questiontype = \"" + FeedbackQuestionType.RANK_RECIPIENTS.name() + "\"><a href=\"javascript:;\">"
              + Const.FeedbackQuestionTypeNames.RANK_RECIPIENT + "</a></li>";
    }

    @Override
    public List<String> validateQuestionDetails() {
        return new ArrayList<>();
    }

    @Override
    public List<String> validateResponseAttributes(
            List<FeedbackResponseAttributes> responses,
            int numRecipients) {
        if (responses.isEmpty()) {
            return new ArrayList<String>();
        }
        
        if (isAreDuplicatesAllowed()) {
            return new ArrayList<String>();
        }
        List<String> errors = new ArrayList<>();
        
        Set<Integer> responseRank = new HashSet<>();
        for (FeedbackResponseAttributes response : responses) {
            FeedbackRankRecipientsResponseDetails frd =
                    (FeedbackRankRecipientsResponseDetails) response.getResponseDetails();
            
            if (responseRank.contains(frd.answer)) {
                errors.add("Duplicate rank " + frd.answer + " in question");
            } else if (frd.answer > numRecipients) {
                errors.add("Invalid rank " + frd.answer + " in question");
            }
            responseRank.add(frd.answer);
        }
    
        return errors;
    }
    
    @Override
    public Comparator<InstructorFeedbackResultsResponseRow> getResponseRowsSortOrder() {
        return new Comparator<InstructorFeedbackResultsResponseRow>() {

            @Override
            public int compare(InstructorFeedbackResultsResponseRow o1,
                               InstructorFeedbackResultsResponseRow o2) {
                
                if (!o1.getGiverTeam().equals(o2.getGiverTeam())) {
                    return o1.getGiverTeam().compareTo(o2.getGiverTeam());
                }
                
                if (!o1.getGiverDisplayableIdentifier().equals(o2.getGiverDisplayableIdentifier())) {
                    return o1.getGiverDisplayableIdentifier().compareTo(o2.getGiverDisplayableIdentifier());
                }
                
                if (!o1.getDisplayableResponse().equals(o2.getDisplayableResponse())) {
                    return o1.getDisplayableResponse().compareTo(o2.getDisplayableResponse());
                }
                
                if (!o1.getRecipientTeam().equals(o2.getRecipientTeam())) {
                    return o1.getRecipientTeam().compareTo(o2.getRecipientTeam());
                }
                
                return o1.getRecipientDisplayableIdentifier().compareTo(o2.getRecipientDisplayableIdentifier());
            }
            
        };
    }

    @Override
    public String validateGiverRecipientVisibility(FeedbackQuestionAttributes feedbackQuestionAttributes) {
        return "";
    }
}
