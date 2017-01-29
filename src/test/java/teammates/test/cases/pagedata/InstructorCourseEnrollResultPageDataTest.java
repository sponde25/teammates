package teammates.test.cases.pagedata;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.StudentAttributes;
import teammates.common.datatransfer.StudentUpdateStatus;
import teammates.test.cases.BaseTestCase;
import teammates.ui.pagedata.InstructorCourseEnrollResultPageData;

public class InstructorCourseEnrollResultPageDataTest extends BaseTestCase {
    private static DataBundle dataBundle = getTypicalDataBundle();
    
    @BeforeClass
    public static void classSetUp() {
        printTestClassHeader();
    }
    
    @Test
    public void testAll() {
        ______TS("test typical case");
        AccountAttributes account = dataBundle.accounts.get("instructor1OfCourse1");
        String courseId = "idOfTypicalCourse1";
        
        @SuppressWarnings("unchecked")
        List<StudentAttributes>[] students = new ArrayList[StudentUpdateStatus.STATUS_COUNT];
        for (int i = 0; i < StudentUpdateStatus.STATUS_COUNT; i++) {
            students[i] = new ArrayList<StudentAttributes>();
        }
        students[StudentUpdateStatus.NEW.numericRepresentation].add(dataBundle.students.get("student1InCourse1"));
        students[StudentUpdateStatus.NEW.numericRepresentation].add(dataBundle.students.get("student2InCourse1"));
        students[StudentUpdateStatus.MODIFIED.numericRepresentation].add(dataBundle.students.get("student3InCourse1"));
        students[StudentUpdateStatus.UNMODIFIED.numericRepresentation].add(dataBundle.students.get("student4InCourse1"));
        students[StudentUpdateStatus.ERROR.numericRepresentation].add(dataBundle.students.get("student5InCourse1"));
        
        boolean hasSection = true;
        String enrollStudents = "enrollString";
        
        InstructorCourseEnrollResultPageData pageData = new InstructorCourseEnrollResultPageData(account,
                                                                courseId, students, hasSection, enrollStudents);
        
        assertNotNull(pageData.getCourseId());
        assertEquals(courseId, pageData.getCourseId());
        
        assertNotNull(pageData.account);
        assertEquals(account.googleId, pageData.account.googleId);
        
        assertNotNull(pageData.getEnrollResultPanelList());
        assertEquals(students.length, pageData.getEnrollResultPanelList().size());
        assertEquals(students[StudentUpdateStatus.NEW.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.NEW.numericRepresentation)
                             .getStudentList().size());
        assertEquals(students[StudentUpdateStatus.ERROR.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.ERROR.numericRepresentation)
                             .getStudentList().size());
        assertEquals(students[StudentUpdateStatus.MODIFIED.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.MODIFIED.numericRepresentation)
                             .getStudentList().size());
        assertEquals(students[StudentUpdateStatus.NOT_IN_ENROLL_LIST.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.NOT_IN_ENROLL_LIST.numericRepresentation)
                             .getStudentList().size());
        assertEquals(students[StudentUpdateStatus.UNKNOWN.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.UNKNOWN.numericRepresentation)
                             .getStudentList().size());
        assertEquals(students[StudentUpdateStatus.UNMODIFIED.numericRepresentation].size(),
                     pageData.getEnrollResultPanelList()
                             .get(StudentUpdateStatus.UNMODIFIED.numericRepresentation)
                             .getStudentList().size());
        
        assertTrue(pageData.isHasSection());
        assertEquals(enrollStudents, pageData.getEnrollStudents());
        
    }
}
