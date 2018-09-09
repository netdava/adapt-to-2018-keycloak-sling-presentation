package org.apache.sling.auth.keycloak;

import javax.servlet.Filter;
import org.keycloak.adapters.servlet.KeycloakOIDCFilter;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.http.whiteboard.HttpWhiteboardConstants;

@Component(
    immediate = true,
    service = Filter.class,
    property = {
      KeycloakOIDCFilter.CONFIG_FILE_PARAM + "=" + "keycloak.json",
      HttpWhiteboardConstants.HTTP_WHITEBOARD_FILTER_PATTERN + "=" + "/",
      HttpWhiteboardConstants.HTTP_WHITEBOARD_CONTEXT_SELECT
          + "="
          + "(osgi.http.whiteboard.context.name=org.apache.sling)"
    })
public class KeycloakFilter extends KeycloakOIDCFilter {}
