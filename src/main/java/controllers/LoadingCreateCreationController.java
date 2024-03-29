package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.tasks.CreateCreationTask;

/**
 * Controller for LoadingCreateCreationController.fxml
 * @author wcho400
 *
 */
public class LoadingCreateCreationController extends Controller{
	private CreateCreationTask _task;

	@FXML
	private Button _mainMenuBtn;

	/**
	 * returns to main menu when button is pressed
	 */
	@FXML
	private void mainMenu() {
		// cancel current task before going back to main menu
		if (_task != null) {
			_task.cancel();
		}

		_mainApp.displayMainMenuScene();
	}

	/**
	 * ensures correct task is canceled when button is pressed
	 * @param task
	 */
	public void setTask(CreateCreationTask task) {
		_task = task;
	}
}
