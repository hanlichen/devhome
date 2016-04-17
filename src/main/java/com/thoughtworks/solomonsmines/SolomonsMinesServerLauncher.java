package com.thoughtworks.solomonsmines;

import com.thoughtworks.solomonsmines.utils.ConfigUtil;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;

import java.util.Arrays;
import java.util.List;

public class SolomonsMinesServerLauncher {

	public static final int PORT = Integer.parseInt(ConfigUtil.getProperty("server.port"));
	private static final String CONTEXT_PATH = "/mines";
	private static Logger log = Logger.getLogger(SolomonsMinesServerLauncher.class);
	private final Server jettyServer;

	public SolomonsMinesServerLauncher() {
		jettyServer = createJettyServer();
	}

	public static void launch(final SolomonsMinesServerLauncher serverLauncher) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				serverLauncher.shutdown();
			}
		});

		new Thread() {
			@Override
			public void run() {
				try {
					serverLauncher.runForever();
				} catch (Exception e) {
					log.error("Failed to launch jetty server due to: ", e);
				}
			}
		}.start();

	}

	protected final int getServerPort() {
		return PORT;
	}

	private void shutdown() {
		try {
			jettyServer.stop();
		} catch (Exception e) {
			log.warn("Failed to stop server normally.", e);
		}
	}

	private void runForever() throws Exception {
		jettyServer.start();
		jettyServer.join();
	}

	private Server createJettyServer() {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);

		// spring context loader
		ContextLoaderListener contextLoaderListener = new ContextLoaderListener();
		handler.addEventListener(contextLoaderListener);
		handler.setInitParameter("contextConfigLocation", "classpath:context.xml");

		handler.addServlet(createJerseyServletHolder(), "/rest/*");

		handler.setContextPath(CONTEXT_PATH);

		Server server = new Server(getServerPort());
		server.setHandler(handler);

		return server;
	}

	private ServletHolder createJerseyServletHolder() {
		ServletHolder jerseyServletHolder = new ServletHolder(new ServletContainer());
		jerseyServletHolder.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

		List<String> packages = Lists.newArrayList("com.thoughtworks.solomonsmines.filter",
				"com.thoughtworks.solomonsmines.api");
		jerseyServletHolder.setInitParameter("jersey.config.server.provider.packages", Joiner.on(",").join(packages));

		List<String> features = Arrays.asList("org.glassfish.jersey.message.filtering.EntityFilteringFeature",
				"org.glassfish.jersey.jackson.JacksonFeature");
		jerseyServletHolder.setInitParameter("jersey.config.server.provider.classnames", Joiner.on(",").join(features));

		jerseyServletHolder.setInitParameter("jersey.config.server.provider.scanning.recursive", "true");
		jerseyServletHolder.setInitOrder(1);
		return jerseyServletHolder;
	}

	public static void main(String[] args) throws Exception {
		launch(new SolomonsMinesServerLauncher());
	}
}
