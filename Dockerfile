# Use Elasticsearch 9.1.4 to match the plugin version
FROM docker.elastic.co/elasticsearch/elasticsearch:9.1.4

# Copy the plugin distribution
COPY build/distributions/elasticsearch-token-count-query-0.1.0.zip /tmp/plugin.zip

# Install the plugin
RUN elasticsearch-plugin install --batch file:///tmp/plugin.zip
