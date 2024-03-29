package main.java.tasks;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.REST;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.PhotosInterface;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.photos.Size;

import javafx.application.Platform;
import javafx.concurrent.Task;
import main.java.application.StringManipulator;
import main.java.application.WikiApplication;

public class GetImagesTask extends Task<Void>{

	private String _term;
	private File _imageFolder;
	private WikiApplication _mainApp;
	private ArrayList<String> _audioList;

	public GetImagesTask(String term, WikiApplication mainApp, ArrayList<String> audioList) {
		_term = term;
		_mainApp = mainApp;
		_audioList = audioList;
	}

	@Override
	protected Void call() throws Exception {
		String s = File.separator;
		_imageFolder = new File(System.getProperty("user.dir") + s + "bin" + s + "tempImages" + s + _term);
		_imageFolder.mkdirs();
		
		try {
			String apiKey = getAPIKey("apiKey");
			String sharedSecret = getAPIKey("sharedSecret");

			Flickr flickr = new Flickr(apiKey, sharedSecret, new REST());
			
			String query = _term;
			int resultsPerPage = 10;
			int page = 0;
			
	        PhotosInterface photos = flickr.getPhotosInterface();
	        SearchParameters params = new SearchParameters();
	        params.setSort(SearchParameters.RELEVANCE);
	        params.setMedia("photos"); 
	        params.setText(query);
	        
	        PhotoList<Photo> results = photos.search(params, resultsPerPage, page);
	        
	        for (Photo photo: results) {
	        	try {
	        		BufferedImage image = photos.getImage(photo,Size.LARGE);
	        		String filename = query.trim().replace(' ', '-')+"-"+System.currentTimeMillis()+"-"+photo.getId()+".jpg";
		        	File outputfile = new File(_imageFolder,filename);
		        	ImageIO.write(image, "jpg", outputfile);
	        	} catch (FlickrException fe) {
	        		System.err.println("Ignoring image " +photo.getId() +": "+ fe.getMessage());
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void cancelled() {
		
		// delete previously made folder
		if (_imageFolder != null && _imageFolder.exists() && _imageFolder.listFiles().length == 0) {
			_imageFolder.delete();
		}
		
		Platform.runLater(() -> {
			_mainApp.displayMainMenuScene();
		});
	}
	
	@Override
	public void succeeded() {
		Platform.runLater(() -> {
			_mainApp.displayCreateCreationChooseImagesScene(_term, _audioList);
		}); 
	}
	
	public String getAPIKey(String keyType) {
		String config = System.getProperty("user.dir") 
				+ System.getProperty("file.separator")+ "flickr-api-keys.txt"; 
		File keyFile = new File(config); 
		
		String key = new StringManipulator().readFromFile(keyFile, keyType);
		
		return key;
	}

}
