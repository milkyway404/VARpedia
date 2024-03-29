package main.java.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.tasks.ViewAudioTask;
import main.java.tasks.ViewCreationsTask;

/**
 * Controller for LoadingViewCreations.fxml
 * @author wcho400
 *
 */
public class LoadingViewCreationsController extends Controller{

	private ViewCreationsTask _creationTask;
	private ViewAudioTask _audioTask;
	
	/**
	 * button to return to the main menu
	 */
	@FXML
	private Button _mainMenuBtn;
	
	/**
	 * button to stop displaying creations. Returns to main menu
	 */
	@FXML
	private void mainMenu() {
		_creationTask.cancel();
		_audioTask.cancel();
		_mainApp.displayMainMenuScene();
	}
	
	/**
	 * Method to ensure correct tasks can be canceled
	 * @param creationTask
	 * @param audioTask
	 */
	public void setTask(ViewCreationsTask creationTask, ViewAudioTask audioTask) {
		_creationTask = creationTask;
		_audioTask = audioTask;
	}
}
