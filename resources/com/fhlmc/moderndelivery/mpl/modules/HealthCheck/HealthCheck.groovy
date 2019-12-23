/**
 * Health Checker
 */

echo 'Toolchain Healthcheck ...'

MPLModule('Jira HealthCheck', CFG)
MPLModule('Sonar HealthCheck', CFG)
//MPLModule('Artifactory HealthCheck', CFG)
//MPLModule('Elasticache HealthCheck', CFG)
//MPLModule('Fluentd HealthCheck', CFG)
//MPLModule('Kibana HealthCheck', CFG)
//MPLModule('Spinnaker HealthCheck', CFG)
