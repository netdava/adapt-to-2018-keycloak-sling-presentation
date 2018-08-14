# adapt-to-2018-keycloak-sling-presentation

Presentation slides for Keycloak and Sling integration for Adapt.to 2018

- For the actual slides use [presentation link](https://netdava.github.io/adapt-to-2018-keycloak-sling-presentation/presentation) .

## Local development

To work on the presentation locally you can use any web server and refresh the pages.
One good available almost anywhere is python http server:

```sh
  python -m http.server
  echo "View presentation http://0.0.0.0:8000/"
```

Once you are satisfied, commit and pusho your changes and view them online.

## Creating new presentations

To create new presentations you should use *reveal-cli* npm package and of course, npm and node.
Install the tooling with `npm install`.

Once that is done, you can create new reveal presentations with `npx`: `npx reveal new presentation-name` .

[reveal-cli homepage](https://www.npmjs.com/package/reveal-cli)

