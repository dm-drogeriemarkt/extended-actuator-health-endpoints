:warning: | This repository is not being maintained anymore.
:---: | :---

# Extended Actuator Health Endpoints

[![Build Status](https://travis-ci.org/dm-drogeriemarkt/extended-actuator-health-endpoints.svg?branch=master)](https://travis-ci.org/dm-drogeriemarkt/extended-actuator-health-endpoints)

Extension of Spring Boot Actuator for different levels of HealthIndicators implementing the dm Healthcheck Standard.

## Additional Interfaces

### ApplicationAliveIndicator

Fast and cheap checks, used by load balancer to decide if application is alive, callable at least once per second.

### BasicHealthIndicator

Basic checks including all of Spring Boot's default checks, used by ops to track application health, callable at least once per minute.

### DetailHealthIndicator

Detailed, expensive checks, may take up to 30 seconds.

## Configuration

Beans implementing one of these Interfaces are automatically included in one of these health endpoint categories.
The health endpoints respect Spring Boot Actuator's `management.context-path` property.

ExtendedHealthEndpoint paths can be extended via properties:

* `extended.health.aliveId`
* `extended.health.basicId`
* `extended.health.detailId`

## License

Copyright (c) 2017 dm-drogerie markt GmbH & Co. KG, https://dm.de

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
