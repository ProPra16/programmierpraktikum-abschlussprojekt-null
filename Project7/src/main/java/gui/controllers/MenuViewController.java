package gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.controllers.cycle.RedViewController;
import gui.views.menu.ExerciseMenuItem;
import gui.views.menu.ImportMenuItem;
import gui.views.menu.MenuItem;
import gui.views.menu.OverviewMenuItem;
import gui.views.menu.StatisticsMenuItem;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import models.Exercise;
import xmlModelParser.ModelStorageController;

public class MenuViewController implements Initializable {

	@FXML
	AnchorPane menuSection;
	@FXML
	VBox menu;

	Pane mainSection;
	Node exerciseView;
	List<MenuItem> menuItems;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		menuItems = new ArrayList<MenuItem>();		
		
		createDefaultMenuItems();
		
		menu.getChildren().clear();
		menu.getChildren().addAll(menuItems);
	}

	/**
	 * Sets the main section
	 * 
	 * @param mainSection
	 */
	public void setMainSection(Pane mainSection) {
		this.mainSection = mainSection;
	}

	/**
	 * Sets the exercise view
	 * 
	 * @param exerciseView
	 */
	public void setExerciseView(Node exerciseView) {
		this.exerciseView = exerciseView;
		for (MenuItem menuItem : menuItems) {
			if (menuItem instanceof OverviewMenuItem)
				menuItem.setMainView(exerciseView);
		}
	}

	/**
	 * Selects the exercise overview menu item
	 */
	public void selectExerciseOverview() {
		boolean foundMenuItem = false;
		for (MenuItem menuItem : menuItems) {
			if (menuItem instanceof OverviewMenuItem) {
				foundMenuItem = true;
				selectMenuItem(menuItem);
				break; // Usually only one overview menu item
			}
		}
		if(!foundMenuItem) {
			createExerciseOverviewMenuItem();
			selectExerciseOverview();
		}
	}

	/**
	 * Selects the menu item of the given {@link Exercise}
	 * 
	 * @param exercise
	 */
	public void selectExercise(Exercise exercise) {
		int exerciseIndexInMenu = getMenuIndex(exercise);

		if (exerciseIndexInMenu == -1) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/CycleView.fxml"));
				RedViewController testController = new RedViewController();
				loader.setController(testController);
				Parent testView = loader.load();
				AnchorPane.setTopAnchor(testView, 0.0);
				AnchorPane.setLeftAnchor(testView, 0.0);
				AnchorPane.setRightAnchor(testView, 0.0);
				AnchorPane.setBottomAnchor(testView, 0.0);
				testController.setExercise(exercise);
				testController.setMainSection(mainSection);;

				ExerciseMenuItem exerciseMenuItem = new ExerciseMenuItem(exercise);
				exerciseMenuItem.setMainView(testView);
				EventHandler<MouseEvent> menuItemClickEventHandler = new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						selectMenuItem(exerciseMenuItem);
					}
				};

				exerciseMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, menuItemClickEventHandler);
				exerciseMenuItem.setRemoveHandler(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						int menuIndex = getMenuIndex(exercise);
						if (menuIndex != -1) {
							MenuItem menuItem = menuItems.get(menuIndex);
							// Remove click event handler, otherwise exercise
							// will selected and created immediately after
							// removing
							menuItem.removeEventHandler(MouseEvent.MOUSE_RELEASED, menuItemClickEventHandler);
							removeMenuItem(menuItem);
							// Select menu item before this one if one exists,
							// otherwise select exercise overview
							if (menuIndex > 0)
								selectMenuItem(menuItems.get(menuIndex - 1));
							else
								selectExerciseOverview();
						}
					}
				});
				unselectAllMenuItems();
				selectMenuItem(exerciseMenuItem);
				menuItems.add(exerciseMenuItem);
				menu.getChildren().add(exerciseMenuItem);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		} else {
			selectMenuItem(menuItems.get(exerciseIndexInMenu));
		}
	}
	
	/**
	 * Creates an exercise overview menu item
	 */
	public void createExerciseOverviewMenuItem() {
		// Create overview menu item
		MenuItem overviewMenuItem = new OverviewMenuItem();
		
		// Load ExercisesView and connect it to the menu item
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/ExercisesView.fxml"));
			Parent exerciseView = loader.load();
			MainViewController.setAllAnchorsNull(exerciseView);
			ExercisesViewController exercisesController = loader.getController();
			exercisesController.setMenuController(this);
			overviewMenuItem.setMainView(exerciseView);
		} catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Select menu item on click
		overviewMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				selectMenuItem(overviewMenuItem);
			}
		});
		
		// Preselect menu item
		overviewMenuItem.select();
		
		menuItems.add(overviewMenuItem);
		menu.getChildren().clear();
		menu.getChildren().addAll(menuItems);
	}

	/**
	 * Removes all active style classes from menu items
	 */
	private void unselectAllMenuItems() {
		for (MenuItem menuItem : menuItems)
			menuItem.unselect();
	}

	/**
	 * Selects a menu item and shows it in main view
	 * 
	 * @param menuItem
	 */
	private void selectMenuItem(MenuItem menuItem) {
		unselectAllMenuItems();
		menuItem.select();
		mainSection.getChildren().clear();
		mainSection.getChildren().add(menuItem.getMainView());
	}

	/**
	 * Checks if exercise is already in menu
	 * 
	 * @param exercise
	 * @return -1 if not found, otherwise the index in menu items list
	 */
	private int getMenuIndex(Exercise exercise) {
		for (int i = 0; i < menuItems.size(); i++) {
			MenuItem menuItem = menuItems.get(i);
			if (menuItem instanceof ExerciseMenuItem && ((ExerciseMenuItem) menuItem).getExercise() == exercise)
				return i;
		}

		return -1;
	}

	/**
	 * Removes an exercise from menu
	 * 
	 * @param exercise
	 */
	private void removeMenuItem(MenuItem menuItem) {
		if (!menuItems.contains(menuItem))
			return;

		menuItems.remove(menuItem);
		menu.getChildren().remove(menuItem);
	}
	
	/**
	 * Creates the default menu items and adds them to menuItems
	 */
	private void createDefaultMenuItems() {
		// Create import menu item
				MenuItem importMenuItem = new ImportMenuItem();
				
				// Load ImportView and add connect it to the menu item
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/ImportView.fxml"));
					Parent importView = loader.load();
					MainViewController.setAllAnchorsNull(importView);
					ImportViewController importController = loader.getController();
					importController.setMenuController(this);
					importMenuItem.setMainView(importView);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Select menu item on click
				importMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						selectMenuItem(importMenuItem);
					}
				});
				menuItems.add(importMenuItem);

				
				if(ModelStorageController.getInstance().getCatalog().getExercises().size() != 0) {
					// If exercises are loaded show exercises overview - autoselected while creating
					createExerciseOverviewMenuItem();
				} else {
					// Preselect menu item - loaded in main view controller
					importMenuItem.select();
				}
				
				// Create statistics menu item 
				MenuItem statisticsMenuItem = new StatisticsMenuItem();
				// Load StatisticsView and add connect it to the menu item
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/views/StatisticsView.fxml"));
					Parent statisticsView = loader.load();
					MainViewController.setAllAnchorsNull(statisticsView);
					statisticsMenuItem.setMainView(statisticsView);
					StatisticsViewController statisticsController = loader.getController();
					
					// Select menu item on click and update statistics
					statisticsMenuItem.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent event) {
							statisticsController.updateStatistics();
							selectMenuItem(statisticsMenuItem);
						}
					});
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				menuItems.add(statisticsMenuItem);
	}
}
