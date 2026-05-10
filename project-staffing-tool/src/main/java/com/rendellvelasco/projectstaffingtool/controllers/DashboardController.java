
package com.rendellvelasco.projectstaffingtool.controllers;

import com.rendellvelasco.projectstaffingtool.App;
import com.rendellvelasco.projectstaffingtool.data.EmployeeRepository;
import com.rendellvelasco.projectstaffingtool.data.ProjectRepository;
import com.rendellvelasco.projectstaffingtool.models.*;
import com.rendellvelasco.projectstaffingtool.models.Assignment;
import com.rendellvelasco.projectstaffingtool.models.FixedPriceProject;
import com.rendellvelasco.projectstaffingtool.models.Project;
import com.rendellvelasco.projectstaffingtool.services.ResourceService;
import com.google.inject.Inject;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Portfolio Dashboard controller.
 * All three dependencies are injected by Guice.
 */
public class DashboardController {

    private final ProjectRepository  projRepo;
    private final ResourceService    resourceService;
    private final EmployeeRepository empRepo;

    @FXML private TextField          searchField;
    @FXML private ChoiceBox<String>  statusChoiceBox;
    @FXML private ListView<Project>  projectListView;

    @FXML private Label              projectTitleLabel;
    @FXML private Label              projectTypeLabel;
    @FXML private Label              totalCostLabel;

    @FXML private TableView<Assignment>           assignmentsTable;
    @FXML private TableColumn<Assignment, String> nameColumn;
    @FXML private TableColumn<Assignment, String> roleColumn;
    @FXML private TableColumn<Assignment, Double> hoursColumn;
    @FXML private TableColumn<Assignment, Double> costColumn;

    @FXML private Button addMemberButton;

    private FilteredList<Project> filteredProjects;

    @Inject
    public DashboardController(ProjectRepository projRepo,
                               ResourceService resourceService,
                               EmployeeRepository empRepo) {
        this.projRepo        = projRepo;
        this.resourceService = resourceService;
        this.empRepo         = empRepo;
    }

    @FXML
    public void initialize() {
        setupFilters();
        setupListView();
        setupTable();
        addMemberButton.setOnAction(e -> openAllocator());
    }

    private void setupFilters() {
        filteredProjects = new FilteredList<>(projRepo.findAll(), p -> true);
        statusChoiceBox.setItems(FXCollections.observableArrayList("All", "PLANNING", "ACTIVE", "CLOSED"));
        statusChoiceBox.setValue("All");
        searchField.textProperty().addListener((obs, old, val) -> applyFilter());
        statusChoiceBox.valueProperty().addListener((obs, old, val) -> applyFilter());
    }

    private void applyFilter() {
        String search = searchField.getText() == null ? "" : searchField.getText().toLowerCase();
        String status = statusChoiceBox.getValue();
        filteredProjects.setPredicate(p -> {
            boolean titleMatch  = p.getTitle().toLowerCase().contains(search);
            boolean statusMatch = "All".equals(status) || p.getStatus().name().equalsIgnoreCase(status);
            return titleMatch && statusMatch;
        });
    }

    private void setupListView() {
        projectListView.setItems(filteredProjects);
        projectListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, selected) -> {
                    if (selected != null) populateDetail(selected);
                });
    }

    private void populateDetail(Project project) {
        projectTitleLabel.setText(project.getTitle());
        projectTypeLabel.setText(project instanceof FixedPriceProject ? "Fixed Price" : "Time & Material");
        bindTotalCost(project);
        assignmentsTable.setItems(project.getAssignments());
        addMemberButton.setUserData(project);
    }

    private void bindTotalCost(Project project) {
        DoubleBinding totalBinding = Bindings.createDoubleBinding(
                () -> project.getAssignments().stream()
                        .mapToDouble(a -> a.getCost().get()).sum(),
                project.getAssignments());
        totalCostLabel.textProperty().bind(Bindings.format("$%.2f", totalBinding));
        totalCostLabel.setTextFill(Color.GREEN);
    }

    private void setupTable() {
        nameColumn.setCellValueFactory(cell -> cell.getValue().getEmployee().nameProperty());
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        hoursColumn.setCellValueFactory(new PropertyValueFactory<>("allocatedHours"));

        costColumn.setCellValueFactory(cell -> cell.getValue().getCost().asObject());
        costColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("$%.2f", item));
            }
        });
    }

    private void openAllocator() {
        Project selected = (Project) addMemberButton.getUserData();
        if (selected == null) {
            showAlert("No Project Selected", "Please select a project first.");
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/rendellvelasco/projectstaffingtool/resource-allocator.fxml"));

            // AllocatorController needs the selected Project at runtime,
            // so we use an assisted factory pattern: get from Guice then set the project
            AllocatorController allocCtrl = App.getInjector().getInstance(AllocatorController.class);
            allocCtrl.setTargetProject(selected);
            loader.setController(allocCtrl);

            Parent root = loader.load();
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Resource Allocator – " + selected.getTitle());
            dialog.setScene(new Scene(root));
            dialog.showAndWait();

            assignmentsTable.refresh();
        } catch (Exception ex) {
            ex.printStackTrace();
            showAlert("Error", "Could not open Resource Allocator.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING, msg, ButtonType.OK);
        alert.setHeaderText(title);
        alert.showAndWait();
    }
}
