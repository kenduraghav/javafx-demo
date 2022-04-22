package project.mini.demo.app.components;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegistrationMainWindow extends Application {

	private static final AtomicLong autoGenerateId = new AtomicLong(100);

	StudentDAO dao = new StudentDAO();

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GridPane root = new GridPane();
		root.setPadding(new Insets(15, 15, 15, 15));
		root.setAlignment(Pos.TOP_LEFT);
		
		Button registerButton = new Button();
		registerButton.setText("Register Here");
		registerButton.setAlignment(Pos.CENTER_LEFT);
		registerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				showRegisterScreen();
			}
		});
		
		
		Button viewButton = new Button();
		viewButton.setText("View Registered Students");
		viewButton.setAlignment(Pos.CENTER_RIGHT);
		viewButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				TableView<StudentDetail> tableView = showListScreen();
				root.add(tableView, 0, 3, 10,10);
			}

		});

		
		root.add(registerButton, 0, 0);
		root.add(viewButton, 1, 0);

		primaryStage.setTitle("College Registration System");

		Scene scene = new Scene(root, 800, 500);
		primaryStage.setScene(scene);

		primaryStage.setOnShowing((event) -> {
			DBListener.getInstance().createTable();
		});
		primaryStage.show();
	}

	private void showRegisterScreen() {
		GridPane pane = new GridPane();

		pane.setPadding(new Insets(15, 15, 15, 15));
		pane.setAlignment(Pos.TOP_LEFT);

		Label lblName = new Label("Name");
		TextField nameTxtField = new TextField();

		Label collegeNameLbl = new Label("College Name");
		TextField collegeTextField = new TextField();

		Label departmentLbl = new Label("Department");
		TextField departmentField = new TextField();

		Label yearOfStudyLabel = new Label("Year Of Study");
		TextField yearTextField = new TextField();

		Label contactNumber = new Label("Contact Number");
		TextField contactField = new TextField();

		Button submitBtn = new Button();
		submitBtn.setText("Submit");
		submitBtn.setAlignment(Pos.BASELINE_CENTER);

		pane.setVgap(5);
		pane.setHgap(5);

		pane.add(lblName, 0, 0);
		pane.add(nameTxtField, 1, 0);
		pane.add(collegeNameLbl, 0, 1);
		pane.add(collegeTextField, 1, 1);
		pane.add(departmentLbl, 0, 2);
		pane.add(departmentField, 1, 2);
		pane.add(yearOfStudyLabel, 0, 3);
		pane.add(yearTextField, 1, 3);
		pane.add(contactNumber, 0, 4);
		pane.add(contactField, 1, 4);

		pane.add(submitBtn, 0, 5);

		Stage registerStage = new Stage();

		Scene scene = new Scene(pane, 400, 300);
		registerStage.setTitle("Register Student Details");
		registerStage.setScene(scene);
		registerStage.show();

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				String name = nameTxtField.getText();
				String collegeName = collegeTextField.getText();
				String department = departmentField.getText();
				String year = yearTextField.getText();
				String contactNumber = contactField.getText();

				long registrationNo = Long.parseLong(
						LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + autoGenerateId.getAndIncrement());

				StudentDetail student = new StudentDetail();
				student.setStudentName(name);
				student.setCollegeName(collegeName);
				student.setDepartment(department);
				student.setRegistrationNo(registrationNo);
				student.setContactNumber(Long.parseLong(contactNumber));
				student.setYear(Long.parseLong(year));
				try {
					int rowUpdated = dao.insertStudentDetails(student);
					if (rowUpdated > 0) {
						String message = String.format(
								"Student Details Registered successfully. Please find the registration no %d",
								registrationNo);
						showAlert(Alert.AlertType.INFORMATION, "Registration Successful", message);
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(
							"RegistrationMainWindow.showRegisterScreen().new EventHandler() {...}.SubmitRegistration()"
									+ e.getMessage());
					showAlert(Alert.AlertType.ERROR, "ERROR", "Unable to register student details");
				}
				registerStage.close();
			}
		});
	}
	
	private TableView<StudentDetail> showListScreen() {
		TableView<StudentDetail> tableView = new TableView<>();
		try {
			ObservableList<StudentDetail> students = dao.displayStudents();
			if(students != null && !students.isEmpty()) {
				tableView.setPlaceholder(new Label("No records found"));	
			}
			TableColumn<StudentDetail, Long> idColumn = new TableColumn<>("Id");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			
			TableColumn<StudentDetail, String> nameColumn = new TableColumn<>("Student Name");
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
			
			TableColumn<StudentDetail, String> collegeNameColumn = new TableColumn<>("College Name");
			collegeNameColumn.setCellValueFactory(new PropertyValueFactory<>("collegeName"));
			
			TableColumn<StudentDetail, String> departmentColumn = new TableColumn<>("Department");
			departmentColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
			
			TableColumn<StudentDetail, Long> contactNoColumn = new TableColumn<>("Contact No");
			contactNoColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));
			
			TableColumn<StudentDetail, Long> registrationNoColumn = new TableColumn<>("Registration No");
			registrationNoColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNo"));
			
			TableColumn<StudentDetail, Long> yearColumn = new TableColumn<>("Year");
			yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
			
			tableView.getColumns().addAll(idColumn, nameColumn, collegeNameColumn, departmentColumn, contactNoColumn,
					registrationNoColumn, yearColumn);
			
			tableView.setItems(students);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("RegistrationMainWindow.showListScreen()" + e.getMessage());
			showAlert(Alert.AlertType.ERROR, "ERROR", "Unable to retrieve student details");
		}
		return tableView;
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.show();
	}

}
