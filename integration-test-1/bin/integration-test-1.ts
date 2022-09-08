#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { IntegrationTest1Stack } from '../lib/integration-test-1-stack';

const app = new cdk.App();
new IntegrationTest1Stack(app, 'IntegrationTest1Stack', {
  stackName: 'IntegrationTest1Stack',
  env: {
    region: process.env.CDK_DEFAULT_REGION,
    account: process.env.CDK_DEFAULT_ACCOUNT,
  },
});