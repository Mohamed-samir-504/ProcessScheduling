module com.collegegroup.processscheduling {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.collegegroup.processscheduling to javafx.fxml;
    exports com.collegegroup.processscheduling;
    exports com.collegegroup.processscheduling.Controllers;
    opens com.collegegroup.processscheduling.Controllers to javafx.fxml;
    exports com.collegegroup.processscheduling.Processes;
    opens com.collegegroup.processscheduling.Processes to javafx.fxml;

}