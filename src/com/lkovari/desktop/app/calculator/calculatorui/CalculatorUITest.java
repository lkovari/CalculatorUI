/**
 * Copyright (c) 2021 by László Kövári
 * laszlo.kovary@gmail.com
 * Projet: CalculatorUI
 * Package: com.lkovari.desktop.app.calculator.calculatorui
 * File: CalculatorUITest.java
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
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class CalculatorUITest {

	private static void createAndShowUI() {
		JFrame mainFrame = new JFrame();
		
        mainFrame.setTitle("RPN Calculator, Copyright © 2007 by Laszlo Kovari (still not an exhaustive solution, contains known bugs!)");
        mainFrame.pack();
        mainFrame.setSize(1024, 768);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setLayout(new BorderLayout());
		@SuppressWarnings("unused")
		CalculatorUI calculatorUI = new CalculatorUI(mainFrame);	
        // mainFrame.addComponentListener(calculatorUI);
	}
	
	public static void main(String[] args) {
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	createAndShowUI();
            }
        });
        


	}

}
