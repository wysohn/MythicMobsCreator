/*******************************************************************************
 *     Copyright (C) 2017 wysohn
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package com.naver.wysohn2002.mythicmobcreator.main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import java.awt.EventQueue;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class FrameFileLocationSelector extends JFrame {

	private JPanel contentPane;
	private JTextField textField_JarLoc;
	private JButton btnEdit_JarLoc;
	private JPanel panel_1;
	private JTextField textField_MMFolder;
	private JButton btnEdit_MMFolder;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	
	private File jarFile = null;
	private File MMFolder = null;
	
	public FrameFileLocationSelector(Runnable onClose){
		this(Main.instance.getBukkitAPIJarLocation(), Main.instance.getMythicMobsFolderLocation(), onClose);
	}
	/**
	 * @wbp.parser.constructor
	 */
	private File lastDirectory = null;
	private JPanel panel_2;
	private JButton btnQuit;
	private JPanel panel_3;
	private JButton btnConfirm;
	/**
	 * @wbp.parser.constructor
	 */
	public FrameFileLocationSelector(File initJar, File initMMFolder, final Runnable onClose) {
		setResizable(false);
		this.jarFile = initJar;
		this.MMFolder = initMMFolder;
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				Main.instance.setBukkitAPIJarLocation(jarFile);
				Main.instance.setMythicMobsFolderLocation(MMFolder);

				onClose.run();
			}
		});
		
		setTitle("Select Location");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 333, 208);
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(400, 200));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 5, 5));
		
		JPanel panel = new JPanel();
		panel_2.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel = new JLabel("Bukkit(or Spigot) Jar Location");
		panel.add(lblNewLabel);
		
		textField_JarLoc = new JTextField();
		textField_JarLoc.setEditable(false);
		panel.add(textField_JarLoc);
		textField_JarLoc.setColumns(10);
		if(this.jarFile != null)
			textField_JarLoc.setText(this.jarFile.getAbsolutePath());
		
		btnEdit_JarLoc = new JButton("Edit");
		btnEdit_JarLoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fc.setDialogTitle("Select Bukkit or Spigot Jar file. (ex) spigot_xxx.jar");
				fc.setCurrentDirectory(lastDirectory);
				fc.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						return f.isDirectory() || f.getName().endsWith(".jar");
					}

					@Override
					public String getDescription() {
						return "jar files";
					}
				});
				int returnVal = fc.showDialog(FrameFileLocationSelector.this, "Select");
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					
					if(!validateJar(file)){
						JOptionPane.showMessageDialog(null, file+" is not a valid jar file");
						return;
					}
					
					jarFile = file;
					
					textField_JarLoc.setText(file.getPath());
					
					lastDirectory = file.getParentFile();
				}
			}
		});
		panel.add(btnEdit_JarLoc);
		
		panel_1 = new JPanel();
		panel_2.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		lblNewLabel_1 = new JLabel("MythicMob Folder");
		panel_1.add(lblNewLabel_1);
		
		textField_MMFolder = new JTextField();
		textField_MMFolder.setEditable(false);
		textField_MMFolder.setColumns(10);
		if(this.MMFolder != null)
			textField_MMFolder.setText(this.MMFolder.getAbsolutePath());
		panel_1.add(textField_MMFolder);
		
		btnEdit_MMFolder = new JButton("Edit");
		btnEdit_MMFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.setDialogTitle("Select MythicMobs Folder. (ex) plugins/MythicMobs/");
				fc.setCurrentDirectory(lastDirectory);
				fc.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						return f.isDirectory();
					}

					@Override
					public String getDescription() {
						return "directories";
					}
				});
				int returnVal = fc.showDialog(FrameFileLocationSelector.this, "Select");
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					
					if(!validateMythicMobFolder(file)){
						JOptionPane.showMessageDialog(null, file+" is not a MythicMobs folder");
						return;
					}
					
					MMFolder = file;
					
					textField_MMFolder.setText(file.getPath());
					
					lastDirectory = file.getParentFile();
				}
			}
		});
		panel_1.add(btnEdit_MMFolder);
		
		panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(1, 0, 5, 5));
		
		btnQuit = new JButton("Quit");
		panel_3.add(btnQuit);
		
		btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		panel_3.add(btnConfirm);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		this.setLocationRelativeTo(null);
		this.pack();
	}
	
	
	
	public File getJarFile() {
		return jarFile;
	}



	public void setJarFile(File jarFile) {
		this.jarFile = jarFile;
	}



	public File getMMFolder() {
		return MMFolder;
	}



	public void setMMFolder(File mMFolder) {
		MMFolder = mMFolder;
	}



	private boolean validateJar(File file){
		if(!file.isFile())
			return false;
		
		if(!file.exists())
			return false;
		
		if(!file.getName().endsWith(".jar"))
			return false;
			
		return true;
	}

	private boolean validateMythicMobFolder(File folder){
		if(!folder.isDirectory())
			return false;
		
		if(!folder.exists())
			return false;
		
		if(!folder.getName().equals("MythicMobs"))
			return false;
		
		return true;
	}
}
