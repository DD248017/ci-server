package dd2480.group17.ciserver.domain;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import dd2480.group17.ciserver.infrastructure.HealthHandler;
import dd2480.group17.ciserver.infrastructure.HistoryHandler;
import dd2480.group17.ciserver.infrastructure.RootHandler;
import dd2480.group17.ciserver.infrastructure.WebhookListener;
import dd2480.group17.ciserver.utils.ConfigLoader;

public class Build {

    /**
     * This method starts the server and listens for incoming requests.
     *
     * @throws Exception If an error occurred while starting the server
     */
    public void run() throws Exception {
        ConfigLoader configLoader = new ConfigLoader();
        int port = configLoader.getPort();

        Server server = new Server(port);

        ContextHandler webhookContext = new ContextHandler("/webhook");
        webhookContext.setHandler(new WebhookListener());

        ContextHandler healthCheckContext = new ContextHandler("/health");
        healthCheckContext.setHandler(new HealthHandler());

        ContextHandler historyContext = new ContextHandler("/history");
        historyContext.setHandler(new HistoryHandler());

        ContextHandler rootContext = new ContextHandler("/");
        rootContext.setHandler(new RootHandler());

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[] { "index.html" });
        resourceHandler.setResourceBase("./target/site/apidocs");

        ContextHandler docsContext = new ContextHandler("/docs");
        docsContext.setHandler(resourceHandler);

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(webhookContext);
        contexts.addHandler(healthCheckContext);
        contexts.addHandler(historyContext);
        contexts.addHandler(rootContext);
        contexts.addHandler(docsContext);

        server.setHandler(contexts);
        server.start();
        server.join();
    }
}
