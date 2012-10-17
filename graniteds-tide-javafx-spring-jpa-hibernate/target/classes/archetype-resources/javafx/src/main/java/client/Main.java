/*
  GRANITE DATA SERVICES
  Copyright (C) 2011 GRANITE DATA SERVICES S.A.S.

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

package ${package}.client;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.inject.Inject;

import org.granite.client.tide.ContextManager;
import org.granite.client.tide.data.DataObserver;
import org.granite.client.tide.data.EntityManager;
import org.granite.client.tide.data.ValidationExceptionHandler;
import org.granite.client.tide.javafx.JavaFXPlatform;
import org.granite.client.tide.javafx.JavaFXServerSessionStatus;
import org.granite.client.tide.javafx.TideFXMLLoader;
import org.granite.client.tide.javafx.spring.Identity;
import org.granite.client.tide.server.ExceptionHandler;
import org.granite.client.tide.server.ServerSession;
import org.granite.client.tide.server.SimpleTideResponder;
import org.granite.client.tide.server.TideFaultEvent;
import org.granite.client.tide.server.TideResultEvent;
import org.granite.client.tide.spring.SpringContextManager;
import org.granite.client.tide.spring.SpringEventBus;
import org.granite.logging.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 *
 * @author william
 */
public class Main extends Application {
	
	private static final Logger log = Logger.getLogger(Main.class);
    
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
    
    
    @Configuration
    public static class Config {
    	
    	@Bean
    	public SpringEventBus eventBus() {
    		return new SpringEventBus();
    	}
    	
    	@Bean
    	public SpringContextManager contextManager(SpringEventBus eventBus) {
    		return new SpringContextManager(new JavaFXPlatform(eventBus));
    	}
    	
    	@Bean(initMethod="start", destroyMethod="stop")
    	public ServerSession serverSession() throws Exception {
    		ServerSession serverSession = new ServerSession("spring", "/${rootArtifactId}", "localhost", 8080);
    		serverSession.setUseWebSocket(true);
        	serverSession.addRemoteClassPackage("${package}.client.entities");
        	return serverSession;
    	}
    	
    	@Bean
    	public Identity identity(ServerSession serverSession) throws Exception {
    		return new Identity(serverSession);
    	}
    	
    	@Bean(initMethod="start", destroyMethod="stop")
    	public DataObserver welcomeTopic(ServerSession serverSession, EntityManager entityManager) {
    		return new DataObserver("welcomeTopic", serverSession, entityManager);
    	}
    	
    	@Bean
    	public ExceptionHandler validationExceptionHandler() {
    		return new ValidationExceptionHandler();
    	}
    	
    	@Bean
    	public App init() {
    		return new App();
    	}    	
    }
    
    public static class App {
    	
    	@Inject
    	private Identity identity;
    	
    	@Inject
    	private ContextManager contextManager;
    	
    	
    	public void start(final Stage stage) {
            identity.loggedInProperty().addListener(new ChangeListener<Boolean>() {
    			@Override
    			public void changed(ObservableValue<? extends Boolean> property, Boolean oldValue, Boolean newValue) {
					showView(stage, newValue);
    			}
            });
            
            identity.checkLoggedIn(new SimpleTideResponder<String>() {
    			@Override
    			public void result(TideResultEvent<String> event) {
    				if (event.getResult() == null)
						showView(stage, false);
    			}
    			
    			@Override
    			public void fault(TideFaultEvent event) {
					Parent login = showView(stage, false);
					Label message = (Label)login.lookup("#labelMessage");
					message.setText(event.getFault().getFaultDescription());
					message.setVisible(true);
    			}
            });
    	}
    	
    	public void stop() {
    	}
        
        private Parent showView(Stage stage, boolean loggedIn) {
        	try {
	            Parent root = (Parent)TideFXMLLoader.load(contextManager.getContext(), loggedIn ? "Home.fxml" : "Login.fxml", loggedIn ? Home.class : Login.class);
	            
	            stage.setScene(new Scene(root));
	            stage.show();
	            
	            return root;
        	}
        	catch (Exception e) {
        		log.error(e, "Could not show view");
        	}
        	return null;
        }
    }
    
    private AnnotationConfigApplicationContext applicationContext;
    
    @Override
    public void start(final Stage stage) throws Exception {		
    	applicationContext = new AnnotationConfigApplicationContext();
    	applicationContext.scan("${package}.client");
    	applicationContext.scan("${package}.client.services");
    	applicationContext.refresh();
    	applicationContext.registerShutdownHook();
    	applicationContext.start();
    	
    	ServerSession serverSession = applicationContext.getBean(ServerSession.class);
    	((JavaFXServerSessionStatus)serverSession.getStatus()).setStage(stage);
    	applicationContext.getBean(App.class).start(stage);
    }
    
    @Override
    public void stop() throws Exception {
    	applicationContext.getBean(App.class).stop();
    	
    	applicationContext.stop();
    	applicationContext.destroy();
    }
}
