package gui.views.menu;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class RemovableMenuItem extends MenuItem {
	
	static final String iconClosePath = "/gui/images/icons/close.png";
	
	/**
	 * The remove icon view
	 */
	ImageView removeIconView;
	
	/**
	 * The remove action handler
	 */
	EventHandler<MouseEvent> removeActionHandler;
	
	/**
	 * Creates a menu item with an remove
	 */
	public RemovableMenuItem() {
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		removeIconView = new ImageView(new Image(iconClosePath));
		removeIconView.setFitWidth(iconSize / 2.0);
		removeIconView.setFitHeight(iconSize / 2.0);
		HBox.setMargin(removeIconView, new Insets((height - iconSize / 2) / 2, 0, 0, 0)); // Vertical align delete button
		removeIconView.getStyleClass().add("menu-item-remove");
		
		getChildren().addAll(spacer, removeIconView);
	}
	
	/**
	 * Sets the icon of the remove button
	 */
	public void setRemoveIcon(String iconPath) {
		removeIconView.setImage(new Image(iconPath));
	}
	
	/**
	 * Sets the remove click handler 
	 * @param eventHandler
	 */
	public void setRemoveHandler(EventHandler<MouseEvent> eventHandler) {
		if(this.removeActionHandler != null)
			removeIconView.removeEventHandler(MouseEvent.MOUSE_RELEASED, this.removeActionHandler);
		
		this.removeActionHandler = eventHandler;
		removeIconView.addEventHandler(MouseEvent.MOUSE_RELEASED, eventHandler);
	}
	
}
