#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from 'aws-cdk-lib';
import { IntegrationTest2Stack } from '../lib/integration-test-2-stack';

const app = new cdk.App();
new IntegrationTest2Stack(app, 'IntegrationTest2Stack', {
  stackName: 'IntegrationTest2Stack',
  env: {
    region: process.env.CDK_DEFAULT_REGION,
    account: process.env.CDK_DEFAULT_ACCOUNT,
  },
});