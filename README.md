# Environment Filtering Topology in Apache Storm
For the environment filtering data, we have used four bolts and one spout. The four bolts are used for processing different components of the tuple that the spout is emitting.
The spout emits tuples with four parameters. The parameters are:-
* PM-2.5
* PM-10
* Temperature
* Humidity

The code written for the bolts mainly took inspiration from the “WordCountBolt.java” present in the ****apache-storm-2.4.0****.
> #### Path = ***examples\storm-starter\src\jvm\org\apache\storm\starter\bolt\WordCountBolt.java***
The code written for the spout mainly took inspiration from the “RandomIntegerSpout.java” present in the ****apache-storm-2.4.0****.
> #### Path = ***examples\storm-starter\src\jvm\org\apache\storm\starter\spout\RandomIntegerSpout.java***
The code written for the topology used mainly took inspiration from the “StatefulTopology.java” present in the ****apache-storm-2.4.0****.
> #### Path = ***examples\storm-starter\src\jvm\org\apache\storm\starter\StatefulTopology.java***
The PM10 bolt compares the current tuple with the previous valid tuple. If the variation in the current tuple is **>30%**, then the current tuple is discarded. 
The parameters currently used in the topology are:-
* #### For PM 10 - 30% variation
* #### For PM 2.5 - 10% variation
* #### For Temperature - 5% variation
* #### For Humidity - 10% variation

If the variation is within the above-mentioned limit, we acknowledge the tuple, or else we will drop it.

## The Topology structure.

![strcture](https://github.com/atifquamar07/Environment-Filtering-Topology-in-Apache-Storm/assets/88545624/16d8e93f-4f0f-48f4-aff4-f603830f3f33)


## Summary of the topology being submitted to the storm cluster
![summary](https://github.com/atifquamar07/Environment-Filtering-Topology-in-Apache-Storm/assets/88545624/6f7700a2-528b-4613-a145-4b8edf52f234)


