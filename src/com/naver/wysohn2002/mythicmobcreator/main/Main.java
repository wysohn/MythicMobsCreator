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

import java.awt.EventQueue;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {	
	public static Main instance;
	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());
	
	private static FileHandler fh;
	//private static Map<String, >
	
	private static final Class[] parameters = new Class[]{URL.class};
	
	public static void main(String[] ar) throws SecurityException, IOException{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-M-d");
		fh = new FileHandler("logs"+format.format(Calendar.getInstance().getTime())+".log", true);
		LOGGER.addHandler(fh);
		fh.setFormatter(new SimpleFormatter());
		
		try {
			instance = new Main();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Initialization failed! -- "+e.getMessage());
			LOGGER.log(Level.SEVERE, e.toString(), e);
		}
		
		loadJar();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setConfigs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private void setConfigs() {
				if(Main.instance.getBukkitAPIJarLocation() == null || Main.instance.getMythicMobsFolderLocation() == null){
					FrameFileLocationSelector selector = new FrameFileLocationSelector(new Runnable(){
						@Override
						public void run() {
							if(Main.instance.getBukkitAPIJarLocation() == null || Main.instance.getMythicMobsFolderLocation() == null){
								setConfigs();
							}else{
								loadJar();
								showJFrame(new FrameMain());
							}
						}
					});
					showJFrame(selector);
				}else{
					loadJar();
					showJFrame(new FrameMain());
				}
			}
		});
	}
	
	private static void showJFrame(JFrame frame){
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	private static void loadJar() {
		if (Main.instance.getBukkitAPIJarLocation() != null) {
			try {
				URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
				Class clazz = URLClassLoader.class;
				
				Method method = clazz.getDeclaredMethod("addURL", parameters);
			     method.setAccessible(true);
			     method.invoke(sysloader, new Object[]{
			    		 Main.instance.getBukkitAPIJarLocation().toURI().toURL(),
			     });
				
			} catch (Exception e) {
				Main.LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
	private final File config = new File("configs");
	private final Properties configProp = new Properties();
	
	private File bukkitAPIJarLocation;
	private File mythicMobsFolderLocation;
	
	public Main() throws IOException{
		instance = this;
		
		if(!config.exists())
			config.createNewFile();
		
		configProp.load(new FileReader(config));
		
		bukkitAPIJarLocation = checkAndGet(configProp.getProperty("bukkitAPIJarLocation"));
		mythicMobsFolderLocation = checkAndGet(configProp.getProperty("mythicMobsFolderLocation"));
	}
	
	private File checkAndGet(String path){
		if(path == null)
			return null;
		
		File file = new File(path);
		if(!file.exists())
			return null;
		
		return file;
	}

	public File getBukkitAPIJarLocation() {
		return bukkitAPIJarLocation;
	}

	public void setBukkitAPIJarLocation(File bukkitAPIJarLocation) {
		this.bukkitAPIJarLocation = bukkitAPIJarLocation;
		
		if(bukkitAPIJarLocation != null){
			configProp.setProperty("bukkitAPIJarLocation", bukkitAPIJarLocation.getAbsolutePath());
			try {
				configProp.store(new FileWriter(config), "Settings");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}

	public File getMythicMobsFolderLocation() {
		return mythicMobsFolderLocation;
	}

	public void setMythicMobsFolderLocation(File mythicMobsFolderLocation) {
		this.mythicMobsFolderLocation = mythicMobsFolderLocation;
		
		if(mythicMobsFolderLocation != null){
			configProp.setProperty("mythicMobsFolderLocation", mythicMobsFolderLocation.getAbsolutePath());
			try {
				configProp.store(new FileWriter(config), "Settings");
			} catch (IOException e) {
				LOGGER.log(Level.SEVERE, e.getMessage(), e);
			}
		}
	}
	
}
