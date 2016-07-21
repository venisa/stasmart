# statsmart
A restful api to monitor performance metrics of machines

## Architecture
statsmart provides a Restful Api to collect performace metrics of machines. This functionality is broken down into two different modules.

- [Statsmart API](#statsmart-api)
- [Metricloader](#metricloader)

### Statsmart API ###
The Statsmart API has two end points. The 'summary' endpoint gives average of the performance metrics of all the hosts configured in the system. The 'hosts' endpoint gives information about all the hosts configured in the system. The hosts endpoint can also be used to get the average and latest performance metrics of a particular host in the system. Refer [API Guide](#api-guide) for usage information.

The Statsmart API is powered by a Jetty server and connects to a centralized MYSQL database to collect performance metrics.

### MetricLoader ###
The MetricLoader provides a service that runs every hour on the host whose performance needs to be monitored. It collects the CPU and Memory informations and stores it in the MYSQL database

## Configuration ##

### Set up Statsmart API ###
- Set up the centralized MYSQL database using [this](https://github.com/venisa/Statsmart/blob/master/src/main/scripts/query.sql) script
- Clone the Git repo for [Statsmart](https://github.com/venisa/statsmart)
- Update the [config file](https://github.com/venisa/Statsmart/blob/master/src/main/resources/config.properties) for the api in your local repo 
- Build the maven project 'mvn clean install'
- Copy statsmart-api-1.0-SNAPSHOT.jar from the target directory to the machine that will run the server for the api
- Start the Jetty server: java -jar statsmart-api-1.0-SNAPSHOT.jar

### Set up MetricLoader ###
For every machine whose performance needs to be monitored, do the following:

- Add the ip address and hostname of the host to the host table in MYSQL. Sample Query: INSERT INTO Statsmart.host (ip, name) VALUES ('192.168.0.13', 'host3');
- Clone the Git repo for Statsmart
- Update the [config file](https://github.com/venisa/MetricLoader/blob/master/src/main/resources/config.properties) in your local repo. 
- Build the maven project 'mvn clean install'
- Copy metricloader-1.0-SNAPSHOT.jar from the target directory to the machine whose performance needs to be monitored.
- Start the metricloader service: java -jar metricloader-1.0-SNAPSHOT.jar

### API Guide ###
- Get information about all hosts in the system : 
  - http://serverhostname:portnumber/hosts
  ```json
[
  {
    "ip": "192.148.0.11",
    "name": "host3"
  },
  {
    "ip": "192.186.0.12",
    "name": "host1"
  }
]
``` 
- Get average statistics of a particular host in the system  
  - http://serverhostname:portnumber/hosts/host1
  ```json
  {
  "statistics": [
    {
      "fields": {
        "cpu": "all",
        "per_usr": 5.06,
        "per_nice": 0,
        "per_sys": 0.23,
        "per_io_wait": 0.395
      },
      "host": "host1",
      "metric": "cpu"
    },
    {
      "fields": {
        "total": 125.4,
        "used": 13.6,
        "free": 13.33
      },
      "host": "host1",
      "metric": "memory"
    }
  ]
}
  ```
- Get latest statistics of a particular host in the system 
  - http://serverhostname:portnumber/hosts/latest
 
- Get a summary of all the hosts in the system:
  - http://serverhostname:portnumber/summary
  ```json
  {
  "statistics": [
    {
      "fields": {
        "cpu": "all",
        "per_usr": 5.0616666666666665,
        "per_nice": 0.005,
        "per_sys": 0.2366666666666667,
        "per_io_wait": 0.395
      },
      "host": "host1",
      "metric": "cpu"
    },
    {
      "fields": {
        "cpu": "all",
        "per_usr": 1.8,
        "per_nice": 0.004,
        "per_sys": 0.5359999999999999,
        "per_io_wait": 0.374
      },
      "host": "host2",
      "metric": "cpu"
    },
    {
      "fields": {
        "total": 1254,
        "used": 136,
        "free": 13.333333333333334
      },
      "host": "host1",
      "metric": "memory"
    },
    {
      "fields": {
        "total": 230.8,
        "used": 60.8,
        "free": 13.8
      },
      "host": "host2",
      "metric": "memory"
    }
  ]
}
  ```



