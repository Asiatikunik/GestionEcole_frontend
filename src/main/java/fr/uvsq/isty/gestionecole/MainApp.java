package fr.uvsq.isty.gestionecole;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.nio.file.FileSystemNotFoundException;
import java.security.DrbgParameters.NextBytes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.spi.AbstractResourceBundleProvider;


import fr.uvsq.isty.gestionecole.controleurs.*;
import fr.uvsq.isty.gestionecole.modeles.Creneau;
import fr.uvsq.isty.gestionecole.modeles.Ecole;
import fr.uvsq.isty.gestionecole.modeles.Promotion;
import fr.uvsq.isty.gestionecole.modeles.UniteEnseignement;

import com.google.gson.Gson;

public class MainApp extends Application {
	private static Scene scene;
	//Modele de donnees
    public Ecole model;
    
    
    @Override
    public void stop() throws IOException {
    	//A l'arret de l'application on convertit notre modele de donnees en JSON
    	//Puis on l'ecrit dans un fichier
    	Gson gson = new Gson();
    	FileWriter writer = new FileWriter("ecole.json");
    	gson.toJson(this.model, writer);
    	writer.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
    	//Au lancement de l'application on verifie si un fichier de sauvegarde existe
    	if(new File("ecole.json").exists()) {
    		//Si oui on le lit
    		Gson gson = new Gson();
    		BufferedReader reader = null;
    		try {
    			reader = new BufferedReader(new FileReader("ecole.json"));
    			this.model = gson.fromJson(reader, Ecole.class);
    		} catch (FileSystemNotFoundException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
    		
    	}else {
    		//Si non on instancie un modele de donnees vierge
    		this.model = new Ecole();
    	}
    	
        scene = new Scene(loadFXML("primary", model), 430, 550);
        stage.setScene(scene);
        stage.setTitle("gestion école");
        stage.show();
        
    }

    static void setRoot(String fxml, Ecole model) throws IOException {
        scene.setRoot(loadFXML(fxml, model));
    }

    public static Parent loadFXML(String fxml, Ecole model) throws IOException {
    	URL chemin = MainApp.class.getResource("/fxml/"+fxml+".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(chemin);
        
        fxmlLoader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> type) {
            	try {
        			
        			for (Constructor<?> c : type.getConstructors()) {
        				if (c.getParameterCount() == 1 && c.getParameterTypes()[0] == Ecole.class) {
        					return c.newInstance(model);
        				}
        			}
        			
        			//NOTE: deprecated...
        			return type.newInstance(); 
        			
        		} catch (Exception exc) {
        			throw new RuntimeException(exc);
        		}
            }
        });
        
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
