package teammates.test.cases.pagedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import teammates.common.datatransfer.AccountAttributes;
import teammates.common.datatransfer.CourseAttributes;
import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.InstructorAttributes;
import teammates.test.cases.BaseTestCase;
import teammates.ui.pagedata.InstructorCoursesPageData;

public class InstructorCoursesPageDataTest extends BaseTestCase {
    private static DataBundle dataBundle = getTypicalDataBundle();
    
    @BeforeClass
    public static void classSetUp() {
        printTestClassHeader();
    }
    
    @Test
    public void testAll() {
        ______TS("test no course");
        AccountAttributes instructorAccountWithourCourses = dataBundle.accounts.get("instructorWithoutCourses");
        InstructorCoursesPageData pageData = new InstructorCoursesPageData(instructorAccountWithourCourses);
        List<CourseAttributes> activeCourses = new ArrayList<CourseAttributes>();
        List<CourseAttributes> archivedCourses = new ArrayList<CourseAttributes>();
        Map<String, InstructorAttributes> instructorForCourses = new HashMap<String, InstructorAttributes>();
        pageData.init(activeCourses, archivedCourses, instructorForCourses);
        
        assertNotNull(pageData.getActiveCourses());
        assertNotNull(pageData.getActiveCourses().getRows());
        assertEquals(0, pageData.getActiveCourses().getRows().size());
        
        assertNotNull(pageData.getArchivedCourses());
        assertNotNull(pageData.getArchivedCourses().getRows());
        assertEquals(0, pageData.getArchivedCourses().getRows().size());
        
        assertEquals("", pageData.getCourseIdToShow());
        assertEquals("", pageData.getCourseNameToShow());
        
        ______TS("test 1 active course");
        AccountAttributes instructorAccountWithOneActiveCourse = dataBundle.accounts.get("instructor1OfCourse1");
        pageData = new InstructorCoursesPageData(instructorAccountWithOneActiveCourse);
        activeCourses = new ArrayList<CourseAttributes>();
        activeCourses.add(dataBundle.courses.get("typicalCourse1"));
        
        archivedCourses = new ArrayList<CourseAttributes>();
        instructorForCourses = new HashMap<String, InstructorAttributes>();
        instructorForCourses.put("idOfTypicalCourse1", dataBundle.instructors.get("instructor1OfCourse1"));
        pageData.init(activeCourses, archivedCourses, instructorForCourses);
        
        assertNotNull(pageData.getActiveCourses());
        assertNotNull(pageData.getActiveCourses().getRows());
        assertEquals(1, pageData.getActiveCourses().getRows().size());
        
        assertNotNull(pageData.getArchivedCourses());
        assertNotNull(pageData.getArchivedCourses().getRows());
        assertEquals(0, pageData.getArchivedCourses().getRows().size());
        
        assertEquals("", pageData.getCourseIdToShow());
        assertEquals("", pageData.getCourseNameToShow());
        
        ______TS("test 2 active courses");
        AccountAttributes instructorAccountWithTwoActiveCourses = dataBundle.accounts.get("instructor3");
        pageData = new InstructorCoursesPageData(instructorAccountWithTwoActiveCourses);
        activeCourses = new ArrayList<CourseAttributes>();
        activeCourses.add(dataBundle.courses.get("typicalCourse1"));
        activeCourses.add(dataBundle.courses.get("typicalCourse2"));
        
        archivedCourses = new ArrayList<CourseAttributes>();
        instructorForCourses = new HashMap<String, InstructorAttributes>();
        instructorForCourses.put("idOfTypicalCourse1", dataBundle.instructors.get("instructor3OfCourse1"));
        instructorForCourses.put("idOfTypicalCourse2", dataBundle.instructors.get("instructor3OfCourse2"));
        pageData.init(activeCourses, archivedCourses, instructorForCourses, "Id to show", "Name to show");
        
        assertNotNull(pageData.getActiveCourses());
        assertNotNull(pageData.getActiveCourses().getRows());
        assertEquals(2, pageData.getActiveCourses().getRows().size());
        
        assertNotNull(pageData.getArchivedCourses());
        assertNotNull(pageData.getArchivedCourses().getRows());
        assertEquals(0, pageData.getArchivedCourses().getRows().size());
        
        assertEquals("Id to show", pageData.getCourseIdToShow());
        assertEquals("Name to show", pageData.getCourseNameToShow());
        
        ______TS("test 1 archived course");
        AccountAttributes instructorAccountWithOneArchivedCourse = dataBundle.accounts.get("instructorOfArchivedCourse");
        pageData = new InstructorCoursesPageData(instructorAccountWithOneArchivedCourse);
        activeCourses = new ArrayList<CourseAttributes>();
        
        archivedCourses = new ArrayList<CourseAttributes>();
        archivedCourses.add(dataBundle.courses.get("archivedCourse"));
        
        instructorForCourses = new HashMap<String, InstructorAttributes>();
        instructorForCourses.put("idOfArchivedCourse", dataBundle.instructors.get("instructorOfArchivedCourse"));
        
        pageData.init(activeCourses, archivedCourses, instructorForCourses);
        
        assertNotNull(pageData.getActiveCourses());
        assertNotNull(pageData.getActiveCourses().getRows());
        assertEquals(0, pageData.getActiveCourses().getRows().size());
        
        assertNotNull(pageData.getArchivedCourses());
        assertNotNull(pageData.getArchivedCourses().getRows());
        assertEquals(1, pageData.getArchivedCourses().getRows().size());
        
        assertEquals("", pageData.getCourseIdToShow());
        assertEquals("", pageData.getCourseNameToShow());
        
    }
}
