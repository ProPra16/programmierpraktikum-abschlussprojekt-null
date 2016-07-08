package gui.views.menu;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.Exercise;

public class MenuItem extends HBox {
	
	static final String iconHomePath = "/gui/images/icons/home.png";
	static final String iconPencilPath = "/gui/images/icons/pencil.png"; 
	
	final double height;
	final double iconSize;
	
	/**
	 * The main view node
	 */
	Node mainView;
	
	/**
	 * Image view for the icon
	 */
	ImageView iconView;
	
	/**
	 * Label instance  
	 */
	Label label;
	
	/**
	 * Status as selected menu item
	 */
	boolean selected;
	
	/**
	 * Constructs a menu item  
	 */
	public MenuItem() {
		height = 30;
		iconSize = height;
		
		iconView = new ImageView(new Image(iconHomePath));
		iconView.setFitHeight(iconSize);
		iconView.setFitWidth(iconSize);
		iconView.getStyleClass().add("menu-item-icon");
		
		label = new Label("Menu item");
		label.setPrefHeight(height);
		label.getStyleClass().add("menu-item-label");
		getStyleClass().add("menu-item");
		
		getChildren().clear();
		getChildren().addAll(iconView, label);
	}
	
	/**
	 * Sets the connected main view
	 * @return
	 */
	public Node getMainView() {
		return mainView;
	}

	/**
	 * Gets the main view for this menu item
	 * @param mainView
	 */
	public void setMainView(Node mainView) {
		this.mainView = mainView;
	}

	/**
	 * Sets the icon from a file path
	 * @param icon
	 */
	public void setIcon(String icon) {
		iconView.setImage(new Image(icon));
	}

	/**
	 * Sets the label from a string
	 * @param label
	 */
	public void setLabel(String label) {
		this.label.setText(label);
	}
	
	/**
	 * Gets the selected status
	 * @return 
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the selected status
	 * @param selected
	 */
	public void setSelected(boolean selected) {
		// First make sure if status has changed to add multiple times the same class or remove a non-existent
		if(this.selected != selected) {
			this.selected = selected;
			if(selected) 
				getStyleClass().add("active");
			else
				getStyleClass().remove("active");
		}
	}
	
	/**
	 * Select the menu item
	 */
	public void select() {
		setSelected(true);
	}
	
	/**
	 * Unselects the menu item
	 */
	public void unselect() {
		setSelected(false);
	}

}
