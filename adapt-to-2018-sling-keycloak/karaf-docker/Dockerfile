FROM openjdk:8-jre

# ENV KARAF_USER karaf
ENV KARAF_UID 8181
# ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV KARAF_VERSION 4.2.1
ENV JAVA_MIN_MEM 1024m
ENV JAVA_MAX_MEM 20148m
ENV KARAF_EXEC exec

COPY download.sh /tmp/download.sh

# RUN groupadd -r $KARAF_USER --gid=$KARAF_UID && useradd -rm -g $KARAF_USER --uid=$KARAF_UID $KARAF_USER

RUN apt-get update && apt-get install -y --no-install-recommends jq curl && rm -rf /var/lib/apt/lists/* \
    && chmod a+x /tmp/download.sh && sync && /tmp/download.sh && rm /tmp/download.sh \
    && mkdir -p /opt/karaf \
    && tar --strip-components=1 -C /opt/karaf -xzf /tmp/apache-karaf.tar.gz \
    && rm /tmp/apache-karaf.tar.gz

EXPOSE 1099 8101 44444

COPY docker-entrypoint.sh /usr/local/bin/

# USER $KARAF_USER
COPY etc /opt/karaf/etc

VOLUME ["/opt/karaf/deploy","/opt/karaf/etc","/opt/karaf/data", "/opt/karaf/sling" ]
# ENTRYPOINT ["/usr/local/bin/docker-entrypoint.sh"]
ENTRYPOINT ["/opt/karaf/bin/karaf", "run"]
