package org.apache.sling.auth.keycloak;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.NodesRegistrationManagement;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(
    immediate = true,
    service = Filter.class,
    property = {
      "keycloak.config.file=" + "keycloak.json",
      "keycloak.config.skipPattern=" + "/public/.*",
      HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "=" + "/",
      HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT
          + "="
          + "(osgi.http.whiteboard.context.name=org.apache.sling)"
    })
public class KeycloakFilter extends KeycloakOIDCFilter {

  private static final Logger log = Logger.getLogger(KeycloakOIDCFilter.class.getName());
  private final KeycloakConfigResolver definedconfigResolver;

  /**
   * Constructor that can be used to define a {@code KeycloakConfigResolver} that will be used at
   * initialization to provide the {@code KeycloakDeployment}.
   *
   * @param definedconfigResolver the resolver
   */
  public KeycloakFilter(KeycloakConfigResolver definedconfigResolver) {
    this.definedconfigResolver = definedconfigResolver;
  }

  public KeycloakFilter() {
    this(null);
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    String skipPatternDefinition = filterConfig.getInitParameter(SKIP_PATTERN_PARAM);
    if (skipPatternDefinition != null) {
      skipPattern = Pattern.compile(skipPatternDefinition, Pattern.DOTALL);
    }

    if (definedconfigResolver != null) {
      deploymentContext = new AdapterDeploymentContext(definedconfigResolver);
      log.log(
          Level.INFO,
          "Using {0} to resolve Keycloak configuration on a per-request basis.",
          definedconfigResolver.getClass());
    } else {
      String configResolverClass = filterConfig.getInitParameter(CONFIG_RESOLVER_PARAM);
      if (configResolverClass != null) {
        try {
          KeycloakConfigResolver configResolver =
              (KeycloakConfigResolver)
                  getClass().getClassLoader().loadClass(configResolverClass).newInstance();
          deploymentContext = new AdapterDeploymentContext(configResolver);
          log.log(
              Level.INFO,
              "Using {0} to resolve Keycloak configuration on a per-request basis.",
              configResolverClass);
        } catch (Exception ex) {
          log.log(
              Level.FINE,
              "The specified resolver {0} could NOT be loaded. Keycloak is unconfigured and will deny all requests. Reason: {1}",
              new Object[] {configResolverClass, ex.getMessage()});
          deploymentContext = new AdapterDeploymentContext(new KeycloakDeployment());
        }
      } else {
        String fp = filterConfig.getInitParameter(CONFIG_FILE_PARAM);
        InputStream is = null;
        if (fp != null) {
          Path configPath = Paths.get(fp).toAbsolutePath();
          try {
            is = new FileInputStream(configPath.toFile());
          } catch (FileNotFoundException e) {
            log.log(Level.WARNING, "Could not load config file {0}", configPath);
            throw new RuntimeException(e);
          }
        } else {
          String path = "/WEB-INF/keycloak.json";
          String pathParam = filterConfig.getInitParameter(CONFIG_PATH_PARAM);
          if (pathParam != null) {
            path = pathParam;
          }
          is = filterConfig.getServletContext().getResourceAsStream(path);
        }
        KeycloakDeployment kd = createKeycloakDeploymentFrom(is);
        deploymentContext = new AdapterDeploymentContext(kd);
        log.fine("Keycloak is using a per-deployment configuration.");
      }
    }
    filterConfig
        .getServletContext()
        .setAttribute(AdapterDeploymentContext.class.getName(), deploymentContext);
    nodesRegistrationManagement = new NodesRegistrationManagement();
  }

  private KeycloakDeployment createKeycloakDeploymentFrom(InputStream is) {
    if (is == null) {
      log.fine("No adapter configuration. Keycloak is unconfigured and will deny all requests.");
      return new KeycloakDeployment();
    }
    return KeycloakDeploymentBuilder.build(is);
  }
}
