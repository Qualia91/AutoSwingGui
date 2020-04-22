package com.nick.wood.swing_gui;

import com.nick.wood.swing_gui.model.TestDataTwo;
import com.nick.wood.swing_gui.model.TestModel;
import com.nick.wood.swing_gui.view.GuiBuilder;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;
import com.nick.wood.swing_gui.view.panels.objects.*;
import com.nick.wood.swing_gui.view.frames.WindowContainer;

import javax.swing.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Main {

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			TestModel testModel = new TestModel("id", "TestValue", 1, 2.3, true);
			//TestDataTwo testModel = new TestDataTwo("id", "TestValue", 1, 2.3, true, false);

			GuiBuilder guiBuilder = new GuiBuilder(testModel);



		});

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
