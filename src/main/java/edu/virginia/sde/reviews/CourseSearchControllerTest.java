//package edu.virginia.sde.reviews;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//
//
//public class CourseSearchControllerTest {
//    @Test
//    public void addCourseTest() throws SQLException, IOException {
//        CourseSearchController controller = new CourseSearchController();
//        controller.setDriver(new DatabaseDriver("test.sqlite"));
//
//        controller.handleSearch();
//        int initialSize = controller.courseListView.getItems().size();
//
//        controller.newCourseSubject.setText("CS");
//        controller.newCourseNumber.setText("3421");
//        controller.newCourseTitle.setText("Test Course");
//
//        controller.addCourse();
//        controller.handleSearch();
//
//        assertEquals(initialSize+1, controller.courseListView.getItems().size());
//    }
//
//}
