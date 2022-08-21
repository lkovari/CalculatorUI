/**
 * Copyright (c) 2021 by László Kövári
 * laszlo.kovary@gmail.com
 * Projet: CalculatorUI
 * Package: com.lkovari.desktop.app.calculator.calculatorui
 * File: CalculatorUI.java
 * Created: Dec 23, 2021
 * Author: lkovari
 *
 *
 * Description:
 * 
 * 
 */
package com.lkovari.desktop.app.calculator.calculatorui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.Border;

public class CalculatorUI extends JPanel implements ComponentListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JSplitPane leftRightPanels;

	private JPanel inputPanel;
	private JPanel leftSidePanel;
	private JPanel expressionTreePanel;
	private JPanel postfixNotationPanel;
	private JPanel buttonsPanel;
	private JPanel buttonsNumericalSystemPanel;
	private JPanel buttonsOperatorsNumbersPanel;
	private JPanel messagePanel;
	
	private JLabel expressionLabel;
	private JTextField valueTextField;
    private JTextField expressionTextField;
    private JTextField postfixExpressionTextField;
    private JTree expressionTree;
    private JLabel expressionTreeLabel;
    private JLabel footerLabel;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    
    
    int leftSideWidthPercent = 35;
	int inputHeight = 60;
	int messageHeight = 60;
	int width = 1024;
	int height = 768;
	int treePanelWidth = 150;
	float FONT_SIZE = 15.0f;
	
	public CalculatorUI(JFrame mainFrame) {
		Container contentPane  = mainFrame.getContentPane();

        if (!(contentPane.getLayout() instanceof BorderLayout)) {
        	contentPane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }		
        this.setLayout(new BorderLayout());
		contentPane.setSize(new Dimension(width, height));
		this.addComponentListener(this);
		initializeCalculatorUIParts();
		initializeFields();
		contentPane.applyComponentOrientation(ComponentOrientation.getOrientation(contentPane.getLocale()));
		contentPane.add(this, BorderLayout.CENTER);
	}
	
	private JPanel createtPanel(Integer width, Integer height, Color bgColor) {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		if (width != null && height != null) {
			panel.setPreferredSize(new Dimension(width, height));
		}
		panel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		if (bgColor != null) {
			panel.setBackground(bgColor);
		}
		return panel;
	}
	
	private List<List<String>> generateButtonsDescriptor() {
		List<List<String>> descriptor = new ArrayList<List<String>>();
		List<String> row = Arrays.asList("Pi", "Sqrt", "x^2", "M+", "", "D", "E", "F", "Mod", "Clr"); 
		descriptor.add(row);
		row =  Arrays.asList("Sin", "Exp", "x^3", "M-", "", "A", "B", "C", "And", "+");
		descriptor.add(row);
		row =  Arrays.asList("Tan", "Log", "x^y", "Mr", "", "7", "8", "9", "Or", "-");
		descriptor.add(row);
		row =  Arrays.asList("Cos", "Lng", "1/x", "Mc", "", "4", "5", "6", "Xor", "*");
		descriptor.add(row);
		row =  Arrays.asList("(", ")", "", "", "", "1", "2", "3", "Not", "/");
		descriptor.add(row);
		row =  Arrays.asList("", "", "", "", "", "0", "+/-", ".", "Lsh", "=");
		descriptor.add(row);
		return descriptor;
	}
	
	private void createButtons(JPanel buttonsPanel) {
		List<List<String>> descriptor = generateButtonsDescriptor();
		for (int r = 5; r >= 0; r--) {
			for (int c = 0; c < 10; c++) {
				String title = descriptor.get(r).get(c);
				JButton button = new JButton(title);
				button.setActionCommand(title);
				if (title == "") {
					button.setEnabled(false);
					Border emptyBorder = BorderFactory.createEmptyBorder();
					button.setBorder(emptyBorder);					
				}
				button.setPreferredSize(new Dimension(40, 20));
				button.setFont(button.getFont().deriveFont(FONT_SIZE));
				button.addMouseListener(this);
				// button.add
				buttonsPanel.add(button, r, c);
			}
		}
	}

	private void initializeFields() {
		this.expressionLabel = new JLabel();
		
		this.footerLabel = new JLabel();
        this.footerLabel.setText("-");
        this.footerLabel.setAutoscrolls(true);
        this.footerLabel.setOpaque(true);
        
		this.expressionTextField = new JTextField();
		this.expressionTextField.setHorizontalAlignment(4);

		
		this.valueTextField = new JTextField();
		this.valueTextField.setHorizontalAlignment(4);
		this.inputPanel.add(this.valueTextField);
		
		this.postfixExpressionTextField = new JTextField();
		this.expressionTreeLabel = new JLabel("Expression tree");
		this.expressionTree = new JTree();
		
        this.jScrollPane1.setPreferredSize(new Dimension(100, 48));
        this.jScrollPane1.setViewportView(this.postfixExpressionTextField);
        this.jScrollPane2.setViewportView(this.expressionTree);		
		
	}
	
	private int calculateDividerLocation(Dimension parentSize) {
		int leftWidth = (parentSize.width / 100) * this.leftSideWidthPercent;
		Dimension oldSize = this.leftSidePanel.getSize();
		this.leftSidePanel.setSize(new Dimension(leftWidth, oldSize.height));
		this.leftRightPanels.setDividerLocation(leftWidth);
		return leftWidth;
	}
	
	private void initializeCalculatorUIParts() {
		// Top
		this.inputPanel = this.createtPanel(this.width, this.inputHeight, null);
		this.add(inputPanel, BorderLayout.NORTH);
		
		leftRightPanels = new JSplitPane();
		
		leftSidePanel = this.createtPanel(null, null, Color.cyan);
		buttonsPanel = this.createtPanel(null, null, Color.blue);
		buttonsPanel.setLayout(new BorderLayout());
		buttonsNumericalSystemPanel = this.createtPanel(null, null, Color.orange);
		Dimension size = buttonsNumericalSystemPanel.getPreferredSize();
		size.height = 40;
		buttonsNumericalSystemPanel.setPreferredSize(size);
		buttonsPanel.add(buttonsNumericalSystemPanel, BorderLayout.NORTH);
		
		buttonsOperatorsNumbersPanel = this.createtPanel(null, null, Color.gray);
		buttonsOperatorsNumbersPanel.setLayout(new GridLayout(6, 10, 3, 3));
		createButtons(buttonsOperatorsNumbersPanel);
		buttonsPanel.add(buttonsOperatorsNumbersPanel, BorderLayout.CENTER);
		
		
		Dimension uiSize = this.getSize();
		int dividerLocation = calculateDividerLocation(uiSize);
		// left and right
		leftRightPanels.setDividerLocation(dividerLocation);
		leftRightPanels.setOneTouchExpandable(true);
		// on left the tree
		this.expressionTreePanel = createtPanel(null, null, Color.magenta);
		this.postfixNotationPanel = createtPanel(null, null, Color.white);
		size = postfixNotationPanel.getPreferredSize();
		size.height = 90;
		this.postfixNotationPanel.setPreferredSize(size);		
		leftSidePanel.setLayout(new BorderLayout());
		leftSidePanel.add(this.postfixNotationPanel, BorderLayout.NORTH);
		leftSidePanel.add(this.expressionTreePanel, BorderLayout.CENTER);
		
		leftRightPanels.setLeftComponent(leftSidePanel);
		// on right the buttons
		leftRightPanels.setRightComponent(buttonsPanel);
		this.add(leftRightPanels, BorderLayout.CENTER);
		// bottom
		messagePanel = this.createtPanel(this.width, this.inputHeight, Color.white);
		this.add(messagePanel, BorderLayout.SOUTH);
		
		this.jScrollPane1 = new JScrollPane();
		this.jScrollPane2 = new JScrollPane();
		this.jScrollPane3 = new JScrollPane();
	}

	@Override
	public void componentResized(ComponentEvent e) {
		Dimension parentSize = e.getComponent().getSize();
		this.reSize(parentSize);
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private void reSize(Dimension parentSize) {
		System.out.println("Parent " + parentSize.width + " " + parentSize.height);
		this.setLocation(1, 0);
		int dividerLocation = calculateDividerLocation(parentSize);
		// left and right
		leftRightPanels.setDividerLocation(dividerLocation + 1);
		this.leftRightPanels.setDividerLocation(dividerLocation);
		// oldSize = this.expressionTree.getSize();
		// this.expressionTree.setSize(new Dimension(leftWidth, oldSize.height));
		/*
		this.inputPanel.setSize(new Dimension(parentSize.getSize().width - 20, inputHeight));
		this.inputPanel.setLocation(1, 0);
		System.out.println("Input " + this.inputPanel.getSize().width + " " + this.inputPanel.getSize().height);
		
		this.leftRightDelimitet.setSize(new Dimension(parentSize.getSize().width - 20, parentSize.getSize().height - (inputHeight + messageHeight) - 40));
		this.leftRightDelimitet.setLocation(1, inputHeight + 1);
		// this.leftRightDelimitet.validate();
		System.out.println("LeftRight " + this.leftRightDelimitet.getSize().width + " " + this.leftRightDelimitet.getSize().height);
		this.messagePanel.setLocation(1, this.leftRightDelimitet.getSize().height + inputPanel.getSize().height);
		this.messagePanel.setSize(new Dimension(parentSize.getSize().width - 20, messageHeight));
		System.out.println("Message " + this.messagePanel.getSize().width + " " + this.messagePanel.getSize().height);
		*/
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JButton button = (JButton)e.getSource();
		System.out.println("Mouse released " + button.getActionCommand());
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
