package com.boc_dev.swing_utils;

import com.boc_dev.swing_utils.class_builder.ClassBuilder;
import com.boc_dev.swing_utils.class_builder.ConstructorObject;
import com.boc_dev.swing_utils.class_builder.FieldObject;
import com.boc_dev.swing_utils.utils.BeanChanger;
import com.boc_dev.swing_utils.view.GuiBuilder;
import com.boc_dev.swing_utils.view.frames.EmptyWindow;
import com.boc_dev.swing_utils.view.panels.objects.*;
import com.boc_dev.swing_utils.view.frames.WindowContainer;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Examples {

	public static void main(String[] args) throws Exception {
		autoGui();
	}

	private static void boxesAndLines() {

		SwingUtilities.invokeLater(() -> {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
				System.setProperty("sun.awt.noerasebackground", "true");


			} catch (Exception e) {
				e.printStackTrace();
			}


			DragContextPanel dragContextPanel = new DragContextPanel();
			EmptyWindow emptyWindow = new EmptyWindow(1000, 1000, dragContextPanel, new JMenuBar());
			emptyWindow.setGlassPane(dragContextPanel.getCustomGlassPane());
			emptyWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		});

	}

	private static void autoGui() throws Exception {

		SwingUtilities.invokeLater(() -> {

			String main = "hello";

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			BeanChanger beanChanger = new BeanChanger();

			Toolbar toolbar = new Toolbar("Toolbar");

			GuiBuilder guiBuilder = new GuiBuilder(main, beanChanger, toolbar);

			EmptyWindow emptyWindow = new EmptyWindow(1000, 800, guiBuilder.getFieldListPanel(), new JMenuBar());
			emptyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		});
	}

	private static void autoGuiOld() throws Exception {
		ClassBuilder classBuilder = new ClassBuilder("MyClass", "com.nick.wood.swing_gui.dynamic_classes");

		ArrayList<String> modifiers = new ArrayList<>();
		modifiers.add("private");

		classBuilder.addImport("java.util.ArrayList");
		classBuilder.addImport("java.util.HashMap");

		FieldObject fieldObjectOne = new FieldObject(modifiers, "int", 1);
		FieldObject fieldObjectTwo = new FieldObject(modifiers, "String", "Hello");
		FieldObject fieldObjectThree = new FieldObject(modifiers, "double", 2.0);
		FieldObject fieldObjectFour = new FieldObject(modifiers, "ArrayList<String>", new ArrayList<>());
		FieldObject fieldObjectFive = new FieldObject(modifiers, "HashMap<String, Double>", new HashMap<>());

		classBuilder.addField("fieldOne", fieldObjectOne);
		classBuilder.addField("fieldTwo", fieldObjectTwo);
		classBuilder.addField("fieldThree", fieldObjectThree);
		classBuilder.addField("fieldFour", fieldObjectFour);
		classBuilder.addField("fieldFive", fieldObjectFive);

		ArrayList<String> inputTypes = new ArrayList<>();
		inputTypes.add("int");
		inputTypes.add("double");
		ArrayList<String> inputNames = new ArrayList<>();
		inputNames.add("fieldOne");
		inputNames.add("fieldThree");
		ConstructorObject constructorObject = new ConstructorObject(inputTypes, inputNames);
		classBuilder.addConstructor(constructorObject);

		Class<?> testModelClass = classBuilder.buildClass();

		Object testModel = testModelClass.getConstructor(int.class, double.class).newInstance(12342, 24.536);

		SwingUtilities.invokeLater(() -> {

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			BeanChanger beanChanger = new BeanChanger();

			ClickableImagePanel backButton = new ClickableImagePanel("/icons/icon.png");
			backButton.getLabel().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					beanChanger.undo();
				}
			});

			ClickableImagePanel forwardButton = new ClickableImagePanel("/icons/icon.png");
			forwardButton.getLabel().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					beanChanger.redo();
				}
			});

			backButton.setEnabled(false);
			forwardButton.setEnabled(false);

			ClickableImagePanel minimiseButton = new ClickableImagePanel("/icons/icon.png");

			ArrayList<JPanel> clickableImagePanels = new ArrayList<>();
			clickableImagePanels.add(backButton);
			clickableImagePanels.add(forwardButton);
			clickableImagePanels.add(minimiseButton);

			Toolbar toolbar = new Toolbar(clickableImagePanels);

			GuiBuilder guiBuilder = new GuiBuilder(testModel, beanChanger, toolbar);
			beanChanger.attachBeanChangerListener(() -> guiBuilder.beanActive(backButton, forwardButton));

			ButtonPanel buttonPanel2 = new ButtonPanel();

			SideBarWindow sideBarWindow = new SideBarWindow(0.3, guiBuilder.getFieldListPanel(), buttonPanel2);

			EmptyWindow emptyWindow = new EmptyWindow(1000, 800, guiBuilder.getFieldListPanel(), new JMenuBar());
			emptyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		});
	}

	private void tabPanel() {
		TabPanel tabPanel = new TabPanel();

		EmptyWindow emptyWindow = new EmptyWindow(800, 600, tabPanel, new JMenuBar());
	}

	private void sideBar() {
		ClickableImagePanel buttonPanel1 = new ClickableImagePanel("/icons/icon.png");
		ButtonPanel buttonPanel2 = new ButtonPanel();

		SideBarWindow sideBarWindow = new SideBarWindow(0.3, buttonPanel1, buttonPanel2);

	}

	private void switchable() {
		ArrayList<JPanel> windowPanels = new ArrayList<>();

		//ArrayList<JPanel> switchPanelsOne = new ArrayList<>();
		//ArrayList<Consumer<MouseListener>> switchConsumersOne = new ArrayList<>();
		//ButtonPanel buttonPanelOne = new ButtonPanel();
		//ClickableImagePanel clickableImagePanelOne = new ClickableImagePanel();
		//switchPanelsOne.add(buttonPanelOne);
		//switchPanelsOne.add(clickableImagePanelOne);
		//switchConsumersOne.add(buttonPanelOne::attachEventListener);
		//switchConsumersOne.add(clickableImagePanelOne::attachEventListener);

		ArrayList<JPanel> switchPanelsTwo = new ArrayList<>();
		ArrayList<Consumer<MouseListener>> switchConsumersTwo = new ArrayList<>();
		ButtonPanel buttonPanelTwo = new ButtonPanel();
		ClickableImagePanel clickableImagePanelTwo = new ClickableImagePanel("/icons/icon.png");
		switchPanelsTwo.add(buttonPanelTwo);
		switchPanelsTwo.add(clickableImagePanelTwo);
		switchConsumersTwo.add(buttonPanelTwo::attachEventListener);
		switchConsumersTwo.add(clickableImagePanelTwo::attachEventListener);

		//windowPanels.add(new SwitchablePanel(switchPanelsOne, switchConsumersOne, new ArrayList<>()));
		windowPanels.add(new SwitchablePanel(switchPanelsTwo, switchConsumersTwo, new ArrayList<>()));

		WindowContainer windowContainer = new WindowContainer("Title", 800, 600, windowPanels);
	}


}
