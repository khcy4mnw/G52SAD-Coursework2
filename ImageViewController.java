import java.awt.Button;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

import com.sun.corba.se.impl.orbutil.graph.Node;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Accordion;
import javafx.scene.control.Pagination;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class ImageViewController 
{
	// Private fields for components 
	@FXML
	private ImageView myImageView;

	@FXML
	private ToggleGroup myToggleGroup;

	@FXML
	private RadioButton dogRadioButton;

	@FXML
	private RadioButton catRadioButton;
	
	private Stage stage;
	
	
	File filesJpg[];
	Image images[] = new Image[50];
	ImageView imageViews[];
	BufferedImage bufferedImage[];
	TitledPane titledPanes[];
	
	private int x = 0;
	
	int height=200;
	int width=200;
	int i = 0;
	
	//function to open picture file
	public void openFile(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.*"),
				new FileChooser.ExtensionFilter("PNG", "*.png"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg")
				);
		
		File file = fileChooser.showOpenDialog(stage);
		
		if(file != null){
			System.out.println("Chosen files: "+file);
			//System.out.println(filesJpg.length);
		}
		
		String path = file.getAbsolutePath();
		
		try{
			InputStream inputStream = new FileInputStream(path);
			Image image = new Image(inputStream);
			myImageView.setImage(image);
		}catch(FileNotFoundException ex){
			System.out.println("file is missing");
		}
		
	}
	
	//function to open folder
	public void openFolder(){
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(null);
		
		if(selectedDirectory != null){
			FilenameFilter filterJpg = new FilenameFilter(){
				public boolean accept(File dir,String name){
					return name.toLowerCase().endsWith(".jpg");
				}
			};
			filesJpg = selectedDirectory.listFiles(filterJpg);
			System.out.println(filesJpg.length);
			
			for(int i=0;i<filesJpg.length;i++){
				String path = filesJpg[i].getAbsolutePath();
				try{
					InputStream inputStream = new FileInputStream(path);
					Image image = new Image(inputStream);
					images[i] = image;
					myImageView.setVisible(true);
					myImageView.setImage(images[i]);
				}catch(IOException ex){
					System.out.println("Images is not printed");
				}
			}
		}
	}

	//function to rotate the pictures
	public void rotateButton()
	{
		x += 90;
		myImageView.setRotate(x);
	}
	
	//function to next picture
	public void nextButton(){
		myImageView.setImage(images[i+1]);
		myImageView.setRotate(0);
		x = 0;
		i++;
		System.out.println(filesJpg.length);
	}
	
	//function to previous picture
	public void previousButton(){
		myImageView.setImage(images[i-1]);
		myImageView.setRotate(0);
		x = 0;
		i--;
	}
	
	//Zoom in function
	public void zoomIn() {
		height += 50;
		width += 50;
		
		myImageView.setFitWidth(width);
		myImageView.setFitHeight(height);
	}
	
	//Zoom out function
	public void zoomOut(){
		height -= 50;
		width -= 50;
		
		myImageView.setFitWidth(width);
		myImageView.setFitHeight(height);
	}
}
