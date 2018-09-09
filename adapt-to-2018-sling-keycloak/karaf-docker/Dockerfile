FROM docker-registry.gr8pi.org/adoptopenjdk/openjdk8

ENV KARAF_USER karaf
ENV KARAF_UID 8181
# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV KARAF_VERSION 4.2.1
ENV JAVA_MIN_MEM 1024m
ENV JAVA_MAX_MEM 20148m
ENV KARAF_EXEC exec
ENV KARAF_BASE /opt/karaf-base

COPY download.sh /tmp/download.sh

RUN groupadd -r $KARAF_USER --gid=$KARAF_UID && useradd -rm -g $KARAF_USER --uid=$KARAF_UID $KARAF_USER

RUN apt-get update && apt-get install -y --no-install-recommends jq curl && rm -rf /var/lib/apt/lists/* \
    && chmod a+x /tmp/download.sh && sync && /tmp/download.sh && rm /tmp/download.sh \
    && mkdir -p /opt/karaf \
    && tar --strip-components=1 -C /opt/karaf -xzf /tmp/apache-karaf.tar.gz \
    && rm /tmp/apache-karaf.tar.gz \
    && mkdir -p $KARAF_BASE $KARFA_BASE/log \
    && cp -R /opt/karaf/etc $KARAF_BASE \
    && chown -R $KARAF_USER.$KARAF_USER /opt/karaf \
    && chown -R $KARAF_USER.$KARAF_USER $KARAF_BASE \
    && chmod 700 $KARAF_BASE \
    && ls -la $KARAF_BASE ; ls -la $KARAF_BASE/etc

EXPOSE 1099 8101 44444

COPY docker-entrypoint.sh /usr/local/bin/
COPY etc /opt/karaf/etc

USER $KARAF_USER
VOLUME ["/opt/karaf/deploy","/opt/karaf/etc","/opt/karaf/data", "/opt/karaf/sling" ]
ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
CMD ["/opt/karaf/bin/karaf", "run"]
