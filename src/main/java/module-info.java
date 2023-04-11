module com.collegegroup.processscheduling {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.collegegroup.processscheduling to javafx.fxml;
    exports com.collegegroup.processscheduling;
}