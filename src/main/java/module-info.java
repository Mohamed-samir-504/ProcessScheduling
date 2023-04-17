module com.collegegroup.processscheduling {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.collegegroup.processscheduling to javafx.fxml;
    exports com.collegegroup.processscheduling;
    exports com.collegegroup.processscheduling.GUIControllers;
    opens com.collegegroup.processscheduling.GUIControllers to javafx.fxml;


}