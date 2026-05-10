
package com.rendellvelasco.projectstaffingtool.controllers;

import com.rendellvelasco.projectstaffingtool.data.EmployeeRepository;
import com.rendellvelasco.projectstaffingtool.exceptions.OverAllocationException;
import com.rendellvelasco.projectstaffingtool.models.Assignment;
import com.rendellvelasco.projectstaffingtool.models.Employee;
import com.rendellvelasco.projectstaffingtool.models.Project;
import com.rendellvelasco.projectstaffingtool.services.ResourceService;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Resource Allocator dialog controller.
 *
 * ResourceService and EmployeeRepository are injected by Guice.
 * The target Project is a runtime value set via setTargetProject()
 * before FXMLLoader.load() is called.
 */
public class AllocatorController {

    private final ResourceService    resourceService;
    private final EmployeeRepository empRepo;

    // Set at runtime by DashboardController before load()
    private Project targetProject;

    @FXML private ComboBox<String>   skillComboBox;
    @FXML private ComboBox<Employee> employeeComboBox;
    @FXML private TextField          roleTextField;
    @FXML private TextField          newHoursTextField;
    @FXML private Label              projectedLoadLabel;
    @FXML private Button             assignButton;

    @FXML private TableView<Assignment>           workloadTable;
    @FXML private TableColumn<Assignment, String> workloadProjectColumn;
    @FXML private TableColumn<Assignment, String> workloadRoleColumn;
    @FXML private TableColumn<Assignment, Double> workloadHoursColumn;
    @FXML private TableColumn<Assignment, Double> workloadCostColumn;

    private final Map<Assignment, Double> originalHoursSnapshot = new HashMap<>();
    private boolean committed = false;

    @Inject
    public AllocatorController(ResourceService resourceService,
                               EmployeeRepository empRepo) {
        this.resourceService = resourceService;
        this.empRepo         = empRepo;
    }

    /** Called by DashboardController after Guice builds this controller. */
    public void setTargetProject(Project targetProject) {
        this.targetProject = targetProject;
    }

    @FXML
    public void initialize() {
        setupSkillFilter();
        setupEmployeeComboBox();
        setupWorkloadTable();
        newHoursTextField.textProperty().addListener((obs, old, val) -> recalcLoad());
        if (assignButton != null) assignButton.setDisable(true);
    }

    private void setupSkillFilter() {
        ObservableList<String> allSkills = FXCollections.observableArrayList("All");
        empRepo.findAll().forEach(e -> e.getSkills().forEach(skill -> {
            if (!allSkills.contains(skill)) allSkills.add(skill);
        }));
        skillComboBox.setItems(allSkills);
        skillComboBox.setValue("All");
        skillComboBox.valueProperty().addListener((obs, old, skill) -> {
            String filter = "All".equals(skill) ? null : skill;
            employeeComboBox.setItems(empRepo.filterBySkill(filter));
            employeeComboBox.setValue(null);
        });
    }

    private void setupEmployeeComboBox() {
        employeeComboBox.setItems(empRepo.findAll());
        employeeComboBox.valueProperty().addListener((obs, old, emp) -> {
            if (emp != null) loadWorkload(emp);
        });
    }

    private void setupWorkloadTable() {
        workloadProjectColumn.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        findProjectName(cell.getValue())));
        workloadRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        workloadHoursColumn.setCellValueFactory(new PropertyValueFactory<>("allocatedHours"));
        workloadHoursColumn.setCellFactory(col -> new TableCell<>() {
            private final TextField editor = new TextField();
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) { setGraphic(null); return; }
                editor.setText(String.format("%.1f", item));
                editor.setOnAction(e -> {
                    try {
                        double val = Double.parseDouble(editor.getText());
                        getTableView().getItems().get(getIndex()).setAllocatedHours(val);
                        recalcLoad();
                    } catch (NumberFormatException ignored) {}
                });
                setGraphic(editor);
            }
        });
        workloadHoursColumn.setEditable(true);
        workloadTable.setEditable(true);

        workloadCostColumn.setCellValueFactory(cell -> cell.getValue().getCost().asObject());
        workloadCostColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("$%.2f", item));
            }
        });
    }

    private String findProjectName(Assignment a) {
        if (targetProject != null && targetProject.getAssignments().contains(a))
            return targetProject.getTitle();
        return "Other Project";
    }

    private void loadWorkload(Employee e) {
        ObservableList<Assignment> workload = resourceService.getAssignmentsByEmployee(e);
        originalHoursSnapshot.clear();
        workload.forEach(a -> originalHoursSnapshot.put(a, a.getAllocatedHours()));
        workloadTable.setItems(workload);
        recalcLoad();
    }

    private void recalcLoad() {
        double existingHours = workloadTable.getItems().stream()
                .mapToDouble(Assignment::getAllocatedHours).sum();
        double newHours = 0;
        try {
            String text = newHoursTextField.getText();
            if (text != null && !text.isBlank()) newHours = Double.parseDouble(text);
        } catch (NumberFormatException ignored) {}

        double total = existingHours + newHours;
        projectedLoadLabel.setText(String.format("%.1f h / 40 h", total));
        boolean over = total > 40.0;
        projectedLoadLabel.setTextFill(over ? Color.RED : Color.GREEN);
        if (assignButton != null) assignButton.setDisable(over);
    }

    @FXML
    private void handleConfirm() {
        Employee emp      = employeeComboBox.getValue();
        String role       = roleTextField.getText();
        String hoursText  = newHoursTextField.getText();

        if (emp == null || role == null || role.isBlank()
                || hoursText == null || hoursText.isBlank()) {
            showAlert("Missing Fields", "Please fill in Employee, Role, and Hours.");
            return;
        }
        double hours;
        try {
            hours = Double.parseDouble(hoursText);
            if (hours <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            showAlert("Invalid Hours", "Please enter a positive number for hours.");
            return;
        }
        try {
            resourceService.assignTeamMember(targetProject, emp, role, hours);
            committed = true;
            closeDialog();
        } catch (OverAllocationException ex) {
            showAlert("Over-Allocation", ex.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        if (!committed) {
            originalHoursSnapshot.forEach(Assignment::setAllocatedHours);
        }
        closeDialog();
    }

    private void closeDialog() {
        Stage stage = (Stage) (assignButton != null
                ? assignButton.getScene().getWindow()
                : workloadTable.getScene().getWindow());
        stage.close();
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
