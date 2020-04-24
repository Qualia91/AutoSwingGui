package com.nick.wood.swing_gui;

import com.nick.wood.swing_gui.class_builder.ClassBuilder;
import com.nick.wood.swing_gui.class_builder.FieldObject;
import com.nick.wood.swing_gui.model.TestDataTwo;
import com.nick.wood.swing_gui.model.TestModel;
import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.view.GuiBuilder;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;
import com.nick.wood.swing_gui.view.panels.objects.*;
import com.nick.wood.swing_gui.view.frames.WindowContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, IOException, InvocationTargetException, ClassNotFoundException {


		//TestModel testModel = new TestModel("id", "TestValue", 1, 2.3, true);

		ClassBuilder classBuilder = new ClassBuilder("MyClass", "com.nick.wood.swing_gui.dynamic_classes");

		ArrayList<String> modifiers = new ArrayList<>();
		modifiers.add("private");

		FieldObject fieldObjectOne = new FieldObject(modifiers, "int");
		FieldObject fieldObjectTwo = new FieldObject(modifiers, "String");
		FieldObject fieldObjectThree = new FieldObject(modifiers, "double");

		classBuilder.addField("fieldOne", fieldObjectOne);
		classBuilder.addField("fieldTwo", fieldObjectTwo);
		classBuilder.addField("fieldThree", fieldObjectThree);

		Object testModel = classBuilder.buildClass();

		SwingUtilities.invokeLater(() -> {

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			BeanChanger beanChanger = new BeanChanger();

			ClickableImagePanel backButton = new ClickableImagePanel("/icons/icon.png");
			backButton.getLabel().addMouseListener( new MouseAdapter() {
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

			ArrayList<ClickableImagePanel> clickableImagePanels = new ArrayList<>();
			clickableImagePanels.add(backButton);
			clickableImagePanels.add(forwardButton);
			clickableImagePanels.add(minimiseButton);

			Toolbar toolbar = new Toolbar(clickableImagePanels);

			GuiBuilder guiBuilder = new GuiBuilder(testModel, beanChanger, toolbar);
			beanChanger.attachBeanChangerListener(() -> guiBuilder.beanActive(backButton, forwardButton));

			ButtonPanel buttonPanel2 = new ButtonPanel();

			SideBarWindow sideBarWindow = new SideBarWindow(0.3, guiBuilder.getFieldListPanel(), buttonPanel2,
					minimiseButton::attachEventListener,
					buttonPanel2::attachEventListener);

			EmptyWindow emptyWindow = new EmptyWindow(1000, 800, sideBarWindow);
			emptyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		});

		//while (true) {
//
		//	System.out.println("LOOP.......");
		//	testModel.getHashMap().forEach((integer, aDouble) -> {
		//		System.out.println("Entry");
		//		System.out.println(integer);
		//		System.out.println(aDouble);
		//	});
		//	System.out.println("");
		//	System.out.println("");
		//	try {
		//		Thread.sleep(1000);
		//	} catch (InterruptedException e) {
		//		e.printStackTrace();
		//	}
		//}

	}

	private void autoGui() {

	}

	private void tabPanel() {
		TabPanel tabPanel = new TabPanel();

		EmptyWindow emptyWindow = new EmptyWindow(800, 600, tabPanel);
	}

	private void sideBar() {
		ClickableImagePanel buttonPanel1 = new ClickableImagePanel("/icons/icon.png");
		ButtonPanel buttonPanel2 = new ButtonPanel();

		SideBarWindow sideBarWindow = new SideBarWindow(0.3, buttonPanel1, buttonPanel2, buttonPanel1::attachEventListener, buttonPanel2::attachEventListener);

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
