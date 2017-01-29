package teammates.test.cases.browsertests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import teammates.common.util.AppUrl;
import teammates.common.util.Const;
import teammates.test.pageobjects.AdminEmailLogPage;

public class AdminEmailLogPageUiTest extends BaseUiTestCase {
    
    private static final int ADMIN_EMAIL_LOG_TABLE_NUM_COLUMNS = 3;
    
    private static AdminEmailLogPage emailLogPage;
    
    @Override
    protected void prepareTestData() {
        // no test data used in this test
    }
    
    @Test
    public void testAll() {
        testContent();
        testFilterReference();
    }

    private void testFilterReference() {
        emailLogPage.clickReferenceButton();
        assertTrue(emailLogPage.isFilterReferenceVisible());
    }

    public void testContent() {
        
        ______TS("content: typical page");
        
        AppUrl logPageUrl = createUrl(Const.ActionURIs.ADMIN_EMAIL_LOG_PAGE);
        emailLogPage = loginAdminToPage(logPageUrl, AdminEmailLogPage.class);
        emailLogPage.verifyIsCorrectPage();
        assertTrue(isEmailLogDataDisplayCorrect());
    }

    /**
     * This method only checks if the email log data table are displayed correctly
     * i.e, table headers are correct
     * It does not test for the table content
     */
    private boolean isEmailLogDataDisplayCorrect() {
        return emailLogPage.isElementPresent(By.className("table")) && isEmailLogTableHeaderCorrect();
    }

    private boolean isEmailLogTableHeaderCorrect() {
        int numColumns = emailLogPage.getNumberOfColumnsFromDataTable(0); // 1 table
        
        if (numColumns != ADMIN_EMAIL_LOG_TABLE_NUM_COLUMNS) {
            return false;
        }
        
        List<String> expectedSessionTableHeaders = Arrays.asList("Receiver",
                                                                 "Subject",
                                                                 "Date");
        List<String> actualSessionTableHeaders = new ArrayList<String>();
        
        for (int i = 0; i < numColumns; i++) {
            actualSessionTableHeaders.add(emailLogPage.getHeaderValueFromDataTable(0, 0, i));
        }
        
        return actualSessionTableHeaders.equals(expectedSessionTableHeaders);
    }
}
