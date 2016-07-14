package gui.views.cycle;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class InformationItem extends TitledPane {

	/**
	 * Constructs an information item with title and (optional) children
	 * 
	 * @param title
	 * @param children
	 */
	public InformationItem(String title, String content) {	
		getStyleClass().add("information-item");
		setText(title);
		
		TextFlow textFlow = new TextFlow(new Text(content));;
		AnchorPane contentPane = new AnchorPane(textFlow);
		contentPane.getStyleClass().add("information-item-content-wrap");

		setContent(contentPane);
		
		setExpanded(false);
	}
	
}
