//
// Copyright (c) 2019 Agile Trailblazers, Inc. All Rights Reserved
// https://www.agiletrailblazers.com
//
// Classification level: Confidential
//
// $Id: $
// @Project:     MPL
// @Description: Shared Jenkins Modular Pipeline Library
//

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
