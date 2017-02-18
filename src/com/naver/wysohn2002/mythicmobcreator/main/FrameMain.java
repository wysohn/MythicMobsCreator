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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.naver.wysohn2002.mythicmobcreator.util.ReflectionHelper;

public class FrameMain extends JFrame {

	private JPanel contentPane;
	private JTextField textField_fileName;
	private File currentFile;
	private FileConfiguration currentConfig;

	private JPanel currentPanel;
	private JList<String> currentList;
	private PanelResourceEditor currentEditor;

	private String currentName;

	private JPanel panel_DropTables;
	private JPanel panel_Mobs;

	private int currerntMobIndex = -1;
	private int currerntTypeIndex = -1;
	/**
	 * Create the frame.
	 */
	public FrameMain() {
		setMinimumSize(new Dimension(700, 500));
		setMaximumSize(new Dimension(1200, 600));
		getContentPane().setLayout(new BorderLayout(0, 0));
		setTitle("MythicMobCreator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmOptions = new JMenuItem("Options");
		mntmOptions.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				FrameMain.this.setVisible(false);
				new FrameFileLocationSelector(
						Main.instance.getBukkitAPIJarLocation(),
						Main.instance.getMythicMobsFolderLocation(),
						new Runnable(){
							@Override
							public void run() {
								FrameMain.this.setVisible(true);
							}
						}).setVisible(true);
			}
		});
		mnFile.add(mntmOptions);
		contentPane = new JPanel();
		contentPane.setMinimumSize(new Dimension(600, 400));
		contentPane.setMaximumSize(new Dimension(1000, 600));
		contentPane.setBackground(Color.WHITE);
		contentPane.setPreferredSize(new Dimension(600, 400));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
            public void stateChanged(ChangeEvent e) {
		        JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
				if(currerntTypeIndex != -1 && currerntTypeIndex != sourceTabbedPane.getSelectedIndex()
						&& JOptionPane.showConfirmDialog(null, "Unsaved works will be removed. Continue?") != JOptionPane.OK_OPTION){
					sourceTabbedPane.setSelectedIndex(currerntTypeIndex);
					return;
				}

				if(currerntTypeIndex == sourceTabbedPane.getSelectedIndex()){
					return;
				}

				currerntTypeIndex = sourceTabbedPane.getSelectedIndex();

				currentFile = null;
				if(textField_fileName != null)
					textField_fileName.setText(null);

		        currentName = sourceTabbedPane.getTitleAt(sourceTabbedPane.getSelectedIndex());
		        currentPanel = (JPanel) sourceTabbedPane.getSelectedComponent();

		        if(currentPanel == null){
		        	currentPanel = panel_Mobs;
		        }

		        currentPanel.removeAll();
				EventQueue.invokeLater(new Runnable() {
					@Override
                    public void run() {
						currentFile = null;
						currentConfig = null;

						JScrollPane scrollPane = new JScrollPane();
						scrollPane.setBackground(Color.WHITE);
						scrollPane.setPreferredSize(new Dimension(100, 2));
						scrollPane.setMaximumSize(new Dimension(100, 32767));
						scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
						scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
						currentPanel.add(scrollPane, BorderLayout.WEST);

						currerntMobIndex = -1;
						final JList<String> list = new PropertiesList();
						list.addListSelectionListener(new ListSelectionListener(){
							@Override
							public void valueChanged(ListSelectionEvent e) {
								if (currerntMobIndex != -1
										&& currerntMobIndex != ((JList) e.getSource()).getSelectedIndex()
										&& JOptionPane.showConfirmDialog(null,
												"Unsaved works will be removed. Continue?") != JOptionPane.OK_OPTION) {
									((JList) e.getSource()).setSelectedIndex(currerntMobIndex);
									return;
								}

								if (currerntMobIndex == ((JList) e.getSource()).getSelectedIndex()) {
									return;
								}

								currerntMobIndex = ((JList) e.getSource()).getSelectedIndex();

								try {
									updateResourcePanel();
								} catch (Exception e1) {
									Main.LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
								}
							}
						});

						final JPopupMenu popup = new JPopupMenu();
						JMenuItem createMenuItem = new JMenuItem("Create New "+currentName);
						createMenuItem.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								if(currentConfig == null){
									JOptionPane.showMessageDialog(null, "Yml file is not open yet.");
									return;
								}

								String input = JOptionPane.showInputDialog("Enter the name");
								if(input != null){
									try {
										currentConfig.set(input, createObject(currentName).serialize());

										currentConfig.save(currentFile);
										currentConfig.load(currentFile);

										updateList();
										updateResourcePanel();
									} catch (Exception e1) {
										Main.LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
									}
								}
							}
						});
						popup.add(createMenuItem);
						JMenuItem deleteMenuItem = new JMenuItem("Delete item");
						deleteMenuItem.addActionListener(new ActionListener(){
							@Override
							public void actionPerformed(ActionEvent e) {
								if(currentList == null)
									return;

								int selected = currentList.getSelectedIndex();
								if(selected < 0)
									return;

								String selectedItem = currentList.getSelectedValue();
								int result = JOptionPane.showConfirmDialog(null, "Are you sure to delete "+selectedItem+"?");
								if(result == JOptionPane.OK_OPTION){
									try {
										currentConfig.set(selectedItem, null);
										currentConfig.save(currentFile);

										updateList();
										updateResourcePanel();
									} catch (Exception e1) {
										Main.LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
									}
								}
							}
						});
						popup.add(deleteMenuItem);

						list.addMouseListener(new MouseAdapter(){
							@Override
                            public void mousePressed(MouseEvent e) {
								if(SwingUtilities.isRightMouseButton(e)){
									popup.show(e.getComponent(), e.getX(), e.getY());
								}
							}
						});


						scrollPane.setViewportView(list);
						currentList = list;

						repaint();
					}
				});

			}
		});
		contentPane.add(tabbedPane);

		panel_Mobs = new JPanel();
		panel_Mobs.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Mobs", null, panel_Mobs, null);
		panel_Mobs.setLayout(new BorderLayout(0, 0));

		panel_DropTables = new JPanel();
		panel_DropTables.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("DropTables", null, panel_DropTables, null);
		tabbedPane.setEnabledAt(1, true);
		panel_DropTables.setToolTipText("Create or Edit MythicMobs drops");
		panel_DropTables.setLayout(new BorderLayout(0, 0));

		JPanel panel_Items = new JPanel();
		panel_Items.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Items", null, panel_Items, null);
		tabbedPane.setEnabledAt(2, false);
		panel_Items.setLayout(new BorderLayout(0, 0));

		JPanel panel_RandomSpawns = new JPanel();
		panel_RandomSpawns.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("RandomSpawns", null, panel_RandomSpawns, null);
		tabbedPane.setEnabledAt(3, false);
		panel_RandomSpawns.setLayout(new BorderLayout(0, 0));

		JPanel panel_Skills = new JPanel();
		panel_Skills.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Skills", null, panel_Skills, null);
		tabbedPane.setEnabledAt(4, false);
		panel_Skills.setLayout(new BorderLayout(0, 0));

		JPanel panel_Spawners = new JPanel();
		panel_Spawners.setBorder(new EmptyBorder(5, 5, 5, 5));
		tabbedPane.addTab("Spawners", null, panel_Spawners, null);
		tabbedPane.setEnabledAt(5, false);
		panel_Spawners.setLayout(new BorderLayout(0, 0));

		JPanel panel_South = new JPanel();
		contentPane.add(panel_South, BorderLayout.SOUTH);
		panel_South.setLayout(new BorderLayout(0, 0));

		JPanel panel_south_buttons = new JPanel();
		panel_South.add(panel_south_buttons, BorderLayout.WEST);

		JButton btn_New = new JButton("New");
		btn_New.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				String input = JOptionPane.showInputDialog("Enter the file name (without .yml)");
				if(input != null){
					File folder = new File(Main.instance.getMythicMobsFolderLocation(), currentName);
					File file = new File(folder, input+".yml");

					if(file.exists()){
						JOptionPane.showMessageDialog(null, "This file already exist!");
						return;
					}

					try {
						file.createNewFile();
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(null, "Could not create file. See logs for more info.");
						Main.LOGGER.log(Level.WARNING, e1.getMessage(), e1);
					}

					currentFile = file;
					textField_fileName.setText(currentFile.getName());
					currentConfig = YamlConfiguration.loadConfiguration(currentFile);

					updateList();

					try {
						updateResourcePanel();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error -- "+e1.getMessage());
						Main.LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
					}
				}
			}
		});
		panel_south_buttons.add(btn_New);

		JButton btn_Load = new JButton("Load");
		btn_Load.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(Main.instance.getMythicMobsFolderLocation() == null){
					JOptionPane.showMessageDialog(null, "MythicMobs folder is not set!");
					return;
				}

				File folder = new File(Main.instance.getMythicMobsFolderLocation(), currentName);

				JFileChooser jf = new JFileChooser();
				jf.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jf.setCurrentDirectory(folder);
				jf.setFileFilter(new FileFilter(){
					@Override
					public boolean accept(File f) {
						return f.getName().endsWith(".yml");
					}

					@Override
					public String getDescription() {
						return currentName+" config file";
					}
				});

				int sel = jf.showDialog(null, "Load");
				if(sel == JFileChooser.APPROVE_OPTION){
					currentFile = jf.getSelectedFile();
					textField_fileName.setText(currentFile.getName());
					currentConfig = YamlConfiguration.loadConfiguration(currentFile);

					updateList();

					try {
						updateResourcePanel();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "Error -- "+e1.getMessage());
						Main.LOGGER.log(Level.SEVERE, e1.getMessage(), e1);
					}
				}
			}
		});
		panel_south_buttons.add(btn_Load);

		JButton btn_Save = new JButton("Save");
		btn_Save.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
				if(Main.instance.getMythicMobsFolderLocation() == null){
					JOptionPane.showMessageDialog(null, "MythicMobs folder is not set!");
					return;
				}

				if(currentConfig == null){
					JOptionPane.showMessageDialog(null, "You haven't opend file!");
					return;
				}

				String key = currentList.getSelectedValue();

				currentConfig.set(key, currentEditor.getTarget().serialize());
				try {
					currentConfig.save(currentFile);
				} catch (IOException e1) {
					Main.LOGGER.log(Level.WARNING, e1.getMessage(), e1);
					JOptionPane.showMessageDialog(null, "Could not save! See logs for detail.");
				}
			}
		});
		panel_south_buttons.add(btn_Save);

		JPanel panel_south_fileName = new JPanel();
		panel_south_fileName.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel_South.add(panel_south_fileName, BorderLayout.CENTER);
		panel_south_fileName.setLayout(new BorderLayout(0, 0));

		textField_fileName = new JTextField();
		textField_fileName.setForeground(Color.BLACK);
		textField_fileName.setDisabledTextColor(Color.GRAY);
		textField_fileName.setEditable(false);
		panel_south_fileName.add(textField_fileName);
		textField_fileName.setColumns(10);

		pack();
	}

	private void updateList() {
		DefaultListModel<String> listData = new DefaultListModel<String>();
		for(String key : currentConfig.getKeys(false))
			listData.addElement(key);
		currentList.setModel(listData);
		if(listData.size() > 0)
			currentList.setSelectedIndex(0);
	}

	private void updateResourcePanel() throws Exception{
		int index = currentList.getSelectedIndex();
		if(index < 0)
			return;

		String key = currentList.getSelectedValue();
		Object value = currentConfig.get(key);
		if(!(value instanceof ConfigurationSection))
			return;

		if(currentEditor != null)
			currentPanel.remove(currentEditor);
		currentEditor = new PanelResourceEditor(createObject(currentName, (ConfigurationSection) value));
		currentPanel.add(currentEditor, BorderLayout.CENTER);

		EventQueue.invokeLater(new Runnable() {
			@Override
            public void run() {
				repaint();
			}
		});
	}

	private final String packageName = "com.naver.wysohn2002.mythicmobcreator.constants";

	private ConfigurationSerializable createObject(String type)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
		return createObject(type, null);
	}

	private ConfigurationSerializable createObject(String type, ConfigurationSection section)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, NoSuchFieldException {
		Class<? extends ConfigurationSerializable> clazz = (Class<? extends ConfigurationSerializable>) Class
				.forName(packageName + "." + type);
		Method method = clazz.getMethod("deserialize", Map.class);
		Map map = ReflectionHelper.extractMap(section);
		ConfigurationSerializable obj = (ConfigurationSerializable) method.invoke(null, map);
		return obj;
	}

	private class PropertiesList<T> extends JList<T>{
		@Override
		public String getToolTipText(MouseEvent event) {
			Point p = event.getPoint();
			int location = locationToIndex(p);
			if(getModel().getSize() < 1)
				return null;
			String toolTip = (String) getModel().getElementAt(location);
			return toolTip;
		}
	}
}
