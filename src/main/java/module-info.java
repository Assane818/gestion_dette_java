module com.asn {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.sql;
    requires org.yaml.snakeyaml;
    exports com.asn.data.entities;
    opens com.asn.data.entities to org.hibernate.orm.core;

    opens com.asn to javafx.fxml;
    opens com.asn.controller to javafx.fxml;

    exports com.asn;
}
