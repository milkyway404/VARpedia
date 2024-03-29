package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import main.java.tasks.GetImagesTask;

/**
 * Controller for LoadingScrapingImages.fxml
 * @author wcho400
 *
 */
public class LoadingScrapingImagesController extends Controller{
	
	private GetImagesTask _task;

	@FXML
	private Text _message;
	
	@FXML
	private Button _mainMenuBtn;

	/**
	 * returns to main menu if selected
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
	 * sets task to ensure correct tasks are stopped
	 * @param task
	 * @param term
	 */
	public void setTask(GetImagesTask task, String term) {
		_task = task;
		_message.setText("Getting images for " + term + ". Please wait...");
	}
}
